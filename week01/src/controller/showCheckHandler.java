package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class showCheckHandler extends  RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new cookieHandler();
        handler.handleRequest(request,response);
        String userid = request.getParameter("userid");
        request.setAttribute("userid", userid);
        request.getRequestDispatcher("checkPassword.jsp").forward(request,response);
    }
}
