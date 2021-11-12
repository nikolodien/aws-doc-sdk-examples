//snippet-sourcedescription:[ListGrants.kt demonstrates how to get information about AWS Key Management Service (AWS KMS) grants related to a key.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[AWS Key Management Service]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/04/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.kms

// snippet-start:[kms.kotlin_list_grant.import]
import aws.sdk.kotlin.services.kms.KmsClient
import aws.sdk.kotlin.services.kms.model.ListGrantsRequest
import aws.sdk.kotlin.services.kms.model.KmsException
import aws.sdk.kotlin.services.kms.model.GrantListEntry
import kotlin.system.exitProcess
// snippet-end:[kms.kotlin_list_grant.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args: Array<String>) {

    val usage = """
        Usage:
            <keyId> 
        Where:
            keyId - the unique identifier for the KMS key that the grant applies to (for example, xxxxxbcd-12ab-34cd-56ef-1234567890ab).
        """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
     }

    val keyId = args[0]
    val keyClient = KmsClient{region="us-west-2"}
    displayGrantIds(keyClient, keyId)
    keyClient.close()
}

// snippet-start:[kms.kotlin_list_grant.main]
suspend fun displayGrantIds(kmsClient: KmsClient, keyIdVal: String?) {
        try {

            val grantsRequest = ListGrantsRequest {
                keyId = keyIdVal
                limit = 15
            }

            val response = kmsClient.listGrants(grantsRequest)
            response.grants?.forEach { grant ->
                  println("The grant Id is ${grant.grantId}")
            }

        } catch (ex: KmsException) {
            println(ex.message)
            kmsClient.close()
            exitProcess(0)
        }
 }
// snippet-end:[kms.kotlin_list_grant.main]
