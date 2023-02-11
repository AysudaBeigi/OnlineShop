package com.example.onlineshop.presentation

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.onlineshop.data.OnlineShopDataStore
import com.example.onlineshop.domain.OnlineShopRepository
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class PollingOrdersService : Service() {

    private val repository: OnlineShopRepository by inject()
    private val dataStore: OnlineShopDataStore by inject()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        pollOrders()
        return START_STICKY
    }

    private fun pollOrders() {
        //todo it should be use socket
        scope.launch {
            dataStore.updateOrders(repository.getOrders())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancelChildren()
    }
}