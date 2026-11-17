package com.example.smartmealplanner.data.model

// An object is ONE shared instance, so we call PasswordHasher.hash(...) without creating it
object PasswordHasher {

    // Turns a password into a long string of letters and numbers (a SHA-256 hash).
    // The same password always gives the same hash, and the hash cannot be turned back
    // into the password. We store the hash, never the password itself.
    fun hash(rawPassword: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(rawPassword.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}