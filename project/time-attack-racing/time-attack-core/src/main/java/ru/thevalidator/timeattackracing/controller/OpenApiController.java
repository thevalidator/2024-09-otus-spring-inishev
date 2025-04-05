package ru.thevalidator.timeattackracing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenApiController {

    @GetMapping("/api-docs")
    public String getOpenApiDocs() {
        return "api-docs";
    }

}
