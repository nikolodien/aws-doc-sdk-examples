//snippet-sourcedescription:[ListCampaigns.kt demonstrates how to list Amazon Personalize campaigns.]
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

//snippet-start:[personalize.kotlin.list_campaigns.import]
import aws.sdk.kotlin.services.personalize.PersonalizeClient
import aws.sdk.kotlin.services.personalize.model.ListCampaignsRequest
import aws.sdk.kotlin.services.personalize.model.PersonalizeException
import kotlin.system.exitProcess
//snippet-end:[personalize.kotlin.list_campaigns.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>){

    val usage = """
    Usage:
        <solutionArn>

    Where:
         solutionArn - the ARN of the solution.
    """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
     }

    val solutionArn = args[0]
    val personalizeClient = PersonalizeClient{ region = "us-east-1" }
    listAllCampaigns(personalizeClient,solutionArn)
    personalizeClient.close()
}

//snippet-start:[personalize.kotlin.list_campaigns.main]
suspend fun listAllCampaigns(personalizeClient: PersonalizeClient, solutionArnVal: String?) {
        try {

            val campaignsRequest= ListCampaignsRequest {
                maxResults =10
                solutionArn = solutionArnVal
            }

            val response = personalizeClient.listCampaigns(campaignsRequest)
            response.campaigns?.forEach { campaign ->
                    println("Campaign name is ${campaign.name}")
                    println("Campaign ARN is ${campaign.campaignArn}")
            }

        } catch (ex: PersonalizeException) {
            println(ex.message)
            personalizeClient.close()
            exitProcess(0)
        }
}
//snippet-end:[personalize.kotlin.list_campaigns.main]