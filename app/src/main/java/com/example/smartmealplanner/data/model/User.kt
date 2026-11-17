package com.example.smartmealplanner.data.model

// INHERITANCE: the PARENT class.
// Everything a client and an administrator have in common lives here, once.
// "abstract" means you cannot create a plain User, only a Client or an Administrator.
abstract class User(
    val id: Long,
    val username: String,
    val email: String,
    private val passwordHash: String      // private: nobody outside can read it
) {
    // Every child must say which role it has
    abstract val role: Role

    // Login uses this: hash what was typed and compare it with the hash saved at registration
    fun passwordMatches(rawPassword: String): Boolean =
        PasswordHasher.hash(rawPassword) == passwordHash
}

// CHILD 1: a client has everything a User has, PLUS a dietary profile and a budget
class Client(
    id: Long,
    username: String,
    email: String,
    passwordHash: String,
    val profile: ClientProfile
) : User(id, username, email, passwordHash) {
    override val role: Role = Role.CLIENT
}

// CHILD 2: an administrator has everything a User has, PLUS management privileges
class Administrator(
    id: Long,
    username: String,
    email: String,
    passwordHash: String
) : User(id, username, email, passwordHash) {
    override val role: Role = Role.ADMIN

    fun canManageFoods(): Boolean = true
    fun canManageClients(): Boolean = true
}