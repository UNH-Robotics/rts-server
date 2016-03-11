package edu.unh.cs.ai.realtimesearch.server.controller

import edu.unh.cs.ai.realtimesearch.server.domain.ExperimentData
import edu.unh.cs.ai.realtimesearch.server.persistance.ExperimentConfiguration
import edu.unh.cs.ai.realtimesearch.server.service.ClientManagerService
import edu.unh.cs.ai.realtimesearch.server.service.ExperimentService
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@RestController
class PlannerController @Inject constructor(val experimentService: ExperimentService,val clientManager: ClientManagerService) {

    val counter = AtomicLong()

    @RequestMapping(path = arrayOf("/greeting"), method = arrayOf(RequestMethod.GET))
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {
        return Greeting(counter.incrementAndGet(), "Hello, $name")
    }

    @RequestMapping(path = arrayOf("/status"), method = arrayOf(RequestMethod.GET))
    fun status(): String {
        return "RTS Server is up!"
    }

    @RequestMapping(path = arrayOf("/configuration/{clientId}"), method = arrayOf(RequestMethod.GET))
    fun getConfiguration(@PathVariable clientId: String): ExperimentConfiguration {
        val experimentConfiguration = ExperimentConfiguration("domain", "data", "algorithm", 1, "time", "10", hashMapOf(Pair("A", "B"), Pair("B", listOf<Double>(0.1, 0.2, 0.3))))

        return experimentConfiguration
    }

    @RequestMapping(path = arrayOf("/result/{clientId}"), method = arrayOf(RequestMethod.POST))
    fun uploadResult(@RequestBody experimentResult: ExperimentData, @PathVariable clientId: String) {
        println("Result submitted: $clientId \n$experimentResult")
        experimentService.submitResult(experimentResult)

    }

    @RequestMapping(path = arrayOf("/checkIn"), method = arrayOf(RequestMethod.POST))
    fun checkIn(@RequestBody clientSystemProperies: ExperimentData): String {
        println("Client check-in: $clientSystemProperies")
        return counter.andIncrement.toString()
    }

}

class Test(val map: Map<String, Any>) {
    val id: Long by map
    val name by map
}

data class Greeting(val id: Long, val content: String)