package com.demandbase

import com.demandbase.ServiceModel.{MyIOService, SampleIOModifyService, StringOperationService}
import sttp.tapir.PublicEndpoint
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.http.HttpApp
import zhttp.service.Server
import zio.{Task, ZIO, ZIOAppDefault}
import sttp.tapir.json.circe._
import sttp.tapir.generic.auto._
import io.circe.generic.auto._

object HelloWorldServer extends ZIOAppDefault {

  case class Error(message:String,code:Int) extends Throwable
  val sampleIOModifyService_v3 = for{
    sms <- ZIO.service[SampleIOModifyService]
  }yield sms

  def reverseString(input: String) ={
    for{
      sms <- sampleIOModifyService_v3
      output <- sms.getInputAndPrintInReverse(input)
    }yield output
  }

  val reverseStringEndpoint =
    endpoint.get.in("reverse"/path[String]("userInput")).errorOut(jsonBody[Error]).out(jsonBody[String])

  val reverseStringHttp =
    ZioHttpInterpreter().toHttp(reverseStringEndpoint.zServerLogic(
      userInput=>{
          reverseString(userInput).foldZIO(
            ex => ZIO.fail(Error("Something bad happened",400)),
            value => {
              if(value.equals(userInput))
                ZIO.fail(Error("Pallindrome! no point in reversing",400))
              else
                ZIO.succeed(value)
            }
          )
      }

    )
    )

  override def run =
    Server.start(8080, reverseStringHttp).provide(SampleIOModifyService.live,
      MyIOService.live,
      StringOperationService.live).exitCode
}