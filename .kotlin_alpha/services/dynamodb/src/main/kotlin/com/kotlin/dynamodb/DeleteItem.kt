//snippet-sourcedescription:[DeleteItem.kt demonstrates how to delete an item from an Amazon DynamoDB table.]
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


// snippet-start:[dynamodb.kotlin.delete_item.import]
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.DeleteItemRequest
import aws.sdk.kotlin.services.dynamodb.model.DynamoDbException
import kotlin.system.exitProcess
// snippet-end:[dynamodb.kotlin.delete_item.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */
suspend fun main(args: Array<String>) {

    val usage = """
    Usage:
        <tableName> <key> <keyval>

    Where:
        tableName - the Amazon DynamoDB table to delete the item from (for example, Music3).
        key - the key used in the Amazon DynamoDB table (for example, Artist). 
        keyval - the key value that represents the item to delete (for example, Famous Band).
    """

    if (args.size != 3) {
        println(usage)
        exitProcess(0)
    }

    val tableName = args[0]
    val key = args[1]
    val keyVal = args[2]
    val ddb = DynamoDbClient{ region = "us-east-1" }
    deleteDymamoDBItem(ddb, tableName, key, keyVal)
    ddb.close()
}

// snippet-start:[dynamodb.kotlin.delete_item.main]
suspend fun deleteDymamoDBItem(ddb: DynamoDbClient, tableNameVal: String, keyName: String, keyVal: String) {

        val keyToGet = mutableMapOf<String, AttributeValue>()
        keyToGet[keyName] = AttributeValue.S(keyVal)

        val deleteReq = DeleteItemRequest {
            tableName = tableNameVal
            key = keyToGet
        }

        try {
            ddb.deleteItem(deleteReq)
            println("Item with key matching $keyVal was deleted")

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
 }
// snippet-end:[dynamodb.kotlin.delete_item.main]