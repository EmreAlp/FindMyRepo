package com.ing.findmyrepo.data.network

import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()

        when {

            response.isSuccessful -> {
                return response.body()!!
            }

            else -> {

                val error = JSONObject(response.errorBody()!!.string())
                val message = error.getString("message")

                throw ApiException(message)
            }
        }
    }
}