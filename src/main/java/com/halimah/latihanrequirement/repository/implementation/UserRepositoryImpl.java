package com.halimah.latihanrequirement.repository.implementation;


import com.halimah.latihanrequirement.domain.Role;
import com.halimah.latihanrequirement.domain.User;
import com.halimah.latihanrequirement.domain.UserPrincipal;
import com.halimah.latihanrequirement.dto.UserDTO;
import com.halimah.latihanrequirement.exception.ApiException;
import com.halimah.latihanrequirement.repository.RoleRepository;
import com.halimah.latihanrequirement.repository.UserRepository;
import com.halimah.latihanrequirement.rowMapper.UserRowMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


import static com.halimah.latihanrequirement.enumeration.RoleType.ROLE_USER;
import static com.halimah.latihanrequirement.enumeration.VerificationType.ACCOUNT;
import static com.halimah.latihanrequirement.query.UserQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.time.DateFormatUtils.format;
import static org.apache.commons.lang3.time.DateUtils.addDays;


//@Repository
//@RequiredArgsConstructor
//@Slf4j
//public class UserRepositoryImpl implements UserRepository<User>, UserDetailsService {
//    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";
//    private final NamedParameterJdbcTemplate jdbc;
//    private final RoleRepository<Role> roleRepository;
//    private final BCryptPasswordEncoder encoder;
//
//    @Override
//    public User create(User user) {
//        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0)
//            throw new ApiException("Email already in use. Please use a different email and try again.");
//        try {
//            KeyHolder holder = new GeneratedKeyHolder();
//            SqlParameterSource parameters = getSqlParameterSource(user);
//            jdbc.update(INSERT_USER_QUERY, parameters, holder, new String[]{"id"});
//            user.setId(requireNonNull(holder.getKey()).longValue());
//            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
//            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
//            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "url", verificationUrl));
//            //emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
//            user.setEnabled(false);
//            user.setNotLocked(true);
//            return user;
//        } catch (Exception exception) {
//            log.error(exception.getMessage());
//            throw new ApiException("An error occurred. Please try again.");
//        }
//    }
//
//    @Override
//    public Collection<User> list(int page, int pageSize) {
//        return null;
//    }
//
//    @Override
//    public User get(Long id) {
//        return null;
//    }
//
//    @Override
//    public User update(User data) {
//        return null;
//    }
//
//    @Override
//    public Boolean delete(Long id) {
//        return null;
//    }
//
//    private Integer getEmailCount(String email) {
//        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = getUserByEmail(email);
//        if (user == null) {
//            log.error("User not found in the database");
//            throw new UsernameNotFoundException("User not found in the database");
//        } else {
//            log.info("User found in the database: {}", email);
//            return new UserPrincipal(user, roleRepository.getRoleByUserId(user.getId()).getPermission());
//        }
//    }
//
//    @Override
//    public User getUserByEmail(String email) {
//        try {
//            User user = jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, of("email", email), new UserRowMapper());
//            return user;
//        } catch (EmptyResultDataAccessException exception) {
//            throw new ApiException("No User found by email: " + email);
//        } catch (Exception exception) {
//            log.error(exception.getMessage());
//            throw new ApiException("An error occurred. Please try again.");
//        }
//    }
//
//    @Override
//    public void sendVerificationCode(UserDTO user) {
//        Timestamp expirationDate = Timestamp.valueOf(format(DATE_FORMAT, addDays(new Date(), 1)));
//        String verificationCode = randomAlphabetic(8).toUpperCase();
//        try {
//            jdbc.update(DELETE_VERIFICATION_CODE_BY_USER_ID, of("id", user.getId()));
//            jdbc.update(INSERT_VERIFICATION_CODE_QUERY, of("userId", user.getId(), "code", verificationCode, "expirationDate", expirationDate));
//            // sendSMS(user.getPhone(), "From: UserManagementApp \nVerification code\n" + verificationCode);
//            log.info("Verification Code: {}", verificationCode);
//        } catch (Exception exception) {
//            log.error(exception.getMessage());
//            throw new ApiException("An error occurred. Please try again.");
//        }
//    }
//
//    @Override
//    public User verifyCode(String email, String code) {
//        if (isVerificationCodeExpired(code)) throw new ApiException("This code has expired. Please login again.");
//        try {
//            User userByCode = jdbc.queryForObject(SELECT_USER_BY_USER_CODE_QUERY, of("code", code), new UserRowMapper());
//            User userByEmail = jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, of("email", email), new UserRowMapper());
//            if (userByCode.getEmail().equalsIgnoreCase(userByEmail.getEmail())) {
//                jdbc.update(DELETE_CODE, of("code", code));
//                return userByCode;
//            } else {
//                throw new ApiException("Code is invalid. Please try again.");
//            }
//        } catch (EmptyResultDataAccessException exception) {
//            throw new ApiException("Could not find record");
//        } catch (Exception exception) {
//            throw new ApiException("An error occurred. Please try again.");
//        }
//    }
//
//    private Boolean isVerificationCodeExpired(String code) {
//        try {
//            return jdbc.queryForObject(SELECT_CODE_EXPIRATION_QUERY, of("code", code), Boolean.class);
//        } catch (EmptyResultDataAccessException exception) {
//            throw new ApiException("This code is not valid. Please login again.");
//        } catch (Exception exception) {
//            throw new ApiException("An error occurred. Please try again.");
//        }
//    }
//
//    private SqlParameterSource getSqlParameterSource(User user) {
//        return new MapSqlParameterSource()
//                .addValue("firstName", user.getFirstName())
//                .addValue("lastName", user.getLastName())
//                .addValue("email", user.getEmail())
//                .addValue("password", encoder.encode(user.getPassword()));
//    }
//
//    private String getVerificationUrl(String key, String type) {
//        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
//    }
//}

@Repository
@RequiredArgsConstructor
@Slf4j //=> untuk debiuging lebih mudah
public class UserRepositoryImpl implements UserRepository<User>, UserDetailsService {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";
    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        // check the email is unique
        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0)
            throw new ApiException("email already in use. please use a different email and try again");
        // save new user
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder, new String[]{"id"});
            user.setId(requireNonNull(holder.getKey()).longValue());

            // add role to user
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            // create verification url
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            // save url in verification table
//            Timestamp dateVerification = Timestamp.valueOf(LocalDateTime.now());
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "url", verificationUrl));
            // send email to user with verification url

            user.setEnabled(false);
            user.setNotLocked(true);
            // return the newly created user
            return user;
            // if any error, throw exception with proper messages


        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(" An error occurred. please try again");
        }
    }


    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }


    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY,of ("email", email), Integer.class);
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("user found in the database : {}", email);
            return new UserPrincipal(user, roleRepository.getRoleByUserId(user.getId()).getPermission());
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            User user = jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, of("email", email), new UserRowMapper());
            return user;
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("no user found by email" + email);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("an error occurred. please try again.");
        }
    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        Timestamp expirationDate = Timestamp.valueOf(format(addDays(new Date(),1), DATE_FORMAT));
        String verificationCode = randomAlphabetic(8).toUpperCase();
        try {
            jdbc.update(DELETE_VERIFICATION_CODE_BY_USER_ID, of("id", user.getId()));
            jdbc.update(INSERT_VERIFICATION_CODE_QUERY, of("userId", user.getId(), "code", verificationCode, "expirationDate", expirationDate));
//            untuk kode verifikasi sms
            //            SmsUtils.sendSMS(user.getPhone(), "From: UserManagementApp \n verification code\n" + verificationCode);
            log.info(verificationCode);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(" an error occured. please try again.");
        }
    }

    @Override
    public User verifyCode(String email, String code) {
        if (isVerificationCodeExpired(code))
            throw new ApiException("this code has expired. please login again");
        try {
            User userByCode = jdbc.queryForObject(SELECT_USER_BY_USER_CODE_QUERY, of("code", code), new UserRowMapper());
            User userByEmail = jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, of("email", email), new UserRowMapper());
            if (requireNonNull(userByCode).getEmail().equalsIgnoreCase(requireNonNull(userByEmail).getEmail())) {
                jdbc.update(DELETE_CODE,of("code",code));
                return userByCode;
            } else {
                throw new ApiException("could not find record");
            }
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("could not find record");
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(" an  error occurred. please try again");
        }
    }

    private Boolean isVerificationCodeExpired(String code) {
        try {
            return jdbc.queryForObject(SELECT_CODE_EXPIRATION_QUERY, of("code", code), Boolean.class);
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("this code is not valid. please login again");
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(" an error occurred. please try again.");
        }


//    @Override
//    public void addRoleToUser(Long userId, String roleName) {
//        log.info("Adding role{} to user id: {}", roleName, userId);
//        try {
//            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("name", roleName), new RoleRowMapper());
//            jdbc.update(INSERT_ROLE_TO_USER_QUERY, Map.of("userId", userId, "roleID", requireNonNull(role).getId()));
//
//        } catch (EmptyResultDataAccessException exception) {
//            log.error(exception.getMessage());
//            throw new ApiException("No role found by name: " + ROLE_USER);
//        } catch (Exception exception) {
//            log.error(exception.getMessage());
//            throw new ApiException("An error occurred. Please try again");
//        }
//
//
//    }
    }
}