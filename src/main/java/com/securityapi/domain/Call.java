package com.securityapi.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

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
@Table(name = "calls")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "consumer_id")
    @ToString.Exclude
    Consumer consumer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reason_id")
    @ToString.Exclude
    Reason reason;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Call call = (Call) o;
        return this.id != null && this.id.equals(call.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
