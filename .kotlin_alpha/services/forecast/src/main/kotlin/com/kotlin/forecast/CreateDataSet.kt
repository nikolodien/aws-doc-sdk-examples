//snippet-sourcedescription:[CreateDataSet.kt demonstrates how to create a data set for the Amazon Forecast service.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon Forecast]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/04/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.forecast

// snippet-start:[forecast.kotlin.create_forecast_dataset.import]
import aws.sdk.kotlin.services.forecast.model.ForecastException
import aws.sdk.kotlin.services.forecast.ForecastClient
import aws.sdk.kotlin.services.forecast.model.Schema
import aws.sdk.kotlin.services.forecast.model.SchemaAttribute
import aws.sdk.kotlin.services.forecast.model.CreateDatasetRequest
import aws.sdk.kotlin.services.forecast.model.Domain
import aws.sdk.kotlin.services.forecast.model.AttributeType
import aws.sdk.kotlin.services.forecast.model.DatasetType
import kotlin.system.exitProcess
// snippet-end:[forecast.kotlin.create_forecast_dataset.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>) {

    val usage = """
    Usage:
        <name>  

    Where:
        name - the name of the data set. 
           """

      if (args.size != 1) {
          println(usage)
          exitProcess(0)
     }

    val name = args[0]
    val forecast = ForecastClient{ region = "us-west-2"}
    val myDataSetARN = createForecastDataSet(forecast, name)
    println("The ARN of the new data set is $myDataSetARN")
    forecast.close()
}

// snippet-start:[forecast.kotlin.create_forecast_dataset.main]
suspend fun createForecastDataSet(forecast: ForecastClient, name: String?): String? {
        try {

           val schemaOb = Schema {
                attributes = getSchema()
            }

            val datasetRequest = CreateDatasetRequest {
                datasetName = name
                domain = Domain.fromValue("CUSTOM")
                datasetType = DatasetType.fromValue("RELATED_TIME_SERIES")
                dataFrequency = "D"
                schema = schemaOb
            }

            val response = forecast.createDataset(datasetRequest)
            return response.datasetArn

        } catch (ex: ForecastException) {
            println(ex.message)
            forecast.close()
            exitProcess(0)
        }
    }

 // Create a SchemaAttribute list required to create a data set.
 private fun getSchema(): MutableList<SchemaAttribute> {

        val schemaList = mutableListOf<SchemaAttribute>()

        val att1 = SchemaAttribute {
            attributeName = "item_id"
            attributeType = AttributeType.fromValue("string")
        }

        val att2 = SchemaAttribute {
            attributeName = "timestamp"
            attributeType = AttributeType.fromValue("timestamp")
        }

        val att3 = SchemaAttribute {
            attributeName = "target_value"
            attributeType = AttributeType.fromValue("float")
        }

        //Push the SchemaAttribute objects to the List.
        schemaList.add(att1)
        schemaList.add(att2)
        schemaList.add(att3)

        return schemaList
}
// snippet-end:[forecast.kotlin.create_forecast_dataset.main]