package com.securityapi.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reasons")
public class Reason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "reason_message", nullable = false, length = 200)
    String reasonMessage;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "call_template_id")
    @ToString.Exclude
    CallTemplate callTemplate;

    @OneToMany(mappedBy = "reason", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    List<Call> calls;

    public void addCallTemplate(CallTemplate callTemplate) {
        callTemplate.addTopic(this);
        this.callTemplate = callTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reason reason = (Reason) o;
        return this.id != null && this.id.equals(reason.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
