//snippet-sourcedescription:[GetGroups.kt demonstrates how to retrieve all active group details.]
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

// snippet-start:[xray.kotlin_get_groups.import]
import aws.sdk.kotlin.services.xray.XRayClient
import aws.sdk.kotlin.services.xray.model.XRayException
import aws.sdk.kotlin.services.xray.model.GetGroupsRequest
import kotlin.system.exitProcess
// snippet-end:[xray.kotlin_get_groups.import]

suspend fun main() {

    val xRayClient = XRayClient{region = "us-east-1"}
    getAllGroups(xRayClient)
    xRayClient.close()
}

// snippet-start:[xray.kotlin_get_groups.main]
 suspend fun getAllGroups(xRayClient: XRayClient) {
        try {

            val response = xRayClient.getGroups(GetGroupsRequest{})
            response.groups?.forEach { group ->
                 println("The AWS X-Ray group name is ${group.groupName}")
                }

        } catch (ex: XRayException) {
            println(ex.message)
            xRayClient.close()
            exitProcess(0)
        }
 }
// snippet-end:[xray.kotlin_get_groups.main]


