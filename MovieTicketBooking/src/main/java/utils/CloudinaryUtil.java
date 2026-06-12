package utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * tiện ích quản lý upload và xóa ảnh trên Cloudinary
 */
public class CloudinaryUtil {
    private static final Logger logger = Logger.getLogger(CloudinaryUtil.class.getName());
    private static Cloudinary cloudinary;

    static {
        try {
            // Đọc thông số API key từ file application.properties
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

    /**
     * Tải ảnh lên Cloudinary
     * @param inputStream Luồng dữ liệu của file ảnh
     * @param fileName Tên file ảnh
     * @param folder Thư mục lưu trên Cloudinary (ví dụ: "avatars", "movies")
     * @param publicId Public ID của ảnh cũ cần ghi đè (nếu có)
     * @return Map chứa thông tin kết quả upload (bao gồm url và public_id mới)
     * @throws IOException Nếu xảy ra lỗi upload
     */
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

    /**
     * Xóa ảnh trên Cloudinary
     * @param publicId Public ID của ảnh cần xóa
     * @return true nếu xóa thành công
     */
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

    /**
     * Kiểm tra Cloudinary đã sẵn sàng chưa
     */
    public static boolean isConfigured() {
        return cloudinary != null;
    }
}