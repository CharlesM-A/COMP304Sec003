package com.charles.neilcharles_comp304section003_lab3.database
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.charles.neilcharles_comp304section003_lab3.models.Product
import com.charles.neilcharles_comp304section003_lab3.database.ProductDao
import android.content.Context

@Database(
    entities = [Product::class],
    version = 1
)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object{
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


