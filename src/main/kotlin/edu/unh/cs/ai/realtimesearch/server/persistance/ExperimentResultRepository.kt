package edu.unh.cs.ai.realtimesearch.server.persistance

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author Bence Cserna (bence@cserna.net)
 */
interface ExperimentResultRepository : MongoRepository<ExperimentResult, String>