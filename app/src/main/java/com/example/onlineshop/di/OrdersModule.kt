package com.example.onlineshop.di

import com.example.onlineshop.base.coroutineDispatcherProvider
import com.example.onlineshop.data.OnlineShopApi
import com.example.onlineshop.data.OnlineShopDataStore
import com.example.onlineshop.data.OnlineShopRepositoryImpl
import com.example.onlineshop.domain.GetOrdersUseCase
import com.example.onlineshop.domain.OnlineShopRepository
import com.example.onlineshop.presentation.viewmodel.OrdersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val ordersModule = module {
    viewModel {
        OrdersViewModel(get(), coroutineDispatcherProvider())
    }
    factory {
        GetOrdersUseCase(get())
    }
    single<OnlineShopRepository> {
        OnlineShopRepositoryImpl(get())
    }
    single<OnlineShopApi> {
        get<Retrofit>().create(OnlineShopApi::class.java)
    }
    single {
        OnlineShopDataStore()
    }

}