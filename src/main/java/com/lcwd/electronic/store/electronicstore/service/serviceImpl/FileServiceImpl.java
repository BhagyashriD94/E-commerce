package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.exception.BadApiRequest;
import com.lcwd.electronic.store.electronicstore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename=file.getOriginalFilename();
         logger.info("Filename: {}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension =originalFilename.substring(originalFilename.lastIndexOf("."));
        String filenamewithextention=filename+extension;
        String fullpathwithfilename=path+File.separator+filenamewithextention;
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            File folder=new File(path);
            if(!folder.exists()){

                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullpathwithfilename));
             return filenamewithextention;
        }else{
            throw new BadApiRequest("File with this"+extension+"not allow");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullpath= path+File.separator+name;
        InputStream inputstream=new FileInputStream(fullpath);
        return inputstream;
    }
}
