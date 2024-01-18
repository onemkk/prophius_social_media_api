package com.prophius.service;

import com.prophius.entity.Role;
import com.prophius.enums.UserType;

public interface RoleService {

    Role getRoleByUserType(UserType userType);
}
