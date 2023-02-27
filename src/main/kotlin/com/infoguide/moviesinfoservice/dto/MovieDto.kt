package com.infoguide.moviesinfoservice.dto

/**
 * API request / response for most of the Movie operations.
 * For Add/Update we take this object as part of the request
 * For other operations we sent this as part of a Success API Response.
 *
 * @property id Identifier for the Movie, the id passed in the request and a the id is auto generated.
 * @property title Title of the Movie
 * @property releaseDate Release date for the Movie.
 * @property stars All the stars for the Movie.
 */
data class MovieDto(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val stars: Collection<String>
)