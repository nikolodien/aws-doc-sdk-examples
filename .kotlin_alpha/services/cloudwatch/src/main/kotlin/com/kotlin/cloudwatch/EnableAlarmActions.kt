//snippet-sourcedescription:[EnableAlarmActions.kt demonstrates how to enable actions on a CloudWatch alarm.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon CloudWatch]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/03/2021]
//snippet-sourceauthor:[scmacdon - aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.cloudwatch

// snippet-start:[cloudwatch.kotlin.enable_alarm_actions.import]
import aws.sdk.kotlin.services.cloudwatch.CloudWatchClient
import aws.sdk.kotlin.services.cloudwatch.model.EnableAlarmActionsRequest
import aws.sdk.kotlin.services.cloudwatch.model.CloudWatchException
import kotlin.system.exitProcess
// snippet-end:[cloudwatch.kotlin.enable_alarm_actions.import]

suspend fun main(args:Array<String>) {

    val usage  = """

    Usage:
        <alarmName> 

    Where:
        alarmName - an alarm name to enable.
    """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
     }

    val alarmName = args[0]
    val cwClient = CloudWatchClient{region="us-east-1"}
    enableActions(cwClient, alarmName)
    cwClient.close()
}

// snippet-start:[cloudwatch.kotlin.enable_alarm_actions.main]
suspend fun enableActions(cwClient: CloudWatchClient, alarm: String) {
    try {
        val request = EnableAlarmActionsRequest {
            alarmNames = listOf(alarm)
        }

        cwClient.enableAlarmActions(request)
        println( "Successfully enabled actions on alarm $alarm")

    } catch (ex: CloudWatchException) {
        println(ex.message)
        cwClient.close()
        exitProcess(0)
    }
}
// snippet-end:[cloudwatch.kotlin.enable_alarm_actions.main]