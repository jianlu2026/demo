package com.example.demo.controller;

import com.example.demo.dto.CreateGreetingRequest;
import com.example.demo.dto.GreetingResponse;
import com.example.demo.model.Greeting;
import com.example.demo.service.GreetingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
        log.info("GreetingController initialized");
    }

    @PostMapping
    public GreetingResponse createGreeting(@Valid @RequestBody CreateGreetingRequest request) {
        log.info("POST /api/v1/greetings - Creating greeting");
        return greetingService.createGreeting(request.getMessage());
    }

    @GetMapping("/{id}")
    public GreetingResponse getGreetingById(@PathVariable Long id) {
        log.info("GET /api/v1/greetings/{} - Fetching greeting", id);
        return greetingService.getGreetingById(id);
    }

    @GetMapping
    public List<GreetingResponse> getAllGreetings() {
        log.info("GET /api/v1/greetings - Fetching all greetings");
        return greetingService.getAllGreetings();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable Long id) {
        log.info("DELETE /api/v1/greetings/{} - Deleting greeting", id);
        greetingService.deleteGreeting(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public GreetingResponse updateGreeting(
            @PathVariable Long id,
            @Valid @RequestBody CreateGreetingRequest request
    ) {
        log.info("PUT /api/v1/greetings/{} - Updating greeting", id);
        return greetingService.updateGreeting(id, request.getMessage());
    }
}
