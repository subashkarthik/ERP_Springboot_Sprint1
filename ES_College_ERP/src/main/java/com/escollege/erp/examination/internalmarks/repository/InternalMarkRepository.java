package com.escollege.erp.examination.internalmarks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escollege.erp.examination.internalmarks.entity.InternalMark;

public interface InternalMarkRepository
        extends JpaRepository<InternalMark, Long> {

    // Find all marks belonging to a student
    List<InternalMark> findByStudentId(Long studentId);

    // Find all marks for an assessment
    List<InternalMark> findByAssessment_Id(Long assessmentId);

    // Check whether a student already has marks for an assessment
    boolean existsByAssessment_IdAndStudentId(
            Long assessmentId, Long studentId);

    // Find a student's mark for a specific assessment
    Optional<InternalMark> findByAssessment_IdAndStudentId(
            Long assessmentId, Long studentId);
}