package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deletePersonHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler2 = new cookieHandler();
        handler2.handleRequest(request,response);
        service.deletePerson(request.getParameter("userid"));
        RequestHandler handler =new overviewHandler();
        handler.handleRequest(request,response);
    }
}
