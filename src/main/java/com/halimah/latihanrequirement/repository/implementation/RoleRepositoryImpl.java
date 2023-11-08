package com.halimah.latihanrequirement.repository.implementation;

import com.halimah.latihanrequirement.domain.Role;
import com.halimah.latihanrequirement.exception.ApiException;
import com.halimah.latihanrequirement.repository.RoleRepository;
import com.halimah.latihanrequirement.rowMapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static com.halimah.latihanrequirement.enumeration.RoleType.ROLE_USER;
import static com.halimah.latihanrequirement.query.RoleQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);
        try {
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleName), new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        log.info("Adding role for user id: {}", userId);
        try {
            return jdbc.queryForObject(SELECT_ROLE_BY_ID_QUERY, of("id", userId), new RoleRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}

//@Repository
//@RequiredArgsConstructor
//@Slf4j
//public class RoleRepositoryImpl implements RoleRepository<Role> {
//    private final NamedParameterJdbcTemplate jdbc;
//
//    @Override
//    public Role create(Role data) {
//        return null;
//    }
//
//    @Override
//    public Collection<Role> list(int page, int pageSize) {
//        return null;
//    }
//
//    @Override
//    public Role get(Long id) {
//        return null;
//    }
//
//    @Override
//    public Role update(Role data) {
//        return null;
//    }
//
//    @Override
//    public Boolean delete(Long id) {
//        return null;
//    }
//
//    @Override
//    public void addRoleToUser(Long userId, String name) {
//        log.info("Adding role{} to user id: {}", name,userId);
//        try { //supaya ada eror, erornya di lemparkan//
//            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("name",name),new RoleRowMapper());
//            jdbc.update(INSERT_ROLE_TO_USER_QUERY,Map.of("userId",userId,"roleId",requireNonNull(role).getId()));
//
//        }catch (EmptyResultDataAccessException exception){
//            log.error(exception.getMessage());
//            throw new ApiException("No role found by name: " + ROLE_USER.name());
//        }catch (Exception exception){
//            log.error(exception.getMessage());
//            throw new ApiException("An error occurred. Please try again");
//        }
//
//    }
//
//    @Override
//    public Role getRoleByUserId(Long userId) {
//        try {
//            return jdbc.queryForObject(SELECT_ROLE_BY_ID_USER, Map.of("id",userId),new RoleRowMapper());
//
//        }catch (EmptyResultDataAccessException exception){
//            log.error(exception.getMessage());
//            throw new ApiException("No role found by name: " + ROLE_USER.name());
//        }catch (Exception exception){
//            log.error(exception.getMessage());
//            throw new ApiException("An error occurred. Please try again");
//        }
//    }
//
//    @Override
//    public Role getRoleByUserEmail(String email) {
//        return null;
//    }
//
//    @Override
//    public void updateUserRole(Long userId, String name) {
//
//    }
//}
