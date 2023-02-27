package com.infoguide.moviesinfoservice.validation

import com.infoguide.moviesinfoservice.dto.ErrorConstant
import com.infoguide.moviesinfoservice.dto.MovieDto
import com.infoguide.moviesinfoservice.exception.ValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MovieDtoValidatorTest {
    private val movieDtoValidator = MovieDtoValidator()

    @Test
    fun `valid movie dto - happy path`() {
        val movieDtoMock = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        movieDtoValidator.validateMovieDto(movieDtoMock)
    }

    @Test()
    fun `movie dto with 0 id value`() {
        val movieDtoMock = MovieDto(
            0,
            "",
            "2020-10-14",
            listOf("John Travolta1")
        )
        val exception = assertThrows<ValidationException> {
            movieDtoValidator.validateMovieDto(movieDtoMock)
        }
        assertEquals(ErrorConstant.ID_INVALID.message, exception.message)
    }
    @Test()
    fun `movie dto with empty title`() {
        val movieDtoMock = MovieDto(
            1,
            "",
            "2020-10-14",
            listOf("John Travolta1")
        )
        val exception = assertThrows<ValidationException> {
            movieDtoValidator.validateMovieDto(movieDtoMock)
        }
        assertEquals(ErrorConstant.TITLE_NULL.message, exception.message)
    }

    @Test()
    fun `movie dto with empty release date`() {
        val movieDtoMock = MovieDto(
            1,
            "title",
            "",
            listOf("John Travolta1")
        )
        val exception = assertThrows<ValidationException> {
            movieDtoValidator.validateMovieDto(movieDtoMock)
        }
        assertEquals(ErrorConstant.RELEASE_DATE_NULL.message, exception.message)
    }

    @Test()
    fun `movie dto with empty stars collection`() {
        val movieDtoMock = MovieDto(
            1,
            "title",
            "2020-10-14",
            emptyList()
        )
        val exception = assertThrows<ValidationException> {
            movieDtoValidator.validateMovieDto(movieDtoMock)
        }
        assertEquals(ErrorConstant.STARS_NULL.message, exception.message)
    }

    @Test()
    fun `movie dto with invalid date`() {
        val movieDtoMock = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-1",
            listOf("John Travolta1")
        )
        val exception = assertThrows<ValidationException> {
            movieDtoValidator.validateMovieDto(movieDtoMock)
        }
        assertEquals(ErrorConstant.INVALID_RELEASE_DATE.message, exception.message)
    }

    @Test()
    fun `movie dto with duplicate stars`() {
        val movieDtoMock = MovieDto(
            1,
            "title",
            "2020-10-14",
            listOf("John Travolta1", "Uma Thurman", "John Travolta1")
        )

        val exception = assertThrows<ValidationException> {
            movieDtoValidator.validateMovieDto(movieDtoMock)
        }
        assertEquals(ErrorConstant.DUPLICATE_STAR_EXISTS.message, exception.message)
    }

}