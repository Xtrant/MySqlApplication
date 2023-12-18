package com.example.mysqlapplication


class ApiEndPoint {
    companion object {

        private val SERVER = "http://192.168.137.1:7882/studentcrud/"
        val CREATE = SERVER+"create.php"
        val READ = SERVER+"read.php"
        val DELETE = SERVER+"delete.php"
        val UPDATE = SERVER+"update.php"

    }
}