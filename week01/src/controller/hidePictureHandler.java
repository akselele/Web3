package controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class hidePictureHandler extends  RequestHandler{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler2 = new sessionHandler();
        handler2.handleRequest(request,response);
        Cookie c = null;
        c= new Cookie("foto","false");
        request.setAttribute("foto", false);
        response.addCookie(c);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
