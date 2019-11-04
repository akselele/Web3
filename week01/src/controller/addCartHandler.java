package controller;

import domain.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class addCartHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product;
        product = service.getProduct(request.getParameter("itemid"));
        HttpSession session = request.getSession();
        if(session.getAttribute("cart") == null){
            ArrayList<Product> cart = new ArrayList<>();
            cart.add(product);
            request.getSession().setAttribute("cart", cart);
        }
        else{
            ArrayList<Product> cart = (ArrayList<Product>) session.getAttribute("cart");
            cart.add(product);
            request.getSession().setAttribute("cart", cart);
        }
        response.sendRedirect("Servlet?command=productoverview");
    }
}
