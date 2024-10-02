package ft

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.concurrent.ThreadLocalRandom

class FTOffreSimulation extends Simulation {

  val token = "token"

  val search =
    exec(
      http("Home")
        .get("/search")
        .header("Authorization", s"Bearer ${token}")
    )
    
  val httpProtocol =
    http.baseUrl("https://api.francetravail.io/partenaire/offresdemploi/v2/offres/")
      
  val users = scenario("Users").exec(search)

  setUp(
    //users.inject(rampUsers(10).during(1)) // rampUsers : Injects a given number of users distributed evenly on a time window of a given duration.
    users.inject(constantConcurrentUsers(11).during(30)) // Inject so that number of concurrent users in the system is constant
  ).protocols(httpProtocol)
}
