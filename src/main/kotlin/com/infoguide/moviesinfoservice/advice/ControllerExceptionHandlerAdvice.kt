package com.infoguide.moviesinfoservice.advice

import com.infoguide.moviesinfoservice.dto.ErrorDto
import com.infoguide.moviesinfoservice.exception.NoDataFoundException
import com.infoguide.moviesinfoservice.exception.RecordAlreadyExists
import com.infoguide.moviesinfoservice.exception.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Movie Information Service Exception handler Advice.
 * This advice handles all the exception thrown by the service and converts it into proper Json response
 * for the user.
 *
 */
@ControllerAdvice
class ControllerExceptionHandlerAdvice {

    /**
     * Handles all Record Already Exists exception.
     * Thrown when the record the user is trying to insert or update already exits.
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    fun handleRecordAlreadyExistsException(exception: RecordAlreadyExists): ResponseEntity<ErrorDto> {
        val errorMessage = exception.message?.let {
            ErrorDto(
                "RECORD_EXISTS",
                it
            )
        }
        return ResponseEntity(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    /**
     * Handles No Record Found Exception, this is generally used a lot when there is a lookup on the record.
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    fun handleNoRecordFoundException(exception: NoDataFoundException): ResponseEntity<ErrorDto> {
        val errorMessage = exception.message?.let {
            ErrorDto(
                "DATA_DOES_NOT_EXIST",
                it
            )
        }
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    /**
     * Handles all Invalid request which are thrown by Spring framework for serialization errors.
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    fun handleInvalidRequestException(exception: HttpMessageNotReadableException): ResponseEntity<ErrorDto> {
        return ResponseEntity(
            ErrorDto("BAD_REQUEST", "Request provided is invalid, kindly check the request and try again."),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handles all the Invalid Request exceptions which are caught after Validating the request.
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    fun handleInvalidRequestFromValidationException(exception: ValidationException): ResponseEntity<ErrorDto> {
        val errorMessage = exception.message?.let {
            ErrorDto(
                "INVALID_REQUEST",
                it
            )
        }
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    /**
     * This is catch all exception, and we catch this for unexpected exception and return back a proper formatted
     * Internal Service error response to the user.
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    fun handleInternalServiceException(exception: Exception): ResponseEntity<ErrorDto> {
        return ResponseEntity(
            ErrorDto("INTERNAL_SERVICE_ERROR", "Something when wrong while processing the request. Kindly try again."),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}