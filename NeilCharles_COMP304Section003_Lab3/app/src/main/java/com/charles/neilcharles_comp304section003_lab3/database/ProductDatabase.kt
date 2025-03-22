package com.charles.neilcharles_comp304section003_lab3.database
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.charles.neilcharles_comp304section003_lab3.models.Product
import android.content.Context

@Database(
    entities = [Product::class],
    version = 1
)
abstract class ProductDatabase: RoomDatabase() {
    abstract val dao: ProductDao

    //didn't add a singleton pattern - probably should

}