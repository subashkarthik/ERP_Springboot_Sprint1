package com.escollege.erp.examination.internalmarks.controller;

import java.util.List;
import com.escollege.erp.examination.internalmarks.dto.UpdateInternalMarkRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.escollege.erp.examination.internalmarks.dto.CreateInternalMarkRequest;
import com.escollege.erp.examination.internalmarks.entity.InternalMark;
import com.escollege.erp.examination.internalmarks.service.InternalMarkService;

import jakarta.validation.Valid;


import com.escollege.erp.examination.internalmarks.dto.InternalMarkAuditResponse;

@RestController
@RequestMapping("/api/v1/internal-marks")
public class InternalMarkController {

    private final InternalMarkService internalMarkService;

    // Constructor Injection
    public InternalMarkController(
            InternalMarkService internalMarkService) {
        this.internalMarkService = internalMarkService;
    }

    // 1. Create Student Internal Mark
    @PostMapping
    public ResponseEntity<InternalMark> createMark(
            @Valid @RequestBody CreateInternalMarkRequest request) {

        InternalMark savedMark =
                internalMarkService.createMark(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedMark);
    }

    // 2. Get All Student Marks
    @GetMapping
    public ResponseEntity<List<InternalMark>> getAllMarks() {

        return ResponseEntity.ok(
                internalMarkService.getAllMarks()
        );
    }

    // 3. Get Marks By Student ID
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<InternalMark>> getMarksByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                internalMarkService.getMarksByStudentId(studentId)
        );
    }

    // 4. Get Marks By Assessment ID
    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<InternalMark>> getMarksByAssessment(
            @PathVariable Long assessmentId) {

        return ResponseEntity.ok(
                internalMarkService.getMarksByAssessmentId(assessmentId)
        );
    }

    // 5. Get Specific Student Mark
    @GetMapping("/assessment/{assessmentId}/student/{studentId}")
    public ResponseEntity<InternalMark> getStudentAssessmentMark(
            @PathVariable Long assessmentId,
            @PathVariable Long studentId) {

        return internalMarkService
                .getStudentAssessmentMark(assessmentId, studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
 // 6. Update Student Internal Marks
    @PutMapping("/{markId}")
    public ResponseEntity<InternalMark> updateMark(
            @PathVariable Long markId,
            @Valid @RequestBody UpdateInternalMarkRequest request) {

        InternalMark updatedMark =
                internalMarkService.updateMark(markId, request);

        return ResponseEntity.ok(updatedMark);
    }
    
 // 7. Get Audit History of a Student's Mark
    @GetMapping("/{markId}/audit-history")
    public ResponseEntity<List<InternalMarkAuditResponse>> getAuditHistory(
            @PathVariable Long markId) {

        List<InternalMarkAuditResponse> history =
                internalMarkService.getAuditHistory(markId);

        return ResponseEntity.ok(history);
    }
}