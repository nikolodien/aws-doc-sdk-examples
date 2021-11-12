//snippet-sourcedescription:[GetSecretValue.kt demonstrates how to get the value of a secret from AWS Secrets Manager.]
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

//snippet-start:[secretsmanager.kotlin.get_secret.import]
import aws.sdk.kotlin.services.secretsmanager.model.SecretsManagerException
import aws.sdk.kotlin.services.secretsmanager.SecretsManagerClient
import aws.sdk.kotlin.services.secretsmanager.model.GetSecretValueRequest
import kotlin.system.exitProcess
//snippet-end:[secretsmanager.kotlin.get_secret.import]

suspend fun main(args: Array<String>) {

    val usage = """
    Usage: 
        <secretName> <secretValue>

    Where:
         secretName - the name of the secret (for example, tutorials/MyFirstSecret).
      """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
    }

    val secretName = args[0]
    val secretsClient = SecretsManagerClient { region = "us-east-1" }
    getValue(secretsClient, secretName)
    secretsClient.close()
}

//snippet-start:[secretsmanager.kotlin.get_secret.main]
suspend fun getValue(secretsClient: SecretsManagerClient, secretName: String?) {
        try {

            val valueRequest = GetSecretValueRequest {
                secretId = secretName
            }

            val valueResponse = secretsClient.getSecretValue(valueRequest)
            val secret = valueResponse.secretString
            println("The secret value is $secret")

        } catch (ex: SecretsManagerException) {
            println(ex.message)
            secretsClient.close()
            exitProcess(0)
        }
 }
//snippet-end:[secretsmanager.kotlin.get_secret.main]