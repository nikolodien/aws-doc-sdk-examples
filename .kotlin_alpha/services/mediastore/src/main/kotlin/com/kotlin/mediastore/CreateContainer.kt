//snippet-sourcedescription:[CreateContainer.kt demonstrates how to create an AWS Elemental MediaStore container.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[AWS Elemental MediaStore]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon - AWS]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.mediastore

//snippet-start:[mediastore.kotlin.create_container.import]
import aws.sdk.kotlin.services.mediastore.MediaStoreClient
import aws.sdk.kotlin.services.mediastore.model.CreateContainerRequest
import aws.sdk.kotlin.services.mediastore.model.MediaStoreException
import kotlinx.coroutines.delay
import kotlin.system.exitProcess
//snippet-end:[mediastore.kotlin.create_container.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>){

    val usage = """
    
        Usage: <containerName> 

        Where:
               containerName - the name of the container.
           
    """

     if (args.size != 1) {
         println(usage)
         exitProcess(0)
     }

    val containerName = args[0]
    val mediastoreClient = MediaStoreClient { region = "us-east-1" }
    println("Status is " + createMediaContainer(mediastoreClient, containerName))
    mediastoreClient.close()
}

//snippet-start:[mediastore.kotlin.create_container.main]
suspend fun createMediaContainer(mediaStoreClient: MediaStoreClient, containerNameVal: String?) {

        val sleepTime: Long = 10
        try {
            val containerRequest = CreateContainerRequest {
                containerName = containerNameVal
            }

            val containerResponse = mediaStoreClient.createContainer(containerRequest)
            var status = containerResponse.container?.status.toString()

            // Wait until the container is in an active state.
            while (!status.equals("Active", ignoreCase = true)) {
                status = checkContainer(mediaStoreClient, containerNameVal).toString()
                println("Status - $status")
                delay(sleepTime * 1000)
            }
            println("The container ARN value is ${containerResponse.container?.arn}")
            println("The $containerNameVal is created")

        } catch (e: MediaStoreException) {
            println(e.message)
            mediaStoreClient.close()
            exitProcess(0)
        }
}
//snippet-end:[mediastore.kotlin.create_container.main]