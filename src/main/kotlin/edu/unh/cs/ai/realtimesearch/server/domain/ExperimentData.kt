package edu.unh.cs.ai.realtimesearch.server.domain

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Base class for JSON serialization.
 *
 * The class supports the following structure:
 *
 * MAP -> list of <KEY, VALUE>
 * KEY -> String
 * VALUE -> String | Long | Double | Boolean | null | MAP | ARRAY
 *
 * MAP is the top level item.
 */
@JsonDeserialize(using = ExperimentDeserializer::class)
data class ExperimentData(@JsonIgnore val values: MutableMap<String, Any?> = hashMapOf()) {
    operator fun get(key: String): Any? {
        return values[key]
    }

    @JsonAnySetter
    fun set(key: String, value: String) {
        values[key] = value
    }

    @Suppress("unused")
    @JsonAnyGetter
    fun getProperties() = values

    operator fun set(key: String, value: Any) {
        values[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getTypedValue(key: String): T? = this[key] as? T
    open fun contains(key: String): Boolean = values.contains(key)

    override fun toString(): String {
        return toIndentedJson()
    }
}



