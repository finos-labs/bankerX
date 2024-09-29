import mill._, scalalib._

object api extends ScalaModule {
  def scalaVersion = "3.5.1"

  def ivyDeps = Agg(
    ivy"org.morphir:morphir-sdk-core:0.1.0"
  )

  object test extends Tests {
    def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:3.2.10"
    )
  }
}