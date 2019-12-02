package controller;

import domain.model.Person;
import domain.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class logOutHandler extends RequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("person",null);
        request.getSession().invalidate();
        RequestHandler handler = new showHomeHandler();
        handler.handleRequest(request,response);
    }
}
