package com.example.onlineshop.domain

import com.example.onlineshop.data.OnlineShopDataStore
import kotlinx.coroutines.flow.Flow


class GetOrdersUseCase(private val onlineShopDataStore: OnlineShopDataStore) {
     fun execute(): Flow<List<Order>> {
        return onlineShopDataStore.getOrders()
    }
}