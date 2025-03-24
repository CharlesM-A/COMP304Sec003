package com.charles.neilcharles_comp304section003_lab3.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.set
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.charles.neilcharles_comp304section003_lab3.models.Product
import com.charles.neilcharles_comp304section003_lab3.ui.viewmodels.ProductViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddProductScreen(
    productViewModel: ProductViewModel,
    navController: NavController,
    existingProduct: Product? = null
) {
    var id by remember { mutableStateOf(existingProduct?.prodID?.toString() ?: "") }
    var name by remember { mutableStateOf(existingProduct?.prodName ?: "") }
    var price by remember { mutableStateOf(existingProduct?.prodPrice?.toString() ?: "") }
    var deliveryDate by remember { mutableStateOf(existingProduct?.dateOfDelivery ?: getCurrentDate()) }
    var category by remember { mutableStateOf(existingProduct?.prodCategory ?: "Cell Phone") }
    var isFavorite by remember { mutableStateOf(existingProduct?.prodFavourites ?: false) }

    // Validation states
    val isIdValid = id.isEmpty() || (id.toIntOrNull() in 101..999)
    val isPriceValid = price.isEmpty() || (price.toDoubleOrNull() != null && price.toDoubleOrNull()!! > 0)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = if (existingProduct == null) "Add Product" else "Edit Product",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Only allow ID editing for new products
        if (existingProduct == null) {
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("Product ID (101-999)") },
                isError = !isIdValid,
                supportingText = {
                    if (!isIdValid) Text("ID must be between 101-999")
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            isError = !isPriceValid,
            supportingText = {
                if (!isPriceValid && price.isNotEmpty()) Text("Price must be positive")
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        DatePicker(
            selectedDate = deliveryDate,
            onDateSelected = { deliveryDate = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CategoryDropdown(
            selectedCategory = category,
            onCategorySelected = { category = it }
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Favorite")
            Switch(checked = isFavorite, onCheckedChange = { isFavorite = it })
        }

        Button(
            onClick = {
                val productId = if (existingProduct != null) {
                    existingProduct.prodID
                } else {
                    id.toIntOrNull() ?: 101
                }

                val product = Product(
                    prodID = productId,
                    prodName = name,
                    prodPrice = price.toDoubleOrNull() ?: 0.0,
                    dateOfDelivery = deliveryDate,
                    prodCategory = category,
                    prodFavourites = isFavorite
                )

                productViewModel.upsertProduct(product)
                navController.popBackStack() // Go back to product list
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            enabled = name.isNotEmpty() && isPriceValid && isIdValid && price.isNotEmpty()
        ) {
            Text(if (existingProduct == null) "Save Product" else "Update Product")
        }
    }
}

@Composable
fun CategoryDropdown(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("Cell Phone", "Electronics", "Appliances", "Media")
    var expanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedCategory,
        onValueChange = { },
        readOnly = true,
        label = { Text("Category") },
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.ArrowDropDown, "Dropdown menu")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        categories.forEach { category ->
            DropdownMenuItem(
                text = { Text(category) },
                onClick = {
                    onCategorySelected(category)
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun DatePicker(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val currentDate = remember { Calendar.getInstance() }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                // Format date as yyyy-MM-dd
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                onDateSelected(dateFormat.format(calendar.time))
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis() // Prevents selecting dates before today
        }
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = { },
        readOnly = true,
        label = { Text("Delivery Date") },
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.DateRange, "Select date")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(Date()
}