package com.infoguide.moviesinfoservice.mapper.impl

import com.infoguide.moviesinfoservice.dto.MovieDto
import com.infoguide.moviesinfoservice.mapper.Mapper
import com.infoguide.moviesinfoservice.model.Movie
import org.springframework.stereotype.Component

/**
 * Implementation for mapper.
 *
 */
@Component
class MovieMapper : Mapper<MovieDto, Movie> {

    override fun fromEntity(entity: Movie): MovieDto {
        return MovieDto(
            entity.id,
            entity.title,
            entity.releaseDate,
            entity.stars,
        )
    }

    override fun toEntity(dto: MovieDto): Movie {
        return Movie(
            dto.id,
            dto.title,
            dto.releaseDate,
            dto.stars,
        )
    }

    override fun fromEntityList(entityLst: Collection<Movie>): Collection<MovieDto> {
        return entityLst.stream().map { movie -> fromEntity(movie) }.toList()
    }
}