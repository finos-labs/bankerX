package bankerx.api
import sttp.tapir.*
import sttp.tapir.generic.auto.{given, *}
import sttp.tapir.json.jsoniter.{given, *}
import neotype.interop.jsoniter.{given, *}
import neotype.interop.tapir.{given, *}
import sttp.shared.Identity
import sttp.tapir.server.ServerEndpoint
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker.*
import bankerx.API.*
import bankerx.api.fdc3.*
import bankerx.api.Codecs.{given, *}

object PublicEndpoints:
  val getTermsEndpoint
      : PublicEndpoint[(BankName, Purchase), String, Terms, Any] =
    endpoint.post
      .in("api" / "bank" / path[BankID]("bank") / "terms")
      .in(jsonBody[Purchase])
      .out(jsonBody[Terms])
      .errorOut(stringBody)

  object fdc3:
    import bankerx.api.fdc3.*
    val getTermsEndpoint: PublicEndpoint[
      (BankName, GetTermsIntent),
      String,
      GetTermsResponsePayload,
      Any
    ] =
      endpoint.post
        .in(
          "api" / "fdc3" / "bank" / path[BankID](
            "bank"
          ) / "intents" / "get-terms"
        )
        .in(jsonBody[GetTermsIntent])
        .out(jsonBody[GetTermsResponsePayload])
        .errorOut(stringBody)

      // val makePurchaseEndpoint: PublicEndpoint[
      //   (BankName, GetTermsIntent),
      //   String,
      //   GetTermsResponsePayload,
      //   Any
      // ] =
      //   endpoint.post
      //     .in(
      //       "api" / "fdc3" / "bank" / path[BankID](
      //         "bank"
      //       ) / "intents" / "make-purchase"
      //     )
      //     .in(jsonBody[MakePurchaseIntent])
      //     .out(jsonBody[MakePurchaseResponsePayload])
      //     .errorOut(stringBody)
