//snippet-sourcedescription:[DescribeTrainingJob.kt demonstrates how to obtain information about a training job.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-keyword:[Amazon SageMaker]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon - AWS]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.sage

//snippet-start:[sagemaker.kotlin.describe_train_job.import]
import aws.sdk.kotlin.services.sagemaker.SageMakerClient
import aws.sdk.kotlin.services.sagemaker.model.DescribeTrainingJobRequest
import aws.sdk.kotlin.services.sagemaker.model.SageMakerException
import kotlin.system.exitProcess
//snippet-end:[sagemaker.kotlin.describe_train_job.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */
suspend fun main(args:Array<String>) {

    val usage = """
    Usage:
        <trainingJobName>

    Where:
        trainingJobName - the name of the training job.

    """

    if (args.size != 1) {
       println(usage)
       exitProcess(1)
    }

    val trainingJobName = args[0]
    val sageMakerClient = SageMakerClient{region = "us-west-2" }
    describeTrainJob(sageMakerClient, trainingJobName)
    sageMakerClient.close()
}

//snippet-start:[sagemaker.kotlin.describe_train_job.main]
suspend fun describeTrainJob(sageMakerClient: SageMakerClient, trainingJobNameVal: String?) {
    try {
        val trainingJobRequest = DescribeTrainingJobRequest {
            trainingJobName = trainingJobNameVal
        }

        val jobResponse = sageMakerClient.describeTrainingJob(trainingJobRequest)
        println("The job status is ${jobResponse.trainingJobStatus.toString()}")

    } catch (e: SageMakerException) {
        println(e.message)
        sageMakerClient.close()
        exitProcess(0)
    }
}
//snippet-end:[sagemaker.kotlin.describe_train_job.main]