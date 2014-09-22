package com.example

import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Await, Future}
import com.twitter.finagle.http.Response
import java.net.InetSocketAddress
import org.jboss.netty.handler.codec.http._
import util.Properties
import java.net.URI

import scala.collection.JavaConversions._
import com.stripe.model.Charge
import com.stripe.exception.StripeException
import com.stripe.Stripe

object Server {
  def main(args: Array[String]) {
    val port = Properties.envOrElse("PORT", "8080").toInt
    println("Starting on port: "+port)

    val server = Http.serve(":" + port, new Hello)
    Await.ready(server)
  }
}

class Hello extends Service[HttpRequest, HttpResponse] {
  def apply(request: HttpRequest): Future[HttpResponse] = {
    val response = Response()

    Stripe.apiKey = System.getenv("STRIPE_API_KEY")

    val cardParams = mapAsJavaMap(Map(
      "number" -> "4242424242424242",
      "exp_month" -> 12,
      "exp_year"-> 2020
    )).asInstanceOf[java.util.Map[java.lang.String, java.lang.Object]]

    val chargeParams = mapAsJavaMap(Map(
      "amount" -> 100, // 1 dollar
      "currency" -> "usd",
      "description" -> "Stripe debug test",
      "card" -> cardParams
    )).asInstanceOf[java.util.Map[java.lang.String, java.lang.Object]]

    try {
      val charge = Charge.create(chargeParams)
      println("Successfully processed test charge: " + charge)
      response.setContentString("Done!")
    } catch {
      case se: StripeException => {
        println("Failed to process test charge")
        println(se.printStackTrace.toString)
        response.setContentString("Failed!")
      }
    }

    Future(response)
  }
}
