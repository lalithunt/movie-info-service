package com.infoguide.moviesinfoservice.api.impl

import com.infoguide.moviesinfoservice.api.MovieInfoApiController
import com.infoguide.moviesinfoservice.dto.MovieDto
import com.infoguide.moviesinfoservice.service.MoviesService
import com.infoguide.moviesinfoservice.validation.MovieDtoValidator
import org.springframework.web.bind.annotation.RestController

/**
 * Movie rest controller for taking all the traffic for the given URL.
 *
 * @property moviesInfoService
 * @property requestValidator
 */
@RestController
class MovieInfoControllerImpl(
    private val moviesInfoService: MoviesService,
    private val requestValidator: MovieDtoValidator
) : MovieInfoApiController {

    override fun createMovie(movie: MovieDto): MovieDto {
        requestValidator.validateMovieDto(movie)
        return moviesInfoService.saveMovieInfo(movie);
    }

    override fun getMovies(): Collection<MovieDto> = moviesInfoService.getAllMovies()

    override fun getMoviesById(id: Int): MovieDto = moviesInfoService.getMovieById(id)

    override fun deleteMovieInformation(id: Int) = moviesInfoService.deleteMovie(id)

    override fun updateMovieInformation(movie: MovieDto) {
        requestValidator.validateMovieDto(movie)
        moviesInfoService.updateMovieInformation(movie)
    }
}