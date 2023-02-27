package com.infoguide.moviesinfoservice.model

import javax.persistence.*

/**
 * Entity class for storing Movie Information.
 *
 * @property id
 * @property title
 * @property releaseDate
 * @property stars
 */
@Entity
@Table(name = "movie", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("title", "release_date"))])
class Movie(
    @Id
    @Column(name = "id")
    val id: Int,
    @Column(name = "title")
    val title: String,
    @Column(name = "release_date")
    val releaseDate: String,
    @ElementCollection
    val stars: Collection<String>
)