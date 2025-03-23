package com.charles.neilcharles_comp304section003_lab3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.charles.neilcharles_comp304section003_lab3.models.Product
import com.charles.neilcharles_comp304section003_lab3.ui.viewmodels.ProductViewModel

@Composable
fun AddProductScreen(productViewModel: ProductViewModel, onProductAdded: () -> Unit,navController: NavHostController) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("ID") })
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") })
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date") })
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") })

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isFavorite, onCheckedChange = { isFavorite = it })
            Text("Favorite")
    }
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            if (id.isNotEmpty() && name.isNotEmpty() && price.isNotEmpty() && date.isNotEmpty() && category.isNotEmpty()) {
                val product = Product(

                    prodID = id.toInt(),
                    prodName = name,
                    prodPrice = price.toDouble(),
                    dateOfDelivery = date,
                    prodCategory = category,
                    prodFavourites = isFavorite
                )
                //productViewModel.insert(product)
                onProductAdded() // Navigate back or refresh
                navController.popBackStack()

            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Add Product")
    }
}
}