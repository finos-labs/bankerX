package bankerx.serverless
import sttp.model.{Header, MediaType}
import sttp.tapir.*
import sttp.tapir.serverless.aws.lambda.{given, *}
import sttp.tapir.serverless.aws.ziolambda.{given, *}
import sttp.tapir.ztapir.ZTapir
import java.io.{InputStream, OutputStream}
import zio.{given, *}
import sttp.tapir.json.circe.{given, *}
import sttp.tapir.EndpointIO.annotations.jsonbody

object endpoints extends ZTapir:
  type ZioEndpoint = ZServerEndpoint[Any, Any]
  val hello: ZioEndpoint =
    endpoint.get
      .in("api" / "hello")
      .out(jsonBody[String])
      .zServerLogic(_ => ZIO.attempt("Hello from ZIO!").mapError(_ => ()))

  val allEndpoints: Set[ZioEndpoint] = Set(hello)
