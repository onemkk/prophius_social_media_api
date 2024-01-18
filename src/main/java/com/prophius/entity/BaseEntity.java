package com.prophius.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();
}
