package com.isil.android.pa3.data.datasource

import com.isil.android.pa3.data.model.LoginRequest
import com.isil.android.pa3.data.model.LoginResponse
import com.isil.android.pa3.data.model.MensajeResponse
import com.isil.android.pa3.data.model.ProductRequest
import com.isil.android.pa3.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Endpoints de la API propia
interface ApiService {

    // ---------- Auth ----------
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // ---------- CRUD Productos (requieren token) ----------
    @GET("api/productos")
    suspend fun listarProductos(): Response<List<ProductResponse>>

    @GET("api/productos/{id}")
    suspend fun obtenerProducto(@Path("id") id: Int): Response<ProductResponse>

    @POST("api/productos")
    suspend fun crearProducto(@Body request: ProductRequest): Response<ProductResponse>

    @PUT("api/productos/{id}")
    suspend fun actualizarProducto(
        @Path("id") id: Int,
        @Body request: ProductRequest
    ): Response<ProductResponse>

    @DELETE("api/productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Int): Response<MensajeResponse>
}