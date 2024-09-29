#!/usr/bin/env node
import * as cdk from "aws-cdk-lib";
import { BankerXCdkStack } from "../lib/bankerx-cdk-stack";
import * as process from "node:process";
import * as path from "node:path";
import { existsSync } from "node:fs";

const app = new cdk.App();
new BankerXCdkStack(app, "BankerXCDK");
