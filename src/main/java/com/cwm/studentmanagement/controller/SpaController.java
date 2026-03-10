package com.cwm.studentmanagement.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/*
 * Copyright (c) 2026 Mahesh Shet
 * Licensed under the MIT License.
 *
 * Replaces AuthController + DashboardController.
 * No Thymeleaf — serves the React SPA index.html directly from
 * src/main/resources/static/react/index.html (Vite build output).
 *
 * /login  — public, serves SPA (React renders the login page)
 * /dashboard — protected by Spring Security, serves SPA (React renders dashboard)
 */

@RestController
public class SpaController {

    private static final String INDEX_PATH = "/static/react/index.html";

    @GetMapping(value = {"/login", "/dashboard"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> spa() throws IOException {
        InputStream is = getClass().getResourceAsStream(INDEX_PATH);
        if (is == null) {
            return ResponseEntity.status(503)
                    .body("<h1>Frontend not built</h1><p>Run <code>npm run build</code> and copy dist/ to src/main/resources/static/react/</p>");
        }
        String html = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        return ResponseEntity.ok(html);
    }
}