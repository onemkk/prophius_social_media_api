package com.prophius.service.impl;

import com.prophius.enums.RoleName;
import com.prophius.entity.Role;
import com.prophius.enums.UserType;
import com.prophius.repository.RoleRepository;
import com.prophius.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByUserType(UserType userType) {
        RoleName roleName = switch (userType) {
            case CUSTOMER -> RoleName.ROLE_CUSTOMER;
            case ADMIN -> RoleName.ROLE_ADMIN;
        };

        return roleRepository.findByName(roleName).orElseThrow(() -> new
                EntityNotFoundException("Role not found"));
    }
}
