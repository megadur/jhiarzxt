import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Muster16Ver entity.
 */
class Muster16VerGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Muster16Ver entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all muster16Vers")
            .get("/api/muster-16-vers")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new muster16Ver")
            .post("/api/muster-16-vers")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "aUnfall":"SAMPLE_TEXT"
                , "abDatum":"SAMPLE_TEXT"
                , "vrsNr":"SAMPLE_TEXT"
                , "vrtrgsArztNr":"SAMPLE_TEXT"
                , "sprStBedarf":"SAMPLE_TEXT"
                , "unfall":"SAMPLE_TEXT"
                , "unfallTag":"SAMPLE_TEXT"
                , "vGeb":"SAMPLE_TEXT"
                , "vStat":"SAMPLE_TEXT"
                , "verDat":"SAMPLE_TEXT"
                , "kName":"SAMPLE_TEXT"
                , "kkIk":"SAMPLE_TEXT"
                , "laNr":"SAMPLE_TEXT"
                , "noctu":"SAMPLE_TEXT"
                , "hilf":"SAMPLE_TEXT"
                , "impf":"SAMPLE_TEXT"
                , "kArt":"SAMPLE_TEXT"
                , "rTyp":"SAMPLE_TEXT"
                , "rezeptTyp":"SAMPLE_TEXT"
                , "bgrPfl":"SAMPLE_TEXT"
                , "bvg":"SAMPLE_TEXT"
                , "eigBet":"SAMPLE_TEXT"
                , "gebFrei":"SAMPLE_TEXT"
                , "sonstige":"SAMPLE_TEXT"
                , "vkGueltigBis":"SAMPLE_TEXT"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_muster16Ver_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created muster16Ver")
                .get("${new_muster16Ver_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created muster16Ver")
            .delete("${new_muster16Ver_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
