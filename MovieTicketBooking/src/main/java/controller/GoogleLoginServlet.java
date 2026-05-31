package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/google-login")
public class GoogleLoginServlet extends HttpServlet {
    //thay 2 final nay bang credential tu gg cloud
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://localhost:8080/MovieTicketBooking/google-login";
    //end point api
    private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if(code == null){
            String authorizationUrl = GOOGLE_AUTH_URL+"?"
                    +"client_id"+ URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8)
                    +"&redirect_uri="+URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8)
                    +"&response_type=code"
                    +"&scope="+URLEncoder.encode("email profile", StandardCharsets.UTF_8);
            resp.sendRedirect(authorizationUrl);
            return;

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
