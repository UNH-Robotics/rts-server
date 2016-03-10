package edu.unh.cs.ai.realtimesearch.server.service

import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Bence Cserna (bence@cserna.net)
 */
@Service
class ClientManagerService {
    private val activeClients = ArrayList<String>()

    fun getActiveClients(): List<String> {
        return activeClients
    }

    fun clientCheckIn(clientId: String) {




    }

}