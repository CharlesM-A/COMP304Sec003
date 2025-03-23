package com.charles.neilcharles_comp304section003_lab3.database
import androidx.room.*
import androidx.room.Dao
import com.charles.neilcharles_comp304section003_lab3.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Upsert //Inserts and Updates if the product already exists
    suspend fun upsertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    //Displays all products and sorts by name in alphabetic order
    //Flow allows the UI to update automatically and notify if changes in table occur
    @Query("SELECT * FROM product_table ORDER BY prodName ASC")
    fun getAllProducts(): Flow<List<Product>>

    //Displays ONLY products that are favourited
    @Query("SELECT * FROM product_table WHERE prodFavourites = 1")
    fun getFavProduct(): Flow<List<Product>>

}