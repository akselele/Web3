package controller;

import domain.model.Person;
import domain.model.Role;

import javax.servlet.http.HttpServletRequest;

public class Utility {

    public static void checkRole(HttpServletRequest request, Role[] roles){
        boolean found = false;
        Person user = (Person) request.getSession().getAttribute("person");
        if(user != null){
            for(Role role : roles){
                if(user.getRole().equals(role)){
                    found = true;
                }
            }
            if(!found){
                throw new NotAuthorizedException();
            }
        }
    }
}
