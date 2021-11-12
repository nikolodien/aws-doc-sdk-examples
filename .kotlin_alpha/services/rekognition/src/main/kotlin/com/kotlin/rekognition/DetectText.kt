// snippet-sourcedescription:[DetectText.kt demonstrates how to display words that were detected in an image.]
//snippet-keyword:[AWS SDK for Kotlin]
// snippet-service:[Amazon Rekognition]
// snippet-keyword:[Code Sample]
// snippet-sourcetype:[full-example]
// snippet-sourcedate:[11-05-2021]
// snippet-sourceauthor:[scmacdon - AWS]
/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/
package com.kotlin.rekognition

// snippet-start:[rekognition.kotlin.detect_text.import]
import aws.sdk.kotlin.services.rekognition.RekognitionClient
import aws.sdk.kotlin.services.rekognition.model.RekognitionException
import aws.sdk.kotlin.services.rekognition.model.Image
import aws.sdk.kotlin.services.rekognition.model.DetectTextRequest
import java.io.FileNotFoundException
import java.io.File
import kotlin.system.exitProcess
// snippet-end:[rekognition.kotlin.detect_text.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args: Array<String>){

    val usage = """
        Usage: 
            <sourceImage> 

        Where:
           sourceImage - the name of the image in an Amazon S3 bucket (for example, people.png). 
    """

    if (args.size != 1) {
         println(usage)
         exitProcess(0)
    }

    val sourceImage = args[0]
    val rekClient = RekognitionClient{ region = "us-east-1"}
    detectTextLabels(rekClient, sourceImage)
    rekClient.close()
}

// snippet-start:[rekognition.kotlin.detect_text.main]
suspend fun detectTextLabels(rekClient: RekognitionClient, sourceImage: String?) {
    try {

        val souImage = Image {
            bytes = (File(sourceImage).readBytes())
        }

        val textRequest = DetectTextRequest {
            image = souImage
        }

        val response = rekClient.detectText(textRequest)
        response.textDetections?.forEach { text ->
                 println("Detected: ${text.detectedText}")
                println("Confidence: ${text.confidence.toString()}")
                println("Id: ${text.id}")
                println("Parent Id:  ${text.parentId}")
                println("Type: ${text.type}")
        }

    } catch (e: RekognitionException) {
        println(e.message)
        rekClient.close()
        exitProcess(0)
    } catch (e: FileNotFoundException) {
        println(e.message)
        exitProcess(0)
    }
}
// snippet-end:[rekognition.kotlin.detect_text.main]