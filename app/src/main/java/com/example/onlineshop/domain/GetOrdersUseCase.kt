package com.example.onlineshop.domain


class GetOrdersUseCase(private val onlineShopRepository: OnlineShopRepository) {
    suspend fun execute(): List<Order> {
        return onlineShopRepository.getOrders()
    }
}