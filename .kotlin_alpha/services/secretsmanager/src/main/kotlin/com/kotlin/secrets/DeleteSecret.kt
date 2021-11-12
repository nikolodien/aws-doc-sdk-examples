//snippet-sourcedescription:[DeleteSecret.kt demonstrates how to delete a secret.]
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

//snippet-start:[secretsmanager.kotlin.delete_secret.import]
import aws.sdk.kotlin.services.secretsmanager.model.SecretsManagerException
import aws.sdk.kotlin.services.secretsmanager.SecretsManagerClient
import aws.sdk.kotlin.services.secretsmanager.model.DeleteSecretRequest
import kotlin.system.exitProcess
//snippet-end:[secretsmanager.kotlin.delete_secret.import]

suspend fun main(args: Array<String>) {

    val usage = """
    Usage: 
        <secretName> 

    Where:
         secretName - the name of the secret (for example, tutorials/MyFirstSecret).
     """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
    }

    val secretName = args[0]
    val secretsClient = SecretsManagerClient { region = "us-east-1" }
    deleteSpecificSecret(secretsClient, secretName)
    secretsClient.close()
}

//snippet-start:[secretsmanager.kotlin.delete_secret.main]
suspend fun deleteSpecificSecret(secretsClient: SecretsManagerClient, secretName: String) {
        try {
            val secretRequest = DeleteSecretRequest {
                secretId=secretName
            }

            secretsClient.deleteSecret(secretRequest)
            println("$secretName is deleted.")

        } catch (ex: SecretsManagerException) {
            println(ex.message)
            secretsClient.close()
            exitProcess(0)
        }
}
//snippet-end:[secretsmanager.kotlin.delete_secret.main]