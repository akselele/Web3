package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deleteProductHandler extends RequestHandler{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler2 = new cookieHandler();
        handler2.handleRequest(request,response);
        service.deleteProduct(request.getParameter("itemid"));
        RequestHandler handler =new productoverviewHandler();
        handler.handleRequest(request,response);
    }
}
