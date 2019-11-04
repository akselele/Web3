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
        switch(command == null ?"":command){
            case "productoverview":
                showProductOverview(request,response);
                break;
            case "overview":
                showOverview(request,response);
                break;
            case "signUp":
                showSignUp(request,response);
                break;
            case "addPerson":
                addPerson(request,response);
                break;
            case "showPicture":
                showPicture(request,response);
                break;
            case "hidePicture":
                hidePicture(request,response);
                break;
            case "history":
                history(request,response);
                break;
            case "showAddProduct":
                showAddProduct(request,response);
                break;
            case "addProduct":
                addProduct(request,response);
                break;
            case "deletePerson":
                deletePerson(request,response);
                break;
            case "deleteProduct":
                deleteProduct(request,response);
                break;
            case "showCheck":
                showCheck(request,response);
            case "checkPassword":
                checkPassword(request,response);
                break;
            case "successSignUp":
                successSignUp(request,response);
                break;
            case "successProduct":
                successProduct(request,response);
                break;
            default:
                showHome(request,response);
                break;
        }
    }

    private void successProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session(request);
        request.getRequestDispatcher("successPages/productSuccess.jsp").forward(request,response);
    }

    private void successSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session(request);
        request.getRequestDispatcher("successPages/signUpSuccess.jsp").forward(request,response);
    }

    private void checkPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String userid = request.getParameter("userid");
        if(shopService.getPerson(userid).isCorrectPassword(password)){
            request.setAttribute("right", "Correct Password");
            showCheck(request,response);
        }
        else{
            request.setAttribute("wrong", "Wrong password");
            showCheck(request,response);
        }
    }

    private void showCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session(request);
        String userid = request.getParameter("userid");
        request.setAttribute("userid", userid);
        request.getRequestDispatcher("checkPassword.jsp").forward(request,response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session(request);
        shopService.deleteProduct(request.getParameter("itemid"));
        showProductOverview(request,response);
    }

    private void deletePerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session(request);
        shopService.deletePerson(request.getParameter("userid"));
        showOverview(request,response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                response.sendRedirect("Servlet?command=successProduct&name="+product.getName());
            } catch(IllegalArgumentException exc){
                request.setAttribute("errors", exc.getMessage());
                request.getRequestDispatcher("addProduct.jsp").forward(request,response);
            }
        }
        else{
            request.setAttribute("errors", errorsProduct);
            request.getRequestDispatcher("addProduct.jsp").forward(request,response);
        }
    }

    private void setProductID(Product product, HttpServletRequest request, ArrayList<String> errorsProduct) {
        product.setProductId(shopService.getAllProduct().size() + 1);
    }

    private void showAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cookie(request);
        session(request);
        request.getRequestDispatcher("addProduct.jsp").forward(request,response);
    }

    private void showProductOverview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products",shopService.getAllProduct());
        session(request);
        request.getRequestDispatcher("productoverview.jsp").forward(request,response);
    }

    private void addPerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void showSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cookie(request);
        session(request);
        request.getRequestDispatcher("signUp.jsp").forward(request,response);
    }

    private void showHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cookie(request);
        session(request);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    private void showOverview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("persons",shopService.getAll());
        session(request);
        request.getRequestDispatcher("personoverview.jsp").forward(request,response);
    }

    private void history(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cookie(request);
        HttpSession session = request.getSession();
        if(session.getAttribute("time") != null){
            Map<LocalTime,String> time = (Map<LocalTime, String>) session.getAttribute("time");
            request.setAttribute("history", time);
        }
        request.getRequestDispatcher("activity.jsp").forward(request,response);
    }

    private void showPicture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session(request);
        Cookie c = null;
        c= new Cookie("foto","true");
        c.setMaxAge(300);
        request.setAttribute("foto", true);
        response.addCookie(c);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    private void hidePicture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session(request);
        Cookie c = null;
        c= new Cookie("foto","false");
        request.setAttribute("foto", false);
        response.addCookie(c);
        request.getRequestDispatcher("index.jsp").forward(request,response);
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
            time.put(LocalTime.now(), new Throwable().getStackTrace()[1].getMethodName());
            request.getSession().setAttribute("time", time);
        }
        else{
            Map<LocalTime,String> time = (Map<LocalTime, String>) session.getAttribute("time");
            time.put(LocalTime.now(), new Throwable().getStackTrace()[1].getMethodName());
            request.getSession().setAttribute("time", time);
        }
    }
}
