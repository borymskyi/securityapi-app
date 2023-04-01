package com.securityapi.service;

import com.securityapi.domain.enums.ERole;
import com.securityapi.domain.Role;
import org.springframework.stereotype.Service;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
public interface RoleService {

    Role findRoleByRoleName(ERole eRole);
}
