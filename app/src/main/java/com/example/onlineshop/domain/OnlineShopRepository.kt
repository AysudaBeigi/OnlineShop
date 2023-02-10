package com.example.onlineshop.domain

interface OnlineShopRepository {
    suspend fun getOrders(): List<Order>
}