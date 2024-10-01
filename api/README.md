# BankerX API

Contains the Morphir model for the BankerX project.

The model includes the API for the simple banking application, that is setup to demonstrate the use of the FDC3 protocol to integrate with Morphir based services by speaking FDC3 over REST.

## Developing

### Building

At the root of the project run the following command to build the api project:

```sh
./mill api.build
```

<details>

<summary>Building for Windows users</summary>

```sh
mill.bat api.build
```

or using Powershell

```sh
.\mill.ps1 api.build
```

</details>

### Testing

At the root of the project run the following command to test the api project:

```sh
./mill api.test
```

### Morphir Code Generation

The custom mill target called `morphirScalaGen` found in the `MorphirScalaModule` is responsible for generating the Scala code from the Morphir model.
You can find the various reused custom mill targets in [`util.mill`](../util.mill) at the root of the project.

> NOTE: In order to keep incremental compilation running smoothly, the code is generated into the mill out folder, as according to mill's conventions.
> The generated [morphir-ir.json](../out/api/morphirMakeOutputDir.dest/morphir-ir.json) is found in the out folder if the `api.build` target is ran.
> The [generated Scala code](../out/api/morphirScalaGenOutputDir.dest/) is found in the out folder if the `morphirScalaGen` target is ran (it is automatically ran as a result of running `build`).

Part of the benefit of using mill is its ability to heavily customize the build process using normal Scala code.
