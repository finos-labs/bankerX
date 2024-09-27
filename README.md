![badge-labs](https://user-images.githubusercontent.com/327285/230928932-7c75f8ed-e57b-41db-9fb7-a292a13a1e58.svg)

# BankerX

Morphir is a standard language for business logic, FDC3 is a standard language for application interoperability.  BankerX is a reference example combining the FDC3 and Morphir projects so that a web application can use the FDC3 protocol to integrate with Morphir based services by speaking FDC3 over REST.

## Installation

```sh
npm i

```

run the local application...

## Usage example

This project can be used as a reference example for specific FDC3 and Morphir data flows.  It can also be used as an example of novel FDC3 and Morphir use cases.  In this case, commercial banking, to expand the conception of business value for both projects.

## Development setup

Clone the repo and instal, run the the app locally or from Github pages.

```sh
npm i
```

## FDC3 Flow

intent: GetTerms

Context Data

Purchase ('fdc3.purchase')

```ts
interface Purchase {
    type: string; //'fdc3.purchase'
    amount: number;
    vendor: string;
    timestamp: number;
    purchaser: string; //is there a common identifier for the purchaser?  do we even want to include this (or is this too much PII)?
    merchant: string; //identifier for the merchant/point of purchase - is there a common identifier
    category?: string;
}
```

TermsList ('fdc3.termsList')

```ts
interface TermsList {
   type: string; //'fdc3.termsList'
   terms: [Terms];
}
```

Terms ('fdc3.Terms')

```ts
interface Terms {
   type: string; //'fdc3.terms
   points: number;
   rate: number;
   provider: string; //display name of bank providing terms
   providerId: string; //identifier of bank providing terms
}
```

intent: MakePurchase


## Roadmap

List the roadmap steps; alternatively link the Confluence Wiki page where the project roadmap is published.

1. Item 1
2. Item 2
3. ....

## Contributing

1. Fork it (<https://github.com/finos-labs/bankerX/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Read our [contribution guidelines](.github/CONTRIBUTING.md) and [Community Code of Conduct](https://www.finos.org/code-of-conduct)
4. Commit your changes (`git commit -am 'Add some fooBar'`)
5. Push to the branch (`git push origin feature/fooBar`)
6. Create a new Pull Request

## License

Copyright 2024 FINOS

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

SPDX-License-Identifier: [Apache-2.0](https://spdx.org/licenses/Apache-2.0)
