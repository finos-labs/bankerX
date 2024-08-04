module BankerX.FirstBank exposing (..)

import BankerX.API exposing (..)
import Morphir.SDK.LocalDate exposing (LocalDate)
import Morphir.SDK.LocalTime exposing (LocalTime)

bankname = "FirstBank"

preferredVendors : List Vendor
preferredVendors = 
    [ "Vendor.A"
    , "Vendor.W"
    , "Vendor.T"
    ]

getTerms : Purchase -> Terms
getTerms purchase =
    let
        points =
            getPoints purchase.vendor purchase.amount
    in
    { provider = bankname
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