package com.isil.android.pa3.data.repository

import com.google.gson.Gson
import com.isil.android.pa3.data.model.MensajeResponse
import retrofit2.Response
import java.io.IOException

// Ejecuta una llamada a la API y la convierte en Result (éxito o error con mensaje)
suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.success(body)
        } else {
            val mensaje = try {
                Gson().fromJson(response.errorBody()?.string(), MensajeResponse::class.java)?.mensaje
            } catch (e: Exception) {
                null
            }
            Result.failure(Exception(mensaje ?: "Error ${response.code()}"))
        }
    } catch (e: IOException) {
        Result.failure(Exception("No se pudo conectar con la API. Revisa la IP y que estés en la misma red WiFi."))
    } catch (e: Exception) {
        Result.failure(Exception(e.message ?: "Error inesperado"))
    }
}