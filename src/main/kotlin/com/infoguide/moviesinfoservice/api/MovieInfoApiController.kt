package com.infoguide.moviesinfoservice.api

import com.infoguide.moviesinfoservice.dto.MovieDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


/**
 * Movies Info Controller interface exposing Movies resource with all the possible resource endpoints.
 *
 * Note : We can add swagger documentation for this endpoints using the Swagger annotations.
 *
 */
@RequestMapping("/info-guide/v1/movies")
interface MovieInfoApiController {


    /**
     * Create a new Movie Object with all the provided information.
     *
     * @param movie
     * @return MovieDto object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMovie(@RequestBody movie: MovieDto) : MovieDto

    /**
     * Gets all the movies in the DB. This returns the whole list,
     * we can have pagination supporting multiple movie pages.
     *
     * @return Collection<MovieDto>
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getMovies() : Collection<MovieDto>

    /**
     * Getting movie information based on the ID passed in the path Param.
     *
     * @param id
     * @return MovieDto object
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMoviesById(@PathVariable id: Int) : MovieDto

    /**
     * Deletes a Movie resource based on the ID passed in the path Param
     * This can be in future a Soft delete then the hard delete we have implemented here.
     * It returns no content.
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMovieInformation(@PathVariable id: Int)

    /**
     * This operation updates the Movie resource.
     * We have used Patch for updating the resource as it update partial data and that's what we are intending.
     * Also for simplicity we are taking the whole object for update, but generally we should take
     * the ID for update and then the body should mention which field is getting update.
     *
     * @param movie
     */
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateMovieInformation(@RequestBody movie: MovieDto)
}