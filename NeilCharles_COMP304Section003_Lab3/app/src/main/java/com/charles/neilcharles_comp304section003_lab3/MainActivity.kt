package com.charles.neilcharles_comp304section003_lab3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.charles.neilcharles_comp304section003_lab3.ui.theme.NeilCharles_COMP304Section003_Lab3Theme
import com.charles.neilcharles_comp304section003_lab3.ui.screens.ProductListScreen
import com.charles.neilcharles_comp304section003_lab3.ui.viewmodels.ProductViewModel
import com.charles.neilcharles_comp304section003_lab3.repository.ProductRepository
import com.charles.neilcharles_comp304section003_lab3.database.ProductDao
import com.charles.neilcharles_comp304section003_lab3.database.ProductDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize database,dao, and repository
        val database = ProductDatabase.getDatabase(this)
        val repository = ProductRepository(database.productDao())

        setContent {
            NeilCharles_COMP304Section003_Lab3Theme {
//                ProductListScreen(
//                    repository
//                )
                ProductNav()

            }
        }
    }

}
@Composable
fun ProductNav(){
    val navController = rememberNavController()
    val database = ProductDatabase.getDatabase(this)
    val repository = ProductRepository(database.productDao())

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            ProductListScreen(
                repository = repository,
                navController = navController
            )
        }
    }
}