//snippet-sourcedescription:[DescribeVPCs.kt demonstrates how to get information about all the Amazon Elastic Compute Cloud (Amazon EC2) VPCs.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon EC2]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/04/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.ec2

// snippet-start:[ec2.kotlin.describe_vpc.import]
import aws.sdk.kotlin.services.ec2.Ec2Client
import aws.sdk.kotlin.services.ec2.model.DescribeVpcsRequest
import aws.sdk.kotlin.services.ec2.model.Ec2Exception
import kotlin.system.exitProcess
// snippet-end:[ec2.kotlin.describe_vpc.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>) {

    val usage = """

    Usage:
        <vpcId> 

    Where:
        vpcId - a VPC ID that you can obtain from the AWS Management Console (for example, vpc-xxxxxf2f).
    """

    if (args.size != 1) {
         println(usage)
         exitProcess(0)
    }

    val vpcId = args[0]
    val ec2Client = Ec2Client{region = "us-west-2"}
    describeEC2Vpcs(ec2Client,vpcId )
    ec2Client.close()
}

// snippet-start:[ec2.kotlin.describe_vpc.main]
suspend fun describeEC2Vpcs(ec2: Ec2Client, vpcId: String) {
    try {
        val request = DescribeVpcsRequest {
            vpcIds = listOf(vpcId)
        }

        val response = ec2.describeVpcs(request)
        response.vpcs?.forEach { vpc ->
            println("Found VPC with id ${vpc.vpcId} VPC state ${vpc.state} and tenancy ${vpc.instanceTenancy}")
        }

    } catch (e: Ec2Exception) {
        println(e.message)
        exitProcess(0)
    }
}
// snippet-end:[ec2.kotlin.describe_vpc.main]
