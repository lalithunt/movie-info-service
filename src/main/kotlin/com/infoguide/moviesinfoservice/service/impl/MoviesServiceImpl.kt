package com.infoguide.moviesinfoservice.service.impl

import com.infoguide.moviesinfoservice.dto.ErrorConstant
import com.infoguide.moviesinfoservice.dto.MovieDto
import com.infoguide.moviesinfoservice.exception.NoDataFoundException
import com.infoguide.moviesinfoservice.exception.RecordAlreadyExists
import com.infoguide.moviesinfoservice.mapper.impl.MovieMapper
import com.infoguide.moviesinfoservice.repository.MovieInfoRepository
import com.infoguide.moviesinfoservice.service.MoviesService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

/**
 * Service class implementation for supporting all the business logic for Movie operations.
 *
 * @property movieInfoRepository
 * @property movieMapper
 */
@Service
class MoviesServiceImpl(private val movieInfoRepository: MovieInfoRepository, private val movieMapper: MovieMapper) :
    MoviesService {

    override fun saveMovieInfo(movieInfo: MovieDto): MovieDto {

        // Checking if record already exists, if yes then this won't' be a new resource.
        if (doesRecordWithIdExists(movieInfo.id)) {
            throw RecordAlreadyExists(ErrorConstant.ID_EXISTS.message)
        }

        try {
            return movieMapper.fromEntity(movieInfoRepository.save(movieMapper.toEntity(movieInfo)))
        } catch (exception: DataIntegrityViolationException) {
            // We can further add checks to see if the cause is ConstraintVoilationException.
            // And also if the Constraint Name constaind the defined Constraint.
            throw RecordAlreadyExists(ErrorConstant.TITLE_RELEASE_DATE_EXISTS.message)
        }
    }

    override fun getAllMovies(): Collection<MovieDto> {
        val findAll = movieInfoRepository.findAll()
        if (findAll.isNotEmpty()) return movieMapper.fromEntityList(findAll)
        throw NoDataFoundException(ErrorConstant.NO_DATA_FOUND.message)
    }

    override fun getMovieById(id: Int): MovieDto {
        val findById = movieInfoRepository.findById(id)
        if (findById.isPresent) return movieMapper.fromEntity(findById.get())
        throw NoDataFoundException(ErrorConstant.NO_DATA_FOUND.message)
    }

    override fun deleteMovie(id: Int) {
        if (movieInfoRepository.existsById(id)) movieInfoRepository.deleteById(id)
        else throw NoDataFoundException(ErrorConstant.NO_DATA_FOUND.message)

    }

    override fun updateMovieInformation(movie: MovieDto) {
        if (movieInfoRepository.existsById(movie.id)) movieInfoRepository.save(movieMapper.toEntity(movie))
        else throw NoDataFoundException(ErrorConstant.NO_DATA_FOUND.message)
    }

    /**
     * Checks if a record exists with the given id.
     *
     * @param id
     * @return
     */
    private fun doesRecordWithIdExists(id: Int): Boolean {
        return movieInfoRepository.existsById(id)
    }
}