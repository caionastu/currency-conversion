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