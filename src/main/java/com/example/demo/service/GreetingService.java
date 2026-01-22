package com.example.demo.service;

import com.example.demo.dto.GreetingResponse;
import com.example.demo.exception.GreetingNotFoundException;
import com.example.demo.model.Greeting;
import com.example.demo.repository.GreetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GreetingService {

    private static final Logger log = LoggerFactory.getLogger(GreetingService.class);

    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
        log.info("GreetingService initialized");
    }

    private GreetingResponse toResponse(Greeting greeting) {
        return new GreetingResponse(
                greeting.getId(),
                greeting.getMessage()
        );
    }

    public Greeting getGreeting() {
        return greetingRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> greetingRepository.save(
                        new Greeting("Hello from the database!")
                ));
    }

    public GreetingResponse getGreetingById(Long id) {
        log.debug("Fetching greeting with id: {}", id);
        Greeting greeting = greetingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Greeting not found with id: {}", id);
                    return new GreetingNotFoundException(id);
                });
        log.debug("Successfully fetched greeting with id: {}", id);
        return toResponse(greeting);
    }

    public List<GreetingResponse> getAllGreetings() {
        log.debug("Fetching all greetings");
        List<GreetingResponse> greetings = greetingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.info("Retrieved {} greetings", greetings.size());
        return greetings;
    }

    public GreetingResponse createGreeting(String message) {
        log.info("Creating new greeting with message: {}", message);
        Greeting greeting = greetingRepository.save(new Greeting(message));
        log.info("Successfully created greeting with id: {}", greeting.getId());
        return toResponse(greeting);
    }

    public void deleteGreeting(Long id) {
        log.info("Attempting to delete greeting with id: {}", id);
        if (!greetingRepository.existsById(id)) {
            log.warn("Cannot delete - greeting not found with id: {}", id);
            throw new GreetingNotFoundException(id);
        }
        greetingRepository.deleteById(id);
        log.info("Successfully deleted greeting with id: {}", id);
    }

    public GreetingResponse updateGreeting(Long id, String message) {
        log.info("Updating greeting with id: {} to message: {}", id, message);
        Greeting greeting = greetingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update - greeting not found with id: {}", id);
                    return new GreetingNotFoundException(id);
                });

        greeting.setMessage(message);
        GreetingResponse response = toResponse(greetingRepository.save(greeting));
        log.info("Successfully updated greeting with id: {}", id);
        return response;
    }
}
