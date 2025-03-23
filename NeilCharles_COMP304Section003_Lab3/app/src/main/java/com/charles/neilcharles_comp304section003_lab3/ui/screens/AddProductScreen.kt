package com.charles.neilcharles_comp304section003_lab3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.charles.neilcharles_comp304section003_lab3.models.Product
import com.charles.neilcharles_comp304section003_lab3.ui.viewmodels.ProductViewModel

@Composable
fun AddProductScreen(productViewModel: ProductViewModel, onProductAdded: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }


}
