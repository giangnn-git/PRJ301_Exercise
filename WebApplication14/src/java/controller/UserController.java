/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.userDAO;
import model.userDTO;
import utils.PasswordUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    
    private static final String WELCOME = "welcomer.jsp";
    private static final String LOGIN = "login.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN;
        try{
            String action = request.getParameter("action");
            if(action.equals("login")){
                url = handleLogin(request,response);
            } else if(action.equals("logout")){
                url = handleLogout(request,response);
            } else if(action.equals("Register")){
                url = handleRegister(request,response);
            }else if(action.equals("updateProfile")){
                url = handleUpdateProfile(request,response);
            }else{
                url = LOGIN;
                request.setAttribute("message", "Invalid action: " + action);
            }
        }catch(Exception e){
        }finally{
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

    private String handleLogin(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN;
        HttpSession session = request.getSession();
        String useriD = request.getParameter("strUserID");
        String password = request.getParameter("strPassword");
        password = PasswordUtils.encrypeSHA256(password);
        userDAO userDAO = new userDAO();
            if(userDAO.login(useriD, password)){
                url = "welcome.jsp";
                userDTO user = userDAO.getUserById(useriD);
                session.setAttribute("user",user);
            }
            else{
                url = "login.jsp";
                request.setAttribute("password", "userID or password is incorrected");
            }
            return url;
    }
    
    private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if(session!=null){
            userDTO user = (userDTO) session.getAttribute("user");
            if(user!=null){
                session.invalidate();
            }
        }
        return  LOGIN;
    }

    private String handleRegister(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String handleUpdateProfile(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
