package controller;

import domain.model.Person;
import domain.model.Product;
import domain.model.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class logInHandler extends RequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = "";
        String email = request.getParameter("email");
        ArrayList<String> errors = new ArrayList<>();
        for(Person x : service.getAll()){
            if(x.getEmail().equalsIgnoreCase(email)){
                userid = x.getUserid();
            }
        }
        String password = request.getParameter("password");
        try{
            Person person = service.getUserIfAuthenticated(userid, password);
            request.getSession().setAttribute("person", person);
            response.sendRedirect("Servlet?command=showHome&text="+person.getFirstName());
        }catch(NullPointerException | IllegalArgumentException e){
            request.setAttribute("error", "Username or password was incorrect, please try again.");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }

    }
}
