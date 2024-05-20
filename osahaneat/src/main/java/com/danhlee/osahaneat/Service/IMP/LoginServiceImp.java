package com.danhlee.osahaneat.Service.IMP;

import com.danhlee.osahaneat.DTO.UserDTO;
import com.danhlee.osahaneat.Payload.Request.SignUpRequest;


import java.util.List;

public interface LoginServiceImp {
    List<UserDTO> getAllUser();
    boolean checkLogin(String username, String password);
    boolean addUser(SignUpRequest signUpRequest);
}
