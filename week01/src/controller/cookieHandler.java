package controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class cookieHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie c=getCookie(request,"foto");
        request.setAttribute("foto", false);
        if(c != null && c.getValue().equalsIgnoreCase("true")){
            request.setAttribute("foto", true);
        }
    }

    private Cookie getCookie(HttpServletRequest request, String key)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) if (cookie.getName().equals(key)) return cookie;
        return null;
    }
}
