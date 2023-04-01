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
@Table(name = "call_templates")
public class CallTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "call_templates_consumers",
            joinColumns = @JoinColumn(name = "call_template_id"),
            inverseJoinColumns = @JoinColumn(name = "consumer_id"))
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    List<Consumer> consumers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    Person manager;

    @OneToMany(mappedBy = "callTemplate", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    List<Reason> reasons;

    public void setPerson(Person person) {
        this.manager = person;
    }

    public void setTopic(Reason reason) {
        this.reasons.add(reason);
        reason.addCallTemplate(this);
    }

    public void addTopic(Reason reason) {
        if (this.reasons == null) this.reasons = new ArrayList<>();

        if (this.reasons.stream()
                .map(Reason::getReasonMessage)
                .noneMatch(t -> t.equals(reason.getReasonMessage()))) {

            this.reasons.add(reason);
        } else {
            log.error(String.format("CallTemplate has already passed reason. CallTemplate reasons: %s, Passed reason: %s",
                    this.reasons.stream().map(Reason::getReasonMessage).toList(), reason.getReasonMessage()));
            throw new InvalidDataException(String.format("CallTemplate has already passed reason. CallTemplate reasons: %s, Passed reason: %s",
                    this.reasons.stream().map(Reason::getReasonMessage).toList(), reason.getReasonMessage()));
        }
    }

    public void addTopics(List<Reason> reasons) {
        if (this.reasons == null) this.reasons = new ArrayList<>();

        if (!this.reasons.stream()
                .map(Reason::getReasonMessage)
                .anyMatch(reasons::contains)) {

            for (Reason t : reasons) {
                this.reasons.add(t);
            }
        } else {
            log.error(String.format("CallTemplate has already passed reason. CallTemplate reasons: %s, Passed reasons: %s",
                    this.getReasons().stream().map(Reason::getReasonMessage).toList(),
                    reasons.stream().map(Reason::getReasonMessage).toList()));
            throw new InvalidDataException(String.format("CallTemplate has already passed reason. CallTemplate reasons: %s, Passed reasons: %s",
                    this.getReasons().stream().map(Reason::getReasonMessage).toList(),
                    reasons.stream().map(Reason::getReasonMessage).toList()));
        }
    }

    public void addConsumers(List<Consumer> consumers) {
        if (this.consumers == null) this.consumers = new ArrayList<>();

        if (!this.consumers.stream()
                .map(Consumer::getStatus)
                .anyMatch(consumers::contains)) {

            for (Consumer c : consumers) {
                this.consumers.add(c);
            }
        } else {
            log.warn(String.format("CallTemplate has already passed consumer. CallTemplate consumers: %s, Passed consumers: %s",
                    this.getConsumers().stream().map(Consumer::getStatus).toList(),
                    consumers.stream().map(Consumer::getStatus).toList()));
            throw new InvalidDataException(String.format("CallTemplate has already passed consumer. CallTemplate consumers: %s, Passed consumers: %s",
                    this.getConsumers().stream().map(Consumer::getStatus).toList(),
                    consumers.stream().map(Consumer::getStatus).toList()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallTemplate callTemplate = (CallTemplate) o;
        return this.id != null && this.id.equals(callTemplate.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
