package controller;

import db.BikeRepository;
import db.DbException;
import domain.Bike;
import domain.Category;
import domain.DomainException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    private BikeRepository productDb = new BikeRepository();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String command =request.getParameter("command");
        String destination;
            switch(command == null ?"":command){
                case "overview":
                    destination =showOverview(request, response);
                    break;
                case "details":
                    destination = showDetails(request,response);
                    break;
                case "add":
                    destination = showAdd(request, response);
                    break;
                case "addProduct":
                    destination = showAddProduct(request, response);
                    break;
                case "ontvangBrief":
                    destination = ontvangBrief(request, response);
                    break;
                case "history":
                    destination = history(request, response);
                    break;
                default:
                    destination = showHome(request, response);
                    break;
            }
            request.getRequestDispatcher(destination).forward(request, response);
    }

    private String history(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        HttpSession session = request.getSession();
        if(session.getAttribute("bike") != null){
            ArrayList<Bike> bikes = (ArrayList<Bike>) session.getAttribute("bike");
            request.setAttribute("bikeHistory", bikes);
        }
        return "overview.jsp";
    }

    private String showOverview(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        request.setAttribute("products", productDb.getAll());
        return  "bikeOverview.jsp";
    }

    private String showHome(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        return  "index.jsp";
    }

    private String showDetails(HttpServletRequest request, HttpServletResponse response){
        cookie(request);
        request.setAttribute("bike", productDb.get(request.getParameter("itemid")));
        Bike detailbike = productDb.get(request.getParameter("itemid"));

        HttpSession session = request.getSession();
        if(session.getAttribute("bike") == null){
            ArrayList<Bike> bikes =new ArrayList<>();
            bikes.add(detailbike);
            request.getSession().setAttribute("bike", bikes);
        }
        else{
            ArrayList<Bike> bikes = (ArrayList<Bike>) session.getAttribute("bike");
            bikes.add(detailbike);
            request.getSession().setAttribute("bike", bikes);
        }
        return "bikeDetail.jsp";
    }

    private String showAdd(HttpServletRequest request, HttpServletResponse response) {
        cookie(request);
        return "bikeAdd.jsp";
    }

    private String showAddProduct(HttpServletRequest request, HttpServletResponse response){
        ArrayList<String> errors = new ArrayList<>();

        Bike bike = new Bike();
        setBrand(bike, request, errors);
        setDescription(bike, request, errors);
        setItemId(bike, request, errors);
        setPrice(bike, request, errors);
        setCategory(bike, request, errors);

        if(errors.size() == 0){
            try{
                productDb.add(bike);
                return showOverview(request, response);
            } catch(DbException exc){
                request.setAttribute("errors", exc.getMessage());
                return "bikeAdd.jsp";
            }
        }
        else{
            request.setAttribute("errors", errors);
            return "bikeAdd.jsp";
        }
    }

    private void setBrand(Bike bike, HttpServletRequest request, ArrayList<String> errors){
        String brand =request.getParameter("brand");
        try{
            bike.setBrand(brand);
            request.setAttribute("brandClass", "has-succes");
            request.setAttribute("brandPreviousValue", brand);
        }catch(DomainException exc){
            errors.add(exc.getMessage());
            request.setAttribute("brandClass", "has-error");
        }
    }

    private void setDescription(Bike bike, HttpServletRequest request, ArrayList<String> errors){
        String description =request.getParameter("description");
        try{
            bike.setDescription(description);
            request.setAttribute("descriptionClass", "has-succes");
            request.setAttribute("descriptionPreviousValue", description);
        }catch(DomainException exc){
            errors.add(exc.getMessage());
            request.setAttribute("descriptionClass", "has-error");
        }
    }

    private void setItemId(Bike bike, HttpServletRequest request, ArrayList<String> errors){
        String itemId =request.getParameter("itemId");
        try{
            bike.setItemId(itemId);
            request.setAttribute("itemIdClass", "has-succes");
            request.setAttribute("itemIdPreviousValue", itemId);
        }catch(DomainException exc){
            errors.add(exc.getMessage());
            request.setAttribute("itemIdClass", "has-error");
        }
    }

    private void setCategory(Bike bike, HttpServletRequest request, ArrayList<String> errors){
        String category =  (request.getParameter("category")).toUpperCase();
        Category newcat;
        if(category.trim().isEmpty() || category == null || !(category.equalsIgnoreCase("city")) || !(category.equalsIgnoreCase("road"))){
            newcat = null;
        }
        else{
            newcat = Category.valueOf(category);
        }
        try{
            bike.setCategory(newcat);
            request.setAttribute("categoryClass", "has-succes");
            request.setAttribute("categoryPreviousValue", newcat);
        }catch(IllegalArgumentException exc){
            errors.add(exc.getMessage());
            request.setAttribute("categoryClass", "has-error");
        }
    }

    private void setPrice(Bike bike, HttpServletRequest request, ArrayList<String> errors){
        String price = request.getParameter("price");
        if(price == null || price.trim().isEmpty()){
            price="-1";
        }
        try{
            double prijs = Double.parseDouble(price);
            bike.setPrice(prijs);
            request.setAttribute("prijsClass", "has-succes");
            request.setAttribute("prijsPreviousValue", prijs);
        }catch(DomainException exc){
            errors.add(exc.getMessage());
            request.setAttribute("prijsClass", "has-error");
        }
    }

    private String ontvangBrief(HttpServletRequest request, HttpServletResponse response) {
        Cookie c = null;
        if(request.getParameter("nieuwsbrief") == null){
            c = new Cookie("nieuws","false");
            request.setAttribute("nieuws", false);
        }
        else{
            c= new Cookie("nieuws","true");
            request.setAttribute("nieuws", true);
        }
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
        Cookie c=getCookie(request,"nieuws");
        request.setAttribute("nieuws", false);
        if(c != null && c.getValue().equalsIgnoreCase("true")){
            request.setAttribute("nieuws", true);
        }
    }



}
