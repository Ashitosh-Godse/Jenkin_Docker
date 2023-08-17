package com.epam.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class ExceptionResponseTest {

    @Test
     void testAllArgsConstructorAndGetters() {
        String timeStamp = "2023-08-12T12:34:56";
        String status = "404";
        String path = "/api/resource";
        String error = "Resource not found";

        ExceptionResponse exceptionResponse = new ExceptionResponse(timeStamp, status, path, error);

        assertEquals(timeStamp, exceptionResponse.getTimeStamp());
        assertEquals(status, exceptionResponse.getStatus());
        assertEquals(path, exceptionResponse.getPath());
        assertEquals(error, exceptionResponse.getError());
    }

    @Test
    public void testSetters() {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        String timeStamp = "2023-08-12T12:34:56";
        String status = "500";
        String path = "/api/error";
        String error = "Internal server error";

        exceptionResponse.setTimeStamp(timeStamp);
        exceptionResponse.setStatus(status);
        exceptionResponse.setPath(path);
        exceptionResponse.setError(error);

        assertEquals(timeStamp, exceptionResponse.getTimeStamp());
        assertEquals(status, exceptionResponse.getStatus());
        assertEquals(path, exceptionResponse.getPath());
        assertEquals(error, exceptionResponse.getError());
    }
}






