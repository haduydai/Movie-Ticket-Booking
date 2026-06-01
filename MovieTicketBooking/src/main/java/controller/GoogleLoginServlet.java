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
        try {
            String accessToken = getAccessToken(code);

            resp.setContentType("text/html;charset=UTF-8");
            if (accessToken != null) {
                resp.getWriter().println("<h3> Đổi Access Token thành công!</h3>");
                resp.getWriter().println("<p>Mã Access Token nhận được từ Google: </p>");
                resp.getWriter().println("<textarea rows='5' cols='80' readonly>" + accessToken + "</textarea>");
                resp.getWriter().println("<p><i>-- màn hình kết quả --</i></p>");
            } else {
                resp.getWriter().println("<h3>Lỗi: Không lấy được Access Token từ Google!</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}
