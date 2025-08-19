package com.contactmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContactServlet extends HttpServlet {
    private static List<Contact> contacts = new ArrayList<>();
    private static int idCounter = 1;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            if(name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                request.setAttribute("message", "Error: All fields are required.");
                request.getRequestDispatcher("addContact.jsp").forward(request, response);
            } else {
                contacts.add(new Contact(idCounter++, name, email, phone));
                request.setAttribute("message", "Contact added successfully!");
                request.getRequestDispatcher("viewContacts.jsp").forward(request, response);
            }
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            for(Contact c : contacts){
                if(c.getId() == id){
                    c.setName(name);
                    c.setEmail(email);
                    c.setPhone(phone);
                    break;
                }
            }
            request.setAttribute("message", "Contact updated successfully!");
            request.getRequestDispatcher("viewContacts.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            contacts.removeIf(c -> c.getId() == id);
            request.setAttribute("message", "Contact deleted successfully!");
            request.setAttribute("contacts", contacts);
            request.getRequestDispatcher("viewContacts.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Contact contactToEdit = null;
            for(Contact c : contacts){
                if(c.getId() == id){
                    contactToEdit = c;
                    break;
                }
            }
            request.setAttribute("contact", contactToEdit);
            request.getRequestDispatcher("editContact.jsp").forward(request, response);
        } else {
            request.setAttribute("contacts", contacts);
            request.getRequestDispatcher("viewContacts.jsp").forward(request, response);
        }
    }
}
