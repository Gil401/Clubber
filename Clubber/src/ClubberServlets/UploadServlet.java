package ClubberServlets;

// Import required java libraries
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

import ClubberLogic.UserData;
import Utlis.Constants;

@MultipartConfig
public class UploadServlet extends HttpServlet {
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 500 * 1024;
   private int maxMemSize = 40 * 1024;
   private File file ;

   public void init( ){
      // Get the file location where it would be stored.
      filePath = getServletContext().getInitParameter("pic"); 
   }
   public String upload(HttpServletRequest request, String i_Path, String rel_Path, UserData i_UserData )
              throws ServletException, java.io.IOException {
	   request.setCharacterEncoding("UTF-8");
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      if( !isMultipart ){
    	  return null;
      }
      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File(i_Path));
      System.out.println(i_Path);
      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );

      try{ 
      // Parse the request to get file items.
      List<FileItem> fileItems = upload.parseRequest(request);
	
      // Process the uploaded file items
      Iterator<FileItem> i = fileItems.iterator();
      i_UserData.setUserType(request.getSession().getAttribute(Constants.WHO_AM_I).toString());
      while (i.hasNext()) 
      {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            
            // Write the file
            filePath = i_Path  + "\\" + fileName;
            file = new File(filePath) ;
            fi.write(file);
            System.out.println(filePath);
       	 	
            i_UserData.setImageUrl(rel_Path + fileName);

         }
         else
         {
        	 
        	 if (fi.getFieldName().equals(Constants.FIRST_NAME)) 
     		{
        		 String value = fi.getString();
        		 i_UserData.setFirstName(fi.getString("UTF-8").trim());
     		}
        	 else if(fi.getFieldName().equals(Constants.LAST_NAME))
        	 {
        		 String value = fi.getString();
        		 i_UserData.setLastName(fi.getString("UTF-8").trim());
        	 }
        	 else if(fi.getFieldName().equals(Constants.EMAIL))
        	 {
        		 String value = fi.getString();
        		 i_UserData.setEmail(fi.getString("UTF-8").trim());
        	 }
        	 else if(fi.getFieldName().equals(Constants.GENDER))
        	 {
        		 String value = fi.getString();
        		 i_UserData.setGender(fi.getString("UTF-8").trim());
        	 }
        	 else if(fi.getFieldName().equals(Constants.PASSWORD))
        	 {
        		 String value = fi.getString();
        		 i_UserData.setPassword(fi.getString("UTF-8").trim());
        	 }
        	 else if(fi.getFieldName().equals(Constants.PHONE_NUMBER))
        	 {
        		 String value = fi.getString();
        		 i_UserData.setPhoneNumber(fi.getString("UTF-8").trim());
        	 }
        	 else if(fi.getFieldName().equals(Constants.BIRTHDATE))
        	 {
        		 String value = fi.getString();
        		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	        Date birthDate = new Date();
        	        try {
        				birthDate = sdf.parse(fi.getString("UTF-8").trim());
        			} catch (ParseException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        	        i_UserData.setBirthDate(birthDate);
        	 }
         }
      }
      }

      catch(Exception ex) {
	   System.out.println(ex);
   }
      return filePath;
   }  
   public void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        
        throw new ServletException("GET method used with " +
                getClass( ).getName( )+": POST method required.");
   } 
}