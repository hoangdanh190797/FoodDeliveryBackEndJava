package com.danhlee.osahaneat.Service.IMP;

import com.danhlee.osahaneat.DTO.RestaurantDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface RestaurantServiceImp {
    boolean insertRestaurant( MultipartFile file,
                              String title,
                              String subtitle,
                              String description,
                              boolean is_freeship,
                              String address,
                              String open_date);
    // Khi nó đang làm interface thì nó chỉ khai báo thôi, đâu có làm gì thêm!
    // Dùng để khử Dependency gì đó?

    List<RestaurantDTO> getHomePageRestaurant();
}
