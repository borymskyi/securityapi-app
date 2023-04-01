package com.securityapi.service;

import com.securityapi.domain.CallTemplate;
import com.securityapi.dto.CallTemplateFromManagerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
public interface CallTemplateService {

    CallTemplate createNewCallTemplate(CallTemplateFromManagerDTO callTemplateFromManager, Long personId);

    CallTemplate findCallTemplateById(Long id);
}
