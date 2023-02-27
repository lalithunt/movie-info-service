package com.infoguide.moviesinfoservice.repository

import com.infoguide.moviesinfoservice.model.Movie
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Movie Repository class for all the JPA related operation.
 * This class is responsible to save, get, update and delete on Movie Entity.
 *
 */
interface MovieInfoRepository : JpaRepository<Movie, Int> {
}