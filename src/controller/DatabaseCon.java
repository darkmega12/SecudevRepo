package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Account;
import model.Item;
import model.Post;


public class DatabaseCon {
	private ArrayList<Account> results = new ArrayList<Account>();
	private ArrayList<Post> tempSearch = new ArrayList<Post>();
	private Account tempAccount;
	private Post tempPost;
	private Connection dbConnection;
	private String user; //PC user?
	private String password; //PC password?
	
	private Date dateJoined;
	private int totCount;
	private int totDonate;
	private int totTrans;
	
	public DatabaseCon()
	{
		this.user = "b1de8e11f2b535";
		this.password = "2c77e8b1";

	}
	
	public Connection getConnection()
	{
		return dbConnection;
	}
	
	public void open()
	{
		try{
			//For deployed Connection to deployed database
			
//			InitialContext context = new InitialContext();
//			DataSource ds = (DataSource)context.lookup("jdbc/secudev-mysql");
//			dbConnection = ds.getConnection(user, password);
			
			
			/* * For tomcat connection to deployed database * */
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_e0c49d7c967324f";
			dbConnection = DriverManager.getConnection(url, this.user, this.password);
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/secudevs18", "root", "p@ssword");
		} catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void close()
	{
		try { dbConnection.close(); }
		catch(Exception e) { System.out.println(e); }
	}
	/*******************
	 * 
	 * 		ACCOUNT
	 * 
	 ******************/
	public Account getUser(String username)
	{
		tempAccount = null;
		open();
		try
		{
			PreparedStatement getUser = dbConnection.prepareStatement("Select * from accounts where username = ?");
			getUser.setString(1, username);
			ResultSet set = getUser.executeQuery();
			while(set.next())
			{
				tempAccount = new Account(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6), set.getDate(7), set.getBoolean(8), set.getString(9), set.getDate(10), set.getInt(11), set.getInt(12), set.getInt(13));
			}
		} catch(Exception e)
		{
			System.out.println(e);
		} finally
		{
			close();
		}
		return tempAccount;
	}
	
	public HashMap<String,Account> getUsers()
	{
		HashMap<String,Account> results = new HashMap<String,Account>();
		open();
		try{
			Statement statement = dbConnection.createStatement();
			String dbQuery = "Select * FROM accounts";
							
			ResultSet set = statement.executeQuery(dbQuery);
			while(set.next())
			{
				results.put(set.getString(1), new Account(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6), set.getDate(7), set.getBoolean(8), set.getString(9), set.getDate(10), set.getInt(11), set.getInt(12), set.getInt(13)));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
		return results;
	}
	public Account register(String username, String password, String firstname, String lastname, String gender, String salutation, Date birthdate, boolean isAdmin, String aboutme, int totCount, int totDonate, int totTrans)
	{
		tempAccount = null;
		//convert java date to sql date
		java.sql.Date sqldate = new java.sql.Date(birthdate.getTime());
		java.sql.Date dateJoined = new java.sql.Date(new Date().getTime());
		if(!isDuplicate(username))
		{
			open();
			try{			
				String registerQuery = "insert into accounts (username, password, firstname, lastname,"
									 + " gender, salutation, birthdate, isadmin, aboutme, datejoined) values "
									 + "(?, ? , ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement registerAccount = dbConnection.prepareStatement(registerQuery);
				
				registerAccount.setString(1, username);
				registerAccount.setString(2, password);
				registerAccount.setString(3, firstname);
				registerAccount.setString(4, lastname);
				registerAccount.setString(5, gender);
				registerAccount.setString(6, salutation);
				registerAccount.setDate(7, sqldate);
				registerAccount.setInt(8, (isAdmin) ? 1 : 0);
				registerAccount.setString(9, aboutme);
				registerAccount.setDate(10, dateJoined);				
				registerAccount.setInt(11, totCount);
				registerAccount.setInt(12, totDonate);
				registerAccount.setInt(13, totTrans);
				registerAccount.executeUpdate();
			
			tempAccount = new Account(username, password, firstname, lastname, gender, salutation, birthdate, isAdmin, aboutme, dateJoined, totCount, totDonate, totTrans);
			}
			catch(Exception e)
			{
				System.out.print(e);
			}
			finally
			{
				close();
			}
		}
		return tempAccount;
	}
	
	public Account updateAccount(String username, String password, String firstname, String lastname, String gender, String salutation, Date birthdate, boolean isAdmin, String aboutme)
	{
		tempAccount = null;
		java.sql.Date sqldate = new java.sql.Date(new Date().getTime());
		try
		{
			sqldate = new java.sql.Date(birthdate.getTime());
		}
		catch(Exception e)
		{
			System.out.println("There's still an error with the date: "+ e);
		}
		open();
		try{
			String updateQuery = "update accounts set password = ?, firstname = ?, lastname = ?"
					           + ", gender = ?, salutation = ?, birthdate = ?, isadmin = ?, aboutme = ? where username = ?";
			PreparedStatement updateAccount = dbConnection.prepareStatement(updateQuery);
			
			updateAccount.setString(1, password);
			updateAccount.setString(2, firstname);
			updateAccount.setString(3, lastname);
			updateAccount.setString(4, gender);
			updateAccount.setString(5, salutation);
			updateAccount.setDate(6, sqldate);
			updateAccount.setInt(7, (isAdmin) ? 1 : 0);
			updateAccount.setString(8, aboutme);
			updateAccount.setString(9, username);
			updateAccount.executeUpdate();
			
			dbConnection.close();
			tempAccount = authenticate(username, password);
		}
		catch(Exception e)
		{
			System.out.println("Error in Database query:" + e);
		}
		finally
		{
			close();
		}
		return tempAccount;
	}
	public boolean isDuplicate(String username)
	{
		boolean isDuplicate = false;
		open();
		try{
			String query = "Select count(*) from accounts where username = ?";
			PreparedStatement checkDuplicate = dbConnection.prepareStatement(query);
			checkDuplicate.setString(1, username);
			ResultSet result = checkDuplicate.executeQuery();
			while(result.next())
			{
				if(result.getInt(1) > 0)
					isDuplicate = true;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
		return isDuplicate;
	}
	
	public Account authenticate(String username, String password)
	{
		open();
		try{
			String dbQuery = "Select * FROM accounts where username = ? and password = ?";
			PreparedStatement getAccount = dbConnection.prepareStatement(dbQuery);
			
			getAccount.setString(1, username);
			getAccount.setString(2, password);
							
			if(!getAccount.execute())
			{
				System.out.println("WRONGNESSS");
			}
			ResultSet set = getAccount.executeQuery();
			while(set.next())
			{
				tempAccount = new Account(set.getString("username"), set.getString("password"), set.getString("firstname"), set.getString("lastname"), set.getString("gender"), set.getString("salutation"), set.getTimestamp("birthdate"), set.getBoolean("isadmin"), set.getString("aboutme"), set.getTimestamp("datejoined"), set.getInt("totCount"), set.getInt("totDonate"), set.getInt("totTrans"));
			}
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
		finally
		{
			close();
		}
		return tempAccount;
	}
	/******
	 * 
	 * ITEM
	 * 
	 ******/
	
	public void createItem(Item n)
	{
		open();
		try
		{
			String postQuery = "insert into items (itemid, itemname, itemdescription, itemprice, itemimage, imagetype) values (?,?,?,?,?,?)";
			PreparedStatement createPost = dbConnection.prepareStatement(postQuery);
			
			createPost.setInt(1, getLastItemId() + 1);
			createPost.setString(2, n.getName());
			createPost.setString(3, n.getDescription());
			createPost.setFloat(4, n.getPrice());
			createPost.setBlob(5, n.getImage());
			createPost.setString(6, n.getImageType());

			createPost.executeUpdate();
			
			dbConnection.close();
		} catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public int getLastItemId()
	{
		open();
		try
		{
			String query = "select * from items order by itemid desc limit 1";
			Statement statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
		
			return rs.getInt(1);
			
		} catch(Exception e)
		{
			
		}
		finally
		{
			close();
		}
		return 0;
	}
	
	public ArrayList<Item> getItems()
	{
		ArrayList<Item> postList = new ArrayList<Item>();
		open();
		try
		{
			PreparedStatement getPosts;
			String query = "Select * from items order by itemid";
			getPosts = dbConnection.prepareStatement(query);
			ResultSet rs = getPosts.executeQuery();
			Item k;
			while(rs.next())
			{
				k = new Item(rs.getString(2), rs.getString(3),rs.getString(6), rs.getFloat(4));
				k.setId(rs.getInt(1));
				
				postList.add(k);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
		return postList;
	}
	
	/*******************
	 * 
	 * 		POST
	 * 
	 ******************/
	public boolean authenticatePost(int post_id, String username)
	{
		boolean isLegit = true;
		open();
		try
		{
			String query = "Select count(*) from posts where username = ? and post_id = ?";
			PreparedStatement authenticatePost = dbConnection.prepareStatement(query);
			authenticatePost.setString(1, username);
			authenticatePost.setInt(2, post_id);
			ResultSet set = authenticatePost.executeQuery();
			set.next();
			int check = set.getInt(1);
			if(check == 0)
				isLegit= false;
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			close();
		}
		return isLegit;
	}

	public Post getPost(int post_id)
	{
		open();
		try
		{
			String query = "Select * from posts where post_id = ?";
			PreparedStatement authenticatePost = dbConnection.prepareStatement(query);
			authenticatePost.setInt(1, post_id);
			ResultSet rs = authenticatePost.executeQuery();
			rs.next();
			
			Post tempPost = new Post(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6));
			return tempPost;
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
		
	public Post getLast()
	{
		tempPost = null;
		open();
		try
		{
			String query = "select * from post order by id desc limit 1";
			Statement statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
		
			tempPost = new Post(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6));
		} catch(Exception e)
		{
			
		}
		finally
		{
			close();
		}
		return tempPost;
	}
	
	public Post createPost(String username, String message, InputStream attachment, String imagename)
	{
		tempPost = null;
		open();
		try{
			
			String postQuery = "insert into posts (username, message, attachment, datecreated, datemodified, imagename) values (?,?,?,?,?,?)";
			PreparedStatement createPost = dbConnection.prepareStatement(postQuery);
			
			java.sql.Date dateToday = new java.sql.Date(new java.util.Date().getTime());
			
			createPost.setString(1, username);
			createPost.setString(2, message);
	        if (attachment != null) {
                // fetches input stream of the upload file for the blob column
                createPost.setBlob(3, attachment);
                System.out.println("Printed Blob Success!");
            }
			createPost.setDate(4, dateToday);
			createPost.setDate(5, dateToday);
			createPost.setString(6, imagename);
			createPost.executeUpdate();
			
			tempPost = getLast();
			
			updateUserPosts(username);
		} catch(Exception e)
		{
			System.out.println(e);
		} finally {
			close();
		}
		return tempPost;
	}
	
	public void modifyPost(int postid, String message, InputStream attachment, boolean isEdit)
	{
		open();
		try
		{
			PreparedStatement modifyPost;
			String query;
			if(isEdit)
			{
				query = "update posts set message = ?, attachment = ?, datemodified = ? where post_id = ?";
			}
			else
			{
				query = "update posts set deleted = false where post_id = ?";
			}
			
			modifyPost = dbConnection.prepareStatement(query);
			
			if(isEdit)
			{
				java.sql.Date dateToday = new java.sql.Date(new java.util.Date().getTime());
				modifyPost.setString(1, message);
		                // fetches input stream of the upload file for the blob column
		                modifyPost.setBlob(2, attachment);
		                System.out.println("Printed Blob Success!");

				modifyPost.setDate(3, dateToday);
				modifyPost.setInt(4, postid);
			}
			else
			{
				modifyPost.setInt(1, postid);
			}
			
			modifyPost.executeUpdate();
			dbConnection.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
	}

	public int numPosts()
	{
		int numPost = 0;
		open();
		try
		{
			Statement numPosts = dbConnection.createStatement();
			ResultSet rs = numPosts.executeQuery("Select count(*) from posts");
			rs.next();
			numPost = rs.getInt(1);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
		return numPost;
	}
	
	// Parameter batch_num: refers to page value. Default set to 1.
	public ArrayList<Post> getGlobalPosts(int page_num)
	{
		ArrayList<Post> postList = new ArrayList<Post>();
		open();
		try
		{
			int startValue = 10 * (page_num - 1); //start value for sql is 0
			PreparedStatement getPosts;
			String query = "Select * from posts where deleted = 0 order by datemodified desc, post_id desc limit ?, 10";
			getPosts = dbConnection.prepareStatement(query);
			getPosts.setInt(1, startValue);
			ResultSet rs = getPosts.executeQuery();
			while(rs.next())
			{
				postList.add(new Post(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6)));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
		return postList;
	}
	
	public ArrayList<Post> getUserPosts(String username)
	{
		ArrayList<Post> postList = new ArrayList<Post>();
		open();
		try
		{
			PreparedStatement getPosts;
			String query = "Select * from posts where username = ?";
			getPosts = dbConnection.prepareStatement(query);
			getPosts.setString(1, username);
			ResultSet rs = getPosts.executeQuery();
			while(rs.next())
			{
				postList.add(new Post(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6)));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
		return postList;
	}
	
	/**
	 * -------- SEARCH ---------
	 */
	
	public ArrayList<Post> basicSearch(String value)
	{
		tempSearch = new ArrayList<Post>();
		ArrayList<Post> searchedPosts = new ArrayList<Post>();
		open();
		try
		{
			String editValue= "%"+value+"%";
			PreparedStatement basicSearch;
			String query = "Select * from posts where message like ? deleted = 0";
			basicSearch = dbConnection.prepareStatement(query);
			basicSearch.setString(1, editValue);
			ResultSet rs = basicSearch.executeQuery();
			
			while(rs.next())
			{
				searchedPosts.add(new Post(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6)));
			}
		} catch(Exception e)
		{
			System.out.println(e);
		} finally
		{
			close();
		}
		tempSearch = searchedPosts;
		return searchedPosts;
	}
	
	public ArrayList<Post> advancedSearch(String[] criteria, String[] value, String[] setOperator)
	{
		ArrayList<Post> searchedPosts = new ArrayList<Post>();
		System.out.println("I go in");
		open();
		try 
		{
			PreparedStatement advancedSearch;
			String query = "Select * from posts where ";
			for(int i=0; i<criteria.length; i++)
			{
				System.out.println("I go in num "+i);
				if(i > 0)
				{
					if(setOperator[i-1].equals("AND"))
					{
						query += " and ";
					}
					else if(setOperator[i-1].equals("OR"))
					{
						query += " or ";
					}
					else
					{
						break;
					}
				}
				String queryCriteria = criteria[i];
				if(queryCriteria.equals("before date"))
				{
					query += "datemodified <= ? ";
				}
				else if (queryCriteria.equals("after date"))
				{
					query += "datemodified >= ? ";
				}
				else if (queryCriteria.equals("during date"))
				{
					query += "datemodified = ? ";
				}
				else if(queryCriteria.equals("message"))
				{
					query += "message like ?";
				}
				else if(queryCriteria.equals("username"))
				{
					query += "username = ?";
				}
		
			
			}
			query += ";";
			System.out.println(query);
			
			advancedSearch = dbConnection.prepareStatement(query);
			for(int i=0; i<value.length; i++)
			{
				if(value[i] != "" && !criteria[i].equals("message"))
				advancedSearch.setString(i+1, value[i]);
				else if(value[i] != "" && criteria[i].equals("message"))
				advancedSearch.setString(i+1, "%"+value[i]+"%");
			}
			ResultSet rs = advancedSearch.executeQuery();
			while(rs.next())
			{
				searchedPosts.add(new Post(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6)));
			}
		} catch(Exception e)
		{
			
		} finally
		{
			close();
		}
		tempSearch = searchedPosts;
		return searchedPosts;
	}
	
	public ArrayList<Post> getSearchedPosts(int page_num)
	{
		ArrayList<Post> postList = new ArrayList<Post>();
		open();
		try
		{
			int startValue = 10 * (page_num - 1); //start value for sql is 0
			for(int i= (page_num-1) * 10 + 1; i<tempSearch.size(); i++)
			{
				postList.add(tempSearch.get(i));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
		return postList;
	}

	/***
	 * 
	 *  IMAGE
	 * 
	 ***/
	
	public ResultSet getImage(int post_id)
	{
		ResultSet set = null;
		open();
		try {
			String postQuery = "SELECT attachment,imagename FROM posts WHERE post_id = ?";
			PreparedStatement pstmt = dbConnection.prepareStatement(postQuery);
	        pstmt.setInt(1, post_id);
	        set = pstmt.executeQuery();
		} catch(SQLException e) {
			System.out.println(e);
			return null;
		} finally {
			close();
		}
		return set;
	}
	
	/** EXPORT **/
		/*EXPORTCSV*/
	public boolean exportCSV()
	{
		open();
		
		// EXPORTING CSV
        Statement statement;
        String query;
        
        // CHANGE DIRECTORY
        String mac = "/tmp/";
        String windows = "C:/";
        // Else: C:\Program Files\MySQL\MySQL Server 5.0.
        // Else: C:\mysql
        
        // Name CSV with date
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmss");
        
        String filename = ft.format(dNow).toString();
        
        try 
        {
            statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //For comma separated file
            //Note: please change directory, /tmp/ is for Mac
            query = "SELECT username, datecreated, message " +
            		"INTO OUTFILE '" + mac +
            		filename +
            		".csv' FIELDS TERMINATED BY ',' " +
                    "FROM posts";
            
            statement.executeQuery(query);
            
            if (saveToSQL(filename) == true)
            	return true;
            else
            	return false;
        } 
        	catch(Exception e) 
        {
            e.printStackTrace();
            statement = null;
            
            return false;
        }
        	finally
    	{
    		close();
    	}
	}

	public boolean saveToSQL(String filename)
	{
		open();

        // CHANGE DIRECTORY
        String mac = "/tmp/";
        String windows = "C:/";
        
		// NOTE: Please change filepath accordingly
		String filepath = mac+filename+".csv";
		 
        try {
            String sql = "INSERT INTO backup (csv_id, csv_file, date) values (null, ?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(sql);
            InputStream inputStream = new FileInputStream(new File(filepath));
 
            statement.setBlob(1, inputStream);
            statement.setString(2, filename);
            
            int row = statement.executeUpdate();
            
            if (row > 0) 
            {
                System.out.println("A CSV file has been added to the table.");
                return true;
            }
	    } catch (SQLException ex) {
	        ex.printStackTrace();

            return false;
	    } catch (IOException ex) {
	        ex.printStackTrace();

            return false;
	    } finally {
	    	close();
	    }
        
		return false;
	}
	
	public boolean startDownload( int id, HttpServletRequest request, HttpServletResponse response, ServletContext context ) 
	{
		open();
		
		try
		{
			//Get tuple with id value
	        String sql = "SELECT * FROM backup WHERE csv_id = ?";
	        PreparedStatement statement = dbConnection.prepareStatement(sql);
	        statement.setInt(1, id);
	        
	        System.out.println("id = " + id);
	        
	        ResultSet result = statement.executeQuery();
            if (result.next()) {
                // gets file name and file blob data
                String fileName = result.getString("csv_id");
                Blob blob = result.getBlob("csv_file");
                System.out.println("id = " + result.getString("csv_id"));
                InputStream inputStream = blob.getBinaryStream();
                int fileLength = inputStream.available();
                 
                System.out.println("fileLength = " + fileLength);
             
                // sets MIME type for the file download
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {        
                    mimeType = "application/octet-stream";
                }            
                
               // set content properties and header attributes for the response
               response.setContentType(mimeType);
               response.setContentLength(fileLength);
               String headerKey = "Content-Disposition";
               String headerValue = String.format("attachment; filename=\"%s\"", fileName);
               response.setHeader(headerKey, headerValue);

               // writes the file to the client
               OutputStream outStream = response.getOutputStream();
                
               byte[] buffer = new byte[4096];
               int bytesRead = -1;
                
               while ((bytesRead = inputStream.read(buffer)) != -1) {
                   outStream.write(buffer, 0, bytesRead);
               }
                
               inputStream.close();
               outStream.close();    

           } else {
               // no file found
               response.getWriter().print("File not found for the id: " + id);  
            
   	           return false;
           }
	           return true;
		} catch (SQLException ex) {
		    ex.printStackTrace();
		
		    return false;
		} catch (IOException ex) {
	        ex.printStackTrace();
		    return false;
	    } finally {
	    	close();
	    }	
	}

	// BADGES

	public void updateUserPosts(String username)
	{
		int count = countUserPosts(username);
		open();

		try
		{
			Statement numPosts = dbConnection.createStatement();
			ResultSet rs = numPosts.executeQuery("UPDATE accounts SET totCount=" + count + " WHERE username=" + username);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
	}

	public String getTotCount() {
		
		if (totCount >= 3)
			return "Participant Badge";
		else if (totCount >= 5)
			return "Participant Badge - Chatter Badge";
		else if (totCount >= 10)
			return "Participant Badge - Chatter Badge - Socialite Badge";
		else
			return null;
	}

	public int countUserPosts(String username) 
	{
		int count = 0;

		open();

		try
		{
			Statement numPosts = dbConnection.createStatement();
			ResultSet rs = numPosts.executeQuery("SELECT count(*) FROM posts WHERE username = " + username);
			count = rs.getInt(1);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}

		return count;
	}

	public void updateUserDonations(String username)
	{
		int count = countDonations(username);
		open();

		try
		{
			Statement numPosts = dbConnection.createStatement();
			ResultSet rs = numPosts.executeQuery("UPDATE accounts SET totDonate=" + count + " WHERE username=" + username);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
	}

	public int countDonations(String username) 
	{
		int count = 0, numPost;

		open();
		
		try
		{
			Statement numPosts = dbConnection.createStatement();
			ResultSet rs = numPosts.executeQuery("SELECT sum(amount) FROM donations WHERE username = " + username);
			numPost = rs.getInt(1);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}

		return count;
	}

	public void updateUserTrans(String username)
	{
		int count = countTrans(username);
		open();

		try
		{
			Statement numPosts = dbConnection.createStatement();
			ResultSet rs = numPosts.executeQuery("UPDATE accounts SET totTrans=" + count + " WHERE username=" + username);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
	}

	public int countTrans(String username) 
	{
		int count = 0, numPost;

		open();
		
		try
		{
			Statement numPosts = dbConnection.createStatement();
			ResultSet rs = numPosts.executeQuery("SELECT sum(quantity*amount) FROM transactions WHERE username = " + username);
			numPost = rs.getInt(1);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			close();
		}
		return count;
	}

}
