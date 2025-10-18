package org.aasoft.easypos.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.aasoft.easypos.controller.AddEditProductController
import org.aasoft.easypos.controller.ProductsShowController
import org.aasoft.easypos.controller.SellController
import org.aasoft.easypos.data.ItemsViewModel

@Composable
fun MainScreen(modifier: Modifier=Modifier,model: ItemsViewModel = viewModel { ItemsViewModel()}) {
    val navController = rememberNavController()

    Row (modifier=modifier.fillMaxSize())
    {
        Box(modifier=modifier.fillMaxHeight().weight(1f))
        {
            Column(modifier=modifier.fillMaxSize()) {
                Button(onClick = { navController.navigate("SellScreen") }) {
                    Text("Sell")
                }
                Button(onClick = { navController.navigate("ProductsShow") }) {
                    Text("Products")
                }
                Button(onClick = { navController.navigate("addEditProduct") }) {
                    Text("Add/Edit Product")
                }
            }
        }
        Box(modifier=modifier.fillMaxHeight().weight(3f))
        {
            NavHost(navController = navController
                ,modifier=modifier.fillMaxSize()
                , startDestination = "SellScreen")
            {
                composable("SellScreen")
                {
                    SellController(model=model)
                }
                composable("ProductsShow")
                {
                    ProductsShowController()
                }
                composable ("addEditProduct")
                {
                    AddEditProductController()
                }
            }
        }

    }
}