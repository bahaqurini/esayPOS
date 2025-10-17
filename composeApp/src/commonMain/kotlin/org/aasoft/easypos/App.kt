package org.aasoft.easypos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import esaypos.composeapp.generated.resources.Res
import esaypos.composeapp.generated.resources.compose_multiplatform
import org.aasoft.easypos.controller.ProductsShowController
import org.aasoft.easypos.data.ItemFiled
import org.aasoft.easypos.data.SellItem
import org.aasoft.easypos.ui.SellScreen
import kotlin.collections.mutableListOf

@Composable
@Preview
fun App() {

    MaterialTheme {
//        val items = remember { mutableStateListOf<SellItem>() }
//        items.add(SellItem(1, "Item 1", 10.0, 2, 20.0))
//        items.add(SellItem(2, "Item 2", 20.0, 3, 60.0))
//        items.add(SellItem(3, "Item 3", 30.0, 4, 120.0))
//        SellScreen(perSellItems = items)
        ProductsShowController()
    }
}