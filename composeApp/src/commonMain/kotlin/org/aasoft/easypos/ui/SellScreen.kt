package org.aasoft.easypos.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.aasoft.easypos.controller.createDatabase
import org.aasoft.easypos.data.FiledPlace
import org.aasoft.easypos.data.ItemFiled
import org.aasoft.easypos.data.SellItem
import org.jetbrains.compose.ui.tooling.preview.Preview



@Composable
fun SellScreen(
    modifier: Modifier = Modifier,
    sellItems: MutableList<SellItem> = mutableListOf(),
    onSell: () -> Unit = {},
    onBarcodeScan: (String) -> Unit = {},
){
    var selectedItemPlace by remember { mutableStateOf<FiledPlace<ItemFiled>?>(null ) }
    var value by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize())
    {
        Column()
        {
            Text("Sell")
            OutlinedTextField(
                value = value,
                onValueChange = {value = it },
                label = { Text("Id") },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo  = {
                    //toast("Go")
                    onBarcodeScan(value)
                    value = ""
                    selectedItemPlace = null
                }),
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    Row {
                        //ClickableText("Id", modifier = Modifier.weight(1f))
                        ClickableText("Name",modifier = Modifier.weight(1f).border(1.dp, color = Color(0xFF000000), shape = RoundedCornerShape(0f)))
                        ClickableText("Price",modifier = Modifier.weight(1f))
                        ClickableText("Quantity",modifier = Modifier.weight(1f))
                        ClickableText("Total",modifier = Modifier.weight(1f))
                    }
                }

                items(sellItems.count())
                { index ->
                    Card(onClick = {})
                    {
                        ShowSellItem(
                            item = sellItems[index],
                            selectItemFiled = if (selectedItemPlace?.itemId == index) selectedItemPlace?.filed
                                ?: ItemFiled.NONE else ItemFiled.NONE,
                            onClickField = { id, filed ->
                                selectedItemPlace = FiledPlace(index, filed)
                            },
                            onChangeValue = { value ->
                                when (selectedItemPlace!!.filed) {
                                    //ItemFiled.NAME -> sellItems[index] = sellItems[index].copy(name = value)
                                    ItemFiled.PRICE -> {
                                        val value = value.toDoubleOrNull() ?: 0.0
                                        sellItems[index] = sellItems[index].copy(price = value)
                                        //sellItems[index] = sellItems[index].copy(total = sellItems[index].quantity * value)
                                    }

                                    ItemFiled.QUANTITY -> {
                                        val value = value.toIntOrNull() ?: 0
                                        sellItems[index] = sellItems[index].copy(quantity = value)
                                        //sellItems[index] = sellItems[index].copy(total = value * sellItems[index].price)
                                    }

                                    ItemFiled.TOTAL -> {
                                        val value = value.toDoubleOrNull() ?: 0.0
                                        //sellItems[index] = sellItems[index].copy(total = value)
                                        sellItems[index] =
                                            sellItems[index].copy(price = value / sellItems[index].quantity)
                                    }

                                    else -> {}
                                }

                            })
                    }
                }
                item {
                    Text("Total: ${sellItems.sumOf { it.price*it.quantity }}")
                }

            }
            Button(onClick = {onSell()}){
                Text("Sell")
            }
        }
    }
}
@Composable
fun ShowSellItem(modifier: Modifier = Modifier,item: SellItem,selectItemFiled: ItemFiled,onClickField: (Int, ItemFiled) -> Unit = { _, _ ->},onChangeValue: (String) -> Unit = {})
{
    var total by remember { mutableStateOf(item.price * item.quantity) }
    LaunchedEffect(item.price, item.quantity) {
        total = item.price * item.quantity
    }

    Row(modifier = modifier.fillMaxWidth()) {
//        if (selectItemFiled == ItemFiled.NAME)
//            TextField(item.name, modifier = modifier.weight(1f), onValueChange = {onChangeValue(it)})
//        else
        ClickableText(item.name, modifier = modifier.weight(1f), onClick = {onClickField(item.id, ItemFiled.NAME)})
        if (selectItemFiled == ItemFiled.PRICE)
            TextField(item.price.toString(), modifier = modifier.weight(1f), onValueChange = {onChangeValue(it)})
        else
            ClickableText(item.price.toString(),modifier = modifier.weight(1f), onClick = {
                onClickField(item.id, ItemFiled.PRICE)

            })
        if (selectItemFiled == ItemFiled.QUANTITY)
            TextField(item.quantity.toString(), modifier = modifier.weight(1f), onValueChange = {
                onChangeValue(it)
                total = item.price * item.quantity
            })
        else
            ClickableText(item.quantity.toString(),modifier = modifier.weight(1f), onClick = {onClickField(item.id, ItemFiled.QUANTITY)})
        if (selectItemFiled == ItemFiled.TOTAL)
            TextField(total.toString(), modifier = modifier.weight(1f), onValueChange = {
                total = item.price * item.quantity
                onChangeValue(it)
            })
        else
            ClickableText(total.toString(),modifier = modifier.weight(1f), onClick = {onClickField(item.id, ItemFiled.TOTAL)})
    }
}
@Composable
fun ClickableText(text: String, modifier: Modifier = Modifier, onClick: () -> Unit={}) {
    Card (onClick = onClick,
        modifier = modifier.padding(start = 2.dp, end = 2.dp),
        shape = RoundedCornerShape(0f)
    ){
        Text(text, modifier = Modifier.fillMaxSize())
    }
}

@Preview (showBackground = true)
@Composable
fun SellScreenPreview()
{
    val items = mutableListOf(
        SellItem(1, "Item 1", 10.0, 2),
        SellItem(2, "Item 2", 20.0, 3),
        SellItem(3, "Item 3", 30.0, 4)
    )
    SellScreen(sellItems = items)
}
