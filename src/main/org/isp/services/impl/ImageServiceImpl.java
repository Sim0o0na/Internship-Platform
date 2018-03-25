package org.isp.services.impl;

import org.isp.services.api.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageServiceImpl extends StorageServiceImpl implements ImageService {
    public MultipartFile getDefaultAccountAvatar() throws IOException {
        File file = new File(this.staticResourcesLocation + "profile-pictures/account.png");
        return this.convertToMultipartFile(file);
    }
}
