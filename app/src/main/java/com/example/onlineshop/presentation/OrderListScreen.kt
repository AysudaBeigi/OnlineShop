package com.example.onlineshop.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.onlineshop.R
import com.example.onlineshop.base.Failed
import com.example.onlineshop.base.LoadableData
import com.example.onlineshop.base.Loaded
import com.example.onlineshop.base.Loading
import com.example.onlineshop.domain.Order


@Composable
fun OrderListScreen(
    loadableOrders: LiveData<LoadableData<List<Order>>>,
    onOrderClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (val it = loadableOrders.observeAsState().value) {
        is Failed -> {
            Toast.makeText(LocalContext.current, it.throwble.message, Toast.LENGTH_SHORT).show()
        }
        is Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Gray)
            }
        }
        is Loaded -> {
            OrderList(
                list = loadableOrders.observeAsState().value?.data ?: listOf(),
                onOrderClicked = onOrderClicked,
                modifier = modifier
            )
        }
        else -> {}
    }
}

@Composable
fun OrderList(
    list: List<Order>,
    onOrderClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val orderListState = rememberLazyListState()

    if (list.isEmpty())
        EmptyItem()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        state = orderListState, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(list) { item ->
            OderItem(
                order = item,
                modifier = Modifier.clickable { onOrderClicked(item.id) }
            )
        }
    }
}

@Composable
fun OderItem(
    order: Order,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.order_id),
                modifier = Modifier,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                text = order.id,
                modifier = Modifier,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = order.created_at,
            modifier = Modifier,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = order.status,
                modifier = Modifier,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = order.total_price.toString(),
                modifier = Modifier,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
fun EmptyItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.empty_order),
            style = TextStyle(color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Normal),
            textAlign = TextAlign.Center,
            modifier = Modifier,
        )
    }
}