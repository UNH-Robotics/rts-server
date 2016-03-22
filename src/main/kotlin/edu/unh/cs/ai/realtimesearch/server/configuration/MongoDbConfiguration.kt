package edu.unh.cs.ai.realtimesearch.server.configuration

import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@Configuration
@PropertySource("classpath:/mongodb.properties")
open class MongoDbConfiguration {

    @Bean
    open fun mongoTemplate(
            @Value("\${mongodb.host:127.0.0.1}") mongodbHost: String,
            @Value("\${mongodb.port:27017}") mongodbPort: Int,
            @Value("\${mongodb.database:rts}") mongodbDatabase: String,
            @Value("\${mongodb.user}") mongodbUser: String,
            @Value("\${mongodb.password}") mongodbPassword: String): MongoTemplate {

        val credential = MongoCredential.createCredential(mongodbUser, mongodbDatabase, mongodbPassword.toCharArray())
        val serverAddress = ServerAddress(mongodbHost, mongodbPort)
        val mongoClient = MongoClient(serverAddress, listOf(credential))
        val mongoTemplate = MongoTemplate(mongoClient, mongodbDatabase)

        return mongoTemplate
    }
}
