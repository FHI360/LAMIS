package com.mattae.simal.modules.base.domain.entities;
import com.blazebit.persistence.view.AttributeFilter;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.filter.EqualFilter;
import com.mattae.simal.modules.base.domain.enumeration.PartyType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@SQLDelete(sql = "update party set archived = true, last_modified_date = current_timestamp where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "type"})
public class Party {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PartyType type = PartyType.INDIVIDUAL;

    private String legalType = "";

    private String displayName = "";

    @OneToMany(mappedBy = "party", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "party", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<Identifier> identifiers = new HashSet<>();

    private Boolean archived = false;

    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @PreUpdate
    public void update() {
        lastModifiedDate = LocalDateTime.now();
    }

    @EntityView(Party.class)
    public interface View {
        @IdMapping
        UUID getId();

        @AttributeFilter(EqualFilter.class)
        PartyType getType();
    }
}
