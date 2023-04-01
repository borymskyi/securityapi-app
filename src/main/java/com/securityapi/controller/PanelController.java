package com.securityapi.controller;

import com.securityapi.configuration.security.jwt.UserDetailsImpl;
import com.securityapi.dto.CallFromOperatorDTO;
import com.securityapi.dto.CallTemplateFromManagerDTO;
import com.securityapi.dto.director.DirectorDTOs;
import com.securityapi.service.CallService;
import com.securityapi.service.CallTemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/v1/calls")
public class PanelController {

    CallService callService;
    CallTemplateService callTemplateService;
    DirectorDTOs directorDTO;

    @PostMapping("/templates")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> createNewTemplate(@Valid @RequestBody CallTemplateFromManagerDTO callTemplateFromManager) {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        var response = directorDTO.fromCallTemplateToCallTemplateForOperatorDTO(
                callTemplateService.createNewCallTemplate(callTemplateFromManager, userDetailsImpl.getId())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/templates/{id}")
    @PreAuthorize("hasRole('OPERATOR') or hasRole('MANAGER')")
    public ResponseEntity<?> getCallTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                directorDTO.fromCallTemplateToCallTemplateForOperatorDTO(callTemplateService.findCallTemplateById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('OPERATOR') or hasRole('MANAGER')")
    public ResponseEntity<?> createCall(@Valid @RequestBody CallFromOperatorDTO callFromOperatorDTO) {
        callService.saveCallByStatusAndTopicId(callFromOperatorDTO);
        return ResponseEntity.ok().body("Call created");
    }

    @GetMapping()
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> getAllCallByIdCallTemplate(@RequestParam(value = "call_template_id") Long templateId) {
        return ResponseEntity.ok().body(
                directorDTO.fromCallListToCallDTOList(callService.getAllCallByIdCallTemplate(templateId)));
    }
}
