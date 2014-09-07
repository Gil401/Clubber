package ClubberServlets;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ClubberLogic.BusinessData;
import Utlis.Constants;
import Utlis.IdWithName;

/**
 * Servlet implementation class UploadBusinessImageServlet
 */
@WebServlet("/UploadBusinessImageServlet")
public class UploadBusinessImageServlet  extends HttpServlet {
	   
	   private boolean isMultipart;
	   private String filePath;
	   private int maxFileSize = 5000 * 1024;
	   private int maxMemSize = 40 * 1024;
	   private File file ;
	   private String areaName;
	   private String cityName;	   
	   private String streetName;
	   private String businessTypeName;
	   private Integer areaId;
	   private Integer cityId;	   
	   private Integer streetId;
	   private Integer businessTypeId;
	   

	   public void init( ){
	      // Get the file location where it would be stored.
	      filePath = getServletContext().getInitParameter("pic"); 
	   }
	   public String upload(HttpServletRequest request, String i_Path, String rel_Path, BusinessData i_BusinessData )
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
		      		      
		      while (i.hasNext()) 
		      {
		         FileItem fi = (FileItem)i.next();
		         if ( !fi.isFormField () &&  !fi.getName().isEmpty())	
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
		       	 	
		            i_BusinessData.setImageUrl(rel_Path + fileName);
		         }
		         else
		         {
		        	 if (fi.getFieldName().equals(Constants.BUSINESS_NAME)) 
		     		{
		        		 i_BusinessData.setM_Name(fi.getString("UTF-8").trim());
		     		}
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_AREA_NAME))
		        	 {
		        		 areaName = fi.getString("UTF-8").trim();
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_AREA_ID))
		        	 {
		        		 areaId = new Integer(fi.getString("UTF-8").trim());
		        	 }		        	 
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_CITY_NAME))
		        	 {
		        		 cityName = fi.getString("UTF-8").trim();		        	 
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_CITY_ID))
		        	 {
		        		 cityId = new Integer(fi.getString("UTF-8").trim());
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_STREET_NAME))
		        	 {
		        		 streetName = fi.getString("UTF-8").trim();		        	 
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_STREET_ID))
		        	 {
		        		 streetId = new Integer(fi.getString("UTF-8").trim());
		        	 }		        	 
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_HOME_NUMBER))
		        	 {
		        		 Integer homeNumber = new Integer(fi.getString("UTF-8").trim());
		        		 i_BusinessData.setM_HouseNumber(homeNumber);

		        	 }
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_PHONE_NUMBER))
		        	 {
		        		 i_BusinessData.setM_PhoneNumber(fi.getString("UTF-8").trim());
		        	 }		        	 
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_TYPE_NAME))
		        	 {
		        		 businessTypeName = fi.getString("UTF-8").trim();		        	 
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_TYPE_ID))
		        	 {
		        		 businessTypeId = new Integer(fi.getString("UTF-8").trim());
		        	 }
		        	 
		        	 else if(fi.getFieldName().equals(Constants.BUSINESS_DESCRIPTION))
		        	 {
		        		 i_BusinessData.setM_Description(fi.getString("UTF-8").trim());
		        	 }
		         }
		      }
     		 
		      i_BusinessData.setM_AreaId(new IdWithName(areaId, areaName));
     		  i_BusinessData.setM_CityId(new IdWithName(cityId, cityName));
    		  i_BusinessData.setM_StreetId(new IdWithName(streetId, streetName));
    		  i_BusinessData.setM_BusinessTypeId(new IdWithName(businessTypeId, businessTypeName));
		      
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
