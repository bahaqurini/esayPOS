package org.aasoft.easypos.controller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.aasoft.easypos.Database
import org.aasoft.easypos.Products
import org.aasoft.easypos.ui.AddEditProduct

@Composable
fun AddEditProductController(modifier: Modifier = Modifier,product: Products? = null)
{
    val db = createDatabase()
    AddEditProduct(modifier = modifier,product = product)
    {
        if (it.product_id==0L)
        {
            db.productsQueries.insert(it.barcode,it.wholesale_price,it.retail_price,it.product_name)
        }
        else
        {
            db.productsQueries.updateProduct(it.barcode,it.wholesale_price,it.retail_price,it.product_name,it.product_id)
        }

    }

}