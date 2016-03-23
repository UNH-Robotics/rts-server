package edu.unh.cs.ai.realtimesearch.server.service


import edu.unh.cs.ai.realtimesearch.server.domain.ExperimentData
import edu.unh.cs.ai.realtimesearch.server.persistance.*
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


    private val configurationLock: Lock = ReentrantLock()
    private val resultLock: Lock = ReentrantLock()

    init {
        println("Experiment Service !!!!!!!!!! ")

        val experimentData = ExperimentData()
        experimentData["Test field"] = "Test value"

        val experimentConfiguration = ExperimentConfiguration(experimentData)
        experimentConfigurationRepository.insert(experimentConfiguration)
    }

    /**
     * Thread safe configuration getter.
     *
     * It removes a task from the queue and returns the associated configuration.
     * Note: The configuration remain intact.
     */
    fun getConfiguration(): ExperimentConfiguration? = configurationLock.withLock {
        val page = experimentTaskRepository.findAll(PageRequest(0, 1))

        if (page.number == 0) {
            return null
        }

        val task = page.content.first() ?: return null
        experimentTaskRepository.delete(task)

        val configuration = experimentConfigurationRepository.findOne(task.configurationId)

        return configuration
    }

    fun submitConfiguration(configurationData: ExperimentData) = configurationLock.withLock {
        experimentConfigurationRepository.insert(ExperimentConfiguration(configurationData))
    }

    fun submitResult(experimentResultData: ExperimentData) = resultLock.withLock {
        experimentResultRepository.insert(ExperimentResult(experimentResultData))
    }


}