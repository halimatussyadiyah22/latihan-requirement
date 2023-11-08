//package com.halimah.latihanrequirement.utils;
//
//import com.auth0.jwt.exceptions.InvalidClaimException;
//import com.halimah.latihanrequirement.domain.HttpResponse;
//import com.halimah.latihanrequirement.exception.ApiException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.LockedException;
//
//import static java.time.LocalDateTime.now;
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
//@Slf4j
//public class ExceptionUtils {
//    public static void processError(HttpServletRequest request, HttpServletResponse response, Exception exception) {
//        if (exception instanceof ApiException ||
//                exception instanceof DisabledException ||
//                exception instanceof LockedException ||
//                exception instanceof BadCredentialsException ||
//                exception instanceof InvalidClaimException) {
//            HttpResponse httpResponse = getHttpResponse(response, exception.getMessage(), BAD_REQUEST);
//            writeResponse(response, httpResponse);
//        }
//    }
//
//    private static void writeResponse(HttpServletResponse response, HttpResponse httpResponse) {
//    }
//
//    private static HttpResponse getHttpResponse(HttpServletResponse response, String message, HttpStatus httpStatus) {
//        HttpResponse httpResponse = HttpResponse.builder()
//                .timeStamp(now().toString())
//                .reason(message)
//                .status(httpStatus)
//                .statusCode(httpStatus.value())
//                .build();
//        response.setContentType(APPLICATION_JSON_VALUE);
//        response.setStatus(httpStatus.value());
//        return null;
//    }
//}
//
