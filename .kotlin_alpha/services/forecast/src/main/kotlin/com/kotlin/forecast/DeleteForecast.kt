//snippet-sourcedescription:[DeleteForecast.kt demonstrates how to delete a forecast that belongs to the Amazon Forecast service.]
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

// snippet-start:[forecast.kotlin.delete_forecast.import]
import aws.sdk.kotlin.services.forecast.model.ForecastException
import aws.sdk.kotlin.services.forecast.ForecastClient
import aws.sdk.kotlin.services.forecast.model.DeleteForecastRequest
import kotlin.system.exitProcess
// snippet-end:[forecast.kotlin.delete_forecast.import]

/**
To run this Kotlin code example, ensure that you have setup your development environment,
including your credentials.

For information, see this documentation topic:
https://docs.aws.amazon.com/sdk-for-kotlin/latest/developer-guide/setup.html
 */

suspend fun main(args:Array<String>) {

    val usage = """
    Usage:
        <forecastArn>

    Where:
       forecastArn - the ARN that belongs to the forecast to delete. 
      """

      if (args.size != 1) {
          println(usage)
          exitProcess(0)
      }

    val forecastArn = args[0]
    val forecast = ForecastClient{ region = "us-west-2"}
    delForecast(forecast, forecastArn)
    forecast.close()
}

// snippet-start:[forecast.kotlin.delete_forecast.main]
suspend  fun delForecast(forecast: ForecastClient, forecastArnVal: String?) {
        try {
            val forecastRequest = DeleteForecastRequest {
                forecastArn = forecastArnVal
            }

            forecast.deleteForecast(forecastRequest)
            println("$forecastArnVal was successfully deleted")

        } catch (ex: ForecastException) {
            println(ex.message)
            forecast.close()
            exitProcess(0)
        }
}
// snippet-end:[forecast.kotlin.delete_forecast.main]