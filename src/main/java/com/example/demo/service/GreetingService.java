package com.example.demo.service;

import com.example.demo.dto.GreetingResponse;
import com.example.demo.exception.GreetingNotFoundException;
import com.example.demo.model.Greeting;
import com.example.demo.repository.GreetingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GreetingService {

    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
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
        Greeting greeting = greetingRepository.findById(id)
                .orElseThrow(() -> new GreetingNotFoundException(id));
        return toResponse(greeting);
    }

    public List<GreetingResponse> getAllGreetings() {
        return greetingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public GreetingResponse createGreeting(String message) {
        Greeting greeting = greetingRepository.save(new Greeting(message));
        return toResponse(greeting);
    }

    public void deleteGreeting(Long id) {
        if (!greetingRepository.existsById(id)) {
            throw new GreetingNotFoundException(id);
        }
        greetingRepository.deleteById(id);
    }

    public GreetingResponse updateGreeting(Long id, String message) {
        Greeting greeting = greetingRepository.findById(id)
                .orElseThrow(() -> new GreetingNotFoundException(id));

        greeting.setMessage(message);
        return toResponse(greetingRepository.save(greeting));
    }
}
