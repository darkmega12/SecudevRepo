package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Post;

import java.util.*;
import java.text.*;

/**
 * Servlet implementation class ExportController
 */
@WebServlet("/ExportController")
public class ExportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportController() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Backing up...");
		DatabaseCon dbConnection = new DatabaseCon();
		
		if (dbConnection.exportCSV() == true) {
			System.out.println("Backup Successful");
			response.sendRedirect("BackupSuccess.jsp");
		} else { 
			System.out.println("Backup Unsuccessful");
			response.sendRedirect("BackupFailed.jsp");
		}
	}

}
