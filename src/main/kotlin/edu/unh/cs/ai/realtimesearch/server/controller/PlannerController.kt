package edu.unh.cs.ai.realtimesearch.server.controller

import edu.unh.cs.ai.realtimesearch.server.domain.ExperimentData
import edu.unh.cs.ai.realtimesearch.server.service.ClientManagerService
import edu.unh.cs.ai.realtimesearch.server.service.ExperimentService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@RestController
class PlannerController @Inject constructor(val experimentService: ExperimentService, val clientManager: ClientManagerService) {

    private val logger = LoggerFactory.getLogger(PlannerController::class.java)
    val counter = AtomicLong()

    @RequestMapping(path = arrayOf("/status"), method = arrayOf(RequestMethod.GET))
    fun status(): String {
        return "RTS Server is up!"
    }

    @RequestMapping(path = arrayOf("/configuration/{clientId}"), method = arrayOf(RequestMethod.GET))
    fun getConfiguration(@PathVariable clientId: String): ExperimentData? {
        val configuration = experimentService.getConfiguration()?.configuration
        return if (configuration != null) {
            ExperimentData(configuration)
        } else {
            null
        }
    }

    @RequestMapping(path = arrayOf("/result/{clientId}"), method = arrayOf(RequestMethod.POST))
    fun uploadResult(@RequestBody experimentResult: ExperimentData, @PathVariable clientId: String) {
        logger.info("Result submitted: $clientId")
        experimentService.submitResult(experimentResult)
    }

    @RequestMapping(path = arrayOf("/configuration"), method = arrayOf(RequestMethod.POST))
    fun uploadConfiguration(@RequestBody experimentConfiguration: ExperimentData) {
        logger.info("Configuration submitted")
        experimentService.submitConfiguration(experimentConfiguration)
    }

    @RequestMapping(path = arrayOf("/configurations"), method = arrayOf(RequestMethod.POST))
    fun uploadConfigurations(@RequestBody experimentConfigurations: List<ExperimentData>) {
        logger.info("Configurations submitted: [${experimentConfigurations.size}]")
        experimentService.submitConfigurations(experimentConfigurations)
    }

    @RequestMapping(path = arrayOf("/checkIn"), method = arrayOf(RequestMethod.POST))
    fun checkIn(@RequestBody clientSystemProperties: ExperimentData): String {
        logger.debug("Client check-in: $clientSystemProperties")
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
    fun removeAllTasks(): String {
        logger.debug("Remove all scheduled tasks")
        experimentService.removeAllTasks()
        return counter.andIncrement.toString()
    }

    @RequestMapping(path = arrayOf("/task/{clientId}"), method = arrayOf(RequestMethod.GET))
    fun getTasks(@PathVariable clientId: String): ExperimentData? {
        TODO()
    }

    @RequestMapping(path = arrayOf("/task/schedule/{configurationTag}"), method = arrayOf(RequestMethod.POST))
    fun createTasks(@PathVariable configurationTag: String): Int {
        println("Add tasks with tag.")
        val createdTaskCount: Int = experimentService.createTasks(configurationTag)
        return createdTaskCount
    }

    @RequestMapping(path = arrayOf("/task/schedule"), method = arrayOf(RequestMethod.POST))
    fun scheduleAllConfiguration(): Int {
        println("Add tasks for all configuration")
        val createdTaskCount: Int = experimentService.createTasksForAllConfigurations()
        return createdTaskCount
    }

}