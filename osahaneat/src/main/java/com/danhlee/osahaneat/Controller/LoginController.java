package com.danhlee.osahaneat.Controller;

import com.danhlee.osahaneat.Payload.Request.SignUpRequest;
import com.danhlee.osahaneat.Payload.ResponseData;
import com.danhlee.osahaneat.Service.IMP.LoginServiceImp;
import com.danhlee.osahaneat.Service.LoginService;
import com.danhlee.osahaneat.Util.JWTUtilHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Base64;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {
    //@Autowired
    //@Qualifier("tenBean")
    //UserInterface userInterface;

    @Autowired
    LoginServiceImp loginServiceImp;

    @Autowired
    JWTUtilHelper jwtUtilHelper;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) { //Dùng @RequestParam hơi phèn ^^
        ResponseData responseData = new ResponseData();
        // Tạo key:

        // Theo hướng dẫn cũ:
        //SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.ES256);
        //String encrypted = Encoder.BASE64.encode(secretKey.getEncoded());
        // System.out.println(encrypted);

        // Theo hướng dẫn mới:
        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        String secretString = Encoders.BASE64.encode(secretKey.getEncoded());

        if (loginServiceImp.checkLogin(username, password)) {
            String token = jwtUtilHelper.generateToken(username);
            responseData.setData(token);
        } else {
            responseData.setData("");
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        ResponseData responseData = new ResponseData();
        responseData.setData(loginServiceImp.addUser(signUpRequest));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}






