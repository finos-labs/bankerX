package bankerx.api
import org.scalatest.* 
import wordspec.* 
import matchers.*
import bankerx.API.*
import bankerx.api.fdc3
import bankerx.banks
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
                    banks.CapitalOne.provider,
                    100,
                    0.1,
                    12
                )
                val json = writeToString(terms)
                println(json)
                val decodedTerms = readFromString[Terms](json)
                decodedTerms.shouldEqual(terms)
            }
        }

        "given a GetTermsIntent" should {
            "be able to encode and decode it" in {
                val getTermsIntent = fdc3.GetTermsIntent(
                    fdc3.IntentName.purchase,
                    fdc3.Source("source1"),
                    fdc3.Target("destination1"),
                    fdc3.GetTermsRequestPayload(
                        fdc3.PayloadType("payloadType1"),
                        Purchase(
                            100,
                            "Vender1",
                            java.time.LocalDate.now(),
                            java.time.LocalTime.now(),
                            "user 1",
                            Fuel,
                            "pointOfSale 1"
                        )
                    )
                )
                val json = writeToString(getTermsIntent)
                println(json)
                val decodedGetTermsIntent = readFromString[fdc3.GetTermsIntent](json)                
                assert(decodedGetTermsIntent == getTermsIntent)
            }
        }

        "given a GetTermsResponsePayload" should {
            "be able to encode and decode it" in {
                val getTermsResponsePayload = fdc3.GetTermsResponsePayload(
                    fdc3.PayloadType("payloadType1"),
                    Terms(
                        banks.Klarna.provider,
                        100,
                        0.1,
                        12
                    )
                )
                val json = writeToString(getTermsResponsePayload)
                println(json)
                val decodedGetTermsResponsePayload = readFromString[fdc3.GetTermsResponsePayload](json)
                assert(decodedGetTermsResponsePayload == getTermsResponsePayload)
            }
        }
    }    
