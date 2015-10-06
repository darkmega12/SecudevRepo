package controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadController
 */
@WebServlet("/DownloadController")
public class DownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DatabaseCon dbcon = new DatabaseCon();
		int downloadid;
		
		try
	    {
			downloadid = Integer.parseInt(request.getParameter("downloadfile"));
			ServletContext context = getServletContext();
			if ( dbcon.startDownload( downloadid, request, response, context ) == true )
				System.out.println("File has been downloaded");
			else
				System.out.println("File has NOW been downloaded");
	    }
	    catch (NumberFormatException nfe)
	    {
	    	System.out.println("NumberFormatException: " + nfe.getMessage());
	    }
		
		
	}

}
