package controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.model.ShopService;
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ShopService service;
    private ControllerFactory factory = new ControllerFactory();

    public Servlet() {
        super();
    }

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

        service = new ShopService(properties);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.getHandler(request,response,service).handleRequest(request,response);

//        RequestHandler handler = factory.getHandler(request,response,service);
//
//        handler.setService(service);
//        String destination = handler.handleRequest(request,response);
//        RequestDispatcher view = request.getRequestDispatcher("/Servlet");
//        view.forward(request, response);
    }



}
