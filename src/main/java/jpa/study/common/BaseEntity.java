package jpa.study.common;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.GenerationType.*;
import static jakarta.persistence.TemporalType.*;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedBy
    @Column(updatable = false)
    @Temporal(TIMESTAMP)
    private String createdBy;

    @LastModifiedBy
    @Temporal(TIMESTAMP)
    private String lastModifiedBy;

    public BaseEntity() {
    }
}
