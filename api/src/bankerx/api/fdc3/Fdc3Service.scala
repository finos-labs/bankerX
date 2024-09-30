package bankerx.api.fdc3
import bankerx.API.{BankID, Terms}
import bankerx.SmartWallet

trait Fdc3Service:
  def getTerms(
      bankId: BankID,
      getTermsIntent: GetTermsIntent
  ): Either[String, GetTermsResponsePayload]

object Fdc3Service:
  case object Live extends Fdc3Service:
    def getTerms(
        bankId: BankID,
        getTermsIntent: GetTermsIntent
    ): Either[String, GetTermsResponsePayload] =
      val purchase = getTermsIntent.context.data
      SmartWallet
        .getTerms(bankId)(purchase)
        .fold(Left(s"Terms unavailable for bank: $bankId"))((terms: Terms) =>
          Right(GetTermsResponsePayload(PayloadType.Fdc3Terms, terms))
        )
