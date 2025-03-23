package com.charles.neilcharles_comp304section003_lab3.repository
import com.charles.neilcharles_comp304section003_lab3.database.ProductDao
import com.charles.neilcharles_comp304section003_lab3.models.Product

class ProductRepository(private val productDao: ProductDao) {
    val allProducts = productDao.getAllProducts()
    val favouriteProducts = productDao.getFavProduct()

    suspend fun upsert(product: Product){
        productDao.upsertProduct(product)
    }

    suspend fun delete(product: Product) {
        productDao.deleteProduct(product)
    }
}