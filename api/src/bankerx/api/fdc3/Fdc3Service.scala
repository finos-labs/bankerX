package bankerx.api.fdc3
import bankerx.API.*
import bankerx.SmartWallet

/**
 * Takes the FDC3 message and forwards it to SmartWallet (the Morphir-implemented logic for getting terms)
 */
trait Fdc3Service:
  def getTerms(
      bankId: BankID,
      getTermsIntent: GetTermsIntent
  ): Either[String, GetTermsResponsePayload]

  def makePurchase(
      bankId: BankID,
      makePurchaseIntent: MakePurchaseIntent
  ): Either[String, MakePurchaseResponse]
end Fdc3Service

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

    def makePurchase(
        bankId: BankID,
        makePurchaseIntent: MakePurchaseIntent
    ): Either[String, MakePurchaseResponse] =
      val purchase = makePurchaseIntent.context.data
      SmartWallet
        .makePurchase(bankId)(purchase)
        .fold(Left(s"Purchase failed for bank: $bankId"))(
          (provider: Provider) =>
            Right(
              MakePurchaseResponse(
                PayloadType.Fdc3PurchaseConfirmation,
                MakePurchaseConfirmation(provider)
              )
            )
        )
  end Live
end Fdc3Service
