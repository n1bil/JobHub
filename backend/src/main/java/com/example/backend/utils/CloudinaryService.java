package com.example.backend.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(@Value("${cloud.name}") String cloudName,
                             @Value("${cloud.api_key}") String apiKey,
                             @Value("${cloud.api_secret}") String apiSecret) {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public Map<?, ?> upload(MultipartFile file) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("folder", "avatars");
        params.put("resource_type", "auto");
        params.put("public_id", null);

        return cloudinary.uploader().upload(file.getBytes(), params);
    }
}
