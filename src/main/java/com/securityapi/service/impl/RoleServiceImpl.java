package com.securityapi.service.impl;

import com.securityapi.domain.enums.ERole;
import com.securityapi.domain.Role;
import com.securityapi.exception.RoleNotFoundException;
import com.securityapi.repository.RoleRepository;
import com.securityapi.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    @Override
    public Role findRoleByRoleName(ERole eRole) {
        return roleRepository.findRoleByName(eRole)
                .orElseThrow(() -> {
                    log.error("failed to find the role with name: " + eRole.name());
                    return new RoleNotFoundException(String.format("Role %s not found", eRole));
                });

    }
}


