package com.example.onlineshop.data


import retrofit2.Response
import retrofit2.http.*

interface OnlineShopApi {
    @GET("orders")
    suspend fun getOrders(): Response<List<OrderDto>>

}