package com.escollege.erp.examination.internalmarks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escollege.erp.examination.internalmarks.entity.InternalAssessment;

public interface InternalAssessmentRepository
        extends JpaRepository<InternalAssessment, Long> {

}