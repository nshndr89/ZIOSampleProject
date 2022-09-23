package com.demandbase

import scala.io.StdIn
import zio._

object ServiceModel {

  class MyIOService{

    def input: ZIO[Any,Throwable,String] = {
      ZIO.succeed{
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
    def reverse(input: String): ZIO[Any,Throwable,String] = {
      ZIO.succeed{
        input.reverse
      }
    }
  }

   object StringOperationService{
     def create() = new StringOperationService
     val live = ZLayer.succeed(create())
   }

  class SampleIOModifyService(myIo: MyIOService, ops: StringOperationService){
    def getInputAndPrintInReverse: ZIO[Any,Throwable,String] = {
      for{
        in <- myIo.input
        rev <- ops.reverse(in)
        _ <- ZIO.succeed(rev)
      } yield rev
    }

    def getInputAndPrintInReverse(input: String): ZIO[Any,Throwable,String] = {
      for{
        rev <- ops.reverse(input)
        result <- ZIO.succeed(rev)
      } yield result
    }
  }

  object SampleIOModifyService{
    def create(myIo: MyIOService, ops: StringOperationService) = new SampleIOModifyService(myIo, ops)
    val live = ZLayer.fromFunction(create _)
  }
}
