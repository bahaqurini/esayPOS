package org.aasoft.easypos.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.aasoft.easypos.Products
import org.aasoft.easypos.controller.createDatabase
import org.aasoft.easypos.data.FiledPlace
import org.aasoft.easypos.data.ItemFiled
import org.aasoft.easypos.data.ProductsFiled
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductsShow(modifier: Modifier=Modifier, products:MutableList<Products>)
{
    var selectedItemPlace by remember { mutableStateOf<FiledPlace<ProductsFiled>?>(null ) }


    LazyColumn {
        item {
            Row {
                // wholesale_price: Double,
                //    retail_price: Double,

                ClickableText("Id", modifier = Modifier.weight(1f))
                ClickableText("barcode",modifier = Modifier.weight(1f).border(1.dp, color = Color(0xFF000000), shape = RoundedCornerShape(0f)))
                ClickableText("Wholesale price",modifier = Modifier.weight(1f))
                ClickableText("Retail price",modifier = Modifier.weight(1f))
                ClickableText("name",modifier = Modifier.weight(1f))
            }
        }

        items(products.count())
        {
            index -> ShowProductItem(
            item = products[index],
            selectProductsFiled = if
                    (selectedItemPlace?.itemId == products[index].product_id.toInt()) selectedItemPlace?.filed ?: ProductsFiled.NONE else ProductsFiled.NONE,
            onClickField = {id, filed -> selectedItemPlace = FiledPlace<ProductsFiled>(id, filed)

            },
            onChangeValue = {value ->
                when(selectedItemPlace!!.filed) {
                    //ItemFiled.NAME -> sellItems[index] = sellItems[index].copy(name = value)
                    ProductsFiled.BARCODE-> {
//                        val value = value.toDoubleOrNull() ?: 0.0
//                        sellItems[index] = sellItems[index].copy(price = value)
//                        sellItems[index] = sellItems[index].copy(total = sellItems[index].quantity * value)
                    }

                    ProductsFiled.WHOLESALE_PRICE -> {
//                        val value = value.toIntOrNull() ?: 0
//                        sellItems[index] = sellItems[index].copy(quantity = value)
//                        sellItems[index] = sellItems[index].copy(total = value * sellItems[index].price)
                    }

                    ProductsFiled.REGULAR_PRICE -> {
//                        val value = value.toDoubleOrNull() ?: 0.0
//                        sellItems[index] = sellItems[index].copy(total = value)
//                        sellItems[index] = sellItems[index].copy(price = value / sellItems[index].quantity)
                    }
                    ProductsFiled.NAME -> {}

                    else -> {}
                }

            })
        }
        item {
            Text("count: ${products.count()}")
        }

    }

}

@Composable
fun ShowProductItem(modifier: Modifier = Modifier,item: Products,selectProductsFiled: ProductsFiled,onClickField: (Int, ProductsFiled) -> Unit = { _, _ ->},onChangeValue: (String) -> Unit = {})
{
    LaunchedEffect(selectProductsFiled)
    {
        println("selectProductsFiled: $selectProductsFiled")
    }

    Row(modifier = modifier.fillMaxWidth()) {
        ClickableText(item.product_id.toString(), modifier = modifier.weight(1f))
        ClickableText(item.product_name?:"", modifier = modifier.weight(1f))

        if (selectProductsFiled == ProductsFiled.WHOLESALE_PRICE)
            TextField(item.wholesale_price.toString(), modifier = modifier.weight(1f), onValueChange = {onChangeValue(it)})
        else
            ClickableText(item.wholesale_price.toString(),modifier = modifier.weight(1f), onClick = {onClickField(item.product_id.toInt(), ProductsFiled.WHOLESALE_PRICE)})
        if (selectProductsFiled == ProductsFiled.REGULAR_PRICE)
            TextField(item.retail_price.toString(), modifier = modifier.weight(1f), onValueChange = {onChangeValue(it)})
        else
            ClickableText(item.retail_price.toString(),modifier = modifier.weight(1f), onClick = {onClickField(item.product_id.toInt(), ProductsFiled.REGULAR_PRICE)})
        if (selectProductsFiled == ProductsFiled.BARCODE)
            TextField(item.barcode, modifier = modifier.weight(1f), onValueChange = {onChangeValue(it)})
        else
            ClickableText(item.barcode,modifier = modifier.weight(1f), onClick = {onClickField(item.product_id.toInt(), ProductsFiled.BARCODE)})
    }
}
@Preview(showBackground = true)
@Composable
fun ShowProductItemPreview()
{
    MaterialTheme {
        val products = listOf(
            Products(1, "Product 1", 10.0, 20.0, "1234567890",""),
            Products(2, "Product 2", 15.0, 25.0, "0987654321",""),
            Products(3, "Product 3", 20.0, 30.0, "1111111111",""),
            Products(4, "Product 4", 25.0, 35.0, "2222222222",""),
            )
        //val database = createDatabase()
        //val products = database.productsQueries.selectAll().executeAsList()
        ProductsShow(products = products.toMutableList())
    }
}