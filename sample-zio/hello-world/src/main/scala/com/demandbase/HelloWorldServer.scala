package com.demandbase



import io.circe.generic.auto._
//import sttp.tapir.PublicEndpoint
//import sttp.tapir.generic.auto._
//import sttp.tapir.json.circe._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
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

  val reverseStringEndpoint: PublicEndpoint[String,Throwable,String,Any] =
    endpoint.get.in("reverse"/path[String]("input")).errorOut(jsonBody[Throwable]).out(stringBody)

  val reverseStringHttp: Http[Any, Throwable, Request, Response] =
    ZioHttpInterpreter().toHttp(reverseStringEndpoint.zServerLogic(reverseString))

  override def run =
    Server.start(8080, reverseStringHttp).exitCode
}