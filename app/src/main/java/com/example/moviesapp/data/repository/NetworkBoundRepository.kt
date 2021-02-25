package com.example.moviesapp.data.repository

import androidx.annotation.MainThread
import com.example.moviesapp.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * A repository which provides resource from local database as well as remote end point.
 *
 * [RESULT] represents the type for database.
 * [REQUEST] represents the type for network.
 */
@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<Businesses> {

    fun asFlow() = flow<State<Businesses>> {

        // Emit Loading State
        emit(State.loading())

        // Fetch latest posts from remote
        val apiResponse = fetchFromRemote()

        // Parse body
        val businesses = apiResponse

        // Check for response validation
        if (businesses.isNotEmpty()) {
            // Save posts into the persistence storage
            emit(State.success<Businesses>(apiResponse.first()))
        } else {
            // Something went wrong! Emit Error state.
            emit(State.error("error"))
        }

    }.catch { e ->
        // Exception occurred! Emit error
        emit(State.error("Network error! Can't get latest posts."))
        e.printStackTrace()
    }


    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): List<Businesses>

}
