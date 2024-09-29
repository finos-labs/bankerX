module BankerX.SmartWallet exposing (..)
import BankerX.API exposing (..)

import BankerX.Banks.CapitalOne as CapitalOne
import BankerX.Banks.Etrade as Etrade
import BankerX.Banks.FirstBank as FirstBank
import BankerX.Banks.SecondBank as SecondBank

{-| Service definition for the SmartWallet service -}
type alias Service =
    { getTerms: BankName -> Purchase -> Maybe Terms}

{-| Service implementation for the SmartWallet service -}
service : Service
service =
    { getTerms = getTerms }

{-| Get the terms for a purchase from a bank -} 
getTerms : BankName -> Purchase -> Maybe Terms
getTerms bankName purchase =
    case bankName of
        "CapitalOne" -> Just(CapitalOne.getTerms purchase)
        "Etrade" -> Just(Etrade.getTerms purchase)
        "FirstBank" -> Just(FirstBank.getTerms purchase)
        "SecondBank" -> Just(SecondBank.getTerms purchase)
        _ -> Nothing