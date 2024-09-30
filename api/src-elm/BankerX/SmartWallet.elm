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
    let 
        normalizedBankId:String
        normalizedBankId = String.toLower bankId
    in
    case normalizedBankId of
        "capitalone" -> Just(CapitalOne.getTerms purchase)
        "e*trade"  -> Just(Etrade.getTerms purchase)
        "etrade" -> Just(Etrade.getTerms purchase)
        "klarna" -> Just(Klarna.getTerms purchase)
        _ -> Nothing