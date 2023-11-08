package com.halimah.latihanrequirement.Service.implementation;

import com.halimah.latihanrequirement.Service.RoleService;
import com.halimah.latihanrequirement.domain.Role;
import com.halimah.latihanrequirement.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository<Role> roleRoleRepository;

    @Override
    public Role getRoleByUserId(Long id) {
        return roleRoleRepository.getRoleByUserId(id);
    }
}
//@Service
//@RequiredArgsConstructor
//public class RoleServiceImpl implements RoleService {
//    private final RoleRepository<Role> roleRepository;
//    @Override
//    public Role getRoleByUserId(Long id) {
//        return roleRepository.getRoleByUserId(id);
//    }
//}
