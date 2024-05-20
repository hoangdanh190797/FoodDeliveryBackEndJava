package com.danhlee.osahaneat.Repository;

import com.danhlee.osahaneat.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Tại sao lại là Interger?
//Xét coi đang có cột nào là KHÓA CHÍNH
//Và lấy lớp bao của nó làm kiểu dữ liệu thêm vào JpaRepository
//Khi đánh dấu nó là 1 Repository (@Repository)
//Nó sẽ được đưa lên Container dùng chung
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    // select * from users where username = '' and password = '';
    List<Users> findByUserNameAndPassword(String username, String password);
    Users findByUserName(String userName);
}
