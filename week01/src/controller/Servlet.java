package controller;

import db.DbException;
import db.PersonDbInMemory;
import domain.model.DomainException;
import domain.model.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    private PersonDbInMemory personenDB = new PersonDbInMemory();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String command = request.getParameter("command");
        String destination;
        switch(command == null ?"":command){
            case "overview":
                destination = showOverview(request,response);
                break;
            case "signUp":
                destination = showSignUp(request,response);
                break;
            case "addPerson":
                destination = addPerson(request,response);
                break;
            case "showPicture":
                destination = showPicture(request,response);
                break;
            default:
                destination = showHome(request,response);
                break;
        }
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private String addPerson(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errors = new ArrayList<>();

        Person person = new Person();
        setUserID(person, request, errors);
        setFirstName(person, request, errors);
        setLastName(person, request, errors);
        setEmail(person, request, errors);
        setPassword(person, request, errors);

        if(errors.size() == 0){
            try{
                personenDB.add(person);
                return showHome(request,response);
            } catch(DbException exc){
                request.setAttribute("errors", exc.getMessage());
                return "signUp.jsp";
            }
        }
        else{
            request.setAttribute("errors", errors);
            return "signUp.jsp";
        }
    }

    private void setEmail(Person person, HttpServletRequest request, ArrayList<String> errors){
        String email =request.getParameter("email");
        try{
            person.setEmail(email);
            request.setAttribute("emailClass", "has-succes");
            request.setAttribute("emailPreviousValue", email);
        }catch(DomainException exc){
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
        }catch(DomainException exc){
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
        }catch(DomainException exc){
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
        }catch(DomainException exc){
            errors.add(exc.getMessage());
            request.setAttribute("LastNameClass", "has-error");
        }
    }

    private void setPassword(Person person, HttpServletRequest request, ArrayList<String> errors){
        String password =request.getParameter("password");
        try{
            person.setPassword(password);
            request.setAttribute("PasswordClass", "has-succes");
            request.setAttribute("PasswordPreviousValue", password);
        }catch(DomainException exc){
            errors.add(exc.getMessage());
            request.setAttribute("PasswordClass", "has-error");
        }
    }

    private String showSignUp(HttpServletRequest request, HttpServletResponse response) {
        return "signUp.jsp";
    }

    private String showHome(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        return "index.jsp";
    }

    private String showOverview(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("persons",personenDB.getAll());
        return "personoverview.jsp";
    }

    private String showPicture(HttpServletRequest request, HttpServletResponse response) {
        Cookie c = null;
        c= new Cookie("foto","false");
        request.setAttribute("foto", false);
        response.addCookie(c);
        return "index.jsp";
    }

    private void cookie(HttpServletRequest request){
        Cookie c=getCookie(request,"foto");
        request.setAttribute("foto", false);
        if(c != null && c.getValue().equalsIgnoreCase("true")){
            request.setAttribute("foto", true);
        }
    }

    private Cookie getCookie(HttpServletRequest request, String key)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) if (cookie.getName().equals(key)) return cookie;
        return null;
    }
}
