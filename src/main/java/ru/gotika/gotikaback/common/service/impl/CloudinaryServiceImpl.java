package ru.gotika.gotikaback.common.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final Logger log = LoggerFactory.getLogger(CloudinaryServiceImpl.class);
    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary
                    .uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}
