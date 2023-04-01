package com.securityapi.domain;

import com.securityapi.domain.enums.EConsumerStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
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
@Table(name = "consumer")
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, unique = true)
    EConsumerStatus status;

    @ManyToMany(mappedBy = "consumers", cascade = CascadeType.PERSIST)
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    List<CallTemplate> callTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "consumer")
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    List<Call> call;
}
