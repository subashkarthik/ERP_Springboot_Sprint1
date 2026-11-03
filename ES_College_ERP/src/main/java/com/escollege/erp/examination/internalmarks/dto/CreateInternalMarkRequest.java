package com.escollege.erp.examination.internalmarks.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateInternalMarkRequest {

    @NotNull(message = "Assessment ID is required")
    @Positive(message = "Assessment ID must be positive")
    private Long assessmentId;

    @NotNull(message = "Student ID is required")
    @Positive(message = "Student ID must be positive")
    private Long studentId;

    // Marks can be null when the student is absent
    private BigDecimal marksObtained;

    @NotBlank(message = "Attendance status is required")
    @Size(max = 20)
    private String attendanceStatus;

    @Size(max = 255)
    private String remarks;

    @NotNull(message = "Faculty ID is required")
    @Positive(message = "Faculty ID must be positive")
    private Long enteredBy;


    // Getters and Setters

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public BigDecimal getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(BigDecimal marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(Long enteredBy) {
        this.enteredBy = enteredBy;
    }
}