package org.aasoft.easypos.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.aasoft.easypos.Products
import org.aasoft.easypos.ui.ProductsShow

@Composable
fun ProductsShowController()
{
    val database= createDatabase()
    val dbProducts = database.productsQueries.selectAll().executeAsList().toMutableList()
    val products = remember { mutableStateListOf<Products>()}
    products.addAll(dbProducts)
    //val products by remember { mutableStateOf(database.productsQueries.selectAll().executeAsList().toMutableList()) }
    ProductsShow(products =  products)
}


