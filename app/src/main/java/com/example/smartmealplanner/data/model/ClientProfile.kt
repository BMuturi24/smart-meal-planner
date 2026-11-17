package com.example.smartmealplanner.data.model

// ENCAPSULATION
// The three facts that drive every recommendation are PRIVATE. Nothing outside this class
// can change them directly, so a negative budget can never reach the meal planner.
class ClientProfile(
    initialBudget: Double = 0.0,
    initialDiet: DietaryPreference = DietaryPreference.NONE,
    initialGoal: HealthGoal = HealthGoal.MAINTENANCE
) {
    // PRIVATE state. Daily budget is in KSh
    private var budgetKsh: Double = 0.0
    private var diet: DietaryPreference = initialDiet
    private var goal: HealthGoal = initialGoal

    init {
        // Even the starting budget goes through the same check as any later change
        updateBudget(initialBudget)
    }

    // READ access: getters only
    fun getBudget(): Double = budgetKsh
    fun getDietaryPreference(): DietaryPreference = diet
    fun getHealthGoal(): HealthGoal = goal

    // WRITE access: only through these methods, which protect the data.
    // Returns true if the budget was accepted, false if it was rejected
    fun updateBudget(newBudget: Double): Boolean {
        if (newBudget.isNaN() || newBudget < MIN_BUDGET || newBudget > MAX_BUDGET) {
            return false
        }
        budgetKsh = newBudget
        return true
    }

    fun updateDietaryPreference(newDiet: DietaryPreference) {
        diet = newDiet
    }

    fun updateHealthGoal(newGoal: HealthGoal) {
        goal = newGoal
    }

    companion object {
        const val MIN_BUDGET = 50.0     // KSh per day
        const val MAX_BUDGET = 5000.0
    }
}