//snippet-sourcedescription:[RemoveQueueTag.kt demonstrates how to remove a tag from an Amazon Simple Queue Service (Amazon SQS) queue.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon Simple Queue Service]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.sqs

// snippet-start:[sqs.kotlin.remove_tag.import]
import aws.sdk.kotlin.services.sqs.SqsClient
import aws.sdk.kotlin.services.sqs.model.GetQueueUrlRequest
import aws.sdk.kotlin.services.sqs.model.UntagQueueRequest
import aws.sdk.kotlin.services.sqs.model.SqsException
import kotlin.system.exitProcess
// snippet-end:[sqs.kotlin.remove_tag.import]

suspend fun main(args:Array<String>) {


    val usage = """
        Usage: 
            <queueName> <tagName>
        Where:
           queueName - the name of the queue from which tags are removed.
           tagName - the name of the tag to remove.

        """

    if (args.size != 2) {
         println(usage)
         exitProcess(0)
     }

    val queueName = args[0]
    val tagName = args[1]
    val sqsClient = SqsClient { region = "us-east-1" }
    removeTag(sqsClient, queueName, tagName)
    sqsClient.close()
}

// snippet-start:[sqs.kotlin.remove_tag.main]
suspend fun removeTag(sqsClient: SqsClient, queueNameVal: String, tagName: String) {
    try {
        val urlRequest = GetQueueUrlRequest {
            queueName = queueNameVal
        }

        val getQueueUrlResponse = sqsClient.getQueueUrl(urlRequest)
        val queueUrlVal = getQueueUrlResponse.queueUrl

        val untagQueueRequest = UntagQueueRequest {
            queueUrl = queueUrlVal
            tagKeys = listOf(tagName)
        }

        sqsClient.untagQueue(untagQueueRequest)
        println("The $tagName tag was removed from  $queueNameVal")

    } catch (e: SqsException) {
        println(e.message)
        sqsClient.close()
        exitProcess(0)
    }
}
// snippet-end:[sqs.kotlin.remove_tag.main]