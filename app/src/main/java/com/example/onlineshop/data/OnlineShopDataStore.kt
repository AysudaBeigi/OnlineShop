package com.example.onlineshop.data

import com.example.onlineshop.domain.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class OnlineShopDataStore {
    private val ordersFlow = MutableStateFlow<List<Order>?>(null)
    fun updateOrders(orders: List<Order>) {
        ordersFlow.value = orders
    }

    fun getOrders(): Flow<List<Order>> {
        return ordersFlow.filterNotNull()
    }
}