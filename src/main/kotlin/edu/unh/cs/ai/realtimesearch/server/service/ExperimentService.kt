package edu.unh.cs.ai.realtimesearch.server.service


import edu.unh.cs.ai.realtimesearch.server.domain.ExperimentData
import edu.unh.cs.ai.realtimesearch.server.persistance.ExperimentConfiguration
import edu.unh.cs.ai.realtimesearch.server.persistance.ExperimentConfigurationRepository
import edu.unh.cs.ai.realtimesearch.server.persistance.ExperimentResult
import edu.unh.cs.ai.realtimesearch.server.persistance.ExperimentResultRepository
import org.springframework.stereotype.Service
import javax.inject.Inject

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@Service
class ExperimentService @Inject constructor(val experimentResultRepository: ExperimentResultRepository,
                                            val experimentConfigurationRepository: ExperimentConfigurationRepository) {

    init {
        println("Experiment Service !!!!!!!!!! ")

        val experimentConfiguration = ExperimentConfiguration("domain", "data", "algorithm", 1, "time", "10", hashMapOf(Pair("A", "B"), Pair("B", 1)))
        experimentConfigurationRepository.insert(experimentConfiguration)

        println(experimentConfigurationRepository.count())
        println(experimentConfigurationRepository.findAll()[0])
    }

    fun submitResult(experimentResultData: ExperimentData) {
        experimentResultRepository.insert(ExperimentResult(experimentResultData))
    }

}