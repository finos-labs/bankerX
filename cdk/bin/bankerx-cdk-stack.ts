#!/usr/bin/env node
import * as cdk from 'aws-cdk-lib';
import { TapirCdkStack } from '../lib/bankerx-cdk-stack';
import * as process from 'node:process';
import * as path from 'node:path';
import { existsSync } from 'node:fs';

const codeAssetPath = process.env["BANKERX_LAMBDA_CODE_ASSET"];
if (!codeAssetPath) {
    const errorMessage = 'BANKERX_LAMBDA_CODE_ASSET environment variable is not set, this is required to deploy the BankerX Lambda. Set it to the path of the BankerX Lambda code asset.';
    console.error(errorMessage);
    throw new Error(errorMessage);
}    

const normalizedCodeAssetPath = path.normalize(codeAssetPath);

if(!existsSync(normalizedCodeAssetPath)) {
    const errorMessage = `The BANKERX_LAMBDA_CODE_ASSET environment variable points to a path that does not exist: ${normalizedCodeAssetPath}`;
    console.error(errorMessage);
    throw new Error(errorMessage);
}

const app = new cdk.App();
new TapirCdkStack(app, 'TapirCdkStack', normalizedCodeAssetPath);