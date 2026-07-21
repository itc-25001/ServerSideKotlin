package dao

import org.jetbrains.exposed.v1.jdbc.Database

fun dbConnect() {
    Database.connect(
        url = "jdbc:postgresql://127.0.0.1:5432/exposed_example",
        driver = "org.postgresql.Driver",
        user = "exposed",
        password = "kotlin+exposed",
    )
}