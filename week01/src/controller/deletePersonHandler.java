package controller;

import domain.model.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deletePersonHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler2 = new cookieHandler();
        handler2.handleRequest(request,response);
        Person person;
        person = service.getPerson(request.getParameter("userid"));
        String name = person.getFirstName();
        service.deletePerson(request.getParameter("userid"));
        response.sendRedirect("Servlet?command=successDelete&name="+name);

    }
}
