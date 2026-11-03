package com.escollege.erp.examination.internalmarks.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.escollege.erp.examination.internalmarks.dto.CreateAssessmentRequest;
import com.escollege.erp.examination.internalmarks.entity.InternalAssessment;
import com.escollege.erp.examination.internalmarks.service.InternalAssessmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/internal-assessments")
public class InternalAssessmentController {

    private final InternalAssessmentService assessmentService;

    public InternalAssessmentController(
            InternalAssessmentService assessmentService) {

        this.assessmentService = assessmentService;
    }

    // 1. Create Assessment
    @PostMapping
    public ResponseEntity<InternalAssessment> createAssessment(
            @Valid @RequestBody CreateAssessmentRequest request) {

        InternalAssessment assessment =
                assessmentService.createAssessment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assessment);
    }

    // 2. Get All Assessments
    @GetMapping
    public ResponseEntity<List<InternalAssessment>> getAllAssessments() {

        return ResponseEntity.ok(
                assessmentService.getAllAssessments());
    }

    // 3. Get Assessment By ID
    @GetMapping("/{id}")
    public ResponseEntity<InternalAssessment> getAssessmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assessmentService.getAssessmentById(id));
    }

    // 4. Update Assessment
    @PutMapping("/{id}")
    public ResponseEntity<InternalAssessment> updateAssessment(
            @PathVariable Long id,
            @Valid @RequestBody CreateAssessmentRequest request) {

        InternalAssessment updatedAssessment =
                assessmentService.updateAssessment(id, request);

        return ResponseEntity.ok(updatedAssessment);
    }

    // 5. Submit Assessment
    @PostMapping("/{id}/submit")
    public ResponseEntity<InternalAssessment> submitAssessment(
            @PathVariable Long id) {

        InternalAssessment assessment =
                assessmentService.submitAssessment(id);

        return ResponseEntity.ok(assessment);
    }

    // 6. Lock Assessment
    @PostMapping("/{id}/lock")
    public ResponseEntity<InternalAssessment> lockAssessment(
            @PathVariable Long id) {

        InternalAssessment assessment =
                assessmentService.lockAssessment(id);

        return ResponseEntity.ok(assessment);
    }

    // 7. Unlock Assessment
    @PostMapping("/{id}/unlock")
    public ResponseEntity<InternalAssessment> unlockAssessment(
            @PathVariable Long id) {

        InternalAssessment assessment =
                assessmentService.unlockAssessment(id);

        return ResponseEntity.ok(assessment);
    }
}