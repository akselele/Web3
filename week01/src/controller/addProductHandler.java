package controller;

import domain.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class addProductHandler extends  RequestHandler{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new cookieHandler();
        handler.handleRequest(request,response);
        ArrayList<String> errorsProduct = new ArrayList<>();

        Product product = new Product();
        setProductID(product,request,errorsProduct);
        setName(product, request, errorsProduct);
        setPrice(product, request, errorsProduct);
        setDescription(product, request, errorsProduct);

        if(errorsProduct.size() == 0){
            try{
                service.addProduct(product);
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

    private void setProductID(Product product, HttpServletRequest request, ArrayList<String> errorsProduct) {
        product.setProductId(service.getAllProduct().size() + 1);
    }
}
