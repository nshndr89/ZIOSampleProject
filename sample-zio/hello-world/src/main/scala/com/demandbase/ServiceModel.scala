package com.demandbase

import scala.io.StdIn
import zio.*

object ServiceModel {

  class MyIOService{

    def input: Task[String] = {
      ZIO.attempt{
        println("Please Enter Some Value:")
        StdIn.readLine()
      }
    }
  }
  object MyIOService{
    def create(): MyIOService = new MyIOService
    val live = ZLayer.succeed(create())
  }

   class StringOperationService{
    def reverse(input: String): Task[String] = {
      ZIO.attempt{
        input.reverse
      }
    }
  }

   object StringOperationService{
     def create() = new StringOperationService
     val live = ZLayer.succeed(create())
   }

  class SampleIOModifyService(myIo: MyIOService, ops: StringOperationService){
    def getInputAndPrintInReverse: Task[String] = {
      for{
        in <- myIo.input
        rev <- ops.reverse(in)
        _ <- ZIO.succeed(println(s"Reversed value: $rev"))
      } yield rev
    }
  }

  object SampleIOModifyService{
    def create(myIo: MyIOService, ops: StringOperationService) = new SampleIOModifyService(myIo, ops)
    val live = ZLayer.fromFunction(create _)
  }
}
