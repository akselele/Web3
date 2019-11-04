package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class checkPasswordHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new showCheckHandler();
        String password = request.getParameter("password");
        String userid = request.getParameter("userid");
        if(service.getPerson(userid).isCorrectPassword(password)){
            request.setAttribute("right", "Correct Password");
            handler.handleRequest(request,response);
        }
        else{
            request.setAttribute("wrong", "Wrong password");
            handler.handleRequest(request,response);
        }
    }
}
