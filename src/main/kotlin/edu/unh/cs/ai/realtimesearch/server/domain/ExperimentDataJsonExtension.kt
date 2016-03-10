package edu.unh.cs.ai.realtimesearch.server.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun <T : ExperimentData> T.toJson(): String {
    val mapper = ObjectMapper()
    return mapper.writeValueAsString(this)
}

fun <T : ExperimentData> T.toIndentedJson(): String {
    val mapper = ObjectMapper()
    mapper.enable(SerializationFeature.INDENT_OUTPUT)

    return mapper.writeValueAsString(this)
}

fun experimentDataFromJson(jsonExperimentConfiguration: String): ExperimentData {
    val mapper = ObjectMapper().registerKotlinModule()
    return mapper.readValue<ExperimentData>(jsonExperimentConfiguration)
}