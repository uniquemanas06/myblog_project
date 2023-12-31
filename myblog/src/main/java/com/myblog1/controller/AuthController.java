package com.myblog1.controller;
import com.myblog1.Admin.EmailAddresses;
import com.myblog1.entity.Role;
import com.myblog1.entity.User;
import com.myblog1.payload.JWTAuthResponse;
import com.myblog1.payload.LoginDto;
import com.myblog1.payload.SignUpDto;
import com.myblog1.repository.RoleRepository;
import com.myblog1.repository.UserRepository;
import com.myblog1.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
//import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

// ...

//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//
//        } catch (AuthenticationException e) {
//            // Handle authentication failure
//            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
//        }


//    }


//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        // add check for username exists in a DB
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()<>{}_+-]).{8,}$";
        if (signUpDto.getPassword().matches(regex)) {
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        } else {
            return new ResponseEntity<>("password must be 8-9 charaters including atleast 1 lowercase,1 uppercase letter with 1 digit and 1 special character", HttpStatus.BAD_REQUEST);
        }
//        Role roles= roleRepository.findByName("ROLE_USER").get();
//        Set<Role> role = new HashSet<>();
//        role.add(roles);
//        user.setRoles(role);
//        userRepository.save(user);
//        return new ResponseEntity<>("Registration Successful", HttpStatus.OK);
        Role role = new Role();
        int countForAdmin=1;
        int countForUser=2;
         if(countForAdmin==1){
             role.setId(1);
             role.setName("ROLE_ADMIN");
            roleRepository.save(role);
            countForAdmin=3;
         }
         if(countForUser==2){
             role.setId(2);
             role.setName("ROLE_USER");
             roleRepository.save(role);
             countForUser=3;
         }

        if(signUpDto.getEmail().equalsIgnoreCase(EmailAddresses.EMAIL_1.getEmailAddress())
        ||signUpDto.getEmail().equalsIgnoreCase(EmailAddresses.EMAIL_2.getEmailAddress())
        ||signUpDto.getEmail().equalsIgnoreCase(EmailAddresses.EMAIL_3.getEmailAddress())){

            Role roles = roleRepository.findByName("ROLE_ADMIN").get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
            return new ResponseEntity<>("Registration Successful", HttpStatus.OK);

        }else{
            Role roles = roleRepository.findByName("ROLE_USER").get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
            return new ResponseEntity<>("Registration Successful", HttpStatus.OK);

        }

    }
}


