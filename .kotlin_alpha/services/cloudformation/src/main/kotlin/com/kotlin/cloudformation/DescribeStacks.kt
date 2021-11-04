// snippet-comment:[These are tags for the AWS doc team's sample catalog. Do not remove.]
// snippet-sourcedescription:[DescribeStacks.kt demonstrates how to obtain information about stacks.]
//snippet-keyword:[AWS SDK for Kotlin]
// snippet-service:[AWS CloudFormation]
// snippet-keyword:[Code Sample]
// snippet-sourcetype:[full-example]
// snippet-sourcedate:[11/03/2021]
// snippet-sourceauthor:[AWS-scmacdon]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/


package com.kotlin.cloudformation

// snippet-start:[cf.kotlin.get_stacks.import]
import aws.sdk.kotlin.services.cloudformation.CloudFormationClient
import aws.sdk.kotlin.services.cloudformation.model.DescribeStacksResponse
import aws.sdk.kotlin.services.cloudformation.model.CloudFormationException
import aws.sdk.kotlin.services.cloudformation.model.DescribeStacksRequest
import kotlin.system.exitProcess
// snippet-end:[cf.kotlin.get_stacks.import]

suspend fun main() {

    val cfClient = CloudFormationClient{region="us-east-1"}
    describeAllStacks(cfClient)
    cfClient.close()
}

// snippet-start:[cf.kotlin.get_stacks.main]
suspend fun describeAllStacks(cfClient: CloudFormationClient) {
    try {
        val stacksResponse: DescribeStacksResponse = cfClient.describeStacks(DescribeStacksRequest{})
        stacksResponse.stacks?.forEach { stack ->
            println("The stack description is ${stack.description}")
            println("The stack Id is ${stack.stackId}" )
        }

    }catch (e: CloudFormationException) {
        println(e.message)
        cfClient.close()
        exitProcess(0)
    }
}
// snippet-end:[cf.kotlin.get_stacks.main]