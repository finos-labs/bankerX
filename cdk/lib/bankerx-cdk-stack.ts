import * as cdk from "aws-cdk-lib";
import * as lambda from "aws-cdk-lib/aws-lambda";
import * as apigw from "aws-cdk-lib/aws-apigateway";

export class TapirCdkStack extends cdk.Stack {
  constructor(
    scope: cdk.App,
    id: string,
    codeAssetPath: string,
    props?: cdk.StackProps
  ) {
    super(scope, id, props);

    console.info(`codeAssetPath: ${codeAssetPath}`);

    const lambdaJar = new lambda.Function(this, "BankerX", {
      runtime: lambda.Runtime.JAVA_11,
      code: lambda.Code.fromAsset(codeAssetPath),
      handler: "bankerx.serverless.BankerXLambdaHandler::handleRequest",
      timeout: cdk.Duration.seconds(20),
      memorySize: 2048,
    });

    const api = new apigw.LambdaRestApi(this, "BankerXApi", {
      handler: lambdaJar,
      proxy: false,
    });

    const rootApi = api.root.addResource("api");

    // GET /api/hello
    const rootApiHello = rootApi.addResource("hello");
    rootApiHello.addMethod("GET");
  }
}
