package bankerx.api.fdc3
import bankerx.API.*
import bankerx.api.*
import neotype.*
import neotype.interop.jsoniter.{given, *}
import neotype.interop.tapir.{given, *}
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker.*

type IntentName = IntentName.Type
object IntentName extends Subtype[String]:
  inline given jsonValueCodec: JsonValueCodec[IntentName] =
    subtypeCodec[String, IntentName]

type Source = Source.Type
object Source extends Subtype[String]:
  inline given jsonValueCodec: JsonValueCodec[Source] =
    subtypeCodec[String, Source]

type Destination = Destination.Type
object Destination extends Subtype[String]:
  inline given jsonValueCodec: JsonValueCodec[Destination] =
    subtypeCodec[String, Destination]

type PayloadType = PayloadType.Type
object PayloadType extends Subtype[String]:
  given jsonValueCodec: JsonValueCodec[PayloadType] =
    subtypeCodec[String, PayloadType]

trait Fdc3Intent[+Data] extends Product with Serializable:
  def intent: IntentName
  def source: Source
  def destination: Destination
  // def context: Fdc3Payload[Data]

trait Fdc3Payload[+Data] extends Product with Serializable:
  def type_ : PayloadType
  def data: Data

final case class GetTermsIntent(
    intent: IntentName,
    source: Source,
    destination: Destination,
    context: GetTermsRequestPayload
) extends Fdc3Intent[Purchase]
object GetTermsIntent:
  given jsonValueCodec: JsonValueCodec[GetTermsIntent] =
    JsonCodecMaker.make

final case class GetTermsResponsePayload(
    type_ : PayloadType,
    data: Terms
) extends Fdc3Payload[Terms]
object GetTermsResponsePayload:
  given jsonValueCodec: JsonValueCodec[GetTermsResponsePayload] =
    JsonCodecMaker.make

final case class GetTermsRequestPayload(
    type_ : PayloadType,
    data: Purchase
) extends Fdc3Payload[Purchase]

object GetTermsRequestPayload:
  given jsonValueCodec: JsonValueCodec[GetTermsRequestPayload] =
    JsonCodecMaker.make
