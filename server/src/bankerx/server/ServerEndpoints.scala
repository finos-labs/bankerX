package bankerx.server
import sttp.tapir.*
import sttp.shared.Identity
import sttp.tapir.generic.auto.*
import sttp.tapir.json.jsoniter.*
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import bankerx.*
import bankerx.api.*
import bankerx.api.fdc3.Fdc3Service

// The service that will run against a Netty-based server
object ServerEndpoints:
    val getTermsServerEndpoint = 
        PublicEndpoints.getTermsEndpoint.handle{
            case (bankName, purchase) => SmartWallet.getTerms(bankName)(purchase).toRight(s"Terms unavailable for bank: $bankName")
        }

    val apiEndpoints = List(getTermsServerEndpoint) ++ fdc3.apiEndpoints
    val docEndpoints: List[ServerEndpoint[Any, Identity]] = SwaggerInterpreter()
        .fromServerEndpoints[Identity](apiEndpoints, "bankerx", "1.0.0")
    val all = apiEndpoints ++ docEndpoints

    object fdc3:
        val getTermsServerEndpoint = 
            PublicEndpoints.fdc3.getTermsEndpoint.handle{
                case (bankName, getTermsIntent) => Fdc3Service.Live.getTerms(bankName, getTermsIntent)
            }

        val makePurchaseServerEndpoint =
            PublicEndpoints.fdc3.makePurchaseEndpoint.handle{
                case (bankName, makePurchaseIntent) => Fdc3Service.Live.makePurchase(bankName, makePurchaseIntent)
            }

        val apiEndpoints = List(getTermsServerEndpoint, makePurchaseServerEndpoint)
