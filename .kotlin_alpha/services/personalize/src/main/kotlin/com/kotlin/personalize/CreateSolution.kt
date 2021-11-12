//snippet-sourcedescription:[CreateSolution.kt demonstrates how to create an Amazon Personalize solution.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon Personalize]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon - AWS]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.personalize

//snippet-start:[personalize.kotlin.create_solution.import]
import aws.sdk.kotlin.services.personalize.PersonalizeClient
import aws.sdk.kotlin.services.personalize.model.CreateSolutionRequest
import aws.sdk.kotlin.services.personalize.model.PersonalizeException
import kotlin.system.exitProcess
//snippet-end:[personalize.kotlin.create_solution.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>){

    val usage = """
    Usage:
        <datasetGroupArn> <solutionName> <recipeArn>

    Where:
         datasetGroupArn - The ARN of the data set group.
         solutionName - The name of the solution to create.
         recipeArn - The ARN of the recipe (for example, arn:aws:personalize:::recipe/aws-user-personalization).

    """
    if (args.size != 3) {
        println(usage)
        exitProcess(0)
     }

    val datasetGroupArn = args[0]
    val solutionName = args[1]
    val recipeArn = args[2]

    val personalizeClient = PersonalizeClient{ region = "us-east-1" }
    val solutionArn = createPersonalizeSolution(personalizeClient,datasetGroupArn, solutionName, recipeArn)
    println("The Amazon Personalize solution ARN is $solutionArn")
    personalizeClient.close()
}

//snippet-start:[personalize.kotlin.create_solution.main]
suspend fun createPersonalizeSolution(
        personalizeClient: PersonalizeClient,
        datasetGroupArnVal: String?,
        solutionName: String?,
        recipeArnVal: String?
    ): String? {

    try {

        val solutionRequest = CreateSolutionRequest {
            name = solutionName
            datasetGroupArn = datasetGroupArnVal
            recipeArn = recipeArnVal
        }

        val solutionResponse = personalizeClient.createSolution(solutionRequest)
        return solutionResponse.solutionArn

    } catch (ex: PersonalizeException) {
        println(ex.message)
        personalizeClient.close()
        exitProcess(0)
    }
}
//snippet-end:[personalize.kotlin.create_solution.main]