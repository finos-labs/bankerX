module BankerX.SecondBank exposing (..)

import BankerX.API exposing (..)
import Morphir.SDK.LocalDate exposing (LocalDate)
import Morphir.SDK.LocalTime exposing (LocalTime)

bankname = "SecondBank"

getTerms : Purchase -> Terms
getTerms purchase =
    let
        points : Points
        points =
            getPoints purchase.category purchase.amount
    in
    { provider = bankname
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