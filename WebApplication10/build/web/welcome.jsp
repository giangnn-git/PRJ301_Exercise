
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="java.util.List" %>
<%@page import="model.ProductDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Management System</title>
    </head>
    <body>
        <%
          if(AuthUtils.isLoggedIn(request)){
             UserDTO user = AuthUtils.getCurrentUser(request);
             String keyword = (String) request.getAttribute("keyword");
        %>
        <div class="container">
            <div class="header-section">
                <h1>Welcome <%= user.getFullName() %>!</h1>
                <div class="header-actions">
                    <a href="MainController?action=logout" class="logout-btn">Logout</a>
                </div>
            </div>

            <div class="content">
                <div class="search-section">
                    <form action="MainController" method="post" class="search-form">
                        <input type="hidden" name="action" value="searchProduct"/>
                        <label class="search-label">Search product by name:</label>
                        <input type="text" name="keyword" value="<%=keyword!=null?keyword:""%>" 
                               class="search-input" placeholder="Enter product name..."/>
                        <input type="submit" value="Search" class="search-btn"/>
                    </form>
                </div>
                <% if(AuthUtils.isAdmin(request)){ %>
                <a href="productForm.jsp" class="add-product-btn">Add Product</a>
                <% } %>
                <%
                    List<ProductDTO> list = (List<ProductDTO>)request.getAttribute("list");
                    if(list!=null && list.isEmpty()){
                %>
                <div class="no-results">
                    <h3>No products found</h3>
                    <p>No products have name matching with the keyword.</p>
                </div>
                <%
                    }else if(list!=null && !list.isEmpty()){
                %>
                <table class="products-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Image</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Size</th>
                            <th>Status</th>
                                <% if(AuthUtils.isAdmin(request)){ %>
                            <th>Action</th>
                                <%}%>
                        </tr>
                    </thead>
                    <tbody>
                        <% for(ProductDTO p: list) { %>
                        <tr>
                            <td data-label="ID" class="product-id"><%=p.getId()%></td>
                            <td data-label="Name" class="product-name"><%=p.getName()%></td>
                            <td data-label="Image"><%=p.getImage()%></td>
                            <td data-label="Description"><%=p.getDescription()%></td>
                            <td data-label="Price" class="product-price">$<%=p.getPrice()%></td>
                            <td data-label="Size"><%=p.getSize()%></td>
                            <td data-label="Status">
                                <span class="product-status <%=p.isStatus() ? "status-active" : "status-inactive"%>">
                                    <%=p.isStatus() ? "Active" : "Inactive"%>
                                </span>
                            </td>
                            <% if(AuthUtils.isAdmin(request)){ %>
                            <td data-label="Action">
                                <div class="action-buttons">
                                    <!-- <a href="MainController?action=editProduct&productId=<%=p.getId()%>" class="edit-btn">Edit</a>-->
                                    <form action="MainController" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="editProduct"/>
                                        <input type="hidden" name="productId" value="<%=p.getId()%>"/>
                                        <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                        <input type="submit" value="Edit" class="edit-btn" />
                                    </form>
                                    <form action="MainController" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="changeProductStatus"/>
                                        <input type="hidden" name="productId" value="<%=p.getId()%>"/>
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
                %>
            </div>
        </div>
        <%} else { %>
        <div class="container">
            <div class="access-denied">
                <h2>Access Denied</h2>
                <p><%=AuthUtils.getAccessDeniedMessage("welcome.jsp")%></p>
                <a href="<%=AuthUtils.getLoginURL()%>" class="login-link">Login Now</a>
            </div>
        </div>
        <%}%>
    </body>
</html>