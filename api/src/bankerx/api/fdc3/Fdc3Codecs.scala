package bankerx.api.fdc3

import bankerx.API.*
import bankerx.api.*
import bankerx.api.fdc3.*
import com.github.plokhotnyuk.jsoniter_scala.core.{given, *}
import com.github.plokhotnyuk.jsoniter_scala.macros.{given, *}

trait Fdc3Codecs extends MorphirCodecs:
  given PayloadTypeJsonValueCodec: JsonValueCodec[PayloadType] =
    JsonCodecMaker.make
  given GetTermsRequestPayloadJsonValueCodec
      : JsonValueCodec[GetTermsRequestPayload] = JsonCodecMaker.make
  given GetTermsResponsePayloadJsonValueCodec
      : JsonValueCodec[GetTermsResponsePayload] = JsonCodecMaker.make
  given GetTermsIntentJsonValueCodec: JsonValueCodec[GetTermsIntent] =
    JsonCodecMaker.make

object Fdc3Codecs extends Fdc3Codecs
