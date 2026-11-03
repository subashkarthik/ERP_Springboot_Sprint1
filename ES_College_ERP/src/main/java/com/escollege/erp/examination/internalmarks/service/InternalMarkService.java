
package com.escollege.erp.examination.internalmarks.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escollege.erp.examination.internalmarks.dto.CreateInternalMarkRequest;
import com.escollege.erp.examination.internalmarks.dto.InternalMarkAuditResponse;
import com.escollege.erp.examination.internalmarks.dto.UpdateInternalMarkRequest;
import com.escollege.erp.examination.internalmarks.entity.InternalAssessment;
import com.escollege.erp.examination.internalmarks.entity.InternalMark;
import com.escollege.erp.examination.internalmarks.entity.InternalMarkAudit;
import com.escollege.erp.examination.internalmarks.exception.DuplicateResourceException;
import com.escollege.erp.examination.internalmarks.exception.ResourceNotFoundException;
import com.escollege.erp.examination.internalmarks.repository.InternalAssessmentRepository;
import com.escollege.erp.examination.internalmarks.repository.InternalMarkAuditRepository;
import com.escollege.erp.examination.internalmarks.repository.InternalMarkRepository;

@Service
@Transactional(readOnly = true)
public class InternalMarkService {

    private final InternalMarkRepository internalMarkRepository;
    private final InternalAssessmentRepository assessmentRepository;
    private final InternalMarkAuditRepository auditRepository;

    public InternalMarkService(
            InternalMarkRepository internalMarkRepository,
            InternalAssessmentRepository assessmentRepository,
            InternalMarkAuditRepository auditRepository) {

        this.internalMarkRepository = internalMarkRepository;
        this.assessmentRepository = assessmentRepository;
        this.auditRepository = auditRepository;
    }

    // 1. Get All Marks
    public List<InternalMark> getAllMarks() {
        return internalMarkRepository.findAll();
    }

    // 2. Get Marks By Student ID
    public List<InternalMark> getMarksByStudentId(Long studentId) {
        return internalMarkRepository.findByStudentId(studentId);
    }

    // 3. Get Marks By Assessment ID
    public List<InternalMark> getMarksByAssessmentId(Long assessmentId) {
        return internalMarkRepository.findByAssessment_Id(assessmentId);
    }

    // 4. Get Specific Student Assessment Mark
    public Optional<InternalMark> getStudentAssessmentMark(
            Long assessmentId, Long studentId) {

        return internalMarkRepository
                .findByAssessment_IdAndStudentId(assessmentId, studentId);
    }

    // 5. Create Student Mark
    @Transactional
    public InternalMark createMark(CreateInternalMarkRequest request) {

        InternalAssessment assessment = assessmentRepository
                .findById(request.getAssessmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment not found with ID: "
                                + request.getAssessmentId()));

        validateAssessmentEditable(assessment);

        boolean exists = internalMarkRepository
                .existsByAssessment_IdAndStudentId(
                        request.getAssessmentId(),
                        request.getStudentId());

        if (exists) {
            throw new DuplicateResourceException(
                    "Marks already entered for this student");
        }

        String attendance = request.getAttendanceStatus()
                .trim().toUpperCase();

        validateAttendance(attendance);

        BigDecimal marks = request.getMarksObtained();

        validateMarks(marks, attendance, assessment.getMaxMarks());

        InternalMark internalMark = new InternalMark();

        internalMark.setAssessment(assessment);
        internalMark.setStudentId(request.getStudentId());
        internalMark.setMarksObtained(marks);
        internalMark.setAttendanceStatus(attendance);
        internalMark.setRemarks(request.getRemarks());
        internalMark.setEnteredBy(request.getEnteredBy());

        return internalMarkRepository.save(internalMark);
    }

    // 6. Update Student Mark With Audit
    @Transactional
    public InternalMark updateMark(
            Long markId,
            UpdateInternalMarkRequest request) {

        InternalMark existingMark = internalMarkRepository
                .findById(markId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internal mark not found with ID: " + markId));

        validateAssessmentEditable(existingMark.getAssessment());

        String attendance = request.getAttendanceStatus()
                .trim().toUpperCase();

        validateAttendance(attendance);

        BigDecimal newMarks = request.getMarksObtained();

        validateMarks(
                newMarks,
                attendance,
                existingMark.getAssessment().getMaxMarks());

        if ("ABSENT".equals(attendance)) {
            newMarks = null;
        }

        InternalMarkAudit audit = new InternalMarkAudit();

        audit.setInternalMark(existingMark);
        audit.setOldMarks(existingMark.getMarksObtained());
        audit.setNewMarks(newMarks);

        audit.setOldAttendanceStatus(
                existingMark.getAttendanceStatus());

        audit.setNewAttendanceStatus(attendance);

        audit.setOldRemarks(existingMark.getRemarks());
        audit.setNewRemarks(request.getRemarks());

        audit.setModifiedBy(request.getModifiedBy());
        audit.setReason(request.getReason());

        auditRepository.save(audit);

        existingMark.setMarksObtained(newMarks);
        existingMark.setAttendanceStatus(attendance);
        existingMark.setRemarks(request.getRemarks());

        return internalMarkRepository.save(existingMark);
    }

    // 7. Get Audit History
    public List<InternalMarkAuditResponse> getAuditHistory(Long markId) {

        if (!internalMarkRepository.existsById(markId)) {
            throw new ResourceNotFoundException(
                    "Internal mark not found with ID: " + markId);
        }

        List<InternalMarkAudit> auditRecords = auditRepository
                .findByInternalMark_IdOrderByModifiedAtDesc(markId);

        return auditRecords.stream().map(audit -> {

            InternalMarkAuditResponse response =
                    new InternalMarkAuditResponse();

            response.setId(audit.getId());
            response.setInternalMarkId(
                    audit.getInternalMark().getId());

            response.setOldMarks(audit.getOldMarks());
            response.setNewMarks(audit.getNewMarks());

            response.setOldAttendanceStatus(
                    audit.getOldAttendanceStatus());

            response.setNewAttendanceStatus(
                    audit.getNewAttendanceStatus());

            response.setOldRemarks(audit.getOldRemarks());
            response.setNewRemarks(audit.getNewRemarks());

            response.setModifiedBy(audit.getModifiedBy());
            response.setReason(audit.getReason());
            response.setModifiedAt(audit.getModifiedAt());

            return response;

        }).toList();
    }

    // 8. Validate Assessment Status
    private void validateAssessmentEditable(
            InternalAssessment assessment) {

        if (!"DRAFT".equals(assessment.getStatus())) {
            throw new IllegalArgumentException(
                    "Marks cannot be modified. Assessment status is: "
                            + assessment.getStatus());
        }
    }

    // 9. Validate Attendance
    private void validateAttendance(String attendance) {

        if (!"PRESENT".equals(attendance)
                && !"ABSENT".equals(attendance)) {

            throw new IllegalArgumentException(
                    "Attendance status must be PRESENT or ABSENT");
        }
    }

    // 10. Validate Marks
    private void validateMarks(
            BigDecimal marks,
            String attendance,
            BigDecimal maxMarks) {

        if ("ABSENT".equals(attendance)) {
            return;
        }

        if (marks == null) {
            throw new IllegalArgumentException(
                    "Marks are required for PRESENT students");
        }

        if (marks.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Marks cannot be negative");
        }

        if (marks.compareTo(maxMarks) > 0) {
            throw new IllegalArgumentException(
                    "Marks cannot exceed maximum marks: " + maxMarks);
        }
    }
}

