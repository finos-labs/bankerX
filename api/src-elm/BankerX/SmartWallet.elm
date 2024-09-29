module BankerX.SmartWallet exposing (..)
import BankerX.API exposing (..)

import BankerX.Banks.CapitalOne as CapitalOne
import BankerX.Banks.Etrade as Etrade
import BankerX.Banks.Klarna as Klarna

{-| Service definition for the SmartWallet service -}
type alias Service =
    { getTerms: BankID -> Purchase -> Maybe Terms}

{-| Service implementation for the SmartWallet service -}
service : Service
service =
    { getTerms = getTerms }

{-| Get the terms for a purchase from a bank -} 
getTerms : BankID -> Purchase -> Maybe Terms
getTerms bankId purchase =
    case bankId of
        "CapitalOne" -> Just(CapitalOne.getTerms purchase)
        "E*trade" -> Just(Etrade.getTerms purchase)
        "Klarna" -> Just(Klarna.getTerms purchase)
        _ -> Nothing