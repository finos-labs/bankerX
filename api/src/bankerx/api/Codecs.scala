package bankerx.api
import bankerx.API.*
import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker
object Codecs:
  given purchaseJsonValueCodec: JsonValueCodec[Purchase] = JsonCodecMaker.make
  given termsJsonValueCodec: JsonValueCodec[Terms] = JsonCodecMaker.make