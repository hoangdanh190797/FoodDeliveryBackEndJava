package com.danhlee.osahaneat.Service;

import com.danhlee.osahaneat.DTO.RestaurantDTO;
import com.danhlee.osahaneat.Entity.RatingRestaurant;
import com.danhlee.osahaneat.Entity.Restaurant;
import com.danhlee.osahaneat.Repository.RestaurantRepository;
import com.danhlee.osahaneat.Service.IMP.RestaurantServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantService implements RestaurantServiceImp {
    @Autowired
    RestaurantRepository restaurantRepository;
    // Bắt đầu về Logic, hình ảnh phải được đưa lên Server => XONG => THÌ MỚI
    // => Tiến hành tạo dữ liệu cho DB

    //Vì sử dụng lưu file nên phải Autowire lại cái FileService để sử dụng
    @Autowired
    FileService fileService;

    @Override
    public boolean insertRestaurant(MultipartFile file,
                                    String title,
                                    String subtitle,
                                    String description,
                                    boolean is_freeship,
                                    String address,
                                    String open_date) {
        boolean isInsertSuccess = false;
        // Trong đoạn try-cacth tới đó tương đối ổn rồi. Tuy nhiên, để mà clean code nên set từ đầu trạng thái
        //false => khi mà thực hiện thành công thì trả về
        try {
            boolean isSaveFileSuccess = fileService.saveFile(file);
            if (isSaveFileSuccess) {
                Restaurant restaurant = new Restaurant();
                restaurant.setTitle(title);
                restaurant.setSubtitle(subtitle);
                restaurant.setDescription(description);
                restaurant.setImage(file.getOriginalFilename()); // Chỉ lấy tên file vì nếu file có thay đổi ổ cứng, chỉ cần
                //thay đổi đường dẫn ở file application.yml
                restaurant.setFreeship(is_freeship); // Đang lỗi kiểu dữ liệu => Chưa đồng nhất kiểu boolean => Đã fix
                restaurant.setAddress(address);

                //Xử lý kiểu dữ liệu cho String về Date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date openDate = simpleDateFormat.parse(open_date);
                restaurant.setOpenDate(openDate);

                //Sau khi khớp kiểu dữ liệu:
                // => Dùng save trong JPA, Repository để lưu toàn bộ thông tin xuống DB
                restaurantRepository.save(restaurant);
                isInsertSuccess = true;
            }
        } catch (ParseException e) {
            System.out.println("Error insert Open Date: " + e.getMessage());
        }
        return isInsertSuccess;
    }

    @Override
    public List<RestaurantDTO> getHomePageRestaurant() {
        //Làm sao => Câu hỏi đó đặt toàn bộ ở hàm này => Để thực hiện yêu cầu của video 065:
        PageRequest pageRequest = PageRequest.of(0,1);
        Page<Restaurant> listData = restaurantRepository.findAll(pageRequest);
        List<RestaurantDTO> restaurantDTOS = new ArrayList<>();
        for(Restaurant data : listData){
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setTitle(data.getTitle());
            restaurantDTO.setSubTitle(data.getSubtitle());
            restaurantDTO.setImage(data.getImage());
            restaurantDTO.setFreeShip(data.isFreeship());
            restaurantDTO.setRating(calculatorRating(data.getListRatingRestaurant()));
            restaurantDTOS.add(restaurantDTO);
        }
        return restaurantDTOS;
    }

    public double calculatorRating (Set<RatingRestaurant> listRating){
        double totalPoint = 0;
        for(RatingRestaurant data : listRating){
            totalPoint += data.getRatePoint();
        }
        return totalPoint/listRating.size();
    }
}
