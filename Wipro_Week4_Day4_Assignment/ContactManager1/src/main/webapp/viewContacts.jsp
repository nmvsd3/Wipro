<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.contactmanager.Contact" %>
<%@ page import="java.util.List" %>
<%
    List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Contacts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2>My Contacts</h2>
    <% if(message != null) { %>
        <div class="alert alert-success"><%=message%></div>
    <% } %>
    <a href="addContact.jsp" class="btn btn-primary mb-3">Add Contact</a>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% if(contacts != null && !contacts.isEmpty()) {
            for(Contact c : contacts) { %>
                <tr>
                    <td><%=c.getId()%></td>
                    <td><%=c.getName()%></td>
                    <td><%=c.getEmail()%></td>
                    <td><%=c.getPhone()%></td>
                    <td>
                        <a href="ContactServlet?action=edit&id=<%=c.getId()%>" class="btn btn-sm btn-warning">Edit</a>
                        <a href="ContactServlet?action=delete&id=<%=c.getId()%>" class="btn btn-sm btn-danger">Delete</a>
                    </td>
                </tr>
        <%  }
           } else { %>
            <tr><td colspan="5" class="text-center">No contacts found.</td></tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
