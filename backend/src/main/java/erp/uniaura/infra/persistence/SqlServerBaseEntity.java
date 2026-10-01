package erp.uniaura.infra.persistence;

import cloudsupport.persistence.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides({
        @AttributeOverride(name = "createdDate", column = @Column(name = "createdDate", columnDefinition = "datetime2")),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "lastModifiedDate", columnDefinition = "datetime2"))
})

public abstract class SqlServerBaseEntity extends BaseEntity {
}
