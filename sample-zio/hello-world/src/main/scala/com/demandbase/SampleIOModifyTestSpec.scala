package com.demandbase

import com.demandbase.ServiceModel.{MyIOService, SampleIOModifyService, StringOperationService}
import zio.*
import zio.test.*
object SampleIOModifyTestSpec extends ZIOSpecDefault {

  val mockedMyIOService = ZIO.succeed(new MyIOService{
    override def input: Task[String] = {
      ZIO.succeed(
        "nishendra"
      )
    }
  })

  def spec = test("Testing SampleIOModifyService") {


    val revInput = for {
      sampleIOModifyService <- ZIO.service[SampleIOModifyService]
      result <- sampleIOModifyService.getInputAndPrintInReverse
    } yield result

    assertZIO(revInput)(Assertion.equalTo("ardnehsin"))
  }.provide(SampleIOModifyService.live,
    StringOperationService.live,
    ZLayer.fromZIO(mockedMyIOService)
  )
}
