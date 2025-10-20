package org.aasoft.easypos.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.aasoft.easypos.Products
import org.aasoft.easypos.data.ItemsViewModel
import org.aasoft.easypos.data.SellItem
import org.aasoft.easypos.ui.SellScreen

object Sell
{
    private val db = createDatabase()
    fun sell(items: List<SellItem>)
    {
        db.olderQueries.insert(1L,items.sumOf { it.price*it.quantity })
        val id = db.olderQueries.getLastInsertId().executeAsOne()
        items.forEach {
            db.olderItemQueries.insert(id,it.id.toLong(),it.price,it.quantity.toLong())
        }
    }
    fun found(barcode: String): List<Products>
    {
        var product = db.productsQueries.selectByBarcode(barcode).executeAsList()
        if (product.isEmpty() && barcode.toLongOrNull() != null)
            product = db.productsQueries.selectById(barcode.toLong()).executeAsList()
        return product
    }
}

@Composable
fun SellController(modifier: Modifier= Modifier, model: ItemsViewModel )
{
    val items by model.items.collectAsState()
    val sellItems = remember { mutableStateListOf<SellItem>() }
    sellItems.addAll(items)

    SellScreen (
        sellItems = sellItems,
        onSell = {
            if (sellItems.isNotEmpty())
            {
                Sell.sell(sellItems)
            }
            sellItems.clear()
            model.clearItems()


        }
    ){
        val product = Sell.found(it)
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