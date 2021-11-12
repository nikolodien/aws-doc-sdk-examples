//snippet-sourcedescription:[SetTopicAttributes.kt demonstrates how to set attributes for an Amazon Simple Notification Service (Amazon SNS) topic.]
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

//snippet-start:[sns.kotlin.SetTopicAttributes.import]
import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.SetTopicAttributesRequest
import aws.sdk.kotlin.services.sns.model.SnsException
import kotlin.system.exitProcess
//snippet-end:[sns.kotlin.SetTopicAttributes.import]

suspend fun main(args:Array<String>) {

    val usage = """
      Usage: 
        <attribute> <topicArn> <value>

      Where:
        attribute - the attribute action to use. Valid parameters are: Policy | DisplayName | DeliveryPolicy .
        topicArn - The ARN of the topic. 
        value - the value for the attribute.
    """

      if (args.size < 3) {
          println(usage)
          exitProcess(0)
      }

    val attribute = args[0]
    val topicArn = args[1]
    val value = args[2]
    val snsClient = SnsClient{ region = "us-east-1" }
    setTopAttr(snsClient, attribute, topicArn, value)
    snsClient.close()
}

//snippet-start:[sns.kotlin.SetTopicAttributes.main]
suspend fun setTopAttr(snsClient: SnsClient, attribute: String?, topicArnVal: String?, value: String?) {
    try {
        val request = SetTopicAttributesRequest {
            attributeName = attribute
            attributeValue = value
            topicArn = topicArnVal
        }

        snsClient.setTopicAttributes(request)
        println("Topic ${request.topicArn} was updated.")

    } catch (e: SnsException) {
        println(e.message)
        snsClient.close()
        exitProcess(0)
    }
}
//snippet-end:[sns.kotlin.SetTopicAttributes.main]