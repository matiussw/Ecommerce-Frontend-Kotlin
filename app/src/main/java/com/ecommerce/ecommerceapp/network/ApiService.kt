package com.ecommerce.ecommerceapp.network

import com.ecommerce.ecommerceapp.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ========== AUTENTICACIÓN ==========
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body user: User): Response<RegisterResponse>

    // ========== PERFIL DE USUARIO ==========
    @GET("api/users/profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserProfile>

    @PUT("api/users/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body profile: UpdateProfileRequest
    ): Response<UserProfile>

    @PUT("api/auth/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body passwordRequest: ChangePasswordRequest
    ): Response<AuthResponse>

    // ========== ADMINISTRACIÓN DE USUARIOS ==========
    @GET("api/users/")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("search") search: String? = null
    ): Response<UsersResponse>

    @GET("api/users/{id}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<UserProfile>

    @PUT("api/users/{id}/roles")
    suspend fun updateUserRoles(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body roleRequest: UpdateUserRoleRequest
    ): Response<UserProfile>

    @DELETE("api/users/{id}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<AuthResponse>

    // ========== UBICACIONES ==========
    @GET("api/locations/countries")
    suspend fun getCountries(): Response<List<Country>>

    @POST("api/locations/countries")
    suspend fun createCountry(@Body request: CreateLocationRequest): Response<Country>

    @GET("api/locations/states")
    suspend fun getStates(@Query("country_id") countryId: Int? = null): Response<List<State>>

    @POST("api/locations/states")
    suspend fun createState(@Body request: CreateLocationRequest): Response<State>

    @GET("api/locations/cities")
    suspend fun getCities(@Query("state_id") stateId: Int? = null): Response<List<City>>

    @POST("api/locations/cities")
    suspend fun createCity(@Body request: CreateLocationRequest): Response<City>

    @GET("api/auth/roles")
    suspend fun getRoles(): Response<List<Role>>
}