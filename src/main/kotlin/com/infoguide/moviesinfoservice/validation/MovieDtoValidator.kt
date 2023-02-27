package com.infoguide.moviesinfoservice.validation

import com.infoguide.moviesinfoservice.dto.ErrorConstant
import com.infoguide.moviesinfoservice.dto.MovieDto
import com.infoguide.moviesinfoservice.exception.ValidationException
import org.apache.commons.validator.GenericValidator
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils.hasText

/**
 * Validation class for validating / sanitizing if the request is correct or not.
 * It also checks for some contraints which need to be maintained for a valid request.
 */
@Component
class MovieDtoValidator {
    /**
     * Validating the MovieDto request object.
     * 1. Checks if the id is greater than 0
     * 2. Checks if title is present.
     * 3. Checks if release date is present
     * 4. Check of Stars are present or not.
     * 5. Validates if release date is a valid date or not.
     * 6. Checks if all stars are unique for the movie or not.
     * @param movieDto
     */
    fun validateMovieDto(movieDto: MovieDto) {
        if (movieDto.id <= 0) throw ValidationException(ErrorConstant.ID_INVALID.message)
        if (!hasText(movieDto.title)) throw ValidationException(ErrorConstant.TITLE_NULL.message)
        if (!hasText(movieDto.releaseDate)) throw ValidationException(ErrorConstant.RELEASE_DATE_NULL.message)
        if (movieDto.stars.isEmpty()) throw ValidationException(ErrorConstant.STARS_NULL.message)
        if (!validateReleaseDate(movieDto.releaseDate)) throw ValidationException(ErrorConstant.INVALID_RELEASE_DATE.message)
        if (isNonUniqueStarsForTheMovie(movieDto.stars)) throw ValidationException(ErrorConstant.DUPLICATE_STAR_EXISTS.message)
    }

    /**
     * Validates release date format.
     *
     * @param releaseDate
     * @return
     */
    private fun validateReleaseDate(releaseDate: String): Boolean {
        return GenericValidator.isDate(releaseDate, "yyyy-MM-dd", true)
    }

    /**
     * Checks for all stars in the movie are unique or not.
     *
     * @param stars
     * @return
     */
    private fun isNonUniqueStarsForTheMovie(stars: Collection<String>): Boolean {
        val hashSet: MutableSet<String> = hashSetOf()
        stars.forEach {
            if (hashSet.contains(it)) {
                return true
            } else {
                hashSet.add(it)
            }
        }
        return false
    }
}