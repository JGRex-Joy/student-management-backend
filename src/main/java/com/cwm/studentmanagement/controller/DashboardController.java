package com.cwm.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Copyright (c) 2026 Mahesh Shet
 * Licensed under the MIT License.
 *
 * After migrating to React SPA all UI routes return "react-app" template.
 * Spring Security still guards /dashboard (and all other non-public paths).
 * The React app handles client-side navigation internally.
 *
 * The legacy Thymeleaf controllers (CourseController, StudentController,
 * EnrollmentController) can be removed once the team fully switches to the
 * React frontend — the REST API controllers under /api/* replace them.
 */

@Controller
public class DashboardController {

    /**
     * Serves the React SPA shell. Spring Security ensures only authenticated
     * users reach this endpoint; the React app then calls /api/* endpoints.
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "react-app";
    }
}