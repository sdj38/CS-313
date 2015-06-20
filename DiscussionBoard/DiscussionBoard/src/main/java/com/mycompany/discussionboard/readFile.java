/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discussionboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stephen
 */
@WebServlet(name = "readFile", urlPatterns = {"/readFile"})
public class readFile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        /* TODO output your page here. You may use following sample code. */
        String path = getServletContext().getRealPath("/") + "threads.txt";
        out.print(path);
        File file = new File(path);
        if(!file.exists()){
             request.getRequestDispatcher("DiscussionThreads.jsp").forward(request, response);
            
        }
        ArrayList<Board> boards = new ArrayList<Board>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String sCurrentLine;
            boolean test = true;

            while (test) {
                String name = "";
                String content = "";
                String date = "";
                for (int i = 0; i < 3; i++) {
                    sCurrentLine = br.readLine();
                   
                    if (i == 0) {
                        name = sCurrentLine;
                    } else if (i == 1) {
                        content = sCurrentLine;
                    } else {
                        date = sCurrentLine;
                    }

                }
                 
                if(date == null){
                    test = false;
                    out.print("made it!");
                    Collections.reverse(boards);
                    request.setAttribute("boards", boards);
                    request.getRequestDispatcher("DiscussionThreads.jsp").forward(request, response);
                }else{
                    
                boards.add(new Board(name, content, date));
                }
            }

        }

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

}
