package com.example.smartmealplanner.data.planner

import com.example.smartmealplanner.data.model.ClientProfile
import com.example.smartmealplanner.data.model.DietaryPreference
import com.example.smartmealplanner.data.model.Food
import com.example.smartmealplanner.data.model.FoodCategory
import com.example.smartmealplanner.data.model.HealthGoal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MealPlanGeneratorTest {

    // A small helper so each food takes one line
    private fun food(name: String, price: Double, calories: Int, protein: Int, vegan: Boolean = true) =
        Food(
            name = name, category = FoodCategory.CARBS, pricePerServing = price,
            calories = calories, proteinGrams = protein, isVegan = vegan, isHalal = true,
            servingSize = "1 serving", prepTip = "Cook and serve."
        )

    private val foods = listOf(
        food("Banana", 10.0, 90, 1),
        food("Ugali", 25.0, 200, 4),
        food("Beans", 30.0, 230, 12),
        food("Eggs", 15.0, 75, 6, vegan = false),
        food("Omena", 40.0, 150, 20, vegan = false),
        food("Spinach", 15.0, 25, 3),
        food("Chicken", 120.0, 240, 28, vegan = false)
    )

    private val generator = MealPlanGenerator()

    @Test
    fun veganClient_neverGetsAnimalFood() {
        val profile = ClientProfile(500.0, DietaryPreference.VEGAN, HealthGoal.BULKING)
        val plan = generator.generatePlan(profile, foods)
        assertTrue(plan.items.isNotEmpty())
        assertTrue(plan.items.all { it.food.isVegan })
    }

    @Test
    fun plan_neverCostsMoreThanTheBudget() {
        for (goal in HealthGoal.entries) {
            val profile = ClientProfile(300.0, DietaryPreference.NONE, goal)
            assertTrue(generator.generatePlan(profile, foods).totalCost <= 300.0)
        }
    }

    @Test
    fun bulking_givesMoreCaloriesThanWeightLoss() {
        val bulk = generator.generatePlan(ClientProfile(500.0, DietaryPreference.NONE, HealthGoal.BULKING), foods)
        val loss = generator.generatePlan(ClientProfile(500.0, DietaryPreference.NONE, HealthGoal.WEIGHT_LOSS), foods)
        assertTrue(bulk.totalCalories > loss.totalCalories)
    }

    @Test
    fun clientProfile_rejectsABadBudget() {
        val profile = ClientProfile(500.0)
        assertFalse(profile.updateBudget(-10.0))
        assertEquals(500.0, profile.getBudget(), 0.0)   // the old value is still there
    }
}