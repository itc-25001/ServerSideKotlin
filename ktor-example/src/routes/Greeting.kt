package jp.ac.it_college.std.s25001.kotlin.ktor.example.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.RoutingNode
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Routing.greetingRoute(){
    route("/greeting") {
        get("/hello"){
            call.respondText("Hello!")
        }
        get("/goodmorning"){
            call.respondText("Good morning!")
        }
    }
}