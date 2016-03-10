package edu.unh.cs.ai.realtimesearch.server.configuration

import com.mongodb.MongoClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@Configuration
open class MongoDbConfiguration {

    @Bean
    open fun mongoTemplate() : MongoTemplate {
        val mongoTemplate = MongoTemplate(MongoClient(), "rtsdb")
        return mongoTemplate
    }

}
