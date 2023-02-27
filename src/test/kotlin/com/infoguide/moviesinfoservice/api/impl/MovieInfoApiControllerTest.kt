package com.infoguide.moviesinfoservice.api.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.infoguide.moviesinfoservice.dto.MovieDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class MovieInfoApiControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    val movieDto = MovieDto(
        1,
        "Pulp Fiction",
        "2020-10-14",
        listOf("John Travolta1")
    )
    val baseUrl: String = "/info-guide/v1/movies"
    val recordDoesNotExistsId: Int = 9999

    @Test
    fun `saving a movie - happy path`() {

        val response = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movieDto)
        }
        response
            .andExpect {
                status { isCreated() }
                jsonPath("$.id") {value (1)}
                jsonPath("$.title") {value ("Pulp Fiction")}
                jsonPath("$.releaseDate") {value ("2020-10-14")}
                jsonPath("$.stars.[0]") {value ("John Travolta1")}}
    }

    @Test
    fun `should return all movies - happy path`() {
        createAMovie(2)

        mockMvc.get(baseUrl)
            .andExpect {
                status { isOk() }
            }

    }


    @Test
    fun `should return single movies - happy path`() {
        createAMovie(3)

        mockMvc.get("$baseUrl/3")
            .andExpect {
                status {isOk()}
                jsonPath("$.id") {value(3)}
                jsonPath("$.title") {value ("Pulp Fiction3")}
                jsonPath("$.releaseDate") {value ("2020-10-14")}
                jsonPath("$.stars.[0]") {value ("John Travolta1")}}

    }

    @Test
    fun `delete a single movies - happy path`() {
        createAMovie(4)

        mockMvc.delete("$baseUrl/4")
            .andExpect {
                status {isNoContent()}}

    }

    @Test
    fun `update a single movies - happy path`() {
        createAMovie(5)
        val movieDtoPatch = MovieDto(
            5,
            "Pulp Fiction",
            "2020-10-22",
            listOf("John Travolta1")
        )
        mockMvc.patch(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movieDtoPatch)
        }
            .andExpect {
                status {isNoContent()}}

    }

    /**
     * Utility method to generate data.
     *
     */
    fun createAMovie(id:Int) {
        mockMvc.post("/info-guide/v1/movies") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(MovieDto(
                id,
                "Pulp Fiction$id",
                "2020-10-14",
                listOf("John Travolta1")
            ))
        }.andExpect {
            status { isCreated() } }
        }

    @Test
    fun `saving a movie - with id 0`() {
        val movieDto = MovieDto(
            0,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1")
        )

        val response = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movieDto)
        }
        response
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.status") { value("INVALID_REQUEST") }
                jsonPath("$.message") { value("Id should be greater than 0") }
            }
    }

    @Test
    fun `saving a movie - with title invalid`() {
        val movieDto = MovieDto(
            1,
            "",
            "2020-10-14",
            listOf("John Travolta1")
        )

        val response = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movieDto)
        }
        response
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.status") { value("INVALID_REQUEST") }
                jsonPath("$.message") { value("Title is mandatory to be passed. Please pass a valid Title in the request.") }
            }
    }

    @Test
    fun `saving a movie - with date invalid`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-1",
            listOf("John Travolta1")
        )

        val response = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movieDto)
        }
        response
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.status") { value("INVALID_REQUEST") }
                jsonPath("$.message") { value("Release Date passed is not a valid date. Please pass a valid Release Date in the request.") }
            }
    }

    @Test
    fun `saving a movie - with empty stars`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            emptyList()
        )

        val response = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movieDto)
        }
        response
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.status") { value("INVALID_REQUEST") }
                jsonPath("$.message") { value("Stars is mandatory to be passed. Please pass a valid Stars in the request.") }
            }
    }

    @Test
    fun `saving a movie - with duplicate stars`() {
        val movieDto = MovieDto(
            1,
            "Pulp Fiction",
            "2020-10-14",
            listOf("John Travolta1", "Uma Thurman", "John Travolta1")
        )

        val response = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movieDto)
        }
        response
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.status") { value("INVALID_REQUEST") }
                jsonPath("$.message") { value("Stars in the movie cannot be duplicate. Please pass valid Stars in the request.") }
            }
    }

    @Test
    fun `should return single movies - record with that id does not exists`() {
        mockMvc.get("$baseUrl/$recordDoesNotExistsId")
            .andExpect {
                status { isNotFound() }
                jsonPath("$.status") { value("DATA_DOES_NOT_EXIST") }
                jsonPath("$.message") { value("No Data present with the requested query.") }
            }

    }

    @Test
    fun `delete a single movies - record with that id does not exists`() {
        mockMvc.delete("$baseUrl/$recordDoesNotExistsId")
            .andExpect {
                status { isNotFound() }
                jsonPath("$.status") { value("DATA_DOES_NOT_EXIST") }
                jsonPath("$.message") { value("No Data present with the requested query.") }
            }

    }

    @Test
    fun `update a single movies - record with that id does not exists`() {
        val movieDtoPatch = MovieDto(
            recordDoesNotExistsId,
            "Pulp Fiction",
            "2020-10-22",
            listOf("John Travolta1")
        )
        mockMvc.patch(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movieDtoPatch)
        }
            .andExpect {
            status { isNotFound() }
            jsonPath("$.status") { value("DATA_DOES_NOT_EXIST") }
            jsonPath("$.message") { value("No Data present with the requested query.") }
        }

    }


}