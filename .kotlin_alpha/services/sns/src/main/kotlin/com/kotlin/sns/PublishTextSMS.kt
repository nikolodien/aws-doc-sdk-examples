//snippet-sourcedescription:[PublishTextSMS.kt demonstrates how to send an Amazon Simple Notification Service (Amazon SNS) text message.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-keyword:[Amazon Simple Notification Service]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon- AWS]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.sns

//snippet-start:[sns.kotlin.PublishTextSMS.import]
import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import aws.sdk.kotlin.services.sns.model.SnsException
import kotlin.system.exitProcess
//snippet-end:[sns.kotlin.PublishTextSMS.import]


suspend fun main(args:Array<String>) {

    val usage = """
    
        Usage: 
            <message> <phoneNumber>

        Where:
            message - the message text to send.
            phoneNumber - the mobile phone number to which a message is sent (for example, +1XXX5550100). 
        """

    if (args.size != 3) {
        println(usage)
        exitProcess(0)
    }

    val message = args[0]
    val phoneNumber = args[1]
    val snsClient = SnsClient{ region = "us-east-1" }
    pubTextSMS(snsClient, message, phoneNumber)
    snsClient.close()
}

//snippet-start:[sns.kotlin.PublishTextSMS.main]
suspend fun pubTextSMS(snsClient: SnsClient, messageVal: String?, phoneNumberVal: String?) {
    try {
        val request = PublishRequest {
            message = messageVal
            phoneNumber = phoneNumberVal
        }

        val result = snsClient.publish(request)
        println("${result.messageId} message sent.")

    } catch (e: SnsException) {
        println(e.message)
        snsClient.close()
        exitProcess(0)
    }
}
//snippet-end:[sns.kotlin.PublishTextSMS.main]