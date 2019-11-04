package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class showHomeHandler extends RequestHandler{

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new cookieHandler();
        handler.handleRequest(request,response);
        RequestHandler handler2 = new sessionHandler();
        handler2.handleRequest(request,response);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
