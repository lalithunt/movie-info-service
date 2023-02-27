package com.infoguide.moviesinfoservice.service

import com.infoguide.moviesinfoservice.dto.MovieDto

/**
 * All the business logic to support save,update,get and delete operation on Movie
 * is added in this class.
 * All Business validation along with other business logic should be in this class.
 */
interface MoviesService {

    /**
     * Save a movie information. Before the operate we do validate if all the requested data is fine or not.
     *
     * @param movieInfo
     * @return
     */
    fun saveMovieInfo(movieInfo: MovieDto): MovieDto

    /**
     * Gets all the movies information.
     * This operation should have pagination supported as the data can be huge.
     *
     * @return
     */
    fun getAllMovies(): Collection<MovieDto>

    /**
     * Gets movie information based on the ID provided.
     *
     * @param id
     * @return
     */
    fun getMovieById(id: Int): MovieDto

    /**
     * Deletes a movie based on the ID.
     * Again soft delete would be better.
     *
     * @param id
     */
    fun deleteMovie(id: Int)

    /**
     * Updates a single movie information based on the request.
     * We do validation before allowing to update.
     *
     * @param movie
     */
    fun updateMovieInformation(movie: MovieDto)
}