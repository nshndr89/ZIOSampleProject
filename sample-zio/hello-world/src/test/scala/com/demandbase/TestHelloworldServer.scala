package com.demandbase

import com.demandbase.ServiceModel.{FileIO}
import sttp.client3._
import sttp.client3.circe._
import sttp.client3.httpclient.zio.HttpClientZioBackend
import zio._
import zio.test._

object TestHelloworldServer extends ZIOSpecDefault {

  def spec = suite("Checking Hello World Server")(
    test("Testing Hello World server Pass with stub"){
      for{
        server <- HelloWorldServer.run.fork
        inputFromFile <- ZIO.service[FileIO]
        inputForApi <- inputFromFile.readFromFile("helloworld_input_success")
        outputForApi <- inputFromFile.readFromFile("helloworld_output_success")
        request = basicRequest.get(uri"http://localhost:8080/reverse/${inputForApi.head}").response(asJson[String])
        response <- HttpClientZioBackend().flatMap{backend => backend.send(request)}
      }yield {
        println(s"Response is ${response.body}")
        response.body match{
          case Right(v) => assert(v)(Assertion.equalTo(outputForApi.head))
          case Left(e) => {
            println(s"Request Failed: $e")
            assertTrue(false)
          }
        }
      }
    },
    test("Testing Hello World server Pass"){
      for{
        server <- HelloWorldServer.run.fork
        inputFromFile <- ZIO.service[FileIO]
        inputForApi <- inputFromFile.readFromFile("helloworld_input_success")
        outputForApi <- inputFromFile.readFromFile("helloworld_output_success")
        request = basicRequest.get(uri"http://localhost:8080/reverse/${inputForApi.head}").response(asJson[String])
        response <- HttpClientZioBackend().flatMap{backend => backend.send(request)}
      }yield {
        println(s"Response is ${response.body}")
        response.body match{
          case Right(v) => assert(v)(Assertion.equalTo(outputForApi.head))
          case Left(e) => {
            println(s"Request Failed: $e")
            assertTrue(false)
          }
        }
      }
    },
    test("Testing Hello world for pallindrome"){
      for{
        server <- HelloWorldServer.run.fork
        inputFromFile <- ZIO.service[FileIO]
        inputForApi <- inputFromFile.readFromFile("helloworld_pallindrome_input")
        outputForApi <- inputFromFile.readFromFile("helloworld_pallindrome_output")
        request = basicRequest.get(uri"http://localhost:8080/reverse/${inputForApi.head}").response(asJson[String])
        response <- HttpClientZioBackend().flatMap{backend => backend.send(request)}
      }yield {
        println(s"Response is ${response.body}")
        response.body match{
          case Right(v) => assertTrue(false)
          case Left(e) => {
            println(s"Request Failed: $e")
            assert(e.getMessage)(Assertion.equalTo(outputForApi.head))
          }
        }
      }
    }
  ).provide(FileIO.live)
}
