package com.infoguide.moviesinfoservice.exception

/**
 * Exception class for No Data Found exception.
 *
 * @constructor
 * Message which is sent as part of the API Response.
 *
 * @param message
 */
class NoDataFoundException(message: String) : Exception(message)