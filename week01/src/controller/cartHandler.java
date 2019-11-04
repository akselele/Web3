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

public class cartHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new cookieHandler();
        handler.handleRequest(request,response);
        HttpSession session = request.getSession();
        if(session.getAttribute("cart") != null){
            ArrayList<Product> cart = (ArrayList<Product>) session.getAttribute("cart");
            request.setAttribute("products", cart);
        }
        request.getRequestDispatcher("cart.jsp").forward(request,response);
    }
}
