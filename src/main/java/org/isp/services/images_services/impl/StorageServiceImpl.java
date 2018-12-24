package org.isp.services.images_services.impl;

import org.isp.services.images_services.api.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public abstract class StorageServiceImpl implements StorageService {
    @Value("${static.resources.location}")
    protected String staticResourcesLocation;

    @Override
    public void store(MultipartFile file, String resourceName) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Failed to store empty file!");
            }
            file.transferTo(new File(staticResourcesLocation + File.separator + resourceName + ".jpeg"));
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public MultipartFile getResource(String resourceLocation) throws IOException {
        File file = null;
        try {
            file = new File(this.staticResourcesLocation + resourceLocation + ".jpeg");
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("Image not found");
        }
        return this.convertToMultipartFile(file);
    }

    protected MultipartFile convertToMultipartFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "image/png", IOUtils.toByteArray(fileInputStream));
        return multipartFile;
    }
}
