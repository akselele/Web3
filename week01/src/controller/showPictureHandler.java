package controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class showPictureHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler2 = new sessionHandler();
        handler2.handleRequest(request,response);
        Cookie c = null;
        c= new Cookie("foto","true");
        c.setMaxAge(300);
        request.setAttribute("foto", true);
        response.addCookie(c);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
