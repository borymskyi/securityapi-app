package com.securityapi.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Entity
@Table(name = "persons")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 500)
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_roles",
                joinColumns = @JoinColumn(name = "person_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        this.roles.add(role);
        role.addPerson(this);
    }

    public void addRoles(List<Role> roles) {
        if (this.roles == null) {
            roles = new ArrayList<>();
        }
        for (Role r : roles) {
            this.roles.add(r);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;
        return this.id != null && this.id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
