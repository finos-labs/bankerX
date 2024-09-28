package bankerx.server
import sttp.tapir.*
import sttp.shared.Identity
import sttp.tapir.generic.auto.*
import sttp.tapir.json.jsoniter.*
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import bankerx.*
import bankerx.api.*


object Endpoints:
    val getTermsServerEndpoint = 
        PublicEndpoints.getTermsEndpoint.handleSuccess(FirstBank.getTerms)

    val apiEndpoints = List(getTermsServerEndpoint)
    val docEndpoints: List[ServerEndpoint[Any, Identity]] = SwaggerInterpreter()
        .fromServerEndpoints[Identity](apiEndpoints, "bankerx", "1.0.0")
    val all = apiEndpoints ++ docEndpoints
