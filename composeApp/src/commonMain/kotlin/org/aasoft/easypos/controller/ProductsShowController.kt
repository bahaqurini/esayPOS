package org.aasoft.easypos.controller

import androidx.compose.runtime.Composable
import org.aasoft.easypos.Products
import org.aasoft.easypos.ui.ProductsShow

@Composable
fun ProductsShowController()
{
    val database= createDatabase()
    val products = database.productsQueries.selectAll().executeAsList()
    ProductsShow(products = products.toMutableList())
}


