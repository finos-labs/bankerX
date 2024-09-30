import * as cdk from "aws-cdk-lib";
import * as lambda from "aws-cdk-lib/aws-lambda";
import * as apigw from "aws-cdk-lib/aws-apigateway";
import * as process from "node:process";
import * as path from "node:path";
import { existsSync } from "node:fs";

export class BankerXCdkStack extends cdk.Stack {
  constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const codeAssetPath = process.env["BANKERX_LAMBDA_CODE_ASSET"];
    if (!codeAssetPath) {
      const errorMessage =
        "BANKERX_LAMBDA_CODE_ASSET environment variable is not set, this is required to deploy the BankerX Lambda. Set it to the path of the BankerX Lambda code asset.";
      console.error(errorMessage);
      throw new Error(errorMessage);
    }

    const normalizedCodeAssetPath = path.normalize(codeAssetPath);

    if (!existsSync(normalizedCodeAssetPath)) {
      const errorMessage = `The BANKERX_LAMBDA_CODE_ASSET environment variable points to a path that does not exist: ${normalizedCodeAssetPath}`;
      console.error(errorMessage);
      throw new Error(errorMessage);
    }

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

    //Create a resource for performing a post to bank/{bankName}/terms
    const rootApiBankerTerms = rootApi.addResource("bank");
    const rootApiBankerTermsBankName =
      rootApiBankerTerms.addResource("{bankName}");
    const rootApiBankerTermsBankNameTerms =
      rootApiBankerTermsBankName.addResource("terms");
    rootApiBankerTermsBankNameTerms.addMethod("POST");

    // Create a resource for performing a POST to /api/fdc3/bank/{bankName}/intents/terms
    rootApi
      .addResource("fdc3")
      .addResource("bank")
      .addResource("{bankName}")
      .addResource("intents")
      .addResource("get-terms")
      .addMethod("POST");
  }
}
