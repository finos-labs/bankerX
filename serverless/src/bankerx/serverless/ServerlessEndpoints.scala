package bankerx.serverless
import sttp.model.{Header, MediaType}
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.jsoniter.*
import sttp.tapir.serverless.aws.lambda.{given, *}
import sttp.tapir.serverless.aws.ziolambda.{given, *}
import sttp.tapir.ztapir.ZTapir
import java.io.{InputStream, OutputStream}
import zio.{given, *}
import sttp.tapir.json.circe.{given, *}
import sttp.tapir.EndpointIO.annotations.jsonbody
import bankerx.*
import bankerx.api.*
import bankerx.api.fdc3.*
object ServerlessEndpoints extends ZTapir:
  type ZioEndpoint = ZServerEndpoint[Any, Any]
  val getTermsServerEndpoint: ZioEndpoint =
    PublicEndpoints.getTermsEndpoint
      .zServerLogic { case (bankName, purchase) =>
        val result = SmartWallet
          .getTerms(bankName)(purchase)
          .toRight(s"Terms unavailable for bank: $bankName")
        ZIO.fromEither(result)
      }

  val apiEndpoints: Set[ZioEndpoint] =
    Set(getTermsServerEndpoint) ++ fdc3.apiEndpoints

  val allEndpoints: Set[ZioEndpoint] = apiEndpoints

  object fdc3:
    val getTermsServerEndpoint: ZioEndpoint =
      PublicEndpoints.fdc3.getTermsEndpoint
        .zServerLogic { case (bankName, getTermsIntent) =>
          val result = Fdc3Service.Live.getTerms(bankName, getTermsIntent)
          ZIO.fromEither(result)
        }

    val makePurchaseServerEndpoint: ZioEndpoint =
      PublicEndpoints.fdc3.makePurchaseEndpoint
        .zServerLogic { case (bankName, makePurchaseIntent) =>
          val result =
            Fdc3Service.Live.makePurchase(bankName, makePurchaseIntent)
          ZIO.fromEither(result)
        }

    val apiEndpoints: Set[ZioEndpoint] =
      Set(getTermsServerEndpoint, makePurchaseServerEndpoint)
    val allEndpoints: Set[ZioEndpoint] = apiEndpoints
