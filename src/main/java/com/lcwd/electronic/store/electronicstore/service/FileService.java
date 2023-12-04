package com.lcwd.electronic.store.electronicstore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadFile(MultipartFile file, String path) throws IOException;

    InputStream getResource(String path, String name) throws FileNotFoundException;

    String uploadCoverImageFile(MultipartFile file,String path) throws IOException;

    InputStream getCoverImageResource(String path,String name) throws FileNotFoundException;

}
