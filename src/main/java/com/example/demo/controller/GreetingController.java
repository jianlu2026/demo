package com.example.demo.controller;

import com.example.demo.dto.CreateGreetingRequest;
import com.example.demo.dto.GreetingResponse;
import com.example.demo.model.Greeting;
import com.example.demo.service.GreetingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @PostMapping
    public GreetingResponse createGreeting(@Valid @RequestBody CreateGreetingRequest request) {
        return greetingService.createGreeting(request.getMessage());
    }

    @GetMapping("/{id}")
    public GreetingResponse getGreetingById(@PathVariable Long id) {
        return greetingService.getGreetingById(id);
    }

    @GetMapping
    public List<GreetingResponse> getAllGreetings() {
        return greetingService.getAllGreetings();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable Long id) {
        greetingService.deleteGreeting(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public GreetingResponse updateGreeting(
            @PathVariable Long id,
            @Valid @RequestBody CreateGreetingRequest request
    ) {
        return greetingService.updateGreeting(id, request.getMessage());
    }
}
