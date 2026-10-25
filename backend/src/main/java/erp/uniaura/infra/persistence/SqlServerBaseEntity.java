package erp.uniaura.infra.persistence;

import cloudsupport.persistence.BaseEntity;
import cloudsupport.services.UidGenerator;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@AttributeOverrides({
        @AttributeOverride(name = "createdDate", column = @Column(name = "createdDate", columnDefinition = "datetime2")),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "lastModifiedDate", columnDefinition = "datetime2"))
})
@Getter
@Setter
public abstract class SqlServerBaseEntity extends BaseEntity {

    @Column(name = "uid")
    private Long uid;

    // --- GERA O uid NA GRAVAÇÃO ---
    @PrePersist
    void gerarUid() {
        if (uid == null) {
            uid = UidGenerator.nextLong();
        }
    }
}
