//snippet-sourcedescription:[CreateDataStream.kt demonstrates how to create an Amazon Kinesis data stream.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-keyword:[Amazon Kinesis]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/04/2021]
//snippet-sourceauthor:[scmacdon AWS]
/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.kinesis
//snippet-start:[kinesis.kotlin.create.import]
import aws.sdk.kotlin.services.kinesis.KinesisClient
import aws.sdk.kotlin.services.kinesis.model.CreateStreamRequest
import aws.sdk.kotlin.services.kinesis.model.KinesisException
import kotlin.system.exitProcess
//snippet-end:[kinesis.kotlin.create.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun  main(args: Array<String>){

    val usage = """
    Usage: 
        <streamName>

    Where:
        streamName - The Amazon Kinesis data stream (for example, StockTradeStream)
    """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
    }

    val name = args[0]
    val kinesisClient = KinesisClient{region ="us-east-1"}
    createStream(kinesisClient, name)
    kinesisClient.close()
}

//snippet-start:[kinesis.kotlin.create.main]
suspend fun createStream(kinesisClient: KinesisClient, streamNameVal: String?) {
    try {
        val streamReq = CreateStreamRequest {
            streamName = streamNameVal
            shardCount = 1
        }
        kinesisClient.createStream(streamReq)
        println("The $streamNameVal data stream was created" )

    } catch (e: KinesisException) {
        println(e.message)
        kinesisClient.close()
        exitProcess(0)
    }
}
//snippet-end:[kinesis.kotlin.create.main]