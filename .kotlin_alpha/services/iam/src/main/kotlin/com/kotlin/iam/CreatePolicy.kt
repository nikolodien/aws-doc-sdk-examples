//snippet-sourcedescription:[CreatePolicy.kt demonstrates how to create a policy by using waiters.]
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

// snippet-start:[iam.kotlin.create_policy.import]
import aws.sdk.kotlin.services.iam.IamClient
import aws.sdk.kotlin.services.iam.model.CreatePolicyRequest
import aws.sdk.kotlin.services.iam.model.IamException
import kotlin.system.exitProcess
// snippet-end:[iam.kotlin.create_policy.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args: Array<String>) {

    val usage = """
        Usage:
            <policyName> 
        Where:
            policyName - a unique policy name. 
        """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
     }

    val policyName = args[0]
    val iamClient = IamClient{region="AWS_GLOBAL"}
    val result = createIAMPolicy(iamClient, policyName)
    println("Successfully created a policy with this ARN value: $result")
    iamClient.close()
}

// snippet-start:[iam.kotlin.create_policy.main]
suspend fun createIAMPolicy(iamClient:IamClient, policyNameVal: String?): String {

    val policyDocumentVal = "{" +
            "  \"Version\": \"2012-10-17\"," +
            "  \"Statement\": [" +
            "    {" +
            "        \"Effect\": \"Allow\"," +
            "        \"Action\": [" +
            "            \"dynamodb:DeleteItem\"," +
            "            \"dynamodb:GetItem\"," +
            "            \"dynamodb:PutItem\"," +
            "            \"dynamodb:Scan\"," +
            "            \"dynamodb:UpdateItem\"" +
            "       ]," +
            "       \"Resource\": \"*\"" +
            "    }" +
            "   ]" +
            "}"

    try {

        val request = CreatePolicyRequest {
            policyName = policyNameVal
            policyDocument = policyDocumentVal
        }

        val response = iamClient.createPolicy(request)
        return response.policy?.arn.toString()

    } catch (e: IamException) {
        println(e.message)
        iamClient.close()
        exitProcess(0)
    }
    return ""
}
// snippet-end:[iam.kotlin.create_policy.main]