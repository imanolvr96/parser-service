package com.resumeparser.parserservice.infrastructure.client;

import com.resumeparser.parserservice.domain.model.Resume;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client interface for interacting with the NLP service.
 * This interface defines a method for sending extracted text to the NLP service
 * for processing and receiving the processed resume in response.
 */
@FeignClient(name = "nlp-service", url = "http://localhost:8081")
public interface NlpClient {
    /**
     * Sends the extracted text to the NLP service for processing and returns
     * the processed resume.
     *
     * @param extractedText the text extracted from the PDF that needs to be processed by the NLP service
     * @return a processed resume object with the NLP analysis results
     * @throws FeignException if the request to the NLP service fails
     */
    @PostMapping("/nlp/process")
    Resume processResume(@RequestBody String extractedText);
}
