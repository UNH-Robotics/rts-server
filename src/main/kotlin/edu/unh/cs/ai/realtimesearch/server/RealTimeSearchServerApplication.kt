package edu.unh.cs.ai.realtimesearch.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@SpringBootApplication
open class RealTimeSearchServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(RealTimeSearchServerApplication::class.java, *args)
}
