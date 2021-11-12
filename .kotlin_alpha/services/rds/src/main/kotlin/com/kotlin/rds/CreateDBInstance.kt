//snippet-sourcedescription:[CreateDBInstance.kt demonstrates how to create an Amazon Relational Database Service (RDS) instance and wait for it to be in an available state.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon Relational Database Service]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/05/2021]
//snippet-sourceauthor:[scmacdon - aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.rds

// snippet-start:[rds.kotlin.create_instance.import]
import aws.sdk.kotlin.services.rds.RdsClient
import aws.sdk.kotlin.services.rds.model.CreateDbInstanceRequest
import aws.sdk.kotlin.services.rds.model.DescribeDbInstancesRequest
import aws.sdk.kotlin.services.rds.model.RdsException
import kotlinx.coroutines.delay
import kotlin.system.exitProcess
// snippet-end:[rds.kotlin.create_instance.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>) {

    val usage = """
        Usage:
            <dbInstanceIdentifier> <dbName> <masterUsername> <masterUserPassword> 

        Where:
            dbInstanceIdentifier - the database instance identifier. 
            dbName - the database name. 
            masterUsername - the master user name. 
            masterUserPassword - the password that corresponds to the master user name. 
        """

    if (args.size != 4) {
      println(usage)
      exitProcess(0)
    }

    val dbInstanceIdentifier = args[0]
    val dbName = args[1]
    val masterUsername = args[2]
    val masterUserPassword = args[3]
    val rdsClient = RdsClient{region="us-west-2"}
    createDatabaseInstance(rdsClient, dbInstanceIdentifier, dbName, masterUsername, masterUserPassword)
    waitForInstanceReady(rdsClient,dbInstanceIdentifier)
    rdsClient.close()
}

// snippet-start:[rds.kotlin.create_instance.main]
suspend fun createDatabaseInstance(
    rdsClient: RdsClient,
    dbInstanceIdentifierVal: String?,
    dbNamedbVal: String?,
    masterUsernameVal: String?,
    masterUserPasswordVal: String?
) {

    try {
        val instanceRequest = CreateDbInstanceRequest{
            dbInstanceIdentifier = dbInstanceIdentifierVal
            allocatedStorage = 100
            dbName = dbNamedbVal
            engine = "mysql"
            dbInstanceClass = "db.m4.large"
            engineVersion = "8.0.15"
            storageType = "standard"
            masterUsername = masterUsernameVal
            masterUserPassword = masterUserPasswordVal
            }

        val response = rdsClient.createDbInstance(instanceRequest)
        print("The status is ${response.dbInstance?.dbInstanceStatus}")

    } catch (e: RdsException) {
        println(e.message)
        rdsClient.close()
        exitProcess(0)
    }
}

// Waits until the database instance is available.
suspend  fun waitForInstanceReady(rdsClient: RdsClient, dbInstanceIdentifierVal: String?) {
    val sleepTime: Long = 20
    var instanceReady = false
    var instanceReadyStr = ""
    println("Waiting for instance to become available.")
    try {
        val instanceRequest = DescribeDbInstancesRequest {
            dbInstanceIdentifier = dbInstanceIdentifierVal
        }

        // Loop until the instance is ready.
        while (!instanceReady) {
            val response = rdsClient.describeDbInstances(instanceRequest)
            val instanceList = response.dbInstances
            if (instanceList != null) {

                for (instance in instanceList) {
                    instanceReadyStr = instance.dbInstanceStatus.toString()
                    if (instanceReadyStr.contains("available"))
                        instanceReady = true
                    else {
                        println("...$instanceReadyStr")
                        delay(sleepTime * 1000)
                    }
                }
            }
        }
        println("Database instance is available!")
    } catch (e: RdsException) {
        println(e.message)
        exitProcess(0)
    } catch (e: InterruptedException) {
        println(e.message)
        exitProcess(0)
    }
}
// snippet-end:[rds.kotlin.create_instance.main]