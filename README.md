# currency-conversion
This app convert two currencies with updated tax rate


## Syncing GitHub repository and Azure repository
** Explain why to use this **

Clone your primary repository (GitHub)

``` git clone {url} ```

Change to .git directory

``` cd .git ```

And add the secondary repository (Azure)

``` git remote add --mirror=fetch azure(alias) {url}```

Run git fetch command to get the commits and refs from GitHub

``` git fetch origin```

And run git push to update secondary (Azure) repository

``` git push azure --all```

To Sync (GitHub -> Azure)

``` git fetch origin ```

``` git push azure --all ```

To Reverse Sync (Azure -> GitHub)

``` git fetch azure ```

``` git push origin ```

Reference: <https://www.opentechguides.com/how-to/article/git/177/git-sync-repos.html>


#Exchange API
https://manage.exchangeratesapi.io/quickstart

https://api.exchangeratesapi.io/v1/convert
? access_key = API_KEY
& from = GBP
& to = JPY
& amount = 25

# Docker with Jib (Google)

mvn compile jib:build

Reference: https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin

# Problem with Pageable and Swagger
https://github.com/springfox/springfox/issues/2623

# Generating all currencies insertions
```java 
String rates = "AED, AFN, ALL, AMD, ANG, AOA, ARS, AUD, AWG, AZN, BAM, BBD, BDT, BGN, BHD, BIF, BMD, BND, BOB, BRL, BSD, BTC, BTN, BWP, BYN, BYR, BZD, CAD, CDF, CHF, CLF, CLP, CNY, COP, CRC, CUC, CUP, CVE, CZK, DJF, DKK, DOP, DZD, EGP, ERN, ETB, EUR, FJD, FKP, GBP, GEL, GGP, GHS, GIP, GMD, GNF, GTQ, GYD, HKD, HNL, HRK, HTG, HUF, IDR, ILS, IMP, INR, IQD, IRR, ISK, JEP, JMD, JOD, JPY, KES, KGS, KHR, KMF, KPW, KRW, KWD, KYD, KZT, LAK, LBP, LKR, LRD, LSL, LTL, LVL, LYD, MAD, MDL, MGA, MKD, MMK, MNT, MOP, MRO, MUR, MVR, MWK, MXN, MYR, MZN, NAD, NGN, NIO, NOK, NPR, NZD, OMR, PAB, PEN, PGK, PHP, PKR, PLN, PYG, QAR, RON, RSD, RUB, RWF, SAR, SBD, SCR, SDG, SEK, SGD, SHP, SLL, SOS, SRD, STD, SVC, SYP, SZL, THB, TJS, TMT, TND, TOP, TRY, TTD, TWD, TZS, UAH, UGX, USD, UYU, UZS, VEF, VND, VUV, WST, XAF, XAG, XAU, XCD, XDR, XOF, XPF, YER, ZAR, ZMK, ZMW, ZWL";
String[] split = rates.split(", ");
StringBuilder builder = new StringBuilder();
for (String s : split) {
    builder.append("insert into currencies values('");
    builder.append(s);
    builder.append("');");
}

log.info(builder.toString());
```

Resulting: 
```sql 
insert into currencies values('AED');insert into currencies values('AFN');...
```

# Customize Exchange Api cache time
