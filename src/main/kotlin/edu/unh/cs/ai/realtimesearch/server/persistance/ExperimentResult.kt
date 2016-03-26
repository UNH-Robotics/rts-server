package edu.unh.cs.ai.realtimesearch.server.persistance

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@Document
@TypeAlias("ExperimentResult")
data class ExperimentResult(val result: MutableMap<String, Any?>) {
    @Id lateinit var id: String
}