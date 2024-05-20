package com.danhlee.osahaneat.Service;

import com.danhlee.osahaneat.DTO.UserDTO;
import com.danhlee.osahaneat.Entity.Users;
import com.danhlee.osahaneat.Repository.UserRepository;
import com.danhlee.osahaneat.Service.IMP.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService implements UserServiceImp {
    @Autowired
    UserRepository userRepository;
    @Override
    public List<UserDTO> getAllUser() {
        List<Users> listUser = userRepository.findAll();
        List<UserDTO> listUserDTO = new ArrayList<>();

        for(Users users : listUser){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setUserName(users.getUserName());
            userDTO.setPassword(users.getPassword());
            userDTO.setFullName(users.getFullName());
            userDTO.setCreateDate(users.getCreateDate());

            listUserDTO.add(userDTO);
        }
        return listUserDTO;
    }
}
