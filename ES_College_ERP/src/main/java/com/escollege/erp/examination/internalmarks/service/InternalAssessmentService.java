package com.escollege.erp.examination.internalmarks.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escollege.erp.examination.internalmarks.dto.CreateAssessmentRequest;
import com.escollege.erp.examination.internalmarks.entity.InternalAssessment;
import com.escollege.erp.examination.internalmarks.exception.ResourceNotFoundException;
import com.escollege.erp.examination.internalmarks.exception.DuplicateResourceException;
import com.escollege.erp.examination.internalmarks.repository.InternalAssessmentRepository;
import com.escollege.erp.examination.internalmarks.repository.InternalMarkRepository;

@Service
@Transactional(readOnly = true)
public class InternalAssessmentService {

    private final InternalAssessmentRepository assessmentRepository;
    private final InternalMarkRepository internalMarkRepository;

    public InternalAssessmentService(
            InternalAssessmentRepository assessmentRepository,
            InternalMarkRepository internalMarkRepository) {

        this.assessmentRepository = assessmentRepository;
        this.internalMarkRepository = internalMarkRepository;
    }

    // 1. Get All Assessments
    public List<InternalAssessment> getAllAssessments() {

        return assessmentRepository.findAll();
    }

    // 2. Get Assessment By ID
    public InternalAssessment getAssessmentById(Long id) {

        return assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assessment not found with ID: " + id));
    }

    // 3. Create Assessment
    @Transactional
    public InternalAssessment createAssessment(
            CreateAssessmentRequest request) {

        InternalAssessment assessment = new InternalAssessment();

        assessment.setAssessmentName(request.getAssessmentName());
        assessment.setAssessmentType(request.getAssessmentType());
        assessment.setAcademicYearId(request.getAcademicYearId());
        assessment.setSemesterId(request.getSemesterId());
        assessment.setSubjectId(request.getSubjectId());
        assessment.setMaxMarks(request.getMaxMarks());
        assessment.setAssessmentDate(request.getAssessmentDate());

        assessment.setStatus("DRAFT");

        return assessmentRepository.save(assessment);
    }

    // 4. Update Assessment
    @Transactional
    public InternalAssessment updateAssessment(
            Long id,
            CreateAssessmentRequest request) {

        InternalAssessment assessment = getAssessmentById(id);

        if (!"DRAFT".equals(assessment.getStatus())) {
            throw new IllegalArgumentException(
                    "Only DRAFT assessments can be updated");
        }

        assessment.setAssessmentName(request.getAssessmentName());
        assessment.setAssessmentType(request.getAssessmentType());
        assessment.setAcademicYearId(request.getAcademicYearId());
        assessment.setSemesterId(request.getSemesterId());
        assessment.setSubjectId(request.getSubjectId());
        assessment.setMaxMarks(request.getMaxMarks());
        assessment.setAssessmentDate(request.getAssessmentDate());

        return assessmentRepository.save(assessment);
    }

    // 5. Submit Assessment
    @Transactional
    public InternalAssessment submitAssessment(Long assessmentId) {

        InternalAssessment assessment = getAssessmentById(assessmentId);

        if (!"DRAFT".equals(assessment.getStatus())) {
            throw new IllegalArgumentException(
                    "Only DRAFT assessments can be submitted");
        }

        boolean hasMarks = !internalMarkRepository
                .findByAssessment_Id(assessmentId).isEmpty();

        if (!hasMarks) {
            throw new IllegalArgumentException(
                    "Cannot submit assessment without student marks");
        }

        assessment.setStatus("SUBMITTED");
        assessment.setSubmittedAt(LocalDateTime.now());

        return assessmentRepository.save(assessment);
    }

    // 6. Lock Assessment
    @Transactional
    public InternalAssessment lockAssessment(Long assessmentId) {

        InternalAssessment assessment = getAssessmentById(assessmentId);

        if (!"SUBMITTED".equals(assessment.getStatus())) {
            throw new IllegalArgumentException(
                    "Only SUBMITTED assessments can be locked");
        }

        assessment.setStatus("LOCKED");
        assessment.setLockedAt(LocalDateTime.now());

        return assessmentRepository.save(assessment);
    }

    // 7. Unlock Assessment
    @Transactional
    public InternalAssessment unlockAssessment(Long assessmentId) {

        InternalAssessment assessment = getAssessmentById(assessmentId);

        if (!"LOCKED".equals(assessment.getStatus())) {
            throw new IllegalArgumentException(
                    "Only LOCKED assessments can be unlocked");
        }

        assessment.setStatus("DRAFT");
        assessment.setLockedAt(null);
        assessment.setSubmittedAt(null);

        return assessmentRepository.save(assessment);
    }
}