package com.escollege.erp.examination.internalmarks.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateAssessmentRequest {

    @NotBlank(message = "Assessment name is required")
    @Size(max = 100)
    private String assessmentName;

    @NotBlank(message = "Assessment type is required")
    @Size(max = 50)
    private String assessmentType;

    @NotNull(message = "Academic year ID is required")
    private Long academicYearId;

    @NotNull(message = "Semester ID is required")
    private Long semesterId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Maximum marks is required")
    @DecimalMin(value = "0.01", message = "Maximum marks must be greater than zero")
    private BigDecimal maxMarks;

    private LocalDate assessmentDate;

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public BigDecimal getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(BigDecimal maxMarks) {
        this.maxMarks = maxMarks;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }
}