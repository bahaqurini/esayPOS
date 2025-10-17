package org.aasoft.easypos.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.aasoft.easypos.Products
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AddEditProduct(modifier: Modifier=Modifier,product: Products?,onProductChange:(Products)->Unit) {
    var id by remember { mutableStateOf(product?.product_id ?: "new id") }
    var name by remember { mutableStateOf(product?.product_name ?: "") }
    var barcode by remember { mutableStateOf(product?.barcode ?: "") }
    var wholesalePrice by remember { mutableStateOf(product?.wholesale_price?.toString() ?: "") }
    var retailPrice by remember { mutableStateOf(product?.retail_price?.toString() ?: "") }
    Box(modifier = modifier)
    {
        Column {
            Text(text = "id: $id")
            TextField(value = name, onValueChange = { name = it }, label = { Text(text = "name") })
            TextField(
                value = barcode,
                onValueChange = { barcode = it },
                label = { Text(text = "barcode") })
            TextField(
                value = wholesalePrice,
                onValueChange = { wholesalePrice = it },
                label = { Text(text = "wholesale price") })
            TextField(
                value = retailPrice,
                onValueChange = { retailPrice = it },
                label = { Text(text = "retail price") })
            Button(onClick = {
                onProductChange(
                    Products(
                        id.toString().toLongOrNull() ?: 0,
                        barcode,
                        wholesalePrice.toDouble(),
                        retailPrice.toDouble(),
                        name,
                        null
                    )
                )
            }) {
                    Text(text = if (product == null) "add" else "save")
            }
        }


    }
}
@Preview(showBackground = true)
@Composable
fun AddEditProductPreview() {
    AddEditProduct(product = null, onProductChange = {})
}


