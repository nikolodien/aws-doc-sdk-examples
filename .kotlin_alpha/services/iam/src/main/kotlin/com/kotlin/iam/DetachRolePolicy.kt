//snippet-sourcedescription:[DetachRolePolicy.kt demonstrates how to detach a policy from an AWS Identity and Access Management (IAM) role.]
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

// snippet-start:[iam.kotlin.detach_role_policy.import]
import aws.sdk.kotlin.services.iam.IamClient
import aws.sdk.kotlin.services.iam.model.DetachRolePolicyRequest
import aws.sdk.kotlin.services.iam.model.IamException
import kotlin.system.exitProcess
// snippet-end:[iam.kotlin.detach_role_policy.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args: Array<String>) {

    val usage = """
        Usage:
            <roleName> <policyArn>
        Where:
            roleName - a role name that you can obtain from the AWS Management Console. 
            policyArn - a policy ARN that you can obtain from the AWS Management Console. 
        """

     if (args.size != 2) {
         println(usage)
         exitProcess(0)
     }

    val roleName = args[0]
    val policyArn = args[1]
    val iamClient = IamClient{region="AWS_GLOBAL"}
    detachPolicy(iamClient, roleName, policyArn)
    iamClient.close()
}

// snippet-start:[iam.kotlin.detach_role_policy.main]
suspend fun detachPolicy(iamClient: IamClient, roleNameVal: String, policyArnVal: String) {
    try {
        val request = DetachRolePolicyRequest {
            roleName = roleNameVal
            policyArn = policyArnVal
        }

        iamClient.detachRolePolicy(request)
        println( "Successfully detached policy $policyArnVal from role $roleNameVal")

    } catch (e: IamException) {
        println(e.message)
        iamClient.close()
        exitProcess(0)
    }
}
// snippet-end:[iam.kotlin.detach_role_policy.main]