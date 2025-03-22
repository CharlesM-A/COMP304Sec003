package com.charles.neilcharles_comp304section003_lab3.repository
import androidx.lifecycle.LiveData
import com.charles.neilcharles_comp304section003_lab3.database.ProductDao
import com.charles.neilcharles_comp304section003_lab3.models.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()
    val favouriteProducts: Flow<List<Product>> = productDao.getFavProduct()

    suspend fun upsert(product: Product){
        productDao.upsertProduct(product)
    }

    suspend fun delete(product: Product) {
        productDao.deleteProduct(product)
    }
}