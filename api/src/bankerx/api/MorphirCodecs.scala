package bankerx.api

import bankerx.API.*
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import com.github.plokhotnyuk.jsoniter_scala.core.{given, *}
import com.github.plokhotnyuk.jsoniter_scala.macros.{given, *}

/**
 * Creates codecs for types that the user defined in Morphir. These can be found under [[api/src-elm]].
 */
trait MorphirCodecs:
  given categoryJsonValueCodec: JsonValueCodec[Category] =
    JsonCodecMaker.makeWithoutDiscriminator
  given purchaseJsonValueCodec: JsonValueCodec[Purchase] = JsonCodecMaker.make
  given termsJsonValueCodec: JsonValueCodec[Terms] = JsonCodecMaker.make

object MorphirCodecs extends MorphirCodecs

trait MorphirSchemas:
  given categorySchema: Schema[Category] = Schema.derived
  given purchaseSchema: Schema[Purchase] = Schema.derived
  given termsSchema: Schema[Terms] = Schema.derived

object MorphirSchemas extends MorphirSchemas
