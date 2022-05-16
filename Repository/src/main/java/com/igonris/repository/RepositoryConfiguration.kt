package com.igonris.repository

enum class RepoBuildType { LOCAL, REMOTE }

object RepositoryConfiguration {
    val PokeRepo = RepoBuildType.REMOTE
}