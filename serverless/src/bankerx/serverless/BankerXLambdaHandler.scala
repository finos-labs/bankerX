package bankerx.serverless

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.circe.generic.auto._
import sttp.tapir.*
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.serverless.aws.lambda.*
import sttp.tapir.serverless.aws.ziolambda.*
import sttp.tapir.ztapir.RIOMonadError
import java.io.{InputStream, OutputStream}
import zio.*

object BankerXLambdaHandler extends RequestStreamHandler:
  private given RIOMonadError[Any] = new RIOMonadError[Any]
  private val handler =
    ZioLambdaHandler.default[Any](endpoints.allEndpoints.toList)

  override def handleRequest(
      input: InputStream,
      output: OutputStream,
      context: Context
  ): Unit =
    val runtime = Runtime.default
    Unsafe.unsafe { implicit unsafe =>
      runtime.unsafe
        .run(handler.process[AwsRequestV1](input, output))
        .getOrThrowFiberFailure()

    }
