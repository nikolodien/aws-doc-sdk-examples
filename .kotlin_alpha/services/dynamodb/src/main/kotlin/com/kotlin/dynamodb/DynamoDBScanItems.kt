//snippet-sourcedescription:[DynamoDBScanItems.kt demonstrates how to return items from an Amazon DynamoDB table.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon DynamoDB]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/04/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/


package com.kotlin.dynamodb

// snippet-start:[dynamodb.kotlin.scan_items.import]
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.ScanRequest
import aws.sdk.kotlin.services.dynamodb.model.DynamoDbException
import kotlin.system.exitProcess
// snippet-end:[dynamodb.kotlin.scan_items.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */
suspend fun main(args: Array<String>) {

    val usage = """
    Usage:
        <tableName>

    Where:
        tableName - the Amazon DynamoDB table to scan (for example, Music3).
    """

     if (args.size != 1) {
           println(usage)
           exitProcess(0)
       }

    val tableName = args[0]
    val ddb = DynamoDbClient{ region = "us-east-1" }
    scanItems(ddb, tableName)
    ddb.close()
}

// snippet-start:[dynamodb.kotlin.scan_items.main]
suspend fun scanItems(ddb: DynamoDbClient, tableNameVal: String) {
        try {

            val scanRequest = ScanRequest {
                tableName = tableNameVal
            }

            val response = ddb.scan(scanRequest)
            response.items?.forEach { item ->
                 item.keys.forEach { key ->
                     println("The key name is $key\n")
                     println("The value is ${item[key]}")
                }
            }

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
 }
// snippet-end:[dynamodb.kotlin.scan_items.main]