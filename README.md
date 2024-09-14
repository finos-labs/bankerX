![badge-labs](https://user-images.githubusercontent.com/327285/230928932-7c75f8ed-e57b-41db-9fb7-a292a13a1e58.svg)

# BankerX

Short blurb about what your project does.

## Installation

OS X & Linux:

```sh
npm install my-crazy-module --save
```

Windows:

```sh
edit autoexec.bat
```

## Usage example

A few motivating and useful examples of how your project can be used. Spice this up with code blocks and potentially screenshots / videos ([LiceCap](https://www.cockos.com/licecap/) is great for this kind of thing).

_For more examples and usage, please refer to the [Wiki][wiki]._

## Development setup

Describe how to install all development dependencies and how to run an automated test-suite of some kind. Potentially do this for multiple platforms.

```sh
make install
npm test
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
