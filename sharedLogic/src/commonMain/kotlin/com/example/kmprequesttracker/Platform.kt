package com.example.kmprequesttracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform