package bankerx.api.fdc3
import bankerx.API.*
import bankerx.api.*

opaque type IntentName <: String = String
opaque type Source <: String = String
opaque type Destination <: String = String
opaque type PayloadType <: String = String

trait Fdc3Intent[+Data] extends Product with Serializable:
  def intent: IntentName
  def source: Source
  def destination: Destination
  def context: Fdc3Payload[Data]

trait Fdc3Payload[+Data] extends Product with Serializable:
  def `type`: PayloadType
  def data: Data

final case class GetTermsIntent(
    intent: IntentName,
    source: Source,
    destination: Destination,
    context: GetTermsRequestPayload
) extends Fdc3Intent[Purchase]

final case class GetTermsResponsePayload(
    `type`: PayloadType,
    data: Terms
) extends Fdc3Payload[Terms]

final case class GetTermsRequestPayload(
    `type`: PayloadType,
    data: Purchase
) extends Fdc3Payload[Purchase]
