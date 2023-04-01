package com.securityapi.dto.director;

import com.securityapi.configuration.security.jwt.UserDetailsImpl;
import com.securityapi.domain.*;
import com.securityapi.dto.*;

import java.util.List;

public interface DirectorDTOs {
    PersonDTO fromUserDetailsImplToPersonDTO(UserDetailsImpl userDetails);

    PersonDTO fromPersonToPersonDTO(Person person);

    List<StatusConsumerFromManagerDTO> fromConsumerListToStatusConsumerDTOList(List<Consumer> consumerList);

    List<ReasonDTO> fromTopicListToTopicDTOList(List<Reason> reasonList);

    CallTemplateForOperatorDTO fromCallTemplateToCallTemplateForOperatorDTO(CallTemplate callTemplate);

    List<CallDTO> fromCallListToCallDTOList(List<Call> callList);
}
