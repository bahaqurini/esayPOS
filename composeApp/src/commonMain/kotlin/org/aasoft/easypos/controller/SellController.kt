package org.aasoft.easypos.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.aasoft.easypos.Products
import org.aasoft.easypos.data.ItemsViewModel
import org.aasoft.easypos.data.SellItem
import org.aasoft.easypos.ui.SellScreen


enum class NavSell
{
    First,
    Last,
    Next,
    Previous,
    New
}
fun getNewId(olderId: Long,navSell: NavSell): Long
{
    val db= createDatabase()
    return try {
        when (navSell) {
            NavSell.Last ->  db.olderQueries.getLastId().executeAsOne()
            NavSell.First -> db.olderQueries.getFirstId().executeAsOne()
            NavSell.Next -> {
                val lastId = db.olderQueries.getLastId().executeAsOne()
                if (olderId == lastId)
                    db.olderQueries.getFirstId().executeAsOne()
                else
                    db.olderQueries.getNextId(olderId).executeAsOne()
            }

            NavSell.Previous -> {
                val firstId = db.olderQueries.getFirstId().executeAsOne()
                if (olderId == firstId)
                    db.olderQueries.getLastId().executeAsOne()
                else
                    db.olderQueries.getPreviousId(olderId).executeAsOne()
            }

            NavSell.New -> 0L

        }
    }
    catch (e: Exception)
    {
        println(e.message)
        0L
    }

}
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

    var id by remember { mutableStateOf(0L) }
    val sellItems = remember { mutableStateListOf<SellItem>() }
    val items by model.items.collectAsState()
    //sellItems.addAll(items)
    LaunchedEffect(id)
    {
        println("id: $id")
        if (id == 0L)
        {

            sellItems.clear()
            sellItems.addAll(items)
        }
        else
        {
            sellItems.clear()
            val db= createDatabase()
//            val older = db.olderItemQueries.selectById(id).executeAsOne()
            val items = db.olderItemQueries.selectByOrderId(id).executeAsList()
            println("items size: ${items.size}")
            println("items: ${items}")
            items.forEach {
                val product = db.productsQueries.selectById(it.product_id).executeAsOne()
                sellItems.add(SellItem(product.product_id.toInt(),product.product_name!!,product.retail_price,it.quantity.toInt()))
            }


        }
    }

    SellScreen (
        sellItems = sellItems,
        olderId = id,
        changeOlder = {
             id = getNewId(id,it)
        },

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