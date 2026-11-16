package com.example.smartmealplanner.data.planner

import com.example.smartmealplanner.data.model.Food
import com.example.smartmealplanner.data.model.HealthGoal
import com.example.smartmealplanner.data.model.MealPlan
import com.example.smartmealplanner.data.model.PlanItem

// POLYMORPHISM: the parent.
// Every recommender has the SAME method, generateRecommendation(), but each goal fills it in
// differently. The caller never needs to know which child it is talking to.
abstract class MealRecommender(
    private val goal: HealthGoal,
    private val targetCalories: Int,        // the most calories one day should reach
    private val maxServingsPerFood: Int     // the most servings of one food we allow
) {
    // Each child decides HOW to rank the foods for its goal
    abstract fun generateRecommendation(foods: List<Food>, dailyBudget: Double): MealPlan

    // Shared by all children: walk down the ranked list, adding one serving at a time,
    // while BOTH the budget and the calorie target still allow it
    protected fun buildPlan(ranked: List<Food>, dailyBudget: Double): MealPlan {
        val servings = linkedMapOf<Food, Int>()
        var cost = 0.0
        var calories = 0

        // Round 1 gives every food one serving, round 2 a second serving, and so on.
        // This spreads the money over many foods instead of buying one food only
        for (round in 1..maxServingsPerFood) {
            for (food in ranked) {
                val alreadyHave = servings[food] ?: 0
                val affordable = cost + food.pricePerServing <= dailyBudget
                val withinCalories = calories + food.calories <= targetCalories
                if (alreadyHave < round && affordable && withinCalories) {
                    servings[food] = alreadyHave + 1
                    cost += food.pricePerServing
                    calories += food.calories
                }
            }
        }
        return MealPlan(goal, dailyBudget, servings.map { PlanItem(it.key, it.value) })
    }
}