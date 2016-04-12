package edu.unh.cs.ai.realtimesearch.server.service


import edu.unh.cs.ai.realtimesearch.server.domain.ExperimentData
import edu.unh.cs.ai.realtimesearch.server.persistance.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import kotlin.concurrent.withLock

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@Service
class ExperimentService @Inject constructor(private val experimentResultRepository: ExperimentResultRepository,
                                            private val experimentConfigurationRepository: ExperimentConfigurationRepository,
                                            private val experimentTaskRepository: ExperimentTaskRepository) {

    private val logger = LoggerFactory.getLogger(ExperimentService::class.java)
    private val configurationLock: Lock = ReentrantLock()
    private val resultLock: Lock = ReentrantLock()

    /**
     * Thread safe configuration getter.
     *
     * It removes a task from the queue and returns the associated configuration.
     * Note: The configuration remain intact.
     */
    fun getConfiguration(): ExperimentConfiguration? = configurationLock.withLock {
        val page = experimentTaskRepository.findAll(PageRequest(0, 1))

        val task = page.content.first() ?: return null
        experimentTaskRepository.delete(task)

        val configuration = experimentConfigurationRepository.findOne(task.configurationId)

        return configuration
    }

    fun submitConfiguration(configurationData: ExperimentData) = configurationLock.withLock {
        experimentConfigurationRepository.insert(ExperimentConfiguration(configurationData.values))
    }

    fun submitConfigurations(configurationData: List<ExperimentData>) = configurationLock.withLock {
        logger.info("Configurations submitted: [${configurationData.size}]")
        for (configuration in configurationData) {
            experimentConfigurationRepository.insert(ExperimentConfiguration(configuration.values))
        }
    }

    fun submitResult(experimentResultData: ExperimentData) = resultLock.withLock {
        // Remove rawDomain from results
        experimentResultRepository.insert(ExperimentResult(experimentResultData.values))
    }

    fun createTasksForAllConfigurations(count: Int) = configurationLock.withLock {
        logger.info("Add tasks for all configurations")

        val configurations = experimentConfigurationRepository.findAll()
        for (i in 1..count) { // Add every configuration multiple times in a interleaved order
            configurations.forEach {
                experimentTaskRepository.insert(ExperimentTask(it.id))
            }
        }

        return@withLock configurations.size
    }

    fun createTasks(configurationTag: String): Int {
        throw UnsupportedOperationException("not implemented")
    }

    fun removeAllTasks() = configurationLock.withLock {
        experimentTaskRepository.deleteAll()
    }

}