package com.example.smartmealplanner.data.model

// One food that can appear in a meal plan
data class Food(
    val id: Long = 0,                 // 0 = not saved yet
    val name: String,
    val category: FoodCategory,
    val pricePerServing: Double,      // in KSh
    val calories: Int,                // per serving
    val proteinGrams: Int,            // per serving
    val isVegan: Boolean,
    val isHalal: Boolean,
    val servingSize: String,          // for example "1 piece"
    val prepTip: String               // a short preparation suggestion
)