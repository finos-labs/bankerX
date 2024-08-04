module BankerX.API exposing (..)

import Morphir.SDK.LocalDate exposing (LocalDate)
import Morphir.SDK.LocalTime exposing (LocalTime)


type alias Provider = String
type alias Points = Int
type alias InterestRate = Float
type alias PromotionalPeriod = Int

type alias Terms = 
    { provider : Provider
    , points : Points
    , interestRate : InterestRate
    , promotionalPeriod : PromotionalPeriod
    }


type alias Amount = Int
type alias Vendor = String
type alias Date = LocalDate
type alias Time = LocalTime
type alias UserID = String
type Category
    = Groceries
    | Dining
    | Home
    | Shopping
    | Travel
    | Fuel
type alias PointOfSale = String

type alias Purchase =
    { amount : Amount
    , vendor : Vendor
    , date : Date
    , time : Time
    , userID : UserID
    , category : Category
    , pointOfSale : PointOfSale
    }


-- getTerms : Purchase -> Terms
-- getTerms purchase = todo "Implement getTerms"


