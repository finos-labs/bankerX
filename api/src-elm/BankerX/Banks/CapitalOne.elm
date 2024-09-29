module BankerX.Banks.CapitalOne exposing (..)

import BankerX.API exposing (..)
import Morphir.SDK.LocalDate exposing (LocalDate)
import Morphir.SDK.LocalTime exposing (LocalTime)

bankname = "Capital One Bank"
bankID = "CapitalOne"
bankLogo = "https://finos-labs.github.io/bankerX/app/images/capitalone.png"

provider : Provider
provider = 
    { name = bankname
    , id = bankID
    , logo = bankLogo
    }

preferredVendors : List Vendor
preferredVendors = 
    [ "Vendor.A"
    , "Vendor.W"
    , "Vendor.T"
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
    , interestRate = 0.5
    , promotionalPeriod = 30
    }

getPoints : Vendor -> Amount -> Points
getPoints vendor amount =
    if List.member vendor preferredVendors then
        4 * amount
    else
        amount
