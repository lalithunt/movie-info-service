package com.infoguide.moviesinfoservice.mapper

/**
 * Mapper class for mapping Dto to Entity and Vice versa for Movie data.
 *
 * @param D Dto object for Movie (@Code: MovieDto)
 * @param E Entity object for Movie (@Code : Movie)
 */
interface Mapper<D, E> {
    /**
     * Maps data from an Entity class to a DTO class.
     *
     * @param entity
     * @return
     */
    fun fromEntity(entity: E): D

    /**
     * Maps a Entity List data to DTO List data.
     *
     * @param entityLst
     * @return
     */
    fun fromEntityList(entityLst: Collection<E>): Collection<D>

    /**
     * Maps a DTO to Entity.
     *
     * @param dto
     * @return
     */
    fun toEntity(dto: D): E
}