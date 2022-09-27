package com.demandbase

import com.demandbase.ServiceModel.{FileIO, MyIOService, SampleIOModifyService, StringOperationService}
import zio._
object SampleIOModify extends ZIOAppDefault {

//    val sampleIOModifyService = new SampleIOModifyService(
//       new MyIOService(),new StringOperationService()
//     )
//
//    val sampleIOModifyService_v2 = ZIO.succeed(
//      SampleIOModifyService.create(
//        MyIOService.create(),StringOperationService.create()
//      )
//    )

    val sampleIOModifyService_v3 = for{
      sms <- ZIO.service[SampleIOModifyService]
    }yield sms

   val program = {
     for{
      sms <- sampleIOModifyService_v3
      _ <- sms.getInputAndPrintInReverse
     }yield ()
   }

  val readFromFile = for{
    inputFromFile <- ZIO.service[FileIO]
    inputForApi <- inputFromFile.readFromFile("/Users/nthakur/Desktop/helloworld_input_success")
  }yield(println(inputForApi.head))

//  def run = program.provide(
//    SampleIOModifyService.live,
//    MyIOService.live,
//    StringOperationService.live
//  )

  def run = readFromFile.provide(
    FileIO.live
  )
}
