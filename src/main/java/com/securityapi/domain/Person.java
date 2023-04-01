package com.securityapi.domain;

import com.securityapi.exception.InvalidDataException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false, length = 50)
    String email;

    @Column(nullable = false, length = 500)
    String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_roles",
                joinColumns = @JoinColumn(name = "person_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "manager", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    List<CallTemplate> callTemplates = new ArrayList<>();

    public void addRole(Role role) {
        if (this.roles == null) this.roles = new ArrayList<>();

        if (!this.roles.contains(role)) {
            this.roles.add(role);
            role.addPerson(this);
        } else {
            log.error(String.format("Person already have a role. Person role: %s, Passed role: %s",
                    this.roles.stream().map(r -> r.getName().toString()).toList(),
                    role.getName().toString()));
            throw new InvalidDataException(String.format("Person already have a role. \nPerson role: %s, \npassed role: %s",
                    this.roles.stream().map(r -> r.getName().toString()).toList(),
                    role.getName().toString()));
        }
    }

    public void addRoles(List<Role> roles) {
        if (this.roles == null) this.roles = new ArrayList<>();

        if (!this.roles.stream()
                .map(Role::getName)
                .anyMatch(roles::contains)) {

            for (Role r : roles) {
                this.roles.add(r);
            }

        } else {
            log.warn(String.format("Person already have a role. Person role: %s, Passed role: %s",
                    this.getRoles().stream().map(r -> r.getName().toString()).toList(),
                    roles.stream().map(r -> r.getName().toString()).toList()));
            throw new InvalidDataException(String.format("Person already have a role. Person role: %s, Passed role: %s",
                    this.getRoles().stream().map(r -> r.getName().toString()).toList(),
                    roles.stream().map(r -> r.getName().toString()).toList()));
        }
    }

    public void addCallTemplate(CallTemplate callTemplate) {
        this.callTemplates.add(callTemplate);
        callTemplate.setPerson(this);
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
