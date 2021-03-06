/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.JavaMailUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thanh Tuyen
 */
public class ForgotPw extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Forgot password");

        String control = request.getParameter("control");
        if (control != null) {
            String username = request.getParameter("username");
            System.out.println("username : " + username);
            HttpSession session = request.getSession();

            String email;
            try {
                email = DB.lib.DB_GetEmail(username);
                if (!email.isEmpty()) {

                    session.setAttribute("username", username);
                    new JavaMailUtil(email);
                    int n = JavaMailUtil.GetCode();

                    System.out.println("Code: " + n);

                    RequestDispatcher dispatcher;
                    dispatcher = getServletContext().getRequestDispatcher(lib.Web.SEND_CODE);
                    dispatcher.forward(request, response);
                    return;
                }
            } catch (Exception ex) {
                System.out.print("have Error value: ");
                System.out.println(ex.getMessage());
                System.out.print(" at ---->");
            }
        }
        RequestDispatcher dispatcher;
        dispatcher = getServletContext().getRequestDispatcher(lib.Web.FORGOT_PASSWORD);
        dispatcher.forward(request, response);
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

}
