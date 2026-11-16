package com.example.smartmealplanner.data.planner

import com.example.smartmealplanner.data.model.Food
import com.example.smartmealplanner.data.model.HealthGoal
import com.example.smartmealplanner.data.model.MealPlan

// BULKING: the most calories for every shilling, up to 3000 kcal a day
class BulkingRecommender : MealRecommender(HealthGoal.BULKING, 3000, 3) {
    override fun generateRecommendation(foods: List<Food>, dailyBudget: Double): MealPlan {
        val ranked = foods.sortedByDescending { it.calories / it.pricePerServing }
        return buildPlan(ranked, dailyBudget)
    }
}

// WEIGHT LOSS: only light foods (350 kcal or less), the most protein for the calories first,
// up to 1800 kcal a day
class WeightLossRecommender : MealRecommender(HealthGoal.WEIGHT_LOSS, 1800, 2) {
    override fun generateRecommendation(foods: List<Food>, dailyBudget: Double): MealPlan {
        val light = foods.filter { it.calories <= 350 }
        val ranked = light.sortedByDescending { it.proteinGrams.toDouble() / it.calories }
        return buildPlan(ranked, dailyBudget)
    }
}

// MAINTENANCE: balance first. Put the cheapest food of each food group at the front,
// then the second cheapest of each group, and so on. Up to 2200 kcal a day
class MaintenanceRecommender : MealRecommender(HealthGoal.MAINTENANCE, 2200, 2) {
    override fun generateRecommendation(foods: List<Food>, dailyBudget: Double): MealPlan {
        val groups = foods.groupBy { it.category }.values
            .map { group -> group.sortedBy { it.pricePerServing } }
        val longest = groups.maxOfOrNull { it.size } ?: 0

        val ranked = mutableListOf<Food>()
        for (position in 0 until longest) {
            for (group in groups) {
                group.getOrNull(position)?.let { ranked.add(it) }
            }
        }
        return buildPlan(ranked, dailyBudget)
    }
}

// Picks the right child for a goal
object RecommenderFactory {
    fun forGoal(goal: HealthGoal): MealRecommender = when (goal) {
        HealthGoal.BULKING -> BulkingRecommender()
        HealthGoal.WEIGHT_LOSS -> WeightLossRecommender()
        HealthGoal.MAINTENANCE -> MaintenanceRecommender()
    }
}