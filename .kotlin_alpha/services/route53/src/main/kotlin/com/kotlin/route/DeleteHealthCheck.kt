// snippet-comment:[These are tags for the AWS doc team's sample catalog. Do not remove.]
// snippet-sourcedescription:[DeleteHealthCheck.kt demonstrates how to delete a health check.]
//snippet-keyword:[AWS SDK for Kotlin]
// snippet-service:[Amazon Route 53]
// snippet-keyword:[Code Sample]
// snippet-sourcetype:[full-example]
// snippet-sourcedate:[11/5/2021]
// snippet-sourceauthor:[AWS - scmacdon]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.route

// snippet-start:[route53.kotlin.delete_health_check.import]
import aws.sdk.kotlin.services.route53.Route53Client
import aws.sdk.kotlin.services.route53.model.DeleteHealthCheckRequest
import aws.sdk.kotlin.services.route53.model.Route53Exception
import kotlin.system.exitProcess
// snippet-end:[route53.kotlin.delete_health_check.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */
suspend fun main(args: Array<String>) {

    val usage = """
    Usage:
        <id> 

    Where:
        id - the health check id. 
    """

      if (args.size != 1) {
          println(usage)
          exitProcess(0)
     }

    val id = args[0]
    val route53Client = Route53Client{region = "AWS_GLOBAL"}
    delHealthCheck(route53Client, id)
    route53Client.close()
}

// snippet-start:[route53.kotlin.delete_health_check.main]
 suspend fun delHealthCheck(route53Client: Route53Client, id: String?) {

        try {
            val delRequest = DeleteHealthCheckRequest {
                healthCheckId = id
            }

            // Delete the Health Check.
            route53Client.deleteHealthCheck(delRequest)
            println("The HealthCheck with id $id was deleted")

        } catch (e: Route53Exception) {
            System.err.println(e.message)
            exitProcess(0)
        }
 }
// snippet-end:[route53.kotlin.delete_health_check.main]