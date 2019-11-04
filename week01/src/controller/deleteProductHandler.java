package controller;

import domain.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deleteProductHandler extends RequestHandler{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler2 = new cookieHandler();
        handler2.handleRequest(request,response);
        Product product;
        product = service.getProduct(request.getParameter("itemid"));
        String name = product.getName();
        service.deleteProduct(request.getParameter("itemid"));
        response.sendRedirect("Servlet?command=successDelete&name="+name);
    }
}
