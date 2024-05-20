package com.danhlee.osahaneat.Service;

import com.danhlee.osahaneat.DTO.UserDTO;
import com.danhlee.osahaneat.Entity.Roles;
import com.danhlee.osahaneat.Entity.Users;
import com.danhlee.osahaneat.Payload.Request.SignUpRequest;
import com.danhlee.osahaneat.Repository.UserRepository;
import com.danhlee.osahaneat.Service.IMP.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class LoginService implements LoginServiceImp {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUser(){
        return null;
    }

    @Override
    public boolean checkLogin(String username, String password){
        Users users = userRepository.findByUserName(username);
        return passwordEncoder.matches(password, users.getPassword());
    }

    @Override
    public boolean addUser(SignUpRequest signUpRequest) {
        Roles roles = new Roles();              //Roles này phải tạo từ Entity ra!
        roles.setId(signUpRequest.getRoleId());

        Users users = new Users();
        users.setFullName(signUpRequest.getFullname());
        users.setUserName(signUpRequest.getEmail());
        users.setPassword(signUpRequest.getPassword());
        users.setRoles(roles);

        try {
            userRepository.save(users);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}

