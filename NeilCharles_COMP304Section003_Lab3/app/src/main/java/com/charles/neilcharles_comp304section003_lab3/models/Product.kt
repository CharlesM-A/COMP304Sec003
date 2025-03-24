package com.charles.neilcharles_comp304section003_lab3.models
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(

    @PrimaryKey(autoGenerate = true) val prodID: Int = 0, //From (101-999)
    val prodName: String,
    val prodPrice: Double, //Must be positive
    val dateOfDelivery: String, //Will think of something
    val prodCategory: String, //will be a dropdown
    val prodFavourites: Boolean = false, //Default value will be false.

    )



