//snippet-sourcedescription:[DeletePolicy.kt demonstrates how to delete a fixed policy with a provided policy name.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Identity and Access Management (IAM)]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/04/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.iam

// snippet-start:[iam.kotlin.delete_policy.import]
import aws.sdk.kotlin.services.iam.IamClient
import aws.sdk.kotlin.services.iam.model.DeletePolicyRequest
import aws.sdk.kotlin.services.iam.model.IamException
import kotlin.system.exitProcess
// snippet-end:[iam.kotlin.delete_policy.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
*/

suspend fun main(args: Array<String>) {

    val usage = """
        Usage:
            <policyARN> 
        Where:
           policyARN - a policy ARN value to delete.
        """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
    }

    val policyARN = "arn:aws:iam::814548047983:policy/DynamoDBTest96" //args[0]
    val iamClient = IamClient{region="AWS_GLOBAL"}
    deleteIAMPolicy(iamClient, policyARN)
    iamClient.close()
}

// snippet-start:[iam.kotlin.delete_policy.main]
suspend fun deleteIAMPolicy(iamClient: IamClient, policyARNVal: String?) {
    try {
        val request = DeletePolicyRequest {
            policyArn = policyARNVal
        }

        iamClient.deletePolicy(request)
        println("Successfully deleted $policyARNVal")

    } catch (e: IamException) {
        println(e.message)
        iamClient.close()
        exitProcess(0)
    }
}
// snippet-end:[iam.kotlin.delete_policy.main]