package ru.otus.spring.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import ru.otus.spring.exception.ApiError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public abstract class ResponseUtil {
    public static void writeResponseErr(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception ex) throws IOException
    {
        response.setHeader("error", ex.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiError apiError = new ApiError(ex.getClass().getSimpleName(), ex.getMessage(), request.getRequestURI());
        response.setContentType(APPLICATION_JSON_VALUE);
        StringUtils.isEmpty("");
        new ObjectMapper().writeValue(response.getOutputStream(), apiError);
    }
}
