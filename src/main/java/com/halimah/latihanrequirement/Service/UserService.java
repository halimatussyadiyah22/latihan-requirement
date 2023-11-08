package com.halimah.latihanrequirement.Service;

import com.halimah.latihanrequirement.domain.User;
import com.halimah.latihanrequirement.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);

    UserDTO getUserByEmail(String email);

    void sendVerificationCode(UserDTO user);

    UserDTO verifyCode(String email, String code);
}
//public interface UserService {
//    UserDTO createUser(User user);
////dibawah itu diganti karena ga semua atribut user itu di transfer
//    //    void createUser(User user);
//    UserDTO getUserByEmail(String email);
//
//    void sendVerificationCode(UserDTO user);
//
//    UserDTO verifyCode(String email, String code);
////    login functionality
////    void sendVerificationCode(UserDTO user);
//
//}
