package com.halimah.latihanrequirement.Service.implementation;

import com.halimah.latihanrequirement.Service.UserService;
import com.halimah.latihanrequirement.domain.Role;
import com.halimah.latihanrequirement.domain.User;
import com.halimah.latihanrequirement.dto.UserDTO;
import com.halimah.latihanrequirement.dtomapper.UserDTOMapper;
import com.halimah.latihanrequirement.repository.RoleRepository;
import com.halimah.latihanrequirement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.halimah.latihanrequirement.dtomapper.UserDTOMapper.fromUser;

//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//    private final UserRepository<User> userRepository;
//    private final RoleRepository<Role> roleRoleRepository;
//
//    @Override
//    public UserDTO createUser(User user) {
//        return mapToUserDTO(userRepository.create(user));
//    }
//
//    @Override
//    public UserDTO getUserByEmail(String email) {
//        return mapToUserDTO(userRepository.getUserByEmail(email));
//    }
//
//    @Override
//    public void sendVerificationCode(UserDTO user) {
//        userRepository.sendVerificationCode(user);
//    }
//
//    @Override
//    public UserDTO verifyCode(String email, String code) {
//        return mapToUserDTO(userRepository.verifyCode(email, code));
//    }
//
//    private UserDTO mapToUserDTO(User user) {
//        return fromUser(user, roleRoleRepository.getRoleByUserId(user.getId()));
//    }
//}
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;
    private final RoleRepository<Role> roleRepository;
    @Override
    public UserDTO createUser(User user) {
        return mapToUserDTO(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return mapToUserDTO(userRepository.getUserByEmail(email));
    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        userRepository.sendVerificationCode(user);
    }

    @Override
    public UserDTO verifyCode(String email,String code){
        return  mapToUserDTO(userRepository.verifyCode(email,code));
    }
    private UserDTO mapToUserDTO(User user){
        return fromUser(user,roleRepository.getRoleByUserId(user.getId()));
    }
}
