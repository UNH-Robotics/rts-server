package edu.unh.cs.ai.realtimesearch.server.persistance

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Experiment configuration domain object.
 *
 * @author Bence Cserna (bence@cserna.net)
 */
@Document
@TypeAlias("ExperimentConfiguration")
data class ExperimentConfiguration(val configuration: MutableMap<String, Any?>) {
    @Id lateinit var id: String;
}