package edu.unh.cs.ai.realtimesearch.server.controller

import edu.unh.cs.ai.realtimesearch.server.domain.ExperimentData
import edu.unh.cs.ai.realtimesearch.server.service.ClientManagerService
import edu.unh.cs.ai.realtimesearch.server.service.ExperimentService
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@RestController
class PlannerController @Inject constructor(val experimentService: ExperimentService, val clientManager: ClientManagerService) {

    val counter = AtomicLong()

    @RequestMapping(path = arrayOf("/status"), method = arrayOf(RequestMethod.GET))
    fun status(): String {
        return "RTS Server is up!"
    }

    @RequestMapping(path = arrayOf("/configuration/{clientId}"), method = arrayOf(RequestMethod.GET))
    fun getConfiguration(@PathVariable clientId: String): ExperimentData? {
        return experimentService.getConfiguration()?.experimentData
    }

    @RequestMapping(path = arrayOf("/result/{clientId}"), method = arrayOf(RequestMethod.POST))
    fun uploadResult(@RequestBody experimentResult: ExperimentData, @PathVariable clientId: String) {
        println("Result submitted: $clientId \n$experimentResult")
        experimentService.submitResult(experimentResult)
    }

    @RequestMapping(path = arrayOf("/configuration/"), method = arrayOf(RequestMethod.POST))
    fun uploadConfiguration(@RequestBody experimentConfiguration: ExperimentData) {
        println("Configuration submitted")
        experimentService.submitConfiguration(experimentConfiguration)
    }

    @RequestMapping(path = arrayOf("/checkIn"), method = arrayOf(RequestMethod.POST))
    fun checkIn(@RequestBody clientSystemProperties: ExperimentData): String {
        println("Client check-in: $clientSystemProperties")
        val clientId = counter.andIncrement.toString()
        clientManager.clientCheckIn(clientId)
        return clientId
    }

    @RequestMapping(path = arrayOf("/checkIn/{clientId}"), method = arrayOf(RequestMethod.POST))
    fun checkInWithId(@RequestBody clientSystemProperies: ExperimentData, @PathVariable clientId: String) {
        println("Client check-in: $clientSystemProperies")
        clientManager.clientCheckIn(clientId)
    }

    @RequestMapping(path = arrayOf("/task/remove"), method = arrayOf(RequestMethod.POST))
    fun removeAllTasks(@RequestBody clientSystemProperties: ExperimentData): String {
        println("Remove all tasks: $clientSystemProperties")
        return counter.andIncrement.toString()
    }

    @RequestMapping(path = arrayOf("/task/{clientId}"), method = arrayOf(RequestMethod.GET))
    fun getTasks(@PathVariable clientId: String): ExperimentData? {
        println("Get all tasks.")
        return null
    }

    @RequestMapping(path = arrayOf("/task/create/{configurationTag}"), method = arrayOf(RequestMethod.POST))
    fun createTasks(@PathVariable configurationTag: String): Int {
        println("Add tasks with tag.")
        val createdTaskCount: Int = experimentService.createTasks(configurationTag)
        return createdTaskCount
    }

}