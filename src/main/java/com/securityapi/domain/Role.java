package com.securityapi.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ERole name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    private List<Person> persons;

    public void addPerson(Person person) {
        this.persons.add(person);
        person.getRoles().add(this);
    }
}
