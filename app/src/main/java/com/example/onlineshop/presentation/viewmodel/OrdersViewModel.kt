package com.example.onlineshop.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.base.*
import com.example.onlineshop.domain.GetOrdersUseCase
import com.example.onlineshop.domain.Order
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) :
    BaseViewModel(coroutineContexts = coroutineDispatcherProvider) {

    private val _loadableOrders = MutableLiveData<LoadableData<List<Order>>>()
    val loadableOrders: LiveData<LoadableData<List<Order>>>
        get() = _loadableOrders

    init {
        loadOrders()
    }

    private fun loadOrders() {
        _loadableOrders.value = Loading
        launch {
            onBg {
                runCatching {
                    getOrdersUseCase.execute()
                }.onSuccess { list ->
                    _loadableOrders.postValue(Loaded(list))
                }.onFailure {
                    _loadableOrders.postValue(Failed(it))
                }
            }
        }
    }

    fun getOrder(id: String):Order?{
         return _loadableOrders.value?.data?.find {
            id == it.id
        }
    }


}