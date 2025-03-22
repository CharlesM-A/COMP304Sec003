package com.charles.neilcharles_comp304section003_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.charles.neilcharles_comp304section003_lab3.ui.theme.NeilCharles_COMP304Section003_Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            NeilCharles_COMP304Section003_Lab3Theme {
                ProductListScreen()
                }
            }
        }
    }

@Composable
fun ProductListScreen(){
    val products = listOf("ASUS TUF Gaming A15(2024) Gaming Laptop","iPhone 12 Mini","Sony Bravia 8 77' 4K UHD HDR OLED Smart Google TV ")

    LazyColumn {
        items(products) { products ->
            Text(
                text = products,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp))
        }
    }
}