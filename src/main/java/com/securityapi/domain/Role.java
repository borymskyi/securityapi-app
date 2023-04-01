package com.securityapi.domain;

import com.securityapi.domain.enums.ERole;
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
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, unique = true)
    ERole name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    List<Person> persons = new ArrayList<>();

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;
        return this.id != null && this.id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
