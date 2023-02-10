package com.example.onlineshop.data

import com.example.onlineshop.domain.OnlineShopRepository
import com.example.onlineshop.domain.Order

class OnlineShopRepositoryImpl(private val api: OnlineShopApi) : OnlineShopRepository {
    override suspend fun getOrders(): List<Order>{
        return api.getOrders().body()?.map {
            it.toOrder()
        }?:run{
            listOf()
        }
    }
}