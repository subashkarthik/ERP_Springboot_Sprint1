package com.escollege.erp.examination.internalmarks.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InternalMarkAuditResponse {

    private Long id;
    private Long internalMarkId;

    private BigDecimal oldMarks;
    private BigDecimal newMarks;

    private String oldAttendanceStatus;
    private String newAttendanceStatus;

    private String oldRemarks;
    private String newRemarks;

    private Long modifiedBy;
    private String reason;
    private LocalDateTime modifiedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInternalMarkId() {
        return internalMarkId;
    }

    public void setInternalMarkId(Long internalMarkId) {
        this.internalMarkId = internalMarkId;
    }

    public BigDecimal getOldMarks() {
        return oldMarks;
    }

    public void setOldMarks(BigDecimal oldMarks) {
        this.oldMarks = oldMarks;
    }

    public BigDecimal getNewMarks() {
        return newMarks;
    }

    public void setNewMarks(BigDecimal newMarks) {
        this.newMarks = newMarks;
    }

    public String getOldAttendanceStatus() {
        return oldAttendanceStatus;
    }

    public void setOldAttendanceStatus(String oldAttendanceStatus) {
        this.oldAttendanceStatus = oldAttendanceStatus;
    }

    public String getNewAttendanceStatus() {
        return newAttendanceStatus;
    }

    public void setNewAttendanceStatus(String newAttendanceStatus) {
        this.newAttendanceStatus = newAttendanceStatus;
    }

    public String getOldRemarks() {
        return oldRemarks;
    }

    public void setOldRemarks(String oldRemarks) {
        this.oldRemarks = oldRemarks;
    }

    public String getNewRemarks() {
        return newRemarks;
    }

    public void setNewRemarks(String newRemarks) {
        this.newRemarks = newRemarks;
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

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}