package controller;

import domain.model.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class showHomeHandler extends RequestHandler{

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new cookieHandler();
        handler.handleRequest(request,response);
        HttpSession session = request.getSession();
        if(session.getAttribute("person") != null){
            Person person = (Person) session.getAttribute("person");
            request.setAttribute("text", person.getFirstName());
        }
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
