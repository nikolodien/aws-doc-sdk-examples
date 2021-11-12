//snippet-sourcedescription:[CreateSegment.kt demonstrates how to create a segment for a campaign in Amazon Pinpoint.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-keyword:[Amazon Pinpoint]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.pinpoint

//snippet-start:[pinpoint.kotlin.createsegment.import]
import aws.sdk.kotlin.services.pinpoint.PinpointClient
import aws.sdk.kotlin.services.pinpoint.model.AttributeDimension
import aws.sdk.kotlin.services.pinpoint.model.AttributeType
import aws.sdk.kotlin.services.pinpoint.model.RecencyDimension
import aws.sdk.kotlin.services.pinpoint.model.Duration
import aws.sdk.kotlin.services.pinpoint.model.RecencyType
import aws.sdk.kotlin.services.pinpoint.model.SegmentBehaviors
import aws.sdk.kotlin.services.pinpoint.model.SegmentDemographics
import aws.sdk.kotlin.services.pinpoint.model.SegmentLocation
import aws.sdk.kotlin.services.pinpoint.model.SegmentDimensions
import aws.sdk.kotlin.services.pinpoint.model.WriteSegmentRequest
import aws.sdk.kotlin.services.pinpoint.model.CreateSegmentRequest
import aws.sdk.kotlin.services.pinpoint.model.CreateSegmentResponse
import aws.sdk.kotlin.services.pinpoint.model.PinpointException
import kotlin.system.exitProcess
//snippet-end:[pinpoint.kotlin.createsegment.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args: Array<String>) {

    val usage = """
        Usage: <appId> 

        Where:
             appId - the Id value of the application.
      """

     if (args.size != 1) {
         println(usage)
         exitProcess(0)
     }

    val appId = args[0]
    val pinpointClient = PinpointClient { region = "us-east-1" }
    createPinpointSegment(pinpointClient, appId)
    pinpointClient.close()
}

//snippet-start:[pinpoint.kotlin.createsegment.main]
suspend fun createPinpointSegment(pinpoint: PinpointClient, applicationIdVal: String?): String? {

        try {
            val segmentAttributes = mutableMapOf<String, AttributeDimension>()
            val myList = mutableListOf<String>()
            myList.add("Lakers")

            val atts = AttributeDimension {
                attributeType = AttributeType.Inclusive
                values = myList
            }

            segmentAttributes["Team"] = atts
            val recencyDimension = RecencyDimension {
                duration = Duration.fromValue("DAY_30")
                recencyType = RecencyType.fromValue("ACTIVE")
                }

            val segmentBehaviors = SegmentBehaviors {
                recency = recencyDimension
            }

            val segmentLocation = SegmentLocation {}
            val dimensionsOb = SegmentDimensions {
                attributes= segmentAttributes
                behavior = segmentBehaviors
                demographic = SegmentDemographics {}
                location =segmentLocation
            }

            val writeSegmentRequestOb = WriteSegmentRequest {
                name ="MySegment101"
                dimensions =dimensionsOb
            }

            val createSegmentRequest = CreateSegmentRequest{
                applicationId =applicationIdVal
                writeSegmentRequest = writeSegmentRequestOb
                }

            val createSegmentResult: CreateSegmentResponse = pinpoint.createSegment(createSegmentRequest)
            println("Segment ID is ${createSegmentResult.segmentResponse?.id}")
            return createSegmentResult.segmentResponse?.id

        } catch (ex: PinpointException) {
            println(ex.message)
            pinpoint.close()
            exitProcess(0)
        }
 }
//snippet-end:[pinpoint.kotlin.createsegment.main]