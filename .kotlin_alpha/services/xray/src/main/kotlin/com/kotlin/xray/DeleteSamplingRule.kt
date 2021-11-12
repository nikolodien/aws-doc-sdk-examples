//snippet-sourcedescription:[DeleteSamplingRule.kt demonstrates how to create a rule to control sampling behavior for instrumented applications.]
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

// snippet-start:[xray.kotlin_delete_rule.import]
import aws.sdk.kotlin.services.xray.XRayClient
import aws.sdk.kotlin.services.xray.model.*
import aws.sdk.kotlin.services.xray.model.XRayException
import kotlin.system.exitProcess
// snippet-end:[xray.kotlin_delete_rule.import]

suspend fun main(args:Array<String>) {

    val usage = """
        
        Usage: 
            <ruleName>
        
        Where:
            ruleName - the name of the rule. 
                
        """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
     }

    val ruleName = args[0]
    val xRayClient = XRayClient{region = "us-east-1"}
    deleteRule(xRayClient,ruleName)
    xRayClient.close()
}

// snippet-start:[xray.kotlin_delete_rule.main]
suspend fun deleteRule(xRayClient: XRayClient, ruleNameVal: String?) {
        try {
            val ruleRequest = DeleteSamplingRuleRequest {
                ruleName = ruleNameVal
            }

            xRayClient.deleteSamplingRule(ruleRequest)
            println("$ruleNameVal was deleted")

        } catch (ex: XRayException) {
            println(ex.message)
            xRayClient.close()
            exitProcess(0)
        }
 }
// snippet-end:[xray.kotlin_delete_rule.main]