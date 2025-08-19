<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.contactmanager.Contact" %>
<%
    Contact contact = (Contact) request.getAttribute("contact");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Contact</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2>Edit Contact</h2>
    <form action="ContactServlet" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" value="<%=contact.getId()%>">
        <div class="mb-3">
            <label class="form-label">Name</label>
            <input type="text" class="form-control" name="name" value="<%=contact.getName()%>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" class="form-control" name="email" value="<%=contact.getEmail()%>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Phone</label>
            <input type="text" class="form-control" name="phone" value="<%=contact.getPhone()%>" required>
        </div>
        <button type="submit" class="btn btn-success">Update Contact</button>
        <a href="ContactServlet" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>