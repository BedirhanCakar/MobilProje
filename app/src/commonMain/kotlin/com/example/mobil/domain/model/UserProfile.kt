package com.example.mobil.domain.model

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: Long,
    val isSynced: Boolean,
    val updatedAt: Long
)
