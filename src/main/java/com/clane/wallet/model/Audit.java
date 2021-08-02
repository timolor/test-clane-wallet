package com.clane.wallet.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Data
@MappedSuperclass
public abstract class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @CreatedBy
    protected int createdBy;

    @CreatedDate
    protected LocalDateTime dateCreated;

    @LastModifiedBy
    protected int lastModifiedBy;

    @LastModifiedDate
    protected LocalDateTime lastModified;

    @PrePersist
    public void prePersist() {
        lastModified = dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        if( dateCreated == null)
            dateCreated = LocalDateTime.now();
        lastModified = LocalDateTime.now();
    }

}
