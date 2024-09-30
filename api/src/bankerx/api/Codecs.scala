package bankerx.api
import bankerx.API.*
import bankerx.api.fdc3.*
import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker

trait Codecs extends MorphirCodecs

object Codecs extends Codecs

trait Schemas extends MorphirSchemas
object Schemas extends Schemas
