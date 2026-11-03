package com.escollege.erp.examination.internalmarks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escollege.erp.examination.internalmarks.entity.InternalMarkAudit;

@Repository
public interface InternalMarkAuditRepository
        extends JpaRepository<InternalMarkAudit, Long> {

    List<InternalMarkAudit> findByInternalMark_IdOrderByModifiedAtDesc(
            Long internalMarkId
    );

    List<InternalMarkAudit> findByModifiedByOrderByModifiedAtDesc(
            Long modifiedBy
    );
}