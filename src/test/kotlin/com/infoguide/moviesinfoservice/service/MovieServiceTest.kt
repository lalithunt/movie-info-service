package com.infoguide.moviesinfoservice.service

import com.infoguide.moviesinfoservice.dto.ErrorConstant
import com.infoguide.moviesinfoservice.dto.MovieDto
import com.infoguide.moviesinfoservice.exception.NoDataFoundException
import com.infoguide.moviesinfoservice.exception.RecordAlreadyExists
import com.infoguide.moviesinfoservice.mapper.impl.MovieMapper
import com.infoguide.moviesinfoservice.model.Movie
import com.infoguide.moviesinfoservice.repository.MovieInfoRepository
import com.infoguide.moviesinfoservice.service.impl.MoviesServiceImpl
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DataIntegrityViolationException
import java.util.*

class MovieServiceTest {
    private val movieMapperMock: MovieMapper = mockk()
    private val movieInfoRepositoryMock: MovieInfoRepository = mockk()
    private val moviesService = MoviesServiceImpl(movieInfoRepositoryMock, movieMapperMock)

    @Test
    fun `saving movie dto - happy path`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        val movieEntity = Movie(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        every { movieMapperMock.toEntity(any()) } returns movieEntity
        every { movieMapperMock.fromEntity(any()) } returns movieDto

        every { movieInfoRepositoryMock.existsById(1) } returns false
        every { movieInfoRepositoryMock.save(any()) } returns movieEntity

        val movieDtoReturn = moviesService.saveMovieInfo(movieDto)


        Assertions.assertEquals(1, movieDtoReturn.id)
        Assertions.assertEquals("Pulp Fiction", movieDtoReturn.title)
        Assertions.assertEquals("2020-10-14", movieDtoReturn.releaseDate)
        Assertions.assertEquals(listOf("John Travolta1"), movieDtoReturn.stars)

        verify(exactly = 1) { movieInfoRepositoryMock.existsById(any()) }
        verify(exactly = 1) { movieInfoRepositoryMock.save(any()) }
        verify(exactly = 1) { movieMapperMock.toEntity(movieDto) }
        verify(exactly = 1) { movieMapperMock.fromEntity(movieEntity) }

    }

    @Test
    fun `getting movie dto - happy path`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        val movieEntity = Movie(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        every { movieMapperMock.fromEntity(any()) } returns movieDto

        every { movieInfoRepositoryMock.findById(1) } returns Optional.of(movieEntity)

        val movieDtoReturn = moviesService.getMovieById(1)

        Assertions.assertEquals(1, movieDtoReturn.id)
        Assertions.assertEquals("Pulp Fiction", movieDtoReturn.title)
        Assertions.assertEquals("2020-10-14", movieDtoReturn.releaseDate)
        Assertions.assertEquals(listOf("John Travolta1"), movieDtoReturn.stars)

        verify(exactly = 1) { movieInfoRepositoryMock.findById(any()) }
        verify(exactly = 1) { movieMapperMock.fromEntity(movieEntity) }

    }

    @Test
    fun `getting all movies dto - happy path`() {
        val movieDtoLst = listOf<MovieDto>(
            MovieDto(
                1,
                "Pulp Fiction",
                "2020-10-14",
                listOf("John Travolta1")
            ), MovieDto(
                2,
                "Pulp Fiction2",
                "2020-10-14",
                listOf("John Travolta2")
            )
        )

        val movieLst = listOf<Movie>(
            Movie(
                1,
                "Pulp Fiction",
                "2020-10-14",
                listOf("John Travolta1")
            ),
            Movie(
                2,
                "Pulp Fiction2",
                "2020-10-14",
                listOf("John Travolta2")
            )
        )
        every { movieMapperMock.fromEntityList(any()) } returns movieDtoLst
        every { movieInfoRepositoryMock.findAll() } returns movieLst

        val movieDtoReturn = moviesService.getAllMovies()

        Assertions.assertEquals(1, movieDtoReturn.elementAt(0).id)
        Assertions.assertEquals("Pulp Fiction", movieDtoReturn.elementAt(0).title)
        Assertions.assertEquals("2020-10-14", movieDtoReturn.elementAt(0).releaseDate)
        Assertions.assertEquals(listOf("John Travolta1"), movieDtoReturn.elementAt(0).stars)

        Assertions.assertEquals(2, movieDtoReturn.elementAt(1).id)
        Assertions.assertEquals("Pulp Fiction2", movieDtoReturn.elementAt(1).title)
        Assertions.assertEquals("2020-10-14", movieDtoReturn.elementAt(1).releaseDate)
        Assertions.assertEquals(listOf("John Travolta2"), movieDtoReturn.elementAt(1).stars)

        verify(exactly = 1) { movieInfoRepositoryMock.findAll() }
        verify(exactly = 1) { movieMapperMock.fromEntityList(movieLst) }
    }

    @Test
    fun `update movie dto - happy path`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        val movieEntity = Movie(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        every { movieMapperMock.toEntity(any()) } returns movieEntity

        every { movieInfoRepositoryMock.existsById(1) } returns true
        every { movieInfoRepositoryMock.save(any()) } returns movieEntity

        moviesService.updateMovieInformation(movieDto)

        verify(exactly = 1) { movieInfoRepositoryMock.existsById(any()) }
        verify(exactly = 1) { movieInfoRepositoryMock.save(any()) }
        verify(exactly = 1) { movieMapperMock.toEntity(movieDto) }

    }

    @Test
    fun `delete movie dto - happy path`() {
        val movieEntity = Movie(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )

        every { movieMapperMock.toEntity(any()) } returns movieEntity

        every { movieInfoRepositoryMock.existsById(any()) } returns true
        every { movieInfoRepositoryMock.deleteById(any()) } returns Unit

        moviesService.deleteMovie(1)

        verify(exactly = 1) { movieInfoRepositoryMock.existsById(any()) }
        verify(exactly = 1) { movieInfoRepositoryMock.deleteById(1) }

    }

    @Test
    fun `id already exists while trying to save`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )

        every { movieInfoRepositoryMock.existsById(any()) } returns true

        val exception = assertThrows<RecordAlreadyExists> {
            moviesService.saveMovieInfo(movieDto)
        }
        Assertions.assertEquals(ErrorConstant.ID_EXISTS.message, exception.message)
        verify { movieMapperMock wasNot Called }
        verify{ movieInfoRepositoryMock.save(any()) wasNot Called }
    }

    @Test
    fun `title and release date not unique while trying to save`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        val movieEntity = Movie(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )

        every { movieInfoRepositoryMock.existsById(any()) } returns false
        every { movieMapperMock.toEntity(any()) } returns movieEntity

        every { movieInfoRepositoryMock.save(any()) }.throws(DataIntegrityViolationException("Some error"))


        val exception = assertThrows<RecordAlreadyExists> {
            moviesService.saveMovieInfo(movieDto)
        }
        Assertions.assertEquals(ErrorConstant.TITLE_RELEASE_DATE_EXISTS.message, exception.message)
        verify { movieMapperMock.fromEntity(movieEntity) wasNot Called }
    }

    @Test
    fun `no record present when trying to get all the records`() {
        every { movieInfoRepositoryMock.findAll()} returns emptyList()

        val exception = assertThrows<NoDataFoundException> {
            moviesService.getAllMovies()
        }
        Assertions.assertEquals(ErrorConstant.NO_DATA_FOUND.message, exception.message)
        verify { movieMapperMock wasNot Called }
    }

    @Test
    fun `no record present when trying to get movie info by id`() {
        every { movieInfoRepositoryMock.findById(1)} returns Optional.empty()

        val exception = assertThrows<NoDataFoundException> {
            moviesService.getMovieById(1)
        }
        Assertions.assertEquals(ErrorConstant.NO_DATA_FOUND.message, exception.message)
        verify { movieMapperMock wasNot Called }
    }

    @Test
    fun `no record present when trying to update`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        every { movieInfoRepositoryMock.existsById(1)} returns false

        val exception = assertThrows<NoDataFoundException> {
            moviesService.updateMovieInformation(movieDto)
        }
        Assertions.assertEquals(ErrorConstant.NO_DATA_FOUND.message, exception.message)
        verify { movieMapperMock wasNot Called }
        verify{ movieInfoRepositoryMock.save(any()) wasNot Called }
    }

    @Test
    fun `no record present when trying to delete`() {

        every { movieInfoRepositoryMock.existsById(1)} returns false

        val exception = assertThrows<NoDataFoundException> {
            moviesService.deleteMovie(1)
        }
        Assertions.assertEquals(ErrorConstant.NO_DATA_FOUND.message, exception.message)
        verify { movieMapperMock wasNot Called }
        verify{ movieInfoRepositoryMock.save(any()) wasNot Called }
    }

}