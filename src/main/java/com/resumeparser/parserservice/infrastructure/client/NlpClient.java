package com.resumeparser.parserservice.infrastructure.client;

import com.resumeparser.parserservice.domain.model.Resume;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "nlp-service", url = "http://localhost:8081")
public interface NlpClient {
    @PostMapping("/nlp/process")
    Resume processResume(@RequestBody String extractedText);
}
