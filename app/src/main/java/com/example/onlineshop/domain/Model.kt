package com.example.onlineshop.domain

data class Order(
    val products: List<Product>,
    val created_at: Long,
    val status: String,
    val total_price: Int,
    val id: String,
    val receiver_name: String,
)

data class Product(
    val count: Int,
    val image_url: String,
    val name: String,
    val price: Int,
    val id: String,
)
data class OrderList(
    val orders: List<Order>,
)
