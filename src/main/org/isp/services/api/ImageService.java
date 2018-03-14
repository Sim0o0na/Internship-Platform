package org.isp.services.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService extends StorageService {
    MultipartFile getDefaultAccountAvatar() throws IOException;
}
