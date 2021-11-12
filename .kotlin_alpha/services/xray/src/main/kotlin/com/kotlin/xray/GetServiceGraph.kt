//snippet-sourcedescription:[GetServiceGraph.kt demonstrates how to retrieve a document that describes services that process incoming requests.]
//snippet-keyword:[SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[AWS X-Ray Service]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.xray

// snippet-start:[xray.kotlin_get_graph.import]
import aws.sdk.kotlin.services.xray.XRayClient
import aws.sdk.kotlin.services.xray.model.GetServiceGraphRequest
import aws.sdk.kotlin.services.xray.model.XRayException
import kotlin.system.exitProcess
// snippet-end:[xray.kotlin_get_graph.import]

suspend fun main(args:Array<String>) {

    val usage = """
        
        Usage: 
            <groupName>
        
        Where:
            groupName - the name of a group based on which you want to generate a graph. 
                
        """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
    }

    val groupName = args[0]
    val xRayClient = XRayClient{region = "us-east-1"}
    getGraph(xRayClient,groupName)
    xRayClient.close()
}

// snippet-start:[xray.kotlin_get_graph.main]
suspend fun getGraph(xRayClient: XRayClient, groupNameVal: String?) {
    try {

        val time = aws.smithy.kotlin.runtime.time.Instant
        val getServiceGraphRequest = GetServiceGraphRequest {
            groupName = groupNameVal
            this.startTime = time.now()
            endTime = time.now()
        }

        val response = xRayClient.getServiceGraph(getServiceGraphRequest)
        response.services?.forEach { service ->
                println("The name of the service is  ${service.name}")
        }


    } catch (ex: XRayException) {
        println(ex.message)
        exitProcess(0)
    }
}
// snippet-end:[xray.kotlin_get_graph.main]