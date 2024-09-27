package bankerx.serverless
import caseapp.*
import caseapp.catseffect.*
import cats.data.NonEmptyList
import cats.effect.*
import sttp.tapir.serverless.aws.cdk.{AwsCdkInterpreter, AwsCdkOptions}

import scala.concurrent.duration.*
import java.nio.file.Paths
object BankerXCdkGenerator extends IOCaseApp[BankerXCdkGeneratorOptions] {
  val jarPath: String = Paths.get("serverless/out/assembly.dest/out.jar").toAbsolutePath.toString

  val awsCdkOptions: AwsCdkOptions = AwsCdkOptions(
    codeUri = jarPath,
    handler = BankerXLambdaHandler.handlerName,
    apiName = "BankerXApi",
    lambdaName = "BankerX",
    timeout = 20.seconds, // default value
    memorySizeInMB = 2048 // default value
  )

  def run(options: BankerXCdkGeneratorOptions, args: RemainingArgs): IO[ExitCode] = {
    for {
      _ <- IO.println("Generating BankerX CDK template...")
      //_ <- IO.println(s"options: $options")
      _ <- configureCdk(options, awsCdkOptions)
      _ <- IO.println("Done!")
    } yield ExitCode.Success
  }

  def configureCdk(bankerXOptions: BankerXCdkGeneratorOptions, awsCdkOptions:AwsCdkOptions): IO[Unit] = 
     for {
      _ <- IO.println("Configuring CDK...")
      cdkOptions = bankerXOptions.codeLocation.map { codeLocation =>
        val codePath = Paths.get(codeLocation).toAbsolutePath
        awsCdkOptions.copy(codeUri = codePath.toString)
      } . getOrElse(awsCdkOptions)
      _ <- IO.println(s"CDK options: $cdkOptions")
      _ <- AwsCdkInterpreter(cdkOptions)
            .toCdkAppTemplate[IO](BankerXLambdaHandler.helloEndpoint)
            .generate()
            .rethrow
      _ <- IO.println("CDK Configuration Done!")
     } yield ()
  
}

case class BankerXCdkGeneratorOptions(codeLocation: Option[String] = None)