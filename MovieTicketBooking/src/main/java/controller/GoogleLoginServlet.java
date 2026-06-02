package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.UserDAO;
import model.User;

@WebServlet("/google-login")
public class GoogleLoginServlet extends HttpServlet {
    //thay 2 final nay bang credential tu gg cloud
    private static final String CLIENT_ID = "1050658086752-iqpu6b01r89jf8sh2p1h1ok7c3fa7lv5.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/MovieTicketBooking/google-login";
    //end point api
    private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code == null) {
            String authorizationUrl = GOOGLE_AUTH_URL + "?"
                    + "client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8)
                    + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8)
                    + "&response_type=code"
                    + "&scope=" + URLEncoder.encode("email profile", StandardCharsets.UTF_8);
            resp.sendRedirect(authorizationUrl);
            return;
        }
        //new
        // gọi API lấy Access  Token
        //test
        // --- Lấy thông tin Email và Name ---
        // ---  Kiểm tra DB và xử lý đăng nhập ---
        try {
            String accessToken = getAccessToken(code);
            if (accessToken != null) {
                String userInfoJson = getUserInfo(accessToken);
                String email = getJsonKeyValue(userInfoJson, "email");
                String name = getJsonKeyValue(userInfoJson, "name");

                // A. Kiểm tra xem email này đã tồn tại trong DB chưa
                User user = userDAO.getUserByEmail(email);

                resp.setContentType("text/html;charset=UTF-8");
                if (user != null) {
                    // THỨ 1: ĐÃ TỒN TẠI -> Lưu vào Session và chuyển hướng về trang chủ
                    req.getSession().setAttribute("user", user);

                    // Chuyển hướng về trang chủ
                    resp.sendRedirect(req.getContextPath() + "/home");
                } else {
                    // THỨ 2: CHƯA TỒN TẠI -> Tạm thời in thông báo để kiểm tra (Sẽ code tiếp ở Bước 3.2)
                    resp.getWriter().println("<h3> Kết nối thành công!</h3>");
                    resp.getWriter().println("<p>Email <b>" + email + "</b> chưa tồn tại trong DB.</p>");
                    resp.getWriter().println("<p><i>Hệ thống cần tự động tạo tài khoản ...</i></p>");
                }
            } else {
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("<h3>Lỗi: Không lấy được Access Token!</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("Lỗi hệ thống: " + e.getMessage());
        }
    }
    // Hàm gửi POST request tới Google để đổi mã "code" lấy "access_token"
    private String getAccessToken(String code) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // cấu hình  tham số gửi đi   theo chuẩn   OAuth2
        String parameters = "client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8)
                + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
                + "&grant_type=authorization_code";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GOOGLE_TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(parameters))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            // Trích xuất access_token từ JSON trả về
            return getJsonKeyValue(response.body(), "access_token");
        }
        System.err.println("Yêu cầu Token thất bại: " + response.body());
        return null;
    }

    // Hàm phụ trợ dùng Regex để tách chuỗi giá trị từ JSON phản hồi
    private String getJsonKeyValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\":\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    // Hàm gửi Get  request kèm Access Token để lấy thông tin cá nhân từ   Google
    private String getUserInfo(String accessToken) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GOOGLE_USERINFO_URL))
                .header("Authorization", "Bearer " + accessToken)  //Gửi token lên header
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();    // Trả về  chuỗi JSON chứa thông tin người   dùng
        }
        System.err.println("Yêu cầu UserInfo thất bại: " + response.body());
        return null;
    }
}
