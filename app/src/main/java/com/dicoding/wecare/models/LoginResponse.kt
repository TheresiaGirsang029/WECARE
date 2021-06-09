package com.dicoding.wecare.models

data class LoginResponse(val error: Boolean, val message:String?, val user: User)