package bankerx.api
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.jsoniter.*

import sttp.shared.Identity
import sttp.tapir.server.ServerEndpoint
import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker
import bankerx.API.*
import bankerx.api.Codecs.{given, *}

object PublicEndpoints:
  val getTermsEndpoint: PublicEndpoint[(BankName, Purchase), Unit, Terms, Any] =
    endpoint.post
      .in("bank" / path[BankName]("bankName") / "terms")
      .in(jsonBody[Purchase])
      .out(jsonBody[Terms])
