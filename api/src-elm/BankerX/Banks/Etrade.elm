module BankerX.Banks.Etrade exposing (..)

import BankerX.API exposing (..)
import Morphir.SDK.LocalDate exposing (LocalDate)
import Morphir.SDK.LocalTime exposing (LocalTime)

bankname = "Etrade"
bankId = "Etrade"
registration: BankRegistration
registration = 
    { bankName = bankname
    , bankID = bankId
    , getTerms = getTerms
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
    { provider = bankname
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
