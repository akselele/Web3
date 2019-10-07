package controller;


import domain.model.Person;
import domain.model.ShopService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.*;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
private ShopService shopService = new ShopService();
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
            case "hidePicture":
                destination = hidePicture(request,response);
                break;
            case "history":
                destination = history(request,response);
                break;
            default:
                destination = showHome(request,response);
                break;
        }
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private String addPerson(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errors = new ArrayList<>();
        session(request);

        Person person = new Person();
        setUserID(person, request, errors);
        setFirstName(person, request, errors);
        setLastName(person, request, errors);
        setEmail(person, request, errors);
        setPassword(person, request, errors);

        if(errors.size() == 0){
            try{
                shopService.add(person);
                return showOverview(request,response);
            } catch(IllegalArgumentException exc){
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

    private void setPassword(Person person, HttpServletRequest request, ArrayList<String> errors){
        String password =request.getParameter("password");
        try{
            person.setPassword(password);
            request.setAttribute("PasswordClass", "has-succes");
            request.setAttribute("PasswordPreviousValue", password);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("PasswordClass", "has-error");
        }
    }

    private String showSignUp(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        HttpSession session = request.getSession();
        if(session.getAttribute("time") == null){
            Map<LocalTime,String> time = new HashMap<>();
            time.put(LocalTime.now(), new Throwable().getStackTrace()[0].getMethodName());
            request.getSession().setAttribute("time", time);
        }
        else{
            Map<LocalTime,String> time = (Map<LocalTime, String>) session.getAttribute("time");
            time.put(LocalTime.now(), new Throwable().getStackTrace()[0].getMethodName());
            request.getSession().setAttribute("time", time);
        }
        return "signUp.jsp";
    }

    private String showHome(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        session(request);
        return "index.jsp";
    }

    private String showOverview(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("persons",shopService.getAll());
        session(request);
        return "personoverview.jsp";
    }

    private String history(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        HttpSession session = request.getSession();
        if(session.getAttribute("time") != null){
            Map<LocalTime,String> time = (Map<LocalTime, String>) session.getAttribute("time");
            request.setAttribute("history", time);
        }
        return "activity.jsp";
    }

    private String showPicture(HttpServletRequest request, HttpServletResponse response) {
        session(request);
        Cookie c = null;
        c= new Cookie("foto","true");
        c.setMaxAge(300);
        request.setAttribute("foto", true);
        response.addCookie(c);
        return "index.jsp";
    }

    private String hidePicture(HttpServletRequest request, HttpServletResponse response) {
        session(request);
        Cookie c = null;
        c= new Cookie("foto","false");
        request.setAttribute("foto", false);
        response.addCookie(c);
        return "index.jsp";
    }

    private Cookie getCookie(HttpServletRequest request, String key)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) if (cookie.getName().equals(key)) return cookie;
        return null;
    }

    private void cookie(HttpServletRequest request){
        Cookie c=getCookie(request,"foto");
        request.setAttribute("foto", false);
        if(c != null && c.getValue().equalsIgnoreCase("true")){
            request.setAttribute("foto", true);
        }
    }

    public void session(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("time") == null){
            Map<LocalTime,String> time = new TreeMap<>();
            time.put(LocalTime.now(), new Throwable().getStackTrace()[0].getMethodName());
            request.getSession().setAttribute("time", time);
        }
        else{
            Map<LocalTime,String> time = (Map<LocalTime, String>) session.getAttribute("time");
            time.put(LocalTime.now(), new Throwable().getStackTrace()[0].getMethodName());
            request.getSession().setAttribute("time", time);
        }
    }
}
