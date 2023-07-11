package amaker

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.concurrent.ThreadLocalRandom

class AmakerSimulation extends Simulation {

  val search =
    exec(
      http("Home")
        .get("/getalbum?share_id=63ac278f3d1fa2f955554406")
    )
    .pause(1)
    
  val httpProtocol =
    http.baseUrl("https://data.mongodb-api.com/app/amaker-hejmf/endpoint")
      
  val users = scenario("Users").exec(search)

  setUp(
    users.inject(rampUsers(10).during(10))
  ).protocols(httpProtocol)
}
