package com.example.smartmealplanner.data.model

// Which kind of account this is
enum class Role { CLIENT, ADMIN }

// A client's dietary preference. "label" is the text shown on screen
enum class DietaryPreference(val label: String) {
    NONE("No restriction"),
    VEGAN("Vegan"),
    HALAL("Halal")
}

// A client's health goal. Each goal gets its own meal recommender (polymorphism, Stage 2)
enum class HealthGoal(val label: String) {
    BULKING("Bulking (gain weight)"),
    WEIGHT_LOSS("Losing weight"),
    MAINTENANCE("Maintaining weight")
}

// The food groups the plan draws from
enum class FoodCategory(val label: String) {
    CARBS("Carbohydrate"),
    PROTEIN("Protein"),
    VEGETABLE("Vegetable"),
    FRUIT("Fruit"),
    DAIRY("Dairy")
}