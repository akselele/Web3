package controller;


import db.DbException;
import domain.model.Person;
import domain.model.Product;
import domain.model.ShopService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.*;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
private ShopService shopService;

    public Servlet(){
      super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context =getServletContext();

        Properties properties =new Properties();
        properties.setProperty("user",context.getInitParameter("user"));
        properties.setProperty("password",context.getInitParameter("password"));
        properties.setProperty("ssl",context.getInitParameter("ssl"));
        properties.setProperty("sslfactory",context.getInitParameter("sslfactory"));
        properties.setProperty("sslmode",context.getInitParameter("sslmode"));
        properties.setProperty("url",context.getInitParameter("url"));

        shopService = new ShopService(properties);
    }

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
            case "productoverview":
                destination= showProductOverview(request,response);
                break;
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
            case "showAddProduct":
                destination = showAddProduct(request,response);
                break;
            case "addProduct":
                destination = addProduct(request,response);
                break;
            default:
                destination = showHome(request,response);
                break;
        }
        request.getRequestDispatcher(destination).forward(request,response);
    }

    private String addProduct(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errorsProduct = new ArrayList<>();
        session(request);

        Product product = new Product();
        setProductID(product,request,errorsProduct);
        setName(product, request, errorsProduct);
        setPrice(product, request, errorsProduct);
        setDescription(product, request, errorsProduct);

        if(errorsProduct.size() == 0){
            try{
                shopService.addProduct(product);
                return showProductOverview(request,response);
            } catch(IllegalArgumentException exc){
                request.setAttribute("errors", exc.getMessage());
                return "addProduct.jsp";
            }
        }
        else{
            request.setAttribute("errors", errorsProduct);
            return "addProduct.jsp";
        }
    }

    private void setProductID(Product product, HttpServletRequest request, ArrayList<String> errorsProduct) {
        product.setProductId(shopService.getAllProduct().size() + 1);
    }

    private String showAddProduct(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        session(request);
        return "addProduct.jsp";
    }

    private String showProductOverview(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("products",shopService.getAllProduct());
        session(request);
        return "productoverview.jsp";
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

    private void setName(Product product, HttpServletRequest request, ArrayList<String> errors){
        String name =request.getParameter("name");
        try{
            product.setName(name);
            request.setAttribute("nameClass", "has-succes");
            request.setAttribute("productNamePreviousValue", name);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("nameClass", "has-error");
        }
    }

    private void setPrice(Product product, HttpServletRequest request, ArrayList<String> errors){
        String price =request.getParameter("price");
        try{
            product.setPrice(Double.parseDouble(price));
            request.setAttribute("priceClass", "has-succes");
            request.setAttribute("pricePreviousValue", price);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("priceClass", "has-error");
        }
    }

    private void setDescription(Product product, HttpServletRequest request, ArrayList<String> errors){
        String description =request.getParameter("description");
        try{
            product.setDescription(description);
            request.setAttribute("descriptionClass", "has-succes");
            request.setAttribute("descriptionPreviousValue", description);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("descriptionClass", "has-error");
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

    private String showSignUp(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        session(request);
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
