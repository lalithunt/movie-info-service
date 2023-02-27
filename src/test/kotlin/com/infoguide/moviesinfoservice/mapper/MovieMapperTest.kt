package com.infoguide.moviesinfoservice.mapper

import com.infoguide.moviesinfoservice.dto.MovieDto
import com.infoguide.moviesinfoservice.mapper.impl.MovieMapper
import com.infoguide.moviesinfoservice.model.Movie
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MovieMapperTest {

    private val movieMapper = MovieMapper()

    @Test
    fun `dto to entity - happy path`() {
        val movieDtoMock = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        val movieEntity = movieMapper.toEntity(movieDtoMock)
        assertEquals(1, movieEntity.id)
        assertEquals("Pulp Fiction", movieEntity.title)
        assertEquals("2020-10-14", movieEntity.releaseDate)
        assertEquals(listOf("John Travolta1"), movieEntity.stars)
    }

    @Test
    fun `entity to dto - happy path`() {
        val movieMock = Movie(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )
        val movieDto = movieMapper.fromEntity(movieMock)
        assertEquals(1, movieDto.id)
        assertEquals("Pulp Fiction", movieDto.title)
        assertEquals("2020-10-14", movieDto.releaseDate)
        assertEquals(listOf("John Travolta1"), movieDto.stars)
    }

    @Test
    fun `entity list to dto list- happy path`() {
        val movieLst = listOf<Movie>(Movie(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        ),Movie(
            2,
            "Pulp Fiction2",
            "2020-10-15",
            listOf("John Travolta")
        ))

        val movieDtoLst = movieMapper.fromEntityList(movieLst)
        assertEquals(1, movieDtoLst.elementAt(0).id)
        assertEquals("Pulp Fiction", movieDtoLst.elementAt(0).title)
        assertEquals("2020-10-14", movieDtoLst.elementAt(0).releaseDate)
        assertEquals(listOf("John Travolta1"), movieDtoLst.elementAt(0).stars)

        assertEquals(2, movieDtoLst.elementAt(1).id)
        assertEquals("Pulp Fiction2", movieDtoLst.elementAt(1).title)
        assertEquals("2020-10-15", movieDtoLst.elementAt(1).releaseDate)
        assertEquals(listOf("John Travolta"), movieDtoLst.elementAt(1).stars)

    }
}