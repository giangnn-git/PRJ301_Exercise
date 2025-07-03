

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.userDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page import="java.util.List"%>
<%@page import="model.productDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Welcome Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 40px;
            }

            h1 {
                color: #333;
            }

            a {
                color: #0066cc;
                text-decoration: none;
                font-weight: bold;
            }

            a:hover {
                text-decoration: underline;
            }

            form {
                margin: 20px 0;
            }

            input[type="text"] {
                padding: 8px;
                width: 200px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            input[type="submit"] {
                padding: 8px 12px;
                border: none;
                background-color: #007bff;
                color: white;
                border-radius: 5px;
                cursor: pointer;
                margin-left: 8px;
            }

            input[type="submit"]:hover {
                background-color: #218838;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                margin-top: 20px;
            }

            th, td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: left;
            }

            th {
                background-color: #007bff;
                color: white;
            }

            tr:hover {
                background-color: #f9f9f9;
            }

            .no-results {
                color: red;
                font-style: italic;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <%
            if (AuthUtils.isLoggedIn(request)) {
                userDTO user = (userDTO) session.getAttribute("user");
                String keyword = (String)request.getAttribute("keyword");
        %>
        <h1>Welcome, <%= user.getFullName() %></h1>
        <a href="MainController?action=logout">Logout</a>

        <form action="MainController" method="post">
            <input type="hidden" name="action" value="searchProduct">
            <input type="text" name="keyword" value="<%=keyword!=null?keyword:""%>" placeholder="Enter product name..."/>
            <input type="submit" value="Search"/>
        </form>

        <%
            List<productDTO> list = (List<productDTO>) request.getAttribute("list");
            if (list != null && list.isEmpty()) {
        %>
        <div class="no-results">No products matched your keyword.</div>
        <%
            } else if (list != null && !list.isEmpty()) {
        %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Image</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Size</th>
                    <th>Status</th>
                    <%if(AuthUtils.isAdmin(request)){%>
                        <th>Active</th>
                    <%}%>
                </tr>
            </thead>
            <tbody>
                <% for (productDTO productSearch : list) { 
                %>
                <tr>
                    <td><%= productSearch.getId() %></td>
                    <td><%= productSearch.getName() %></td>
                    <td><%= productSearch.getImage() %></td>
                    <td><%= productSearch.getDescription() %></td>
                    <td><%= productSearch.getPrice() %></td>
                    <td><%= productSearch.getSize() %></td>
                    <td><%= productSearch.isStatus()?"Active":"Invalid"%></td>
                    <% if(AuthUtils.isAdmin(request)){ %>
                            <td data-label="Action">
                                <div class="action-buttons">
                                    <form action="MainController" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="editProduct"/>
                                        <input type="hidden" name="productId" value="<%=productSearch.getId()%>"/>
                                        <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                        <input type="submit" value="Edit" class="edit-btn" />
                                    </form>
                                    <form action="MainController" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="changeProductStatus"/>
                                        <input type="hidden" name="productId" value="<%=productSearch.getId()%>"/>
                                        <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                        <input type="submit" value="Delete" class="delete-btn" 
                                               onclick="return confirm('Are you sure you want to delete this product?')"/>
                                    </form>
                                </div>
                            </td>
                    <% } %>
                </tr>
                <% } %>
            </tbody>
        </table>
        <%
            }
        } else {
        %>
        <%= AuthUtils.getAccessDeniedMessage("welcome.jsp") %><br/>
        (Or <a href="<%= AuthUtils.getLoginURL() %>">Login</a>)
        <% } %>
    </body>
</html>