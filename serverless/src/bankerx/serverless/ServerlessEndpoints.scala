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

  val allEndpoints: Set[ZioEndpoint] = Set(getTermsServerEndpoint)
