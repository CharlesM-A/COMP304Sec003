package com.charles.neilcharles_comp304section003_lab3.ui.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charles.neilcharles_comp304section003_lab3.models.Product
import kotlinx.coroutines.launch
import com.charles.neilcharles_comp304section003_lab3.repository.ProductRepository

class ProductViewModel(private val repository: ProductRepository) :ViewModel() {

    val allProducts = repository.allProducts
    val favProducts = repository.favouriteProducts

    fun upsertProduct(product: Product){
        //viewModelScope runs asynchronously so calling queries to the database won't freeze the UI
        viewModelScope.launch {
            repository.upsert(product)
        }
    }

    fun deleteProduct(product: Product){
        viewModelScope.launch {
            repository.delete(product)
        }
    }

}