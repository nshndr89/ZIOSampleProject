package com.demandbase

import com.demandbase.ServiceModel.{MyIOService, SampleIOModifyService, StringOperationService}
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

  def run = program.provide(
    SampleIOModifyService.live,
    MyIOService.live,
    StringOperationService.live
  )
}
