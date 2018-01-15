package com.smp7d.currency.domain;

/**
 * Handling currency codes more dynamically (ex/ as Strings) would have made
 * this application less failure prone as new codes are added/discovered, but we
 * needed an exhaustive list of currency codes for publishing use case plus
 * using an enum is cleaner...so here we are. I'd image the world's currency
 * codes are manageable. Worst case, we fail fast and know we have to add a
 * code. We can think of better ways around this (db/configuration/custom
 * resiliency/etc.).
 *
 */
public enum CurrencyCode {
	TRL, SIT, ROL, SKK, MTL, LVL, LTL, ISK, EEK, CYP, EUR, AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, GBP, HKD, HRK, HUF, IDR, ILS, INR, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR
}
