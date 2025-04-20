package origin.interview

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import origin.interview.util.Exceptions.FullURLNotFoundException
import origin.interview.util.Exceptions.MalformedURLSuppliedException

@ControllerAdvice
class ApiExceptionHandler {

    // Define a response structure for errors
    data class ErrorResponse(
        val statusCode: Int,
        val message: String,
        val timestamp: Long
    )

    @ExceptionHandler(MalformedURLSuppliedException::class)
    fun handleMalformedURLSuppliedException(
        exception: MalformedURLSuppliedException,
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            exception.message ?: "Invalid URL supplied",
            System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(FullURLNotFoundException::class)
    fun handleFullURLNotFoundException(
        exception: FullURLNotFoundException,
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            exception.message ?: "Original URL not found",
            System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }
}