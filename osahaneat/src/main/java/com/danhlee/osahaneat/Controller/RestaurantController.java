package com.danhlee.osahaneat.Controller;

import com.danhlee.osahaneat.Payload.ResponseData;
import com.danhlee.osahaneat.Service.FileService;
import com.danhlee.osahaneat.Service.IMP.FileServiceImp;
import com.danhlee.osahaneat.Service.IMP.RestaurantServiceImp;
import com.danhlee.osahaneat.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    FileServiceImp fileServiceImp;

    @Autowired
    RestaurantServiceImp restaurantServiceImp;

    @PostMapping() //Tại sao lại bổ sung các tham số của createRestaurant vì để 1 Restaurant hoàn chỉnh
    // cần có các thông tin như: id, title, subtitle, description, image, is_freeship, address, open_date
    public ResponseEntity<?> createRestaurant(  @RequestParam MultipartFile file,
                                                @RequestParam String title,
                                                @RequestParam String subtitle,
                                                @RequestParam String description,
                                                @RequestParam boolean is_freeship, //Có một chút vấn đề với kiểu dữ liệu is_freeship
                                                @RequestParam String address,
                                                @RequestParam String open_date
    ){
        ResponseData responseData = new ResponseData();
        boolean isSuccess = restaurantServiceImp.insertRestaurant(file, title, subtitle, description, is_freeship, address, open_date);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getHomeRestaurant(){
        ResponseData responseData = new ResponseData();

        responseData.setData(restaurantServiceImp.getHomePageRestaurant());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFileRestaurant(@PathVariable String filename){
        Resource resource =  fileServiceImp.loadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
