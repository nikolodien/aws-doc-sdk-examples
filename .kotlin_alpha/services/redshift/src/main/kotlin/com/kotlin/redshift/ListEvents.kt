//snippet-sourcedescription:[ListEvents.kt demonstrates how to list events for a given cluster.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon Redshift ]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon - aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/
package com.kotlin.redshift

// snippet-start:[redshift.kotlin._events.import]
import aws.sdk.kotlin.services.redshift.RedshiftClient
import aws.sdk.kotlin.services.redshift.model.DescribeEventsRequest
import aws.sdk.kotlin.services.redshift.model.RedshiftException
import aws.sdk.kotlin.services.redshift.model.SourceType
import kotlin.system.exitProcess
// snippet-end:[redshift.kotlin._events.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */
suspend fun main(args:Array<String>) {

        val usage = """
        Usage:
            <clusterId> <eventSourceType> 

        Where:
            clusterId - the id of the cluster. 
            eventSourceType - the event type (ie, cluster).
        """

        if (args.size != 2) {
            println(usage)
            exitProcess(0)
        }

    val clusterId = args[0]
    val eventSourceType = args[1]

    val redshiftClient = RedshiftClient{region="us-west-2"}
    listRedShiftEvents(redshiftClient, clusterId, eventSourceType)
    redshiftClient.close()
}

// snippet-start:[redshift.kotlin._events.main]
suspend fun listRedShiftEvents(redshiftClient: RedshiftClient, clusterId: String?, eventSourceType: String) {
    try {

        val describeEventsRequest = DescribeEventsRequest {
            sourceIdentifier=clusterId
            sourceType = SourceType.fromValue(eventSourceType)
            startTime =aws.smithy.kotlin.runtime.time.Instant.fromEpochSeconds("1634058260")
            maxRecords = 20
        }

        val eventsResponse = redshiftClient.describeEvents(describeEventsRequest)
        eventsResponse.events?.forEach { event ->
              println("Source type is ${event.sourceType}")
              println("Event message is ${event.message}")
        }


    } catch (e: RedshiftException) {
        println(e.message)
        redshiftClient.close()
        exitProcess(0)
    }
    println("The example is done")

}
// snippet-end:[redshift.kotlin._events.main]