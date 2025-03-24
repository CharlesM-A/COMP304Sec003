package com.charles.neilcharles_comp304section003_lab3.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.charles.neilcharles_comp304section003_lab3.models.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    //Singleton pattern that prevents the multiple instance from occurring when something something -its good to have
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


