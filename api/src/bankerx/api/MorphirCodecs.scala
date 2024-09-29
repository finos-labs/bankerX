package bankerx.api

import bankerx.API.*
import com.github.plokhotnyuk.jsoniter_scala.core.{given, *}
import com.github.plokhotnyuk.jsoniter_scala.macros.{given, *}

trait MorphirCodecs:
  given categoryJsonValueCodec: JsonValueCodec[Category] =
    JsonCodecMaker.makeWithoutDiscriminator
  given purchaseJsonValueCodec: JsonValueCodec[Purchase] = JsonCodecMaker.make
  given termsJsonValueCodec: JsonValueCodec[Terms] = JsonCodecMaker.make
