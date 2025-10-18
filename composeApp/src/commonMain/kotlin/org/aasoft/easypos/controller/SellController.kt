package org.aasoft.easypos.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.aasoft.easypos.data.ItemsViewModel
import org.aasoft.easypos.data.SellItem
import org.aasoft.easypos.ui.SellScreen

@Composable
fun SellController(modifier: Modifier= Modifier, model: ItemsViewModel )
{
    val db = createDatabase()

    val items by model.items.collectAsState()
    val sellItems = remember { mutableStateListOf<SellItem>() }
    sellItems.addAll(items)
    SellScreen (sellItems = sellItems){
        var product = db.productsQueries.selectByBarcode(it).executeAsList()
        if (product.isEmpty() && it.toLongOrNull() != null)
            product = db.productsQueries.selectById(it.toLong()).executeAsList()
        if (product.isNotEmpty())
        {
            val item = sellItems.find { it.id == product.first().product_id.toInt() }
            if (item != null)
            {
               val i = sellItems.indexOf(item)
                sellItems[i]=sellItems[i].copy(quantity = sellItems[i].quantity+1)
                model.setItem(i,sellItems[i])
            }
            else
            {
                sellItems+= SellItem(product.first().product_id.toInt(),product.first().product_name!!,product.first().retail_price,1)
                model.addItem( SellItem(product.first().product_id.toInt(),product.first().product_name!!,product.first().retail_price,1))
            }

        }

    }
}