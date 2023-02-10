package com.example.onlineshop.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OrderDto(
    @SerialName("products")
    val products: List<ProductDto>,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("status")
    val status: String,
    @SerialName("total_price")
    val total_price: Int,
    @SerialName("id")
    val id: String,
    @SerialName("receiver_name")
    val receiver_name: String,
)

@Serializable
data class ProductDto(
    @SerialName("count")
    val count: Int,
    @SerialName("image_url")
    val image_url: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("id")
    val id: String,
)

@Serializable
data class OrderListDto(
    @SerialName("orders")
    val orders: List<OrderDto>,
)