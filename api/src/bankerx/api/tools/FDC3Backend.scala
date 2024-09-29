package bankerx.api.tools

import morphir.ir._
import morphir.ir.module._
import morphir.ir.module.Codec._

import io.circe.Json
import io.circe.parser.parse
import morphir.ir.Distribution.Distribution
import morphir.ir.Documented.Documented
import morphir.ir.formatversion.Codec.decodeVersionedDistribution
import morphir.ir.{AccessControlled, Name,Path, Type, Value}
import morphir.ir.FQName.FQName
import morphir.ir.Name.Name

import scala.io.Source
import scala.util.{Try, Using}

object FDC3Backend extends App {
    var records = Map[FQName, Map[Name, FQName]]()

    loadMorphirIR("api/morphir-ir.json") match {
        case scala.util.Success(ir) => processIR(ir)
        case scala.util.Failure(exception) => println(s"Failed to load IR: $exception")
    }

    def loadMorphirIR(irPath : String): Try[Distribution] = {
        Using(Source.fromFile(irPath)) { source =>
            val source = Source.fromFile(irPath)
            val json = source.mkString
            val parsedIR = parse(json).getOrElse(Json.Null).hcursor
            val distribution = decodeVersionedDistribution(parsedIR)

            distribution match {
                case Left(err) => throw new Exception(s"Error decoding distribution: $err")
                case Right(value) => value.distribution
            }
        }
    }

    println(records)

    def processIR(ir: Distribution): Unit = {
        ir match { 
            case Distribution.Library(packageName, _, packageDef) =>
                packageDef.modules.toList.foreach { (moduleName, module) =>
                    module.value match {
                        case Module.Definition(types, _,  _) => {
                            types.toList.foreach { (typeName, typeDef) => {
                                typeDef.value.value match {
                                    case Type.Definition.TypeAliasDefinition(_, typeOf) => {
                                        typeOf match {
                                            case Type.Record(_, fields) => {
                                                var fieldMap = Map[Name, FQName]()

                                                fields.foreach { field =>
                                                    field.tpe match {
                                                        case Type.Reference(_, reFqn, _) => {
                                                            val fqn : FQName = morphir.ir.Distribution.resolveAliases(reFqn)(ir)
                                                            val name : Name = field.name;
                                                            fieldMap = (fieldMap + (name -> fqn))
                                                        }
                                                        case t =>
                                                            println(s"Type alias: ${t.getClass}")                                        
                                                    }
                                                }

                                                val typeFQName = FQName.fQName(packageName)(moduleName)(typeName)
                                                records = records + (typeFQName -> fieldMap)
                                            }
                                            case t =>
                                                println(s"Type alias: ${t.getClass}")                                        
                                        }
                                    }
                                    case Type.Definition.CustomTypeDefinition(_, typeOf) => {
                                        println(s"Custom Type: ${typeOf.getClass}")                                        
                                    }
                                }
                            } 
                        }
                    }
                }
            }
        }
    }
}
