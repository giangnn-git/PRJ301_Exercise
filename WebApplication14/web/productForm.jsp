

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                padding: 20px;
            }

            form {
                max-width: 500px;
                margin: auto;
                background: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            div {
                margin-bottom: 15px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }

            input[type="text"],
            textarea {
                width: 100%;
                padding: 8px 10px;
                box-sizing: border-box;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            textarea {
                resize: vertical;
                height: 80px;
            }

            input[type="checkbox"] {
                margin-right: 5px;
            }

            input[type="submit"],
            input[type="reset"] {
                background-color: #007BFF;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                margin-right: 10px;
            }

            input[type="submit"]:hover,
            input[type="reset"]:hover {
                background-color: #0056b3;
            }

            a {
                display: inline-block;
                margin-top: 5px;
                padding: 5px 10px;
                font-size: 0.9em;
                border-radius: 3px;
            }

            a[style*="background-color: red"] {
                background-color: #dc3545 !important;
                color: white !important;
            }

            a[style*="background-color: green"] {
                background-color: #28a745 !important;
                color: white !important;
            }
        </style>
        ${requestScope.check1}
        <%
            if(AuthUtils.isAdmin(request)){
            String check1 = (String)request.getAttribute("check1");
            String check2 = (String)request.getAttribute("check2");
            String check3 = (String)request.getAttribute("check3");
            String check4 = (String)request.getAttribute("check4");
            String check5 = (String)request.getAttribute("check5");
            String check6 = (String)request.getAttribute("check6");
            String message = (String)request.getAttribute("message");
            
            productDTO product  = (productDTO)request.getAttribute("product");
            Boolean isEdit = (Boolean)request.getAttribute("isEdit")!=null;
            String keyword = (String)request.getAttribute("keyword");
        %>
        <div class="header">
            <a href="welcome.jsp" class="back-link">← Back to Products</a>
            <h1><%=isEdit ? "EDIT PRODUCT" : "ADD PRODUCT"%></h1>
        </div>
        <div class="form-container">
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="<%=isEdit ? "updateProduct" : "addProduct"%>"/>

                <div>
                    <label for="id"> ID* </label>
                    <input type="text" id="id" name="id" required="required"  value="<%=product!=null?product.getId():""%>" <%=isEdit ? "readonly" : ""%> />
                    <%=(check1==null)?"":check1%>
                </div>

                <div>
                    <label for="name"> Name* </label>
                    <input type="text" id="name" name="name" required="required" value="<%=product!=null?product.getName():""%>"/>
                    <%=(check2==null)?"":check2%>
                </div>

                <div>
                    <label for="image"> Image </label>
                    <input type="text" id="image" name="image" value="<%=product!=null?product.getImage():""%>"/>
                    <%=(check3==null)?"":check3%>
                </div>

                <div>
                    <label for="description"> Description </label>
                    <textarea id="description" name="description" required="required"/>
                    <%=product!=null?product.getDescription():""%>
                    </textarea>
                    <%=(check4==null)?"":check4%>
                </div>

                <div>
                    <label for="price"> Price* </label>
                    <input type="text" id="price" name="price" required="required" value="<%=product!=null?product.getPrice():""%>"/>
                    <%=(check5==null)?"":check5%>"
                </div>

                <div>
                    <label for="size"> Size </label>
                    <input type="text" id="size" name="size" value="<%=product!=null?product.getSize():""%>"/>
                    <%=(check6==null)?"":check6%>"
                </div>

                <div>
                    <label for="status"> Status </label>
                    <input type="checkbox" id="status" name="status" value="true"
                           <%=product!=null&&product.isStatus()?" checked='checked' ":""%>/>
                </div>

                <div>
                    <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                    <input type="submit" value="<%=isEdit ? "Update Product" : "Add Product"%>"/>
                    <input type="reset" value="Reset"/>    
                </div>
                <%=(message==null)?"":message%>
            </form>
        </div>
        <%
    }else {
        %>
        <%=AuthUtils.getAccessDeniedMessage("Product Form")%>
        <%
    }
        %>
    </body>
</html>
