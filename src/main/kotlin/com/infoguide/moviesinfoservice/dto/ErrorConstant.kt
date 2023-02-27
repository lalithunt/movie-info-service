package com.infoguide.moviesinfoservice.dto

/**
 * Enumeration Contact contains all the Error messages.
 *
 * @property message
 */
enum class ErrorConstant(val message: String) {
    ID_EXISTS("Record with this id already exists."),
    ID_INVALID("Id should be greater than 0"),
    TITLE_RELEASE_DATE_EXISTS("Title and Release date combo already exits. Please pass unique combo value."),
    TITLE_NULL("Title is mandatory to be passed. Please pass a valid Title in the request."),
    RELEASE_DATE_NULL("Release Date is mandatory to be passed. Please pass a valid Release Date in the request."),
    INVALID_RELEASE_DATE("Release Date passed is not a valid date. Please pass a valid Release Date in the request."),
    STARS_NULL("Stars is mandatory to be passed. Please pass a valid Stars in the request."),
    DUPLICATE_STAR_EXISTS("Stars in the movie cannot be duplicate. Please pass valid Stars in the request."),
    NO_DATA_FOUND("No Data present with the requested query."),

}