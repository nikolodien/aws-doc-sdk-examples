//snippet-sourcedescription:[UpdateSecret.kt demonstrates how to update a secret for AWS Secrets Manager.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-keyword:[AWS Secrets Manager]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.secrets

//snippet-start:[secretsmanager.kotlin.update_secret.import]
import aws.sdk.kotlin.services.secretsmanager.model.SecretsManagerException
import aws.sdk.kotlin.services.secretsmanager.SecretsManagerClient
import aws.sdk.kotlin.services.secretsmanager.model.UpdateSecretRequest
import kotlin.system.exitProcess
//snippet-end:[secretsmanager.kotlin.update_secret.import]

suspend fun main(args: Array<String>) {

    val usage = """
    Usage: 
        <secretName> <secretValue>

    Where:
         secretName - the name of the secret (for example, tutorials/MyFirstSecret).
         secretValue - the secret value.
      """

    if (args.size != 2) {
        println(usage)
        exitProcess(0)
    }

    val secretName = args[0]
    val secretValue = args[1]
    val secretsClient = SecretsManagerClient { region = "us-east-1" }
    updateMySecret(secretsClient, secretName, secretValue)
    secretsClient.close()
}

//snippet-start:[secretsmanager.kotlin.update_secret.main]
suspend fun updateMySecret(secretsClient: SecretsManagerClient, secretName: String?, secretValue: String?) {
        try {
            val secretRequest = UpdateSecretRequest {
                secretId = secretName
                secretString = secretValue
            }

            secretsClient.updateSecret(secretRequest)
            println("The secret value was updated")

        } catch (ex: SecretsManagerException) {
            println(ex.message)
            secretsClient.close()
            exitProcess(0)
        }
 }
//snippet-end:[secretsmanager.kotlin.update_secret.main]