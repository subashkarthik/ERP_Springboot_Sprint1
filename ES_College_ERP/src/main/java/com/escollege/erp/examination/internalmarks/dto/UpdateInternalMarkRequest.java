package com.escollege.erp.examination.internalmarks.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class UpdateInternalMarkRequest {

    private BigDecimal marksObtained;

    @NotBlank(message = "Attendance status is required")
    @Size(max = 20)
    private String attendanceStatus;

    @Size(max = 255)
    private String remarks;

    @NotNull(message = "Modified by faculty ID is required")
    @Positive(message = "Faculty ID must be positive")
    private Long modifiedBy;

    @NotBlank(message = "Modification reason is required")
    @Size(max = 500)
    private String reason;

    // Getters and Setters

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

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}