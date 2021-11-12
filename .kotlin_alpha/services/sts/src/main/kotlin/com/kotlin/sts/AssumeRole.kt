// snippet-comment:[These are tags for the AWS doc team's sample catalog. Do not remove.]
// snippet-sourcedescription:[AssumeRole.kt demonstrates how to obtain a set of temporary security credentials by using AWS Security Token Service (AWS STS).]
// snippet-keyword:[AWS SDK for Kotlin]
// snippet-keyword:[AWS Security Token Service (AWS STS)]
// snippet-keyword:[Code Sample]
// snippet-sourcetype:[full-example]
// snippet-sourcedate:[11/05/2021]
// snippet-sourceauthor:[AWS - scmacdon]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.sts

// snippet-start:[sts.kotlin.assume_role.import]
import aws.sdk.kotlin.services.sts.StsClient
import aws.sdk.kotlin.services.sts.model.AssumeRoleRequest
import aws.sdk.kotlin.services.sts.model.StsException
import kotlin.system.exitProcess
// snippet-end:[sts.kotlin.assume_role.import]

/**
 * To make this code example work, create a Role that you want to assume.
 * Then define a Trust Relationship in the AWS Console. You can use this as an example:
 *
 * {
 *   "Version": "2012-10-17",
 *   "Statement": [
 *     {
 *       "Effect": "Allow",
 *       "Principal": {
 *         "AWS": "<Specify the ARN of your IAM user you are using in this code example>"
 *       },
 *       "Action": "sts:AssumeRole"
 *     }
 *   ]
 * }
 *
 */

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.
For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>) {

    val usage = """
    Usage:
        <roleArn> <roleSessionName> 

    Where:
        roleArn - the Amazon Resource Name (ARN) of the role to assume (for example, arn:aws:iam::xxxxx8047983:role/s3role). 
        roleSessionName - an identifier for the assumed role session (for example, mysession). 
    """

    if (args.size != 2) {
        println(usage)
        exitProcess(0)
    }

    val roleArn = args[0]
    val roleSessionName = args[1]
    val stsClient = StsClient{region ="us-east-1"}
    assumeGivenRole(stsClient, roleArn, roleSessionName)
    stsClient.close()
}

// snippet-start:[sts.kotlin.assume_role.main]
suspend fun assumeGivenRole(stsClient: StsClient, roleArnVal: String?, roleSessionNameVal: String?) {
    try {
        val roleRequest = AssumeRoleRequest {
            roleArn = roleArnVal
            roleSessionName = roleSessionNameVal
        }

        val roleResponse = stsClient.assumeRole(roleRequest)
        val myCreds = roleResponse.credentials

        // Display the time when the temp creds expire.
        val exTime = myCreds?.expiration
        val tokenInfo = myCreds?.sessionToken
        println("The token $tokenInfo  expires on $exTime.")

    } catch (e: StsException) {
        println(e.message)
        stsClient.close()
        exitProcess(0)
    }
}
// snippet-end:[sts.kotlin.assume_role.main]

