//snippet-sourcedescription:[DeleteSecurityGroup.kt demonstrates how to delete an Amazon Elastic Compute Cloud (Amazon EC2) Security Group.]
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

// snippet-start:[ec2.kotlin.delete_security_group.import]
import aws.sdk.kotlin.services.ec2.Ec2Client
import aws.sdk.kotlin.services.ec2.model.DeleteSecurityGroupRequest
import aws.sdk.kotlin.services.ec2.model.Ec2Exception
import kotlin.system.exitProcess
// snippet-end:[ec2.kotlin.delete_security_group.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>) {

    val usage = """
        Usage:
            <groupId> 

        Where:
            groupId - a security group id that you can obtain from the AWS Management Console (for example, sg-xxxxxx1c0b65785c3).
        """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
    }

    val groupId = args[0]
    val ec2Client = Ec2Client{region = "us-west-2"}
    deleteEC2SecGroup(ec2Client, groupId)
    ec2Client.close()
}

// snippet-start:[ec2.kotlin.delete_security_group.main]
suspend fun deleteEC2SecGroup(ec2: Ec2Client, groupIdVal: String?) {
    try {
        val request = DeleteSecurityGroupRequest {
            groupId = groupIdVal
        }
        ec2.deleteSecurityGroup(request)
        println("Successfully deleted Security Group with id $groupIdVal")

    } catch (e: Ec2Exception) {
        println(e.message)
        exitProcess(0)
    }
}
// snippet-end:[ec2.kotlin.delete_security_group.main]