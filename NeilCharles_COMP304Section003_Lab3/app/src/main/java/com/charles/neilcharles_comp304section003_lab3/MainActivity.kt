package com.charles.neilcharles_comp304section003_lab3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.charles.neilcharles_comp304section003_lab3.database.ProductDatabase
import com.charles.neilcharles_comp304section003_lab3.repository.ProductRepository
import com.charles.neilcharles_comp304section003_lab3.ui.screens.AddProductScreen
import com.charles.neilcharles_comp304section003_lab3.ui.screens.ProductListScreen
import com.charles.neilcharles_comp304section003_lab3.ui.theme.NeilCharles_COMP304Section003_Lab3Theme
import com.charles.neilcharles_comp304section003_lab3.ui.viewmodels.ProductViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Database and Repository
        val database = ProductDatabase.getDatabase(this)
        val repository = ProductRepository(database.productDao())

        // Manually create ProductViewModel (no ViewModelProvider needed)
        val productViewModel = ProductViewModel(repository)

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
}