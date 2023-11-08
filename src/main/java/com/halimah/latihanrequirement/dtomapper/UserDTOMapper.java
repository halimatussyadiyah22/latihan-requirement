package com.halimah.latihanrequirement.dtomapper;

import com.halimah.latihanrequirement.domain.Role;
import com.halimah.latihanrequirement.domain.User;
import com.halimah.latihanrequirement.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static UserDTO fromUser(User user, Role role) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setRoleName(role.getName());
        userDTO.setPermissions(role.getPermission());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
//@Component
//public class UserDTOMapper {
//    public static UserDTO fromUser(User user){
//        UserDTO userDTO = new UserDTO();
//        BeanUtils.copyProperties(user,userDTO //mentransfer user ke userdto
//                );
//        return userDTO;
//    }
//    public static UserDTO fromUser(User user, Role role){
//        UserDTO userDTO = new UserDTO();
//        BeanUtils.copyProperties(user,userDTO //mentransfer user ke userdto
//        );
//        userDTO.setRoleName(role.getName());
//        userDTO.setPermissions(role.getPermission());
//        return userDTO;
//    }
//    public  static  User toUser (UserDTO userDTO){
//        User user = new User();
//        BeanUtils.copyProperties(userDTO,user //transfer userdto ke user
//                 );
//        return user;
//    }
//}
