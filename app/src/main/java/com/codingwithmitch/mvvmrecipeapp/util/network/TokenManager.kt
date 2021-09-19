package com.codingwithmitch.mvvmrecipeapp.util.network

interface TokenManager {
    fun getToken(): String
}

class TokenManagerImpl(
    private val token: String
) : TokenManager {
    override fun getToken(): String = token
}