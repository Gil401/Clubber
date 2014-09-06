package ClubberServlets;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import ClubberLogic.LineData;
import Utlis.Constants;
import Utlis.IdWithName;

/**
 * Servlet implementation class UploadLineImageServlet
 */
@WebServlet("/UploadLineImageServlet")
public class UploadLineImageServlet  extends HttpServlet {
	   
	   private boolean isMultipart;
	   private String filePath;
	   private int maxFileSize = 500 * 1024;
	   private int maxMemSize = 40 * 1024;
	   private File file ;
	   private String businessName;

	   public void init( ){
	      // Get the file location where it would be stored.
	      filePath = getServletContext().getInitParameter("pic"); 
	   }
	   public String upload(HttpServletRequest request, String i_Path, String rel_Path, LineData i_LineData )
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
		       	 	
		            i_LineData.setImageUrl(rel_Path + fileName);
		         }
		         else
		         {
		        	 if (fi.getFieldName().equals(Constants.LINE_NAME_EDT)) 
		     		{
		        		 i_LineData.setM_LineName(fi.getString("UTF-8").trim());
		     		}
		        	 else if(fi.getFieldName().equals(Constants.LINE_BUSINEES_NAME))
		        	 {
		        		 businessName = fi.getString("UTF-8").trim();
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.LINE_BUSINEES_ID))
		        	 {
		        		 Integer id = new Integer(fi.getString("UTF-8").trim());
		        		 i_LineData.setBusiness(new IdWithName(id, businessName));
		        	 }		        	 
		        	 else if(fi.getFieldName().equals(Constants.DAY_EDT))
		        	 {
		        		 Integer day = new Integer(fi.getString("UTF-8").trim());
		        		 i_LineData.setM_DayInWeek(day);
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.START_DATE_EDT))
		        	 {
				        String dateStr = fi.getString("UTF-8").trim();
				        
				        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				        Date date = null;
						try {
							date = df.parse(dateStr);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
				        long dateParam = date.getTime();
				        i_LineData.setStartDate(dateParam);
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.END_DATE_EDT))
		        	 {
					        String dateStr = fi.getString("UTF-8").trim();
					        
					        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					        Date date = null;
							try {
								date = df.parse(dateStr);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
					        long dateParam = date.getTime();
					        i_LineData.setEndDate(dateParam);
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.MIN_AGE_EDT))
		        	 {
		        		 Integer minAge = new Integer(fi.getString("UTF-8").trim());
		        		 i_LineData.setMinAge(minAge);
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.DESCRIPTION_EDT))
		        	 {
		        		 i_LineData.setDescription(fi.getString("UTF-8").trim());
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.ENTRANCE_EDT))
		        	 {
		        		 i_LineData.setEntranceFee(fi.getString("UTF-8").trim());
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.DJ_EDT))
		        	 {
		        		 i_LineData.setEntranceFee(fi.getString("UTF-8").trim());
		        	 }
		        	 else if(fi.getFieldName().equals(Constants.MUSIC_STYLE_LIST))
		        	 {
		        			String musicStyles[]= fi.getString("UTF-8").split("&");
		        			if (musicStyles.length > 0 && !musicStyles[0].equals("")) {
		        				for (String item : musicStyles) {
		        					i_LineData.getMusicStylesIds().add(new IdWithName(Integer.parseInt(item), null));
		        				}
		        			}

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