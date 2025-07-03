package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.productDAO;
import model.productDTO;
import utils.AuthUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    productDAO pdao = new productDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";
        try {
            String action = request.getParameter("action");
            if (action.equals("addProduct")) {
                url = handleAddProduct(request, response);
            } else if (action.equals("searchProduct")) {
                url = handleSearchProduct(request, response);
            } else if (action.equals("changeProductStatus")) {
                url = handleStatusProductChanging(request, response);
            } else if (action.equals("editProduct")) {
                url = handleProductEditing(request, response);
            } else if (action.equals("updateProduct")) {
                url = handleProductUpdating(request, response);
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleAddProduct(HttpServletRequest request, HttpServletResponse response) {
        String check1 = "";
        String check2 = "";
        String check3 = "";
        String check4 = "";
        String check5 = "";
        String check6 = "";
        String message = "";
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String size = request.getParameter("size");
        String status = request.getParameter("status");

        Double valid_price = 0.0;
        try {
            valid_price = Double.parseDouble(price);
        } catch (Exception e) {
        }

        Boolean valid_status = true;
        try {
            valid_status = Boolean.parseBoolean(status);
        } catch (Exception e) {
        }

        if (pdao.isProductExists(id)) {
            check1 = "Prodcut is already exist!";
        }

        if (name.length() < 2) {
            check2 = "Your name must be longer than one character.";
        }
        if (name.matches(".*\\d.*")) {
            check2 += "<br/>Your name must not contain any numbers.";
        }
        if (name.matches(".*[^a-zA-Z ].*")) {
            check2 += "<br/>Your name must not contain any special characters.";
        }

        if (image == null) {
            check3 = "Your image doesn't find";
        }

        if (description.length() <= 0) {
            check4 = "Your description must be greather than zaero";
        }

        if (valid_price < 0) {
            check5 = "Number must be graether than zero.";
        }

        if (!size.matches("^(S|M|L|XL|XXL)$")) {
            check6 = "Your size is invalid";
        }

        if (check1.isEmpty() && check2.isEmpty() && check3.isEmpty() && check4.isEmpty() && check5.isEmpty() && check6.isEmpty()) {
            message = "Add product successfully.";
        }

        productDTO product = new productDTO(id, name, image, description, valid_price, size, valid_status);
        if (!pdao.getCreateProduct(product)) {
            check1 += "<br/>Can not add new product.";
        }

        request.setAttribute("product", product);
        request.setAttribute("check1", check1);
        request.setAttribute("check2", check2);
        request.setAttribute("check3", check3);
        request.setAttribute("check4", check4);
        request.setAttribute("check5", check5);
        request.setAttribute("check6", check6);
        request.setAttribute("massage", message);
        return "productForm.jsp";
    }

    private String handleSearchProduct(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        List<productDTO> list = pdao.getActiveProductByName(keyword);
        request.setAttribute("list", list);
        request.setAttribute("keyword", keyword);
        return "welcome.jsp";
    }

    private String handleStatusProductChanging(HttpServletRequest request, HttpServletResponse response) {
        String productID = request.getParameter("productID");
        pdao.setStatusProduct(productID, false);
        return handleSearchProduct(request, response);
    }

    private String handleProductEditing(HttpServletRequest request, HttpServletResponse response) {
        String productId = request.getParameter("productId");
        String keyword = request.getParameter("keyword");
        productDTO product = pdao.getProductByID(productId);
        if (product != null) {
            request.setAttribute("keyword", keyword);
            request.setAttribute("product", product);
            request.setAttribute("isEdit", true);
            return "productForm.jsp";
        }
        return handleSearchProduct(request, response);
    }

    private String handleProductUpdating(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String size = request.getParameter("size");
        String status = request.getParameter("status");

        double price_value = 0;
        try {
            price_value = Double.parseDouble(price);
        } catch (Exception e) {
            checkError += "Invalid price format.<br/>";
        }

        boolean status_value = status != null && status.equals("true");

        // Validation
        if (name == null || name.trim().isEmpty()) {
            checkError += "Product name is required.<br/>";
        }

        if (price_value <= 0) {
            checkError += "Price must be greater than zero.<br/>";
        }

        if (checkError.isEmpty()) {
            productDTO product = new productDTO(id, name, image, description, price_value, size, status_value);
            if (pdao.getUpdateProduct(product)) {
                message = "Product updated successfully.";
                // Redirect to welcome page after successful update
                return handleSearchProduct(request, response);
            } else {
                checkError += "Cannot update product.<br/>";
                request.setAttribute("product", product);
            }
        } else {
            // Keep form data for user to correct
            productDTO product = new productDTO(id, name, image, description, price_value, size, status_value);
            request.setAttribute("product", product);
        }

        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        request.setAttribute("isEdit", true);
        return "productForm.jsp";
    }
}
