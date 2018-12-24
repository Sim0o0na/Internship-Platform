package org.isp.services.images_services.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    void store(MultipartFile file, String resourceName);
    MultipartFile getResource(String resourceLocation) throws IOException;
}
