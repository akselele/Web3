package controller;

import db.DbException;
import domain.model.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class addPersonHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new cookieHandler();
        handler.handleRequest(request,response);
        ArrayList<String> errors = new ArrayList<>();

        Person person = new Person();
        setUserID(person, request, errors);
        setFirstName(person, request, errors);
        setLastName(person, request, errors);
        setEmail(person, request, errors);
        setPassword(person, request, errors);

        if(errors.size() == 0){
            try{
                service.add(person);
                response.sendRedirect("Servlet?command=successSignUp&firstname="+person.getFirstName());
            } catch(IllegalArgumentException exc){
                request.setAttribute("errors", exc.getMessage());
                request.getRequestDispatcher("signUp.jsp").forward(request,response);
            }
        }
        else{
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("signUp.jsp").forward(request,response);
        }
    }

    private void setEmail(Person person, HttpServletRequest request, ArrayList<String> errors){
        String email =request.getParameter("email");
        try{
            person.setEmail(email);
            request.setAttribute("emailClass", "has-succes");
            request.setAttribute("emailPreviousValue", email);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("emailClass", "has-error");
        }
    }

    private void setUserID(Person person, HttpServletRequest request, ArrayList<String> errors){
        String userID =request.getParameter("userid");
        try{
            person.setUserid(userID);
            request.setAttribute("UserIDClass", "has-succes");
            request.setAttribute("UserIDPreviousValue", userID);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("UserIDClass", "has-error");
        }
    }

    private void setFirstName(Person person, HttpServletRequest request, ArrayList<String> errors){
        String firstName =request.getParameter("firstName");
        try{
            person.setFirstName(firstName);
            request.setAttribute("FirstNameClass", "has-succes");
            request.setAttribute("FirstNamePreviousValue", firstName);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("FirstNameClass", "has-error");
        }
    }

    private void setLastName(Person person, HttpServletRequest request, ArrayList<String> errors){
        String lastName =request.getParameter("lastName");
        try{
            person.setLastName(lastName);
            request.setAttribute("LastNameClass", "has-succes");
            request.setAttribute("LastNamePreviousValue", lastName);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("LastNameClass", "has-error");
        }
    }

    private void setPassword(Person person, HttpServletRequest request, ArrayList<String> errors) {
        String password =request.getParameter("password");
        try{
            person.setHashedPassword(password);
            request.setAttribute("PasswordClass", "has-succes");
            request.setAttribute("PasswordPreviousValue", password);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("PasswordClass", "has-error");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new DbException(e.getMessage(),e);
        }
    }

}
