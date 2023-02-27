package com.infoguide.moviesinfoservice.dto

/**
 * Error API response in cases where there is any exception.
 * This can be either Business Exception or other exceptions.
 *
 * @property status Status for the Error, this could be identifier for the error
 * @property message Message sent as part of this error response.
 */
data class ErrorDto (
    val status: String,
    val message: String)