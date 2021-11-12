//snippet-sourcedescription:[CreateStateMachine.kt demonstrates how to creates a state machine for AWS Step Functions.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[AWS Step Functions]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon-AWS]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.stepfunctions

// snippet-start:[stepfunctions.kotlin.create_machine.import]
import aws.sdk.kotlin.services.sfn.model.SfnException
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import aws.sdk.kotlin.services.sfn.SfnClient
import aws.sdk.kotlin.services.sfn.model.CreateStateMachineRequest
import aws.sdk.kotlin.services.sfn.model.StateMachineType
import java.io.FileReader
import java.io.IOException
import kotlin.system.exitProcess
// snippet-end:[stepfunctions.kotlin.create_machine.import]

suspend fun main(args:Array<String>){

    val usage = """
      Usage:
         <jsonFile> <roleARN> <stateMachineName>
      Where:
        jsonFile - A JSON file that represents the Amazon States Language definition of the state machine.
        roleARN - The Amazon Resource Name (ARN) of the IAM role to use for this state machine.
        stateMachineName - The name of the state machine to create.

    """

    if (args.size != 3) {
       println(usage)
       exitProcess(0)
     }

    val jsonFile = args[0]
    val roleARN = args[1]
    val stateMachineName = args[2]

    val sfnClient = SfnClient{region = "us-east-1" }
    val smARN = createMachine(sfnClient, roleARN, stateMachineName, jsonFile)
    println("The ARN of the new state machine is $smARN")
    sfnClient.close()
}

// snippet-start:[stepfunctions.kotlin.create_machine.main]
suspend fun createMachine(sfnClient: SfnClient, roleARNVal: String?, stateMachineName: String?, jsonFile: String): String? {
        val json = getJSONString(jsonFile)

        try {
            val machineRequest = CreateStateMachineRequest {
                definition= json
                name = stateMachineName
                roleArn= roleARNVal
                type = StateMachineType.Standard
            }

            val response = sfnClient.createStateMachine(machineRequest)
            return response.stateMachineArn

        } catch (ex: SfnException) {
            println(ex.message)
            sfnClient.close()
            exitProcess(0)
        }
        return ""
    }

    private fun getJSONString(path: String): String {
        try {
            val parser = JSONParser()
            val data: JSONObject = parser.parse(FileReader(path)) as JSONObject //path to the JSON file.
            return data.toJSONString()

        } catch (e: IOException) {
            print(e.message)
        } catch (e: org.json.simple.parser.ParseException) {
            print(e.message)
        }
        return ""
 }
// snippet-end:[stepfunctions.kotlin.create_machine.main]