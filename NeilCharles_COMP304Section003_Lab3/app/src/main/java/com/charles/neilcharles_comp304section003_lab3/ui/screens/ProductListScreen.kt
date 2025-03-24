package com.charles.neilcharles_comp304section003_lab3.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.charles.neilcharles_comp304section003_lab3.models.Product
import com.charles.neilcharles_comp304section003_lab3.ui.viewmodels.ProductViewModel

@Composable
fun ProductListScreen(productViewModel: ProductViewModel, navController: NavController) {
    var showFavorites by remember { mutableStateOf(false) }

    val products by if (showFavorites) {
        productViewModel.favProducts.collectAsState(initial = emptyList())
    } else {
        productViewModel.allProducts.collectAsState(initial = emptyList())
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_product") }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B2B2B))
            .padding(paddingValues)) {
            Text(
                text = "Product List",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { showFavorites = false }) {
                    Text("All Products")
                }
                Button(onClick = { showFavorites = true }) {
                    Text("Favorites")
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onClick = { navController.navigate("edit_product/${product.prodID}") },
                        onDelete = {
                            // Call ViewModel to delete the product
                            productViewModel.deleteProduct(product)
                        }
                    )
                }
            }
        }

    }

}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.prodName, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Price: $${product.prodPrice}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Category: ${product.prodCategory}", style = MaterialTheme.typography.bodyMedium)
                if (product.prodFavourites) {
                    Text(text = "★ Favorite", style = MaterialTheme.typography.bodyMedium)
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete product",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}


