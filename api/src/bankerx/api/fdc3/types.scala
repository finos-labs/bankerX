package bankerx.api.fdc3
import bankerx.API.*
import bankerx.api.*
import bankerx.api.Codecs.{given, *}
import bankerx.api.Schemas.{given, *}
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import neotype.*
import neotype.interop.jsoniter.{given, *}
import neotype.interop.tapir.{given, *}
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker.*

type IntentName = IntentName.Type
object IntentName extends Subtype[String]:
  val purchase = IntentName("fdc3.purchase")
  def makeOption(input: String): Option[IntentName] = make(input).toOption

  given tapirSchema: Schema[IntentName] =
    Schema.string.map[IntentName](makeOption)(_.value)
  given jsonValueCodec: JsonValueCodec[IntentName] =
    subtypeCodec[String, IntentName]
  extension (intentName: IntentName) def value: String = intentName

type Source = Source.Type
object Source extends Subtype[String]:
  def makeOption(input: String): Option[Source] = make(input).toOption
  given tapirSchema: Schema[Source] =
    Schema.string.map[Source](makeOption)(_.value)
  given jsonValueCodec: JsonValueCodec[Source] =
    subtypeCodec[String, Source]
  extension (source: Source) def value: String = source

type Target = Target.Type
object Target extends Subtype[String]:
  def makeOption(input: String): Option[Target] = make(input).toOption
  given tapirSchema: Schema[Target] =
    Schema.string.map[Target](makeOption)(_.value)
  given jsonValueCodec: JsonValueCodec[Target] =
    subtypeCodec[String, Target]
  extension (destination: Target) def value: String = destination

type PayloadType = PayloadType.Type
object PayloadType extends Subtype[String]:
  val Fdc3Terms = PayloadType("fdc3.terms")

  def makeOption(input: String): Option[PayloadType] = make(input).toOption
  given tapirSchema: Schema[PayloadType] =
    Schema.string.map[PayloadType](makeOption)(_.value)
  given jsonValueCodec: JsonValueCodec[PayloadType] =
    subtypeCodec[String, PayloadType]
  extension (payloadType: PayloadType) def value: String = payloadType

trait Fdc3Intent[+Data] extends Product with Serializable:
  def intent: IntentName
  def source: Source
  def target: Target
  def context: Fdc3Payload[Data]

trait Fdc3Payload[+Data] extends Product with Serializable:
  def `type`: PayloadType
  def data: Data

final case class GetTermsIntent(
    intent: IntentName,
    source: Source,
    target: Target,
    context: GetTermsRequestPayload
) extends Fdc3Intent[Purchase]
object GetTermsIntent:
  given tapirSchema: Schema[GetTermsIntent] = Schema.derived
  given jsonValueCodec: JsonValueCodec[GetTermsIntent] =
    JsonCodecMaker.make

final case class GetTermsResponsePayload(
    `type`: PayloadType,
    data: Terms
) extends Fdc3Payload[Terms]
object GetTermsResponsePayload:
  given tapirSchema: Schema[GetTermsResponsePayload] = Schema.derived
  given jsonValueCodec: JsonValueCodec[GetTermsResponsePayload] =
    JsonCodecMaker.make

final case class GetTermsRequestPayload(
    `type`: PayloadType,
    data: Purchase
) extends Fdc3Payload[Purchase]

object GetTermsRequestPayload:
  given tapirSchema: Schema[GetTermsRequestPayload] = Schema.derived
  given jsonValueCodec: JsonValueCodec[GetTermsRequestPayload] =
    JsonCodecMaker.make
