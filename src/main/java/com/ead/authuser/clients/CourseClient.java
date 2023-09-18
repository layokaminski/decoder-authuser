package com.ead.authuser.clients;

import com.ead.authuser.DTOs.CourseDTO;
import com.ead.authuser.DTOs.ResponsePageDTO;
import com.ead.authuser.services.UtilsService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsService utilsService;

    @Value("${ead.api.url.course}")
    private String REQUEST_URL_COURSE;

    @Retry(name = "retryInstance", fallbackMethod = "retryFallback")
    public Page<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {
        List<CourseDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<CourseDTO>> result = null;

        String url = REQUEST_URL_COURSE + utilsService.createUrlGetAllCoursesByUser(userId, pageable);

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try {
            ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>  responseType =
                    new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {
            };

            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            searchResult = result.getBody().getContent();

            log.debug("Response Number of Elements: {}", searchResult.size());
        } catch (HttpStatusCodeException exception) {
            log.error("Error request /courses {}", exception);
        }

        log.info("Ending request /courses userId {}", userId);

        return result.getBody();
    }

    public  Page<CourseDTO> retryFallback(UUID userId, Pageable pageable, Throwable throwable) {
        log.error("Inside retry retryFallback, cause - {}", throwable.toString());

        List<CourseDTO> courseDTOList = new ArrayList<>();

        return new PageImpl<>(courseDTOList);
    }
}
