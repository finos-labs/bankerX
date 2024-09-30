module BankerX.Banks.Etrade exposing (..)

import BankerX.API exposing (..)
import Morphir.SDK.LocalDate exposing (LocalDate)
import Morphir.SDK.LocalTime exposing (LocalTime)

bankname = "E*trade"
bankID = "E*trade"
bankLogo = "https://finos-labs.github.io/bankerX/app/images/etrade.png"

provider : Provider
provider = 
    { name = bankname
    , id = bankID
    , logo = bankLogo
    }

preferredVendors : List Vendor
preferredVendors = 
    [ "Gamestop"
    , "Morgan Stanley"
    , "NYSE"
    ]

getTerms : Purchase -> Terms
getTerms purchase =
    let
        points : Points
        points =
            getPoints purchase.vendor purchase.amount
    in
    { provider = provider
    , points = points
    , interestRate = 0.615
    , promotionalPeriod = 60
    }

getPoints : Vendor -> Amount -> Points
getPoints vendor amount =
    if List.member vendor preferredVendors then
        4 * amount
    else
        amount

makePurchase: Purchase -> Provider
makePurchase purchase = 
    let 
        terms: Terms 
        terms = getTerms purchase
    in
        terms.provider    