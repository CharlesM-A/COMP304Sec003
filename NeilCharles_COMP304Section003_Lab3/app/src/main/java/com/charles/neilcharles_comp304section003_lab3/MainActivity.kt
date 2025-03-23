package com.charles.neilcharles_comp304section003_lab3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
        val productDao = database.productDao()
        val repository = ProductRepository(database.productDao)


        setContent {
            NeilCharles_COMP304Section003_Lab3Theme {
                ProductListScreen(repository)
                }
            }
        }
    }

