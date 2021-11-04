// snippet-comment:[These are tags for the AWS doc team's sample catalog. Do not remove.]
// snippet-sourcedescription:[CreateStack.kt demonstrates how to create a stack based on a template and wait until it's ready by using a waiter.]
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

// snippet-start:[cf.kotlin.create_stack.import]
import aws.sdk.kotlin.services.cloudformation.CloudFormationClient
import aws.sdk.kotlin.services.cloudformation.model.CreateStackRequest
import aws.sdk.kotlin.services.cloudformation.model.Parameter
import aws.sdk.kotlin.services.cloudformation.model.OnFailure
import aws.sdk.kotlin.services.cloudformation.model.CloudFormationException
import kotlin.system.exitProcess
// snippet-end:[cf.kotlin.create_stack.import]

suspend fun main(args:Array<String>) {

    val usage = """
    Usage:
        <stackName> <roleARN> <location> <key> <value> 

    Where:
        stackName - the name of the AWS CloudFormation stack. 
        roleARN - the ARN of the role that has AWS CloudFormation permissions. 
        location - the location of file containing the template body. (for example, https://s3.amazonaws.com/<bucketname>/template.yml). 
        key - the key associated with the parameter. 
        value - the value associated with the parameter. 
    """

    if (args.size != 5) {
        println(usage)
        exitProcess(0)
    }

    val stackName = args[0]
    val roleARN = args[1]
    val location = args[2]
    val key = args[3]
    val value = args[4]
    val cfClient = CloudFormationClient{region="us-east-1"}
    createCFStack(cfClient, stackName, roleARN, location, key, value)
    cfClient.close()
}

// snippet-start:[cf.kotlin.create_stack.main]
suspend fun createCFStack(
    cfClient: CloudFormationClient,
    stackNameVal: String,
    roleARNVal: String?,
    location: String?,
    key: String?,
    value: String?
) {
    try {

        val myParameter = Parameter {
            parameterKey = key
            parameterValue = value
        }

        val stackRequest = CreateStackRequest {
            stackName = stackNameVal
            templateUrl = location
            this.roleArn = roleARNVal
            onFailure = OnFailure.Rollback
            parameters = listOf(myParameter)
        }
        cfClient.createStack(stackRequest)
        println("$stackNameVal was created")

    } catch (e: CloudFormationException) {
        println(e.message)
        cfClient.close()
        exitProcess(0)
    }
}
// snippet-end:[cf.kotlin.create_stack.main]