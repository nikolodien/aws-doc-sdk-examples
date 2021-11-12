//snippet-sourcedescription:[DeleteKeyPair.kt demonstrates how to delete an Amazon Elastic Compute Cloud (Amazon EC2) key pair.]
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

// snippet-start:[ec2.kotlin.delete_key_pair.import]
import aws.sdk.kotlin.services.ec2.Ec2Client
import aws.sdk.kotlin.services.ec2.model.DeleteKeyPairRequest
import aws.sdk.kotlin.services.ec2.model.Ec2Exception
import kotlin.system.exitProcess
// snippet-end:[ec2.kotlin.delete_key_pair.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>) {

    val usage = """

    Usage:
        <keyName> 

    Where:
        keyName - a key pair name (for example, TestKeyPair). 
    """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
    }

    val keyName = args[0]
    val ec2Client = Ec2Client{region = "us-west-2"}
    deleteKeys(ec2Client, keyName)
}

// snippet-start:[ec2.kotlin.delete_key_pair.main]
suspend fun deleteKeys(ec2: Ec2Client, keyPair: String?) {
    try {
        val request = DeleteKeyPairRequest {
            keyName = keyPair
        }
        ec2.deleteKeyPair(request)
        println("Successfully deleted key pair named $keyPair")

    } catch (e: Ec2Exception) {
        println(e.message)
        exitProcess(0)
    }
}
// snippet-end:[ec2.kotlin.delete_key_pair.main]