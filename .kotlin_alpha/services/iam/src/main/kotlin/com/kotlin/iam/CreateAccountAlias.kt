//snippet-sourcedescription:[CreateAccountAlias.kt demonstrates how to create an alias for an AWS account.]
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

// snippet-start:[iam.kotlin.create_account_alias.import]
import aws.sdk.kotlin.services.iam.IamClient
import aws.sdk.kotlin.services.iam.model.CreateAccountAliasRequest
import aws.sdk.kotlin.services.iam.model.IamException
import kotlin.system.exitProcess
// snippet-end:[iam.kotlin.create_account_alias.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args: Array<String>) {

    val usage = """
        Usage:
            <alias> 
        Where:
            alias - the account alias to create (for example, myawsaccount).  

        """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
    }

    val alias = args[0]
    val iamClient = IamClient{region="AWS_GLOBAL"}
    createIAMAccountAlias(iamClient, alias)
    iamClient.close()
}

// snippet-start:[iam.kotlin.create_account_alias.main]
suspend fun createIAMAccountAlias(iamClient: IamClient, alias: String) {
    try {
        val request = CreateAccountAliasRequest {
            accountAlias = alias
        }

        iamClient.createAccountAlias(request)
        println("Successfully created account alias named $alias")

    } catch (e: IamException) {
        println(e.message)
        iamClient.close()
        exitProcess(0)
    }
}
// snippet-end:[iam.kotlin.create_account_alias.main]