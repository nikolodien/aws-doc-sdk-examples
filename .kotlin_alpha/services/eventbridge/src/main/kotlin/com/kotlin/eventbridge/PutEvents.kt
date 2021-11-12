// snippet-sourcedescription:[PutEvents.kt demonstrates how to send custom events to Amazon EventBridge.]
//snippet-keyword:[AWS SDK for Kotlin]
// snippet-service:[Amazon EventBridge]
// snippet-keyword:[Code Sample]
// snippet-sourcetype:[full-example]
// snippet-sourcedate:[11/04/2021]
// snippet-sourceauthor:[scmacdon - AWS]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.eventbridge

// snippet-start:[eventbridge.kotlin._put_event.import]
import aws.sdk.kotlin.services.eventbridge.EventBridgeClient
import aws.sdk.kotlin.services.eventbridge.model.PutEventsRequest
import aws.sdk.kotlin.services.eventbridge.model.PutEventsRequestEntry
import aws.sdk.kotlin.services.eventbridge.model.EventBridgeException
import java.util.ArrayList
import kotlin.system.exitProcess
// snippet-end:[eventbridge.kotlin._put_event.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args: Array<String>) {

    val usage =
        """
        To run this example, supply two resources, identified by Amazon Resource Name (ARN), which the event primarily concerns. Any number, including zero, may be present. 
        For example: <resourceArn> <resourceArn2>
        
        """

    if (args.size != 2) {
       println(usage)
       exitProcess(0)
    }

    val resourceArn = args[0]
    val resourceArn2 = args[1]
    val eventbridgeClient = EventBridgeClient{region="us-west-2"}
    putEBEvents(eventbridgeClient,resourceArn,resourceArn2)
    eventbridgeClient.close()
}

// snippet-start:[eventbridge.kotlin._put_event.main]
suspend fun putEBEvents(eventBrClient: EventBridgeClient, resourceArn: String, resourceArn2: String) {
        try {
            // Populate a List with the resource ARN values.
            val resourcesOb  = mutableListOf<String>()
            resourcesOb.add(resourceArn)
            resourcesOb.add(resourceArn2)

            val reqEntry = PutEventsRequestEntry {
                resources = resourcesOb
                source = "com.mycompany.myapp"
                detailType = "myDetailType"
                detail = "{ \"key1\": \"value1\", \"key2\": \"value2\" }"
            }

            val eventsRequest = PutEventsRequest {
                entries = listOf(reqEntry)
            }

            val response = eventBrClient.putEvents(eventsRequest)
            response.entries?.forEach { resultEntry ->

               if (resultEntry.eventId != null) {
                    println("Event Id is ${resultEntry.eventId}")
                } else {
                    println("Injection failed with Error Code ${resultEntry.errorCode}")
                }
            }

        } catch (ex: EventBridgeException) {
            println(ex.message)
            eventBrClient.close()
            exitProcess(0)
        }
 }
// snippet-end:[eventbridge.kotlin._put_event.main]