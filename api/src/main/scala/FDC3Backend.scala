import morphir.ir._
import morphir.ir.module._
import morphir.ir.module.Codec._

import io.circe.Json
import io.circe.parser.parse
import morphir.ir.Distribution.Distribution
import morphir.ir.Documented.Documented
import morphir.ir.formatversion.Codec.decodeVersionedDistribution
import morphir.ir.{AccessControlled, Name,Path, Type, Value}

import scala.io.Source
import scala.util.{Try, Using}

object FDC3Backend extends App {
    for {
        ir <- loadMorphirIR("morphir-ir.json")
    } yield processIR(ir)


    def loadMorphirIR(irPath : String): Try[Distribution] = {
        Using(Source.fromFile(irPath)) { source =>
            val json = source.mkString
            val parsedIR = parse(json).getOrElse(Json.Null).hcursor
            val distribution = decodeVersionedDistribution(parsedIR)

            distribution match {
                case Left(err) => throw new Exception(s"Error decoding distribution: $err")
                case Right(value) => value.distribution
            }
       }
    }

    def processIR(ir: Distribution): Unit = {
        ir match { 
            case Distribution.Library(_, _, packageDef) =>
                packageDef.modules.foreach (println)

                // packageDef.modules.toList.foreach { (moduleName, module) =>

                    // module.value.declarations.foreach { decl =>
                    //     decl match {
                    //         case Documented(name, path, value: Value) => 
                    //             println(s"Value: $name")
                    //         case Documented(name, path, t: Type) => 
                    //             println(s"Type: $name")
                    //         case Documented(name, path, ac: AccessControlled) => 
                    //             println(s"AccessControlled: $name")
                    //         case Documented(name, path, _) => 
                    //             println(s"Unknown: $name")
                    //     }
                    // }
                // }
        }
    }
}
