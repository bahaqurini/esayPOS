package org.aasoft.easypos.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.aasoft.easypos.data.FiledPlace
import org.aasoft.easypos.data.ProductsFiled
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductsShow(modifier: Modifier=Modifier, products:MutableList<Products>)
{
    //val products = remember { mutableStateListOf<Products>() }
    var selectedItemPlace by remember { mutableStateOf<FiledPlace<ProductsFiled>?>(null ) }
//    products.addAll(dbProducts)


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
            onClickField = {id, filed -> selectedItemPlace = FiledPlace(id, filed)

            },
            onChangeValue = {value ->
                println("value: $value")
                println("selectedItemPlace: $selectedItemPlace")
                when(selectedItemPlace!!.filed) {
                    //ItemFiled.NAME -> sellItems[index] = sellItems[index].copy(name = value)
                    ProductsFiled.NAME -> {
                        products[index] = products[index].copy(product_name = value)
                        println("product_name: ${products[index].product_name}")
                    }
                    ProductsFiled.BARCODE-> {
                        val value = value.toDoubleOrNull() ?: 0.0
                        products[index] = products[index].copy(barcode = value.toString())
                    }

                    ProductsFiled.WHOLESALE_PRICE -> {
                        val value = value.toIntOrNull() ?: 0
                        products[index] = products[index].copy(wholesale_price = value.toDouble())
                    }

                    ProductsFiled.REGULAR_PRICE -> {
                        val value = value.toDoubleOrNull() ?: 0.0
                        products[index] = products[index].copy(retail_price = value)
                    }
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
        EditableText(modifier = modifier.weight(1f),item = item,productsFiled = ProductsFiled.NAME,selectProductsFiled = selectProductsFiled,onChangeValue = onChangeValue,onClickField = onClickField)

        EditableText(modifier = modifier.weight(1f),item = item,productsFiled = ProductsFiled.WHOLESALE_PRICE,selectProductsFiled = selectProductsFiled,onChangeValue = onChangeValue,onClickField = onClickField)

        EditableText(modifier = modifier.weight(1f),item = item,productsFiled = ProductsFiled.REGULAR_PRICE,selectProductsFiled = selectProductsFiled,onChangeValue = onChangeValue,onClickField = onClickField)

        EditableText(modifier = modifier.weight(1f),item = item,productsFiled = ProductsFiled.BARCODE,selectProductsFiled = selectProductsFiled,onChangeValue = onChangeValue,onClickField = onClickField)

    }
}
@Composable
fun EditableText( modifier: Modifier = Modifier,item: Products,productsFiled: ProductsFiled,selectProductsFiled: ProductsFiled,onChangeValue: (String) -> Unit = {},onClickField: (Int, ProductsFiled) -> Unit )
{

    val text = when(productsFiled) {
        ProductsFiled.WHOLESALE_PRICE -> item.wholesale_price.toString()
        ProductsFiled.REGULAR_PRICE -> item.retail_price.toString()
        ProductsFiled.BARCODE -> item.barcode
        ProductsFiled.NAME -> item.product_name ?: ""
        else -> ""
    }
    if (selectProductsFiled == productsFiled)
        TextField(value = text, modifier = modifier, onValueChange = { onChangeValue(it)})
    else
        ClickableText(text, modifier = modifier, onClick = {onClickField(item.product_id.toInt(), productsFiled)})

}
@Preview(showBackground = true)
@Composable
fun ShowProductItemPreview()
{
    MaterialTheme {
        val products = mutableListOf(
            Products(1, "Product 1", 10.0, 20.0, "1234567890",0,0,""),
            Products(2, "Product 2", 15.0, 25.0, "0987654321",0,0,""),
            Products(3, "Product 3", 20.0, 30.0, "1111111111",0,0,""),
            Products(4, "Product 4", 25.0, 35.0, "2222222222",0,0,""),
            )
        //val database = createDatabase()
        //val products = database.productsQueries.selectAll().executeAsList()
        ProductsShow(products  = products)
    }
}