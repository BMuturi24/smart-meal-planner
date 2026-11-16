package com.example.smartmealplanner.data.model

// One line of a plan: a food and how many servings of it to buy
data class PlanItem(val food: Food, val servings: Int) {
    val cost: Double get() = food.pricePerServing * servings
    val calories: Int get() = food.calories * servings
    val protein: Int get() = food.proteinGrams * servings
}

// The finished plan. The totals are worked out from the items, so they can never be wrong
data class MealPlan(
    val goal: HealthGoal,
    val budget: Double,
    val items: List<PlanItem>
) {
    val totalCost: Double get() = items.sumOf { it.cost }
    val totalCalories: Int get() = items.sumOf { it.calories }
    val totalProtein: Int get() = items.sumOf { it.protein }

    fun isEmpty(): Boolean = items.isEmpty()
}
