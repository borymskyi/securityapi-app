package com.securityapi.dto.director;

import com.securityapi.configuration.security.jwt.UserDetailsImpl;
import com.securityapi.domain.*;
import com.securityapi.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DirectorDTOsImpl implements DirectorDTOs {

    @Override
    public PersonDTO fromUserDetailsImplToPersonDTO(UserDetailsImpl userDetails) {
        return PersonDTO.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public PersonDTO fromPersonToPersonDTO(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .email(person.getEmail())
                .roles(person.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<StatusConsumerFromManagerDTO> fromConsumerListToStatusConsumerDTOList(List<Consumer> consumerList) {
        return consumerList.stream()
                .map(c -> new StatusConsumerFromManagerDTO(c.getStatus().toString())).toList();
    }

    @Override
    public List<ReasonDTO> fromTopicListToTopicDTOList(List<Reason> reasonList) {
        return reasonList.stream()
                .map(r -> new ReasonDTO(r.getId(), r.getReasonMessage())).toList();
    }

    @Override
    public CallTemplateForOperatorDTO fromCallTemplateToCallTemplateForOperatorDTO(CallTemplate callTemplate) {
        return CallTemplateForOperatorDTO.builder()
                .id(callTemplate.getId())
                .manager(fromPersonToPersonDTO(callTemplate.getManager()))
                .statusList(fromConsumerListToStatusConsumerDTOList(callTemplate.getConsumers()))
                .reasonList(fromTopicListToTopicDTOList(callTemplate.getReasons()))
                .build();
    }

    @Override
    public List<CallDTO> fromCallListToCallDTOList(List<Call> callList) {
        log.info("****map Call to CallDTO");
        return callList.stream()
                .map(c -> CallDTO.builder()
                        .callId(c.getId())
                        .callTemplateId(c.getReason().getCallTemplate().getId())
                        .reasonMessage(c.getReason().getReasonMessage())
                        .consumerStatus(c.getConsumer().getStatus().toString())
                        .createdAt(c.getCreatedAt())
                        .build())
                .toList();
    }
}
