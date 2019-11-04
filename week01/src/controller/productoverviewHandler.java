package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class productoverviewHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new cookieHandler();
        handler.handleRequest(request,response);
        request.setAttribute("products",service.getAllProduct());
        request.getRequestDispatcher("productoverview.jsp").forward(request,response);
    }
}
