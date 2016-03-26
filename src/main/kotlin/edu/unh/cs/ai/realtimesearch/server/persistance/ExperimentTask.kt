package edu.unh.cs.ai.realtimesearch.server.persistance

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Task is a job that should be executed.
 *
 * @author Bence Cserna (bence@cserna.net)
 */
@Document
@TypeAlias("ExperimentTask")
data class ExperimentTask(val configurationId: String) {
    @Id lateinit var id: String
}