package com.demandbase

import com.demandbase.ServiceModel.{MyIOService, SampleIOModifyService, StringOperationService}
import sttp.tapir.PublicEndpoint
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.http.HttpApp
import zhttp.service.Server
import zio.{Task, ZIO, ZIOAppDefault}

object HelloWorldServer extends ZIOAppDefault {

  val sampleIOModifyService_v3 = for{
    sms <- ZIO.service[SampleIOModifyService]
  }yield sms

  def reverseString(input: String) ={
    for{
      sms <- sampleIOModifyService_v3
      output <- sms.getInputAndPrintInReverse(input)
    }yield output
  }

  val reverseStringEndpoint: PublicEndpoint[String,String,String,Any] =
    endpoint.get.in("reverse"/path[String]("userInput")).errorOut(stringBody).out(stringBody)

  val reverseStringHttp =
    ZioHttpInterpreter().toHttp(reverseStringEndpoint.zServerLogic(userInput=>reverseString(userInput)))

  override def run =
    Server.start(8080, reverseStringHttp).provide(SampleIOModifyService.live,
      MyIOService.live,
      StringOperationService.live).exitCode
}