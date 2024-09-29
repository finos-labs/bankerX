package bankerx.api.fdc3

import bankerx.API.*
import bankerx.api.*
import bankerx.api.fdc3.*
import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker

trait Fdc3Codecs extends MorphirCodecs:
  given GetTermsRequestPayloadJsonValueCodec
      : JsonValueCodec[GetTermsRequestPayload] = JsonCodecMaker.make
  given GetTermsResponsePayloadJsonValueCodec
      : JsonValueCodec[GetTermsResponsePayload] = JsonCodecMaker.make
  given GetTermsIntentJsonValueCodec: JsonValueCodec[GetTermsIntent] =
    JsonCodecMaker.make

object Fdc3Codecs extends Fdc3Codecs
