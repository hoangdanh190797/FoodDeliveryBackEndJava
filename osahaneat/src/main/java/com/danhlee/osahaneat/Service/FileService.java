package com.danhlee.osahaneat.Service;

import com.danhlee.osahaneat.Service.IMP.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {
    @Value("${fileUpload.rootPath}")
    private String rootPath;
    private Path root;

    private void init(){
        try {
            root = Paths.get(rootPath);
            if(Files.notExists(root)){
                Files.createDirectories(root);
            }
        } catch (Exception e) {
            System.out.println("Error create folder root: " + e.getMessage());
        }
    }
    @Override
    public boolean saveFile(MultipartFile file) {
        try {
            init();
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("Error save file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Resource loadFile(String fileName) { //Dùng Resource để khai báo vì Resource nó hỗ trợ tốt hơn File
        try {
            init();
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
        } catch (MalformedURLException e) {
            System.out.println("Error save file: " + e.getMessage());
        }
        // Tại sao nó lại buộc ta phải mở try-catch vì
        // Đọc file => mở luồng => nếu luồng bị lỗi thì sao?
        return null;
    }
}
