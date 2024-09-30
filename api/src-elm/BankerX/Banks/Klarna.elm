module BankerX.Banks.Klarna exposing (..)

import BankerX.API exposing (..)
import Morphir.SDK.LocalDate exposing (LocalDate)
import Morphir.SDK.LocalTime exposing (LocalTime)

bankname = "Klarna"
bankID = "Klarna"
bankLogo = "https://finos-labs.github.io/bankerX/app/images/klarna.png"

provider : Provider
provider = 
    { name = bankname
    , id = bankID
    , logo = bankLogo
    }

getTerms : Purchase -> Terms
getTerms purchase =
    let
        points : Points
        points =
            getPoints purchase.category purchase.amount
    in
    { provider = provider
    , points = points
    , interestRate = 0.55
    , promotionalPeriod = 90
    }

getPoints : Category -> Amount -> Points
getPoints category amount =
    if List.member category [Groceries, Fuel] then
        5 * amount
    else if List.member category [Home] then
        2 * amount
    else
        amount

makePurchase: Purchase -> Provider
makePurchase purchase = 
    let 
        terms: Terms 
        terms = getTerms purchase
    in
        terms.provider    