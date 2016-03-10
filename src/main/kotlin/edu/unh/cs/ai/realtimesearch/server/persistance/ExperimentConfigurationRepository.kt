package edu.unh.cs.ai.realtimesearch.server.persistance

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author Bence Cserna (bence@cserna.net)
 */
interface ExperimentConfigurationRepository : MongoRepository<ExperimentConfiguration, String>