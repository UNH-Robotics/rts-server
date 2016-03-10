package edu.unh.cs.ai.realtimesearch.server.persistance

import edu.unh.cs.ai.realtimesearch.server.domain.ExperimentData
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@Document
data class ExperimentResult(val experimentData: ExperimentData) {
    @Id var id: String? = null
}