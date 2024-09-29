package bankerx.api
import org.scalatest.* 
import wordspec.* 
import matchers.*
import bankerx.API.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import com.github.plokhotnyuk.jsoniter_scala.core.*

class CodecsSpec extends AnyWordSpec with should.Matchers with Codecs:
    "Codecs" when {
        "given a Purchase" should {
            "be able to encode and decode it" in {
                val purchase = Purchase(
                    100,
                    "Vender1",
                    java.time.LocalDate.now(),
                    java.time.LocalTime.now(),
                    "user 1",
                    Fuel,
                    "pointOfSale 1"
                )
                val json = writeToString(purchase)
                println(json)
                val decodedPurchase = readFromString[Purchase](json)
                decodedPurchase.shouldEqual(purchase)
            }
        }

        "given a Terms" should {
            "be able to encode and decode it" in {
                val terms = Terms(
                    "Provider1",
                    100,
                    0.1,
                    12
                )
                val json = writeToString(terms)
                val decodedTerms = readFromString[Terms](json)
                decodedTerms.shouldEqual(terms)
            }
        }
    }    
