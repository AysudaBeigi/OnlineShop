package com.example.onlineshop.presentation.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlineshop.R
import com.example.onlineshop.domain.Order
import com.example.onlineshop.domain.Product

@Composable
fun OrderDetailScreen(
    order: Order,
    imageLoader: @Composable (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OrderDetailItem(R.string.order_id, order.id)
        Spacer(modifier = Modifier.size(16.dp))
        OrderDetailItem(R.string.created_at, order.created_at)
        Spacer(modifier = Modifier.size(16.dp))
        OrderDetailItem(R.string.receiver_name, order.receiver_name)
        Spacer(modifier = Modifier.size(16.dp))
        OrderDetailItem(R.string.total_price, order.total_price.toString())
        Spacer(modifier = Modifier.size(16.dp))
        ProductList(order, imageLoader)
    }
}

@Composable
private fun ProductList(
    order: Order,
    imageLoader: @Composable (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val productListState = rememberLazyListState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        state = productListState, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(order.products) { item ->
            ProductItem(
                product = item, imageLoader = imageLoader
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    imageLoader: @Composable (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        imageLoader(product.image_url)
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier) {
            Text(
                text = product.name,
                modifier = Modifier,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.price.toString(),
                    modifier = Modifier,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = product.count.toString(),
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
}

@Composable
private fun OrderDetailItem(titleSrc: Int, content: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(id = titleSrc),
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
            text = content,
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

@Preview
@Composable
fun OrderDetailScreenPreview() {
    MaterialTheme {
        OrderDetailScreen(order = Order(
            listOf(
                Product(1, "", "Shoe", 20000, ""),
                Product(2, "", "Bag", 20000, ""),
                Product(1, "", "Pencil", 20000, "")
            ),
            "2022.03.01",
            "Done",
            560000,
            "",
            "Aysuda"
        ),
            {})
    }
}