package com.example.onlineshop.data

import com.example.onlineshop.domain.Order
import com.example.onlineshop.domain.Product


internal fun OrderDto.toOrder() = Order(
    products = this.products.map { it.toProduct() },
    created_at = created_at,
    status = status,
    total_price = total_price,
    id = id,
    receiver_name = receiver_name
)

internal fun ProductDto.toProduct() =
    Product(count = count, image_url = image_url, name = name, price = price, id = id)