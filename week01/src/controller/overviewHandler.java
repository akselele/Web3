package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class overviewHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler2 = new sessionHandler();
        handler2.handleRequest(request,response);
        request.setAttribute("persons",service.getAll());
        request.getRequestDispatcher("personoverview.jsp").forward(request,response);
    }
}
