package com.charles.neilcharles_comp304section003_lab3.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charles.neilcharles_comp304section003_lab3.ui.viewmodels.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.charles.neilcharles_comp304section003_lab3.models.Product
import com.charles.neilcharles_comp304section003_lab3.repository.ProductRepository
import androidx.compose.runtime.collectAsState

@Composable
fun ProductListScreen(repository: ProductRepository){
    val productViewModel: ProductViewModel = viewModel(
        factory = object: ViewModelProvider.Factory {
            override fun <T: ViewModel>create(modelClass: Class<T>): T {
                return ProductViewModel(repository) as T
            }
        }
    )

    val products = productViewModel.allProducts.collectAsState(initial = emptyList()).value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Product List", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.prodName, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Price: $${product.prodPrice}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


