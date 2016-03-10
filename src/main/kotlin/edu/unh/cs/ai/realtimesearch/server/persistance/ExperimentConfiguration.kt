package edu.unh.cs.ai.realtimesearch.server.persistance

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Experiment configuration domain object.
 *
 * @author Bence Cserna (bence@cserna.net)
 */
@Document
data class ExperimentConfiguration(
        var domainName: String,
        var rawDomain: String,
        var algorithmName: String,
        var numberOfRuns: Int,
        var terminationCheckerType: String,
        var terminationCheckerParameter: String,
        var values: Map<String, Any>) {

    @Id var id: String? = null
}