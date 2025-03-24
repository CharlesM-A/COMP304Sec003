package com.charles.neilcharles_comp304section003_lab3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.charles.neilcharles_comp304section003_lab3.database.ProductDatabase
import com.charles.neilcharles_comp304section003_lab3.models.Product
import com.charles.neilcharles_comp304section003_lab3.repository.ProductRepository
import com.charles.neilcharles_comp304section003_lab3.ui.screens.AddProductScreen
import com.charles.neilcharles_comp304section003_lab3.ui.screens.ProductListScreen
import com.charles.neilcharles_comp304section003_lab3.ui.theme.NeilCharles_COMP304Section003_Lab3Theme
import com.charles.neilcharles_comp304section003_lab3.ui.viewmodels.ProductViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Database and Repository
        val database = ProductDatabase.getDatabase(this)
        val repository = ProductRepository(database.productDao())

        // Manually create ProductViewModel (no ViewModelProvider needed)
        val productViewModel = ProductViewModel(repository)

        // Initialize database with sample data
        lifecycleScope.launch {
            ProductDbInitializer(database).populateDatabase()
        }

        setContent {
            val navController = rememberNavController()

            NeilCharles_COMP304Section003_Lab3Theme {

                NavHost(navController, startDestination = "product_list") {
                    composable("product_list") {
                        ProductListScreen(productViewModel, navController)
                    }
                    composable("add_product") {
                        AddProductScreen(productViewModel, navController)
                    }
                    composable(
                        "edit_product/{productId}",
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        val product = productViewModel.allProducts.collectAsState(initial = emptyList()).value
                            .find { it.prodID == productId }

                        if (product != null) {
                            AddProductScreen(productViewModel, navController, product)
                        } else {
                            Text("Product not found!")
                        }
                    }
                }
            }
        }
    }

    //Sample data
    class ProductDbInitializer(private val database: ProductDatabase) {
        private val dao = database.productDao()

        suspend fun populateDatabase() {
            // Check if database is empty first
            if (dao.getAllProducts().first().isEmpty()) {
                val initialProducts = listOf(
                    Product(
                        prodID = 101,
                        prodName = "IPhone 12 Mini",
                        prodPrice = 799.99,
                        dateOfDelivery = "2025-03-25",
                        prodCategory = "Cell Phone",
                        prodFavourites = true
                    ),
                    Product(
                        prodID = 102,
                        prodName = "ASUS TUF Gaming A15(2024) Gaming Laptop ",
                        prodPrice = 1799.99,
                        dateOfDelivery = "2025-03-26",
                        prodCategory = "Electronics",
                        prodFavourites = false
                    ),
                    Product(
                        prodID = 103,
                        prodName = "Sony Bravia 8 77'in 4k UDH HDR OLED Smart Google TV",
                        prodPrice = 3799.99,
                        dateOfDelivery = "2025-03-30",
                        prodCategory = "Media",
                        prodFavourites = false
                    )
                )
                initialProducts.forEach { dao.upsertProduct(it) }
            }
        }
    }
}

