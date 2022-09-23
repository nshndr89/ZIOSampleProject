
import io.circe.generic.auto._
import sttp.client3._
import sttp.client3.circe._
import sttp.client3.httpclient.zio.HttpClientZioBackend
import zio._
import zio.test._

object HelloworldServerTest extends ZIOSpecDefault{

  def spec = test("Testing Hello World server"){
    println("Inside Test}")
    val request = basicRequest
      .get(uri"http://localhost:8080/reverse/mad")
      .response(asJson[String])
    for{
      response <- HttpClientZioBackend().flatMap{backend => backend.send(request)}
    }yield{
      println(s"Response is ${response.body}")
      response.body match{
        case Right(v) => assert(v)(Assertion.equalTo("aam"))
        case Left(e) => {
          println(s"Request Failed: $e")
          assertTrue(false)
        }
      }
    }
  }
  }
