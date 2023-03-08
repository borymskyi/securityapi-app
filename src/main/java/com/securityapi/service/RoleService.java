package com.securityapi.service;

import com.securityapi.domain.ERole;
import com.securityapi.domain.Role;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */
public interface RoleService {

    Role findRoleByRoleName(ERole eRole);
}
