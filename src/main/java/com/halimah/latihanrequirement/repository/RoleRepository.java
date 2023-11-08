package com.halimah.latihanrequirement.repository;

import com.halimah.latihanrequirement.domain.Role;

import java.util.Collection;

public interface RoleRepository<T extends Role> {
    /* Basic CRUD Operations */
    T create(T data);

    Collection<T> list(int page, int pageSize);

    T get(Long id);

    T update(T data);

    Boolean delete(Long id);

    /* More Complex Operations */
    void addRoleToUser(Long userId, String roleName);

    Role getRoleByUserId(Long userId);

    Role getRoleByUserEmail(String email);

    void updateUserRole(Long userId, String roleName);
}
//public interface RoleRepository<T extends Role> {
//    T create(T data);
//    Collection<T> list(int page, int pageSize);
//
//    T get(Long id);
//    T update (T data);
//    Boolean delete (Long id );
//    void addRoleToUser(Long id, String roleName);
//    Role getRoleByUserId(Long userId);
//    Role getRoleByUserEmail(String email);
//    void updateUserRole(Long userId, String roleName);
//
//}
