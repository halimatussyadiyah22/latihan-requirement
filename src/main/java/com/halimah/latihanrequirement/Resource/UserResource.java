package com.halimah.latihanrequirement.Resource;

import com.halimah.latihanrequirement.Service.RoleService;
import com.halimah.latihanrequirement.Service.UserService;
import com.halimah.latihanrequirement.domain.HttpResponse;
import com.halimah.latihanrequirement.domain.User;
import com.halimah.latihanrequirement.domain.UserPrincipal;
import com.halimah.latihanrequirement.dto.UserDTO;
import com.halimah.latihanrequirement.dtomapper.UserDTOMapper;
import com.halimah.latihanrequirement.form.LoginForm;
import com.halimah.latihanrequirement.provider.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

//@RestController
//@RequestMapping(path = "/user")
//@RequiredArgsConstructor
//public class UserResource {
//    private final UserService userService;
//    private final RoleService roleService;
//    private final AuthenticationManager authenticationManager;
//    private final TokenProvider tokenProvider;
//
//    @PostMapping("/login")
//    public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginForm loginForm) {
//        authenticationManager.authenticate(unauthenticated(loginForm.getEmail(), loginForm.getPassword()));
//        UserDTO user = userService.getUserByEmail(loginForm.getEmail());
//        return user.isUsingMfa() ? sendVerificationCode(user) : sendResponse(user);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user) {
//        UserDTO userDto = userService.createUser(user);
//        return ResponseEntity.created(getUri()).body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user", userDto))
//                        .message("User created")
//                        .status(CREATED)
//                        .statusCode(CREATED.value())
//                        .build());
//    }
//
//    @GetMapping("/profile")
//    public ResponseEntity<HttpResponse> profile(Authentication authentication) {
//        UserDTO user = userService.getUserByEmail(authentication.getName());
//        System.out.println(authentication);
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user", user))
//                        .message("Profile Retrieved")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build());
//    }
//
//    @GetMapping("/verify/code/{email}/{code}")
//    public ResponseEntity<HttpResponse> verifyCode(@PathVariable("email") String email, @PathVariable("code") String code) {
//        UserDTO user = userService.verifyCode(email, code);
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user", user, "access_token", tokenProvider.createAccessToken(getUserPrincipal(user))
//                                , "refresh_token", tokenProvider.createRefreshToken(getUserPrincipal(user))))
//                        .message("Login Success")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build());
//    }
//
//    private URI getUri() {
//        return URI.create(fromCurrentContextPath().path("/user/get/<userId>").toUriString());
//    }
//
//    private UserPrincipal getUserPrincipal(UserDTO user) {
//        return new UserPrincipal(toUser(userService.getUserByEmail(user.getEmail())),
//                roleService.getRoleByUserId(user.getId()).getPermission());
//    }
//
//    private ResponseEntity<HttpResponse> sendVerificationCode(UserDTO user) {
//        userService.sendVerificationCode(user);
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user", user))
//                        .message("Verification Code Sent")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build());
//    }
//
//    private ResponseEntity<HttpResponse> sendResponse(UserDTO user) {
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user", user, "access_token", tokenProvider.createAccessToken(getUserPrincipal(user))
//                                , "refresh_token", tokenProvider.createRefreshToken(getUserPrincipal(user))))
//                        .message("Login Success")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build());
//    }
//}
//fungsinya seperti controller
//@RestController
//@RequestMapping(path = "/user")
//@RequiredArgsConstructor
//public class UserResource {
//    private final UserService userService;
//    private final AuthenticationManager authenticationManager;
//    private final RoleService roleService;
//    private final TokenProvider tokenProvider;
//
//    @PostMapping("/login")
//    public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginForm loginForms){
//        authenticationManager.authenticate(unauthenticated(loginForms.getEmail(),loginForms.getPassword()));
//        UserDTO user = userService.getUserByEmail(loginForms.getEmail());
//        return user.isUsingMfa() ? sendVerificationCode(user): sendResponse(user);
//    }
//    @PostMapping("/register")
//    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
//        UserDTO userDTO = userService.createUser(user);
//        return ResponseEntity.created(getUri()).body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user",userDTO))
//                        .message("User Created")
//                        .status(CREATED)
//                        .statusCode(CREATED.value())
//                        .build()
//        );
//    }
//    private ResponseEntity<HttpResponse> sendVerificationCode(UserDTO user) {
//        userService.sendVerificationCode(user);
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user",user))
//                        .message("Verification code sent")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build()
//        );
//    }
//
//
//    private UserPrincipal getUserPrincipal(UserDTO user) {
//        return new UserPrincipal(UserDTOMapper.toUser(userService.getUserByEmail(user.getEmail())),
//                roleService.getRoleByUserId(user.getId()).getPermission());
//    }
//
//    private URI getUri(){
//        return URI.create(fromCurrentContextPath().path("/user/get/<userId>").toUriString());
//    }
//    private ResponseEntity<HttpResponse> sendResponse(UserDTO user) {
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user",user,"access_token", tokenProvider.createAccessToken(getUserPrincipal(user))
//                        ,"refresh_token",tokenProvider.createRefreshToken(getUserPrincipal(user))))
//                        .message("login succes")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build());
//    }
//    @GetMapping("/verify/code/{email}/{code}")
//    public ResponseEntity<HttpResponse> verifyCode(@PathVariable("email") String email, @PathVariable("code") String code){
//        UserDTO user = userService.verifyCode(code,email);
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user",user,"access_token", tokenProvider.createAccessToken(getUserPrincipal(user))
//                                ,"refresh_token",tokenProvider.createRefreshToken(getUserPrincipal(user))))
//                        .message("login success")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build());
//    }
//    @GetMapping("/profile")
//    public ResponseEntity<HttpResponse> profile(Authentication authentication){
//        UserDTO user = userService.getUserByEmail(authentication.getName());
//        System.out.println(authentication.getPrincipal());
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user",user))
//                        .message("profile retrieved")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build()
//        );
//    }
//}
//ServletUriComponentsBuilder = membuat url