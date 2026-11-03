package com.escollege.erp.examination.internalmarks.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "internal_mark_audit")
public class InternalMarkAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Original internal mark record
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internal_mark_id", nullable = false)
    private InternalMark internalMark;

    // Previous marks
    @Column(name = "old_marks", precision = 6, scale = 2)
    private BigDecimal oldMarks;

    // Updated marks
    @Column(name = "new_marks", precision = 6, scale = 2)
    private BigDecimal newMarks;

    @Column(name = "old_attendance_status", length = 20)
    private String oldAttendanceStatus;

    @Column(name = "new_attendance_status", length = 20)
    private String newAttendanceStatus;

    @Column(name = "old_remarks", length = 255)
    private String oldRemarks;

    @Column(name = "new_remarks", length = 255)
    private String newRemarks;

    // Faculty who modified the marks
    @Column(name = "modified_by", nullable = false)
    private Long modifiedBy;

    // Reason for modification
    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    // Modification timestamp
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;


    // Automatically set modification time
    @PrePersist
    public void prePersist() {
        this.modifiedAt = LocalDateTime.now();
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public InternalMark getInternalMark() {
        return internalMark;
    }

    public void setInternalMark(InternalMark internalMark) {
        this.internalMark = internalMark;
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
}