package com.halimah.latihanrequirement.repository;

import com.halimah.latihanrequirement.domain.User;
import com.halimah.latihanrequirement.dto.UserDTO;

import java.util.Collection;

public interface UserRepository<T extends User> {
    /* Basic CRUD Operations */
    T create(T data);

    Collection<T> list(int page, int pageSize);

    T get(Long id);

    T update(T data);

    Boolean delete(Long id);

    /* More Complex Operations */
    User getUserByEmail(String email);

    void sendVerificationCode(UserDTO user);

    User verifyCode(String email, String code);
}
//public interface UserRepository <T extends User>
//    {
//        /* basic CRUD*/
//        T create(T data);
////        menarik data secara keseluruhan
//        Collection<User> list(int page, int pageSize);
//
////        menarik data secara id
//        T get(Long id);
//        T update (T data);
//        Boolean delete (Long id );
//
//        User getUserByEmail(String email);
//
//        void sendVerificationCode(UserDTO user);
//
//        T verifyCode(String email, String code);
//    }
//
