package utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CloudinaryUtil {
    private static final Logger logger = Logger.getLogger(CloudinaryUtil.class.getName());
    private static Cloudinary cloudinary;

    static {
        try {

            String cloudName = ApplicationLoader.get("cloud_name");
            String apiKey = ApplicationLoader.get("cloud_api_key");
            String apiSecret = ApplicationLoader.get("cloud_api_secret");

            if (cloudName != null && !cloudName.trim().isEmpty() &&
                    apiKey != null && !apiKey.trim().isEmpty() &&
                    apiSecret != null && !apiSecret.trim().isEmpty()) {
                cloudinary = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret
                ));
            } else {
                logger.warning("Cloudinary configuration is incomplete");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize Cloudinary", e);
        }
    }

    
    public static Map<?, ?> uploadImage(InputStream inputStream, String fileName, String folder, String publicId) throws IOException {
        if (cloudinary == null) {
            throw new IOException("Cloudinary is not configured");
        }

        try {
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                    "resource_type", "auto",
                    "folder", folder != null ? folder : "avatars"
            );

            if (publicId != null && !publicId.trim().isEmpty()) {
                uploadParams.put("public_id", publicId);
            }

            return cloudinary.uploader().upload(inputStream, uploadParams);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to upload image to Cloudinary: " + fileName, e);
            throw new IOException("Failed to upload image: " + e.getMessage(), e);
        }
    }

    
    public static boolean deleteImage(String publicId) {
        if (cloudinary == null || publicId == null || publicId.trim().isEmpty()) {
            return false;
        }

        try {
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String resultValue = (String) result.get("result");
            return "ok".equals(resultValue);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to delete image from Cloudinary: " + publicId, e);
            return false;
        }
    }

    
    public static boolean isConfigured() {
        return cloudinary != null;
    }
}