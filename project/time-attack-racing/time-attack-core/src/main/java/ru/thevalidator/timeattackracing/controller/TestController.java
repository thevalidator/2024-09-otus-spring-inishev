package ru.thevalidator.timeattackracing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.thevalidator.timeattackracing.auth.JwtUtil;
import ru.thevalidator.timeattackracing.repository.ClassificationCategoryRepository;
import ru.thevalidator.timeattackracing.repository.SessionTypeRepository;

@RestController
@RequestMapping("/api")
public class TestController {

    private final ClassificationCategoryRepository classificationCategoryRepository;

    private final SessionTypeRepository sessionTypeRepository;

    private final JwtUtil jwtUtil;

    public TestController(ClassificationCategoryRepository classificationCategoryRepository,
                          SessionTypeRepository sessionTypeRepository, JwtUtil jwtUtil) {
        this.classificationCategoryRepository = classificationCategoryRepository;
        this.sessionTypeRepository = sessionTypeRepository;
        this.jwtUtil = jwtUtil;
    }

    @CrossOrigin
    @PostMapping("/data")
    public ResponseEntity<Object> handlePostAjaxRequest(@RequestBody String data) {
        System.out.println("Received data: " + data);
        return ResponseEntity.ok(classificationCategoryRepository.findAll());
    }

    @CrossOrigin
    @GetMapping("/data")
    public ResponseEntity<Object> handleGetAjaxRequest() {
        return ResponseEntity.ok(sessionTypeRepository.findAll());
    }

    @CrossOrigin
    @GetMapping("/data/token")
    public ResponseEntity<Object> generateToken(Authentication authentication) {
        return ResponseEntity.ok(jwtUtil.generateAccessJwtFromAuthentication(authentication));
    }

}
