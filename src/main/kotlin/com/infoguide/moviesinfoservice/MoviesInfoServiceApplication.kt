package com.infoguide.moviesinfoservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main Spring boot class.
 *
 */
@SpringBootApplication
class MoviesInfoServiceApplication

fun main(args: Array<String>) {
    runApplication<MoviesInfoServiceApplication>(*args)
}
