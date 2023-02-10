package com.example.onlineshop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onlineshop.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun OnlineShopNavGraph() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = OnlineShopNavigation.OderListScreen.route
        ) {
            composable(route = OnlineShopNavigation.OderListScreen.route) {
                OrderListScreen(loadableOrders =, onOrderClicked = {
                    navController.navigate(OnlineShopNavigation.OderDetailScreen.route)
                })
            }
            composable(route = OnlineShopNavigation.OderDetailScreen.route) {
                OrderDetailScreen(order =, imageLoader = {
                    GlideImage(
                        imageModel = it,
                        contentScale = ContentScale.FillWidth,
                        contentDescription = stringResource(id = R.string.product),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(start = 8.dp, end = 8.dp)
                            .clip(
                                RoundedCornerShape(250.dp)
                            )
                            .size(48.dp)
                    )
                })
            }
        }
    }
}

sealed class OnlineShopNavigation(val route: String) {
    object OderListScreen : OnlineShopNavigation("oder_list")
    object OderDetailScreen : OnlineShopNavigation("oder_detail")
}

