package com.example.smartmealplanner.data.planner

import com.example.smartmealplanner.data.model.ClientProfile
import com.example.smartmealplanner.data.model.DietaryPreference
import com.example.smartmealplanner.data.model.Food
import com.example.smartmealplanner.data.model.MealPlan

// ABSTRACTION
// The client taps "Generate Plan" and gets a result. HOW the foods are filtered, ranked,
// priced and added up is hidden inside this class. The screen only ever calls generatePlan().
class MealPlanGenerator {

    // The ONE public door
    fun generatePlan(profile: ClientProfile, allFoods: List<Food>): MealPlan {
        val allowed = filterByDiet(allFoods, profile.getDietaryPreference())
        val recommender = RecommenderFactory.forGoal(profile.getHealthGoal())
        return recommender.generateRecommendation(allowed, profile.getBudget())
    }

    // PRIVATE detail: nobody outside needs to know how the diet filter works
    private fun filterByDiet(foods: List<Food>, diet: DietaryPreference): List<Food> =
        when (diet) {
            DietaryPreference.NONE -> foods
            DietaryPreference.VEGAN -> foods.filter { it.isVegan }
            DietaryPreference.HALAL -> foods.filter { it.isHalal }
        }
}