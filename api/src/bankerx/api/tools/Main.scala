import bankerx.banks.FirstBank
import bankerx.API.Purchase
import java.time.LocalDate
import java.time.LocalTime

object Main extends App {
    val purchase = Purchase(
        100,
        "Vender1",
        LocalDate.now(),
        LocalTime.now(),
        "user 1",
        bankerx.API.Fuel,
        "pointOfSale 1"
    )

    val terms = FirstBank.getTerms(purchase)

    println(terms)
}