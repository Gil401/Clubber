package ClubberLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.tomcat.jni.Library;

import Utlis.AuctionManagementData;
import Utlis.AuctionStatusIds;
import Utlis.IdWithName;
import Utlis.LineManagementData;
import Utlis.MyLinesData;
import Utlis.NewAuctionData;
import Utlis.NewOfferData;
import Utlis.OfferPerAuction;
import Utlis.OfferStatusIds;

public class DAL {
	private static Connection conn;
	private static Statement stmt;

	public DAL() {
	}

	private static void connectToDBServer() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/clubber_db?useUnicode=true&characterEncoding=UTF8",
							"root", "a");
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			disconnectFromDBServer();
		}
	}

	private static void disconnectFromDBServer() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List<AuctionData> getAllMyAuctionsData(Integer loggedOnUserID)
			throws ParseException {
		List<AuctionData> data = new LinkedList<AuctionData>();

		connectToDBServer();

		try {

			ResultSet rs = stmt.executeQuery("select auction.*, event_type.Name as event_type_name, COALESCE(counter2,0) as counter"
					+" from event_type,auction left join (select auc.id, count(offers.id) as counter2"
							+" from auction auc, offers"
							+" where offers.auction_id = auc.id" 
							+" group by auc.id) c on c.id = auction.id"
							+" where auction.Event_Type = event_type.id and auction.Created_By="+loggedOnUserID
							+" order by auction.Event_Date desc");

			while (rs.next()) {
				AuctionData auction = new AuctionData(); 
				auction.setEventDate(rs.getLong("Event_Date"));
				auction.setDescription(rs.getString("Description"));
				auction.setEventType(new IdWithName(
						rs.getInt("Event_Type"), rs
								.getString("event_type_name")));
				auction.setId(rs.getInt("id"));
				auction.setOfferNumber(rs.getInt("counter"));
				auction.setCreatedBy(new IdWithName(rs.getInt("Created_By"), null));
				data.add(auction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return data;
	}

	public static AuctionData getReviewedAuctionData(Integer currAuctionID) {
		AuctionData auction = new AuctionData();
		connectToDBServer();

		try {

			/* load auction data */
			ResultSet rs = stmt
					.executeQuery("select auc.*, event_type.Name as event_type_name, auction_status.displayName,areas.Name,businesses.Name "
							+ "from auction auc LEFT JOIN businesses ON businesses.id = auc.Certain_Business "
							+ "LEFT JOIN event_type ON event_type.id = auc.Event_Type "
							+ "LEFT JOIN areas ON areas.id = auc.area "
							+ "LEFT JOIN auction_status ON auction_status.id = auc.Auction_Status "
							+ "where auc.id=" + currAuctionID + ";");

			while (rs.next()) {
				auction.setEventDate(rs.getLong("Event_Date"));
				auction.setDescription(rs.getString("Description"));
				auction.setEventType(new IdWithName(
						rs.getInt("auc.Event_Type"), rs
								.getString("event_type_name")));
				auction.setId(rs.getInt("id"));
				auction.setAuctionStatus(new IdWithName(rs
						.getInt("auc.Auction_Status"), rs
						.getString("auction_status.displayName")));
				auction.setUserDetailsExpose(rs.getInt("Details_To_Display"));
				auction.setCreatedBy(new IdWithName(rs.getInt("Created_By"),
						null));
				String certainBusiness = rs.getString("businesses.Name");

				if (certainBusiness != null) {
					auction.setCertainBusiness(new IdWithName(rs
							.getInt("auc.Certain_Business"), certainBusiness));
				}
				auction.setDateFlexible(rs.getBoolean("Is_Date_Flexible"));
				auction.setExceptionsDescription(rs
						.getString("Exceptions_Description"));
				auction.setGuestesQuantiny(rs.getInt("Guestes_Quantiny"));
				auction.setSmoking(rs.getBoolean("Smoking"));
				auction.setMinAge(rs.getInt("Minimum_Age"));
				auction.setArea(new IdWithName(rs.getInt("auc.area"), rs
						.getString("areas.Name")));
			}

			auction.setMusicStyle(GetIdAndNameData("Select music_style.* from auction_music_style,music_style where auction_music_style.auction_id = "
					+ currAuctionID
					+ " and music_style.id = auction_music_style.music_style_id;"));
			auction.setBusinessType(GetIdAndNameData("Select * from auction_business_type,business_type where auction_business_type.auction_id = "
					+ currAuctionID
					+ " and business_type.id = auction_business_type.business_type_id;"));
			auction.setSittsType(GetIdAndNameData("Select sitts_type.* from auction_sits_type,sitts_type where auction_sits_type.auction_id = "
					+ currAuctionID
					+ "  and sitts_type.id = auction_sits_type.sits_id;"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return auction;
	}

	public static OfferData getReviewedOfferData(Integer currOfferID,
			Integer currAuctionID) {
		OfferData offer = new OfferData();
		connectToDBServer();

		try {

			/* load offer data */
			ResultSet rs = stmt
					.executeQuery("select * from offer_status, line, offers off, users, businesses where off.Line_id = line.id and users.id= off.Pr_id and businesses.id= line.Business_id and offer_status.id=off.Offer_Status and off.ID="
							+ currOfferID);
			while (rs.next()) {
				offer.setOfferStatusId(new IdWithName(rs
						.getInt("offer_status.id"), rs
						.getString("offer_Status.displayName")));
				offer.setId(rs.getInt("off.id"));
				offer.setDescription(rs.getString("off.Description"));
				offer.setExpirationDate(rs.getLong("off.Expiration_Date"));
				offer.setAuctionId(currAuctionID);
				offer.setLineId(new IdWithName(rs.getInt("off.Line_id"), rs
						.getString("line.Name")));
				offer.setMaxArrivalHourAsLong(rs
						.getLong("off.Max_Arrival_Hour"));
				offer.setPrId(new IdWithName(rs.getInt("off.Pr_id"), rs
						.getString("users.First_Name")
						+ " "
						+ rs.getString("users.Last_Name")));
				offer.setSubmitDate(rs.getLong("off.Created_On"));
				offer.setLineBusinessId(new IdWithName(rs
						.getInt("businesses.id"), rs
						.getString("businesses.Name")));
			}

			/* load offer treats */
			rs = stmt
					.executeQuery("select * from offer_treats ot, treats where treats.id= ot.treat_id and ot.Offer_id="
							+ currOfferID);

			while (rs.next()) {
				offer.getOfferTreats().add(
						new IdWithName(rs.getInt("treats.id"), rs
								.getString("treats.Name")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return offer;
	}

	public static List <UserMessagesData> getAllMassages(Integer currAuctionID, Integer prID)
	{
		List <UserMessagesData> messages= new LinkedList<UserMessagesData>();
		connectToDBServer();
		
		try {
			
			/*load messages*/
			ResultSet rs = stmt.executeQuery("select * from messages, users where messages.From_User_id=users.id and Auction_id="+currAuctionID+" and ('"+prID+"' = messages.From_User_id or messages.To_User_id= '"+prID+"') order by Created_On;");
			while (rs.next())
			{
				UserMessagesData message= new UserMessagesData();
				message.setAuctionId(currAuctionID);
				message.setCreatedOn(rs.getTimestamp("Created_On"));
				message.setDescription(rs.getString("Description"));
				message.setFromUserId(new IdWithName(rs.getInt("From_User_id"),rs.getString("users.First_Name")+" "+rs.getString("users.Last_Name")));
				message.setId(rs.getInt("id"));
				message.setToUserId(rs.getInt("To_User_id"));
				
				messages.add(message);
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			disconnectFromDBServer();
		}
		
		return messages;
		
	}
	public static boolean addNewMessage(String description, Integer auctionId, Integer fromId, Integer toId) throws Exception
	{
		connectToDBServer();
		
		try {
			//created on = now :
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			
			//create new message in db:
			String sqlMessageInsertion= String.format("insert into messages values(%d,%d,%d,%d,'%s','%s')",
					null,fromId,toId,auctionId,currentTimestamp,description);
			
			stmt.executeUpdate(sqlMessageInsertion);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally
		{
			disconnectFromDBServer();
		}
	}
	

	public static AuctionManagementData getAllAuctionOffersData(
			Integer currAuctionID) throws ParseException {
		List<OfferData> offers = new LinkedList<OfferData>();
		AuctionData auction = new AuctionData();
		AuctionManagementData data;
		connectToDBServer();

		try {

			ResultSet rs = stmt
					.executeQuery("select * from offer_status, line ,offers off, users where off.Line_id = line.id and users.id= off.Pr_id and off.Auction_ID="
							+ currAuctionID
							+ " and off.Offer_Status = offer_status.id order by off.id");
			while (rs.next()) {
				OfferData offer = new OfferData();
				offer.setOfferStatusId(new IdWithName(rs
						.getInt("off.Offer_Status"), rs
						.getString("offer_Status.displayName")));
				offer.setId(rs.getInt("off.id"));
				offer.setAuctionId(currAuctionID);
				offer.setDescription(rs.getString("off.Description"));
				offer.setExpirationDate(rs.getLong("off.Expiration_Date"));
				offer.setId(rs.getInt("off.id"));
				offer.setLineId(new IdWithName(rs.getInt("off.Line_id"), rs
						.getString("line.Name")));
				offer.setMaxArrivalHourAsLong(rs
						.getLong("off.Max_Arrival_Hour"));
				offer.setPrId(new IdWithName(rs.getInt("off.Pr_id"), rs
						.getString("users.First_Name")
						+ " "
						+ rs.getString("users.Last_Name")));
				offer.setSubmitDate(rs.getLong("off.Created_On"));
				offer.setLinePhotoURL(rs.getString("line.Line_Photo"));
				offers.add(offer);
			}

			auction = getReviewedAuctionData(currAuctionID);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		data = new AuctionManagementData(offers, auction);
		return data;
	}

	public static NewAuctionData getAllNewAuctionData() {
		connectToDBServer();
		NewAuctionData data = new NewAuctionData();
		String query;

		query = "Select * from Event_Type;";
		data.setEventTypes(GetIdAndNameData(query));

		query = "Select * from Music_Style;";
		data.setMusicStyles(GetIdAndNameData(query));

		query = "Select * from areas;";
		data.setArea(GetIdAndNameData(query));

		query = "Select * from Business_Type;";
		data.setBusinessType(GetIdAndNameData(query));

		query = "Select * from businesses;";
		data.setCertainBusiness(GetIdAndNameData(query));

		query = "Select * from Sitts_Type;";
		data.setSittsType(GetIdAndNameData(query));

		disconnectFromDBServer();

		return data;
	}

	public static NewOfferData getAllNewOfferData(Integer i_PRid) {
		connectToDBServer();
		NewOfferData data = new NewOfferData();
		List<IdWithName> lines = new LinkedList<IdWithName>();
		String query;

		query = "Select * from line where (id in (select line_id from line_prs Lp where Lp.PR_id = " + i_PRid + ")) OR (Pr_id=" + i_PRid + ")";
		
		data.setLines(GetIdAndNameData(query));

		query = "Select * from treats;";
		data.setTreats(GetIdAndNameData(query));

		disconnectFromDBServer();
		
		return data;
	}
		

	public static LinkedList<IdWithName> GetIdAndNameData(String query) {

		LinkedList<IdWithName> data = new LinkedList<IdWithName>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				data.add(new IdWithName(rs.getInt("Id"), rs.getString("Name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	public static Integer addNewAuction(AuctionData auction) throws Exception {
		connectToDBServer();

		try {
			// create new auction in db:
			String sqlAuctionInsertion = String
					.format("insert into auction values(%d,%d,'%s','%s',%d,'%s','%s',%d,%d,'%s',%d,'%s',%d,%d, %s)",
							null, auction.getMinAge(),
							auction.getExceptionsDescription(),
							auction.getGuestesQuantiny(),
							getValidID(auction.getEventType()),
							auction.getEventDate(),
							auction.getIsDateFlexible(),
							getValidID(auction.getArea()),
							getValidID(auction.getCertainBusiness()),
							auction.getDescription(),
							(auction.getUserDetailsExposeInt()),
							auction.isSmoking(),
							getValidID(auction.getAuctionStatus()),
							getValidID(auction.getCreatedBy()), null);

			stmt.executeUpdate(sqlAuctionInsertion,
					Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			Integer auctionId = rs.getInt(1);

			// add relevant records to auction music style table:
			for (IdWithName item : auction.getMusicStyle()) {
				String sqlMusicStyles = String.format(
						"insert into Auction_Music_Style values(%d,%d,%d)",
						null, auctionId, item.getId());
				stmt.executeUpdate(sqlMusicStyles);
			}

			// add relevant records to auction business type table:
			for (IdWithName item : auction.getBusinessType()) {
				String sqlBusinessType = String.format(
						"insert into auction_business_type values(%d,%d,%d)",
						null, auctionId, item.getId());
				stmt.executeUpdate(sqlBusinessType);
			}

			// add relevant records to auction sits type table:
			for (IdWithName item : auction.getSittsType()) {
				String sqlSittsType = String.format(
						"insert into auction_sits_type values(%d,%d,%d)", null,
						auctionId, item.getId());
				stmt.executeUpdate(sqlSittsType);
			}
			return auctionId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnectFromDBServer();
		}
	}

	private static Integer getValidID(IdWithName fieldToCheck) {
		Integer res = null;

		if (fieldToCheck != null) {
			res = fieldToCheck.getId();
		}

		return res;
	}

	/*
	 * Maayan: 24/4/2014 * add function - insertNewUser
	 */
	public static boolean insertNewUser(UserData userData, UserType userType)
			throws ParseException {

		boolean isSucceed = true;

		connectToDBServer();

		String sql = "INSERT INTO users(User_Type, First_Name, Last_Name, Gender, Phone_Number, Email, Password, Birth_Date)"
				+ " VALUES('"
				+ userType
				+ "','"
				+ userData.getFirstName()
				+ "','"
				+ userData.getLastName()
				+ "','"
				+ userData.getGender()
				+ "','"
				+ userData.getPhoneNumber()
				+ "','"
				+ userData.getEmail()
				+ "','"
				+ userData.getPassword()
				+ "','"
				+ userData.getBirthDate() + "')";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			isSucceed = false;
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return isSucceed;
	}

	/*
	 * Maayan: 24/4/2014 * add function - getUserLoginAttempts
	 */
	public static int getUserLoginAttempts(String email) {

		int loginAttempts = 0;

		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT Login_Attempts FROM clubber_db.users;");
			while (rs.next()) {
				loginAttempts = rs.getInt("Login_Attempts");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return loginAttempts;
	}

	/*
	 * Orel & Maayan: 25/4/2014 * add function - increaseLoginAttemptsDB
	 */
	public static void increaseLoginAttemptsDB(String email) {

		connectToDBServer();

		try {
			stmt.executeUpdate("UPDATE clubber_db.users "
					+ "SET Login_attempts = Login_attempts + 1 "
					+ "WHERE Email ='" + email + "'");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}
	}

	public static void updateLoginAttemptTimeStamp(String email) {
		connectToDBServer();

		Date now = new Date();
		long expiredDate = now.getTime() + (3600 * 3 * 1000);

		try {
			stmt.executeUpdate("UPDATE clubber_db.users "
					+ "SET LoginAttemptTimeStamp = " + expiredDate
					+ " WHERE Email ='" + email + "'");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}
	}

	public static long getLoginAttemptTimeStamp(String email) {
		long loginAttemptsTimeStamp = 0;

		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT LoginAttemptTimeStamp FROM clubber_db.users");
			while (rs.next()) {
				loginAttemptsTimeStamp = rs.getLong("LoginAttemptTimeStamp");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return loginAttemptsTimeStamp;
	}

	/*
	 * Maayan: 24/4/2014 * add function - isEmailUnique
	 */
	public static boolean isEmailUnique(String userEmail) {

		boolean isEmailUnique = true;

		connectToDBServer();

		try {
			ResultSet rs = stmt.executeQuery("SELECT Email "
					+ "FROM clubber_db.users " + "WHERE Email ='" + userEmail
					+ "'");
			if (rs.next()) {
				isEmailUnique = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isEmailUnique = false;

		} finally {
			disconnectFromDBServer();
		}

		return isEmailUnique;

	}

	/*
	 * Orel & Maayan: 25/4/2014 * add function - isEmailExists
	 */
	public static boolean isEmailExists(String email) {

		boolean isExists = false;

		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT Email " + "FROM clubber_db.users "
							+ "WHERE Email ='" + email + "'");
			if (rs.next()) {
				isExists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return isExists;
	}

	/*
	 * Orel & Maayan: 25/4/2014 * add function - isPasswordMatchesEmail
	 */
	public static boolean isPasswordMatcheEmail(String email, String password) {

		boolean isMatch = true;

		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT Password " + "FROM clubber_db.users "
							+ "WHERE Email ='" + email + "'");
			if (rs.next()) {
				String userPassword = rs.getString("Password");

				if (userPassword.equals(password) == false) {
					isMatch = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isMatch = false;

		} finally {
			disconnectFromDBServer();
		}

		return isMatch;
	}

	public static void updateUserPassword(String email, String password) {
		// TODO Auto-generated method stub

		connectToDBServer();

		try {
			stmt.executeQuery("UPDATE clubber_db.users " + "SET Password ="
					+ password + "WHERE Email ='" + email + "'");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}
	}

	public static PR getUserProfileData(String email) {

		PR pr = new PR();
		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * " + "FROM clubber_db.users "
							+ "WHERE Email ='" + email + "'");

			while (rs.next()) {
				pr.setFirstName(rs.getString("First_Name"));
				pr.setLastName(rs.getString("Last_Name"));
				pr.setGender(rs.getString("Gender"));
				pr.setPhoneNumber(rs.getString("Phone_Number"));
				pr.setEmail(rs.getString("Email"));
				pr.setPassword(rs.getString("Password"));
				pr.setBirthDate(rs.getLong("Birth_Date"));
				pr.setImageUrl(rs.getString("User_Image"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return pr;

	}

	public static Client getClientData(String email) {

		Client client = new Client();
		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * " + "FROM clubber_db.users "
							+ "WHERE Email ='" + email + "'");

			while (rs.next()) {
				client.setFirstName(rs.getString("First_Name"));
				client.setLastName(rs.getString("Last_Name"));
				client.setGender(rs.getString("Gender"));
				client.setPhoneNumber(rs.getString("Phone_Number"));
				client.setEmail(rs.getString("Email"));
				client.setPassword(rs.getString("Password"));
				client.setBirthDate(rs.getLong("Birth_Date"));
				client.setImageUrl(rs.getString("User_Image"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return client;

	}

	public static boolean updateUserDetails(UserData userData)
			throws ParseException {
		// TODO Auto-generated method stub

		boolean isSucceed = true;
		PreparedStatement ps = null;

		connectToDBServer();

		String sql = "UPDATE clubber_db.users " + "SET First_Name = ? "
				+ ", Last_Name = ? " + ", Gender = ? " + ", Phone_Number = ? "
				+ ", Password = ? " + ", User_Image = ? " + ", Birth_Date = ? "
				+ " WHERE Email = ? ";

		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, userData.getFirstName());
			ps.setString(2, userData.getLastName());
			ps.setString(3, userData.getGender().toString());
			ps.setString(4, userData.getPhoneNumber());
			ps.setString(5, userData.getPassword());
			ps.setString(6, userData.getImageURL());
			ps.setLong(7, userData.getBirthDate());
			ps.setString(8, userData.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			isSucceed = false;
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return isSucceed;
	}

	public static UserReviews getPrProfileReview(String email) {
		// TODO Auto-generated method stub

		UserReviews reviews = new UserReviews();
		List<UserReviews> totalReviews = new ArrayList<>();

		connectToDBServer();

		try {

			ResultSet rs = stmt
					.executeQuery("SELECT Availability, Realiability, Treats "
							+ "FROM clubber_db.pr_review " + "WHERE PR_id IN "
							+ "(SELECT id from clubber_db.users "
							+ "WHERE Email ='" + email + "')");

			while (rs.next()) {
				UserReviews temp = new UserReviews();
				temp.setAvailability(rs.getInt("Availability"));
				temp.setRealiability(rs.getInt("Realiability"));
				temp.setTreats(rs.getInt("Treats"));

				totalReviews.add(temp);
			}

			for (UserReviews userReviews : totalReviews) {
				reviews.setAvailability(reviews.getAvailability()
						+ userReviews.getAvailability());
				reviews.setRealiability(reviews.getRealiability()
						+ userReviews.getRealiability());
				reviews.setTreats(reviews.getTreats() + userReviews.getTreats());

			}

			if (totalReviews.size() > 0) {
				reviews.setAvailability(reviews.getAvailability()
						/ totalReviews.size());
				reviews.setRealiability(reviews.getRealiability()
						/ totalReviews.size());
				reviews.setTreats(reviews.getTreats() / totalReviews.size());
				reviews.setGeneral((reviews.getAvailability()
						+ reviews.getRealiability() + reviews.getTreats()) / 3);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return reviews;
	}

	public static UserReviews getClientProfileReview(String email) {
		// TODO Auto-generated method stub
		UserReviews reviews = new UserReviews();
		List<UserReviews> totalReviews = new ArrayList<>();

		connectToDBServer();

		try {

			ResultSet rs = stmt
					.executeQuery("SELECT Punctuality, Realiability "
							+ "FROM clubber_db.customer_review "
							+ "WHERE Customer_id IN "
							+ "(SELECT id from clubber_db.users "
							+ "WHERE Email ='" + email + "')");

			while (rs.next()) {
				UserReviews temp = new UserReviews();
				temp.setPunctuality(rs.getInt("Punctuality"));
				temp.setRealiability(rs.getInt("Realiability"));

				totalReviews.add(temp);
			}

			for (UserReviews userReviews : totalReviews) {
				reviews.setPunctuality(reviews.getPunctuality()
						+ userReviews.getPunctuality());
				reviews.setRealiability(reviews.getRealiability()
						+ userReviews.getRealiability());

			}

			if (totalReviews.size() > 0) {
				reviews.setPunctuality(reviews.getPunctuality()
						/ totalReviews.size());
				reviews.setRealiability(reviews.getRealiability()
						/ totalReviews.size());
				reviews.setGeneral((reviews.getPunctuality() + reviews
						.getRealiability()) / 2);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return reviews;

	}

	public static List<LineManagementData> getCustomerRecomendedLines(
			String email) {

		List<LineManagementData> lines = null;

		connectToDBServer();

		try {

			ResultSet rs = stmt
					.executeQuery("select * "
							+ "From users U, auction A, line L, businesses B, city C, streets S "
							+ "where email = '" + email + "' and "
							+ "U.id = A.Created_By and "
							+ "A.area = B.Area and "
							+ "C.Area_id = A.area and "
							+ "S.City_id = C.id and " + "S.id = B.Street and "
							+ "B.id = L.Business_id");

			while (rs.next()) {

				lines = new ArrayList<>();
				LineData lineData = new LineData();
				BusinessData businessData = new BusinessData();

				lineData.setId(rs.getInt("L.id"));
				lineData.setM_LineName(rs.getString("L.Name"));
				lineData.setBusiness(new IdWithName(rs.getInt("L.Business_id"),
						rs.getString("B.Name")));
				lineData.setMinAge(rs.getInt("L.Min_Age"));
				lineData.setDescription(rs.getString("L.Description"));
				lineData.setEntranceFee(rs.getString("L.Entrance_Fee"));
				lineData.setDj(rs.getString("L.DJ"));
				lineData.setOpeningHour(rs.getTime("L.Opening_Hour").toString());
				lineData.setLinePhotoURL(rs.getString("L.Line_Photo"));
				businessData.setM_Id(rs.getInt("B.id"));
				businessData.setM_Name(rs.getString("B.Name"));
				businessData.setM_StreetId(new IdWithName(rs.getInt("S.id"), rs
						.getString("S.Name")));
				businessData.setM_CityId(new IdWithName(rs.getInt("C.id"), rs
						.getString("C.Name")));
				businessData.setM_HouseNumber(rs.getInt("B.Structure_Number"));

				LineManagementData lineManagementData = new LineManagementData(
						lineData, businessData);
				lines.add(lineManagementData);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return lines;
	}

	public static ArrayList<BusinessData> getWelcomeScreenEvents(String i_Date)
			throws ParseException {
		final String NEW_FORMAT = "yyyy-MM-dd";
		String parsedDate, dateStr;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(i_Date);

		ArrayList<BusinessData> data = new ArrayList<BusinessData>();
		// access date fields
		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("select * "
							+ "from line L, businesses B, areas a, city c, streets s, business_type t "
							+ "where L.Business_id = B.id AND L.Line_End_Date >= '"
							+ date.getTime() + "' And" + " B.Area = a.id and "
							+ "B.city = c.id and " + "B.street = s.id and "
							+ "B.Business_Type = t.id");

			while (rs.next()) {
				dateStr = (rs.getString("L.Line_Start_Date"));
				BusinessData bData = new BusinessData();
				// set business data
				bData.setM_Id(rs.getInt("b.id"));
				bData.setM_Name(rs.getString("b.name"));
				bData.setM_StreetId(new IdWithName(rs.getInt("b.street"), rs
						.getString("s.Name")));
				bData.setM_HouseNumber(rs.getInt("b.structure_number"));
				bData.setM_PhoneNumber(rs.getString("b.Business_Phone_Number"));
				bData.setM_Description(rs.getString("b.Description"));
				bData.setM_BusinessTypeId(new IdWithName(rs
						.getInt("b.Business_Type"), rs.getString("t.Name")));
				bData.setM_CityId(new IdWithName(rs.getInt("b.city"), rs
						.getString("c.Name")));
				bData.setM_AreaId(new IdWithName(rs.getInt("b.area"), rs
						.getString("a.Name")));

				LineData lData = new LineData();
				lData.setLinePhotoURL(rs.getString("L.Line_Photo"));
				lData.setM_LineName(rs.getString("L.name"));
				lData.setDescription(rs.getString("L.Description"));
				lData.setDj(rs.getString("L.Dj"));
				lData.setEntranceFee(rs.getString("L.entrance_fee"));
				lData.setMinAge(rs.getInt("L.Min_Age"));
				lData.setStartDate(date.getTime());
				lData.setId(rs.getInt("L.id"));
				lData.setM_DayInWeek(rs.getInt("L.Day_In_Week"));
				lData.setOpeningHour(rs.getString("L.Opening_Hour"));

				bData.getM_Lines().add(lData);
				data.add(bData);
			}

			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < data.get(i).getM_Lines().size(); j++) {
					data.get(i)
							.getM_Lines()
							.get(j)
							.setMusicStyle(
									GetIdAndNameData("Select music_style.* from line_music_style, music_style where line_music_style.Line_id = "
											+ data.get(i).getM_Lines().get(j)
													.getId()
											+ " and music_style.id = line_music_style.music_style_id;"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return data;
	}

	public static ArrayList<LineData> getLineByPR(String i_userID)
			throws ParseException {

		ArrayList<LineData> data = new ArrayList<LineData>();
		// access date fields
		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("select * "
							+ "from line L, businesses B, areas a, city c, streets s, business_type t "
							+ "where L.Business_id = B.id AND ((L.PR_id ='"
							+ i_userID + "') OR (L.id in (select line_id from line_prs Lp where Lp.PR_id = " + i_userID + "))) AND" + " B.Area = a.id and "
							+ "B.city = c.id and " + "B.street = s.id and "
							+ "B.Business_Type = t.id");

			while (rs.next()) {

				LineData lData = new LineData();
				lData.setM_DayInWeek(rs.getInt("L.Day_In_Week"));
				lData.setM_LineName(rs.getString("L.name"));
				lData.setDescription(rs.getString("L.Description"));
				lData.setDj(rs.getString("L.DJ"));
				lData.setEntranceFee(rs.getString("L.entrance_fee"));
				lData.setMinAge(rs.getInt("L.Min_Age"));
				lData.setStartDate(rs.getLong("L.Line_Start_Date"));
				lData.setId(rs.getInt("L.id"));
				lData.setLinePhotoURL(rs.getString("L.Line_Photo"));

				BusinessData bData = new BusinessData();
				bData.setM_Id(rs.getInt("b.id"));
				bData.setM_Name(rs.getString("b.name"));
				bData.setM_StreetId(new IdWithName(rs.getInt("b.street"), rs
						.getString("s.Name")));
				bData.setM_HouseNumber(rs.getInt("b.structure_number"));
				bData.setM_PhoneNumber(rs.getString("b.Business_Phone_Number"));
				bData.setM_Description(rs.getString("b.Description"));
				bData.setM_BusinessTypeId(new IdWithName(rs
						.getInt("b.Business_Type"), rs.getString("t.Name")));
				bData.setM_CityId(new IdWithName(rs.getInt("b.city"), rs
						.getString("c.Name")));
				bData.setM_AreaId(new IdWithName(rs.getInt("b.area"), rs
						.getString("a.Name")));

				lData.setBusinessData(bData);

				data.add(lData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		for (int i = 0; i < data.size(); i++) {
			getMusicByLineId(data.get(i), data.get(i).getId());
		}
		return data;
	}

	private static void getMusicByLineId(LineData lineData, Integer id) {
		
		LinkedList<IdWithName> music = new LinkedList<IdWithName>();
		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM music_style MS, line_music_style LMS"
							+ " WHERE LMS.Line_Id = "
							+ id
							+ " AND MS.id = LMS.Music_Style_Id");
			
			while (rs.next()) 
			{
				music.add(new IdWithName(rs.getInt("MS.id"),rs.getString("MS.Name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			disconnectFromDBServer();
		}
	lineData.setMusicStyle(music);
	
	}

	public static void unlockUser(String email) {

		connectToDBServer();

		try {
			stmt.executeUpdate("UPDATE clubber_db.users "
					+ "SET Login_attempts = 0 , LoginAttemptTimeStamp = 0 "
					+ "WHERE Email ='" + email + "'");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

	}

	public static UserType getUserType(String email) {

		UserType userType = UserType.Client;

		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT User_Type "
							+ "FROM clubber_db.users " + "WHERE Email ='"
							+ email + "'");
			if (rs.next()) {
				userType = UserType.valueOf(rs.getString("User_Type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return userType;
	}

	public static BusinessData getBusinessData(int businessId)
			throws ParseException {

		connectToDBServer();

		BusinessData businessData = null;

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * "
							+ "FROM businesses B, areas A, city C, streets S, business_type T "
							+ "WHERE B.id ='" + businessId + "' and "
							+ " B.area = A.id and " + "B.city = C.id and "
							+ "B.street = S.id and " + "B.Business_Type = T.id");

			while (rs.next()) {
				businessData = new BusinessData();
				// set business data
				businessData.setM_Id(rs.getInt("b.id"));
				businessData.setM_Name(rs.getString("b.name"));
				businessData.setM_StreetId(new IdWithName(
						rs.getInt("b.street"), rs.getString("s.Name")));
				businessData.setM_HouseNumber(rs.getInt("b.structure_number"));
				businessData.setM_PhoneNumber(rs
						.getString("b.Business_Phone_Number"));
				businessData.setM_Description(rs.getString("b.Description"));
				businessData.setM_BusinessTypeId(new IdWithName(rs
						.getInt("b.Business_Type"), rs.getString("t.Name")));
				businessData.setM_CityId(new IdWithName(rs.getInt("b.city"), rs
						.getString("c.Name")));
				businessData.setM_AreaId(new IdWithName(rs.getInt("b.area"), rs
						.getString("a.Name")));
				businessData.setM_Photo(rs.getString("b.Photo"));
			}

			ResultSet rs1 = stmt.executeQuery("SELECT * "
					+ "FROM businesses B, line L " + "WHERE B.id ='"
					+ businessId + "' and " + "B.id =  L.Business_id");

			while (rs1.next()) {
				LineData lineData = new LineData();
				lineData.setM_LineName(rs1.getString("L.name"));
				lineData.setDescription(rs1.getString("L.Description"));
				lineData.setDj(rs1.getString("L.DJ"));
				lineData.setMinAge(rs1.getInt("L.Min_Age"));
				lineData.setStartDate(rs1.getLong("L.Line_Start_Date"));

				businessData.getM_Lines().add(lineData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return businessData;

	}

	public static boolean updateBusinessDetails(BusinessData businessData) {
		// TODO Auto-generated method stub
		boolean isSucceed = true;

		connectToDBServer();

		String sql = "UPDATE businesses B, streets S " + "SET B.Name = '"
				+ businessData.getM_Name() + "'" + ", B.Area = '"
				+ businessData.getM_AreaId().getId() + "'" + ", B.City = '"
				+ businessData.getM_CityId().getId() + "'" + ", B.Street = '"
				+ businessData.getM_StreetId().getId() + "'"
				+ ", B.Structure_Number = '" + businessData.getM_HouseNumber()
				+ "'" + ", B.Business_Type = '"
				+ businessData.getM_BusinessTypeId().getId() + "'"
				+ ", B.Business_Phone_Number = '"
				+ businessData.getM_PhoneNumber() + "'" + ", B.Description = '"
				+ businessData.getM_Description() + "'" + ", S.City = '"
				+ businessData.getM_CityId().getId() + "'" + ", S.Name = '"
				+ businessData.getM_StreetId().getName() + "'"
				+ " WHERE B.id ='" + businessData.getM_Id() + "' and "
				+ " S.id ='" + businessData.getM_StreetId().getId() + "'";

		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			isSucceed = false;
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return isSucceed;
	}

	public static ArrayList<BusinessData> getAllBusinesses() {

		ArrayList<BusinessData> businesses = new ArrayList<>();

		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * "
							+ "FROM businesses b, areas a, city c, streets s, business_type t "
							+ "WHERE b.area = a.id and " + "b.city = c.id and "
							+ "b.street = s.id and " + "b.Business_Type = t.id");

			while (rs.next()) {
				BusinessData businessData = new BusinessData();
				businessData.setM_Id(rs.getInt("id"));
				businessData.setM_Name(rs.getString("name"));
				businessData.setM_StreetId(new IdWithName(
						rs.getInt("b.street"), rs.getString("s.Name")));
				businessData.setM_HouseNumber(rs.getInt("structure_number"));
				businessData.setM_PhoneNumber(rs
						.getString("Business_Phone_Number"));
				businessData.setM_Photo(rs.getString("b.Photo"));
				businessData.setM_Description(rs.getString("Description"));
				businessData.setM_BusinessTypeId(new IdWithName(rs
						.getInt("b.Business_Type"), rs.getString("t.name")));
				businessData.setM_CityId(new IdWithName(rs.getInt("b.city"), rs
						.getString("c.Name")));
				businessData.setM_AreaId(new IdWithName(rs.getInt("b.area"), rs
						.getString("a.Name")));

				businesses.add(businessData);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return businesses;

	}

	public static ArrayList<IdWithName> getBusinessAreasData() {

		ArrayList<IdWithName> areasList = new ArrayList<>();

		connectToDBServer();

		try {
			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM areas ");

			while (rs.next()) {
				int areadId = rs.getInt("id");
				String areaName = rs.getString("Name");

				IdWithName areaIdWithName = new IdWithName(areadId, areaName);
				areasList.add(areaIdWithName);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return areasList;
	}

	public static ArrayList<IdWithName> getBusinessCitiesData(int areadId) {

		ArrayList<IdWithName> citiesList = new ArrayList<>();

		connectToDBServer();

		try {
			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM city "
					+ "WHERE Area_id = " + areadId);

			while (rs.next()) {
				int cityId = rs.getInt("id");
				String cityName = rs.getString("Name");

				IdWithName cityIdWithName = new IdWithName(cityId, cityName);
				citiesList.add(cityIdWithName);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return citiesList;

	}

	public static ArrayList<IdWithName> getBusinessesTypeData() {

		ArrayList<IdWithName> typesList = new ArrayList<>();

		connectToDBServer();

		try {
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM business_type ");

			while (rs.next()) {
				int typeId = rs.getInt("id");
				String typeName = rs.getString("Name");

				IdWithName typeIdWithName = new IdWithName(typeId, typeName);
				typesList.add(typeIdWithName);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return typesList;
	}

	public static ArrayList<AuctionData> getAuctionsByPrLines(String email) {

		ArrayList<AuctionData> auctionList = new ArrayList<>();
		int createdById = 0;

		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * "
							+ "FROM auction A, line L, users U, businesses B, event_type ET, areas AR, auction_status S "
							+ "WHERE L.PR_id =  U.id and "
							+ "U.Email = '"
							+ email
							+ "' and "
							+ "A.Minimum_Age > L.Min_Age and "
							+ "B.id = L.Business_id and "
							+ "B.Area = A.Area and "

							+ "A.Event_Type = ET.id and "
							+ "A.Area = AR.id and "

							+ "A.Auction_Status = S.id and "
							+ "S.id = 1 and "
							+ "A.Event_Date BETWEEN L.Line_Start_Date AND L.Line_End_Date");

			while (rs.next()) {
				AuctionData auctionData = new AuctionData();
				auctionData.setId(rs.getInt("A.id"));
				auctionData.setMinAge(rs.getInt("A.Minimum_Age"));
				auctionData.setExceptionsDescription(rs
						.getString("A.Exceptions_Description"));
				auctionData.setGuestesQuantiny(rs.getInt("A.Guestes_Quantiny"));
				auctionData.setEventType(new IdWithName(rs.getInt("ET.id"), rs
						.getString("ET.Name")));
				auctionData.setEventDate(rs.getLong("A.Event_Date"));
				auctionData
						.setDateFlexible(rs.getBoolean("A.Is_Date_Flexible"));
				auctionData.setArea(new IdWithName(rs.getInt("AR.id"), rs
						.getString("AR.Name")));

				auctionData.setDescription(rs.getString("A.Description"));

				auctionData.setSmoking(rs.getBoolean("A.Smoking"));
				auctionData.setAuctionStatus(new IdWithName(rs.getInt("S.id"),
						rs.getString("S.Name")));

				auctionList.add(auctionData);
			}

			for (int i = 0; i < auctionList.size(); i++) {
				// get all auction_id music style
				ResultSet rs1 = stmt
						.executeQuery("SELECT * "
								+ "FROM line_music_style LMS, auction_music_style AMS, music_style MS "
								+ "WHERE AMS.Music_Style_Id = LMS.Music_Style_Id and "
								+ "AMS.Music_Style_Id = MS.id and "
								+ "LMS.Music_Style_Id = MS.id and "
								+ "AMS.Auction_Id = "
								+ auctionList.get(i).getId());

				List<IdWithName> musicStyles = new LinkedList<>();

				while (rs1.next()) {
					IdWithName musicStyle = new IdWithName(rs1.getInt("MS.id"),
							rs1.getString("MS.Name"));
					musicStyles.add(musicStyle);
				}

				auctionList.get(i).setMusicStyle(musicStyles);
			}

			for (int i = 0; i < auctionList.size(); i++) {

				// get all auction_id businesses type
				ResultSet rs2 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, business_type BT "
						+ "WHERE A.Business_Type = BT.id and " + "A.id = "
						+ auctionList.get(i).getId());

				List<IdWithName> businessesType = new LinkedList<>();

				while (rs2.next()) {
					IdWithName businessType = new IdWithName(
							rs2.getInt("BT.id"), rs2.getString("BT.Name"));
					businessesType.add(businessType);
				}

				auctionList.get(i).setBusinessType(businessesType);
			}

			for (int i = 0; i < auctionList.size(); i++) {

				// get all auction_id seats type
				ResultSet rs3 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, sitts_type ST "
						+ "WHERE A.Seats_Type = ST.id and " + "A.id = "
						+ auctionList.get(i).getId());

				List<IdWithName> seatsType = new LinkedList<>();

				while (rs3.next()) {
					IdWithName seatType = new IdWithName(rs3.getInt("ST.id"),
							rs3.getString("ST.Name"));
					seatsType.add(seatType);
				}

				auctionList.get(i).setSittsType(seatsType);
			}

			for (int i = 0; i < auctionList.size(); i++) {
				ResultSet rs4 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, users U " + "WHERE U.id = "
						+ createdById + " and " + "A.id = "
						+ auctionList.get(i).getId());

				if (rs4.next()) {
					auctionList.get(i).setCreatedBy(
							new IdWithName(createdById, rs4
									.getString("U.First_Name")));
				}
			}

			for (int i = 0; i < auctionList.size(); i++) {

				ResultSet rs5 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, details_to_display D "
						+ "WHERE A.Details_To_Display = D.id and " + "A.id = "
						+ auctionList.get(i).getId());

				while (rs5.next()) {
					auctionList.get(i).setUserDetailsExpose(rs5.getInt("D.id"));
				}
			}

			for (int i = 0; i < auctionList.size(); i++) {

				ResultSet rs6 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, businesses B "
						+ "WHERE A.Certain_Business = B.id and " + "A.id = "
						+ auctionList.get(i).getId());

				while (rs6.next()) {
					IdWithName certailBusiness = new IdWithName(
							rs6.getInt("B.id"), (rs6.getString("B.Name")));
					auctionList.get(i).setCertainBusiness(certailBusiness);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return auctionList;
	}

	public static ArrayList<IdWithName> getMusicStyleData() {

		ArrayList<IdWithName> musicStyleList = new ArrayList<>();

		connectToDBServer();

		try {
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM music_style MS ");

			while (rs.next()) {
				Integer id = rs.getInt("MS.id");
				String name = rs.getString("MS.Name");
				IdWithName styleIdWithName = new IdWithName(id, name);
				musicStyleList.add(styleIdWithName);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return musicStyleList;
	}

	public static ArrayList<AuctionData> getAllAuctions() {
		ArrayList<AuctionData> auctionList = new ArrayList<>();

		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * "
							+ "FROM auction A, event_type ET, areas AR, auction_status S "

							+ "WHERE A.Event_Type = ET.id and "
							+ "A.Area = AR.id and "

							+ "S.id = 1 and " + "A.Auction_Status = S.id");
			while (rs.next()) {
				AuctionData auctionData = new AuctionData();
				auctionData.setId(rs.getInt("A.id"));
				auctionData.setMinAge(rs.getInt("A.Minimum_Age"));
				auctionData.setExceptionsDescription(rs
						.getString("A.Exceptions_Description"));
				auctionData.setGuestesQuantiny(rs.getInt("A.Guestes_Quantiny"));
				auctionData.setEventType(new IdWithName(rs.getInt("ET.id"), rs
						.getString("ET.Name")));
				auctionData.setEventDate(rs.getLong("A.Event_Date"));
				auctionData
						.setDateFlexible(rs.getBoolean("A.Is_Date_Flexible"));
				auctionData.setArea(new IdWithName(rs.getInt("AR.id"), rs
						.getString("AR.Name")));

				auctionData.setDescription(rs.getString("A.Description"));

				auctionData.setSmoking(rs.getBoolean("A.Smoking"));
				auctionData.setAuctionStatus(new IdWithName(rs.getInt("S.id"),
						rs.getString("S.displayName")));

				auctionList.add(auctionData);
			}

			for (int i = 0; i < auctionList.size(); i++) {
				// get all auction_id music style
				ResultSet rs1 = stmt
						.executeQuery("SELECT * "
								+ "FROM line_music_style LMS, auction_music_style AMS, music_style MS "
								+ "WHERE AMS.Music_Style_Id = LMS.Music_Style_Id and "
								+ "AMS.Music_Style_Id = MS.id and "
								+ "LMS.Music_Style_Id = MS.id and "
								+ "AMS.Auction_Id = "
								+ auctionList.get(i).getId());

				List<IdWithName> musicStyles = new LinkedList<>();

				while (rs1.next()) {
					IdWithName musicStyle = new IdWithName(rs1.getInt("MS.id"),
							rs1.getString("MS.Name"));
					musicStyles.add(musicStyle);
				}

				auctionList.get(i).setMusicStyle(musicStyles);
			}

			for (int i = 0; i < auctionList.size(); i++) {

				// get all auction_id businesses type
				ResultSet rs2 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, business_type BT "
						+ "WHERE A.Business_Type = BT.id and " + "A.id = "
						+ auctionList.get(i).getId());

				List<IdWithName> businessesType = new LinkedList<>();

				while (rs2.next()) {
					IdWithName businessType = new IdWithName(
							rs2.getInt("BT.id"), rs2.getString("BT.Name"));
					businessesType.add(businessType);
				}

				auctionList.get(i).setBusinessType(businessesType);
			}

			for (int i = 0; i < auctionList.size(); i++) {

				// get all auction_id seats type
				ResultSet rs3 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, sitts_type ST "
						+ "WHERE A.Seats_Type = ST.id and " + "A.id = "
						+ auctionList.get(i).getId());

				List<IdWithName> seatsType = new LinkedList<>();

				while (rs3.next()) {
					IdWithName seatType = new IdWithName(rs3.getInt("ST.id"),
							rs3.getString("ST.Name"));
					seatsType.add(seatType);
				}

				auctionList.get(i).setSittsType(seatsType);
			}

			for (int i = 0; i < auctionList.size(); i++) {

				ResultSet rs4 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, details_to_display D "
						+ "WHERE A.Details_To_Display = D.id and " + "A.id = "
						+ auctionList.get(i).getId());

				while (rs4.next()) {
					auctionList.get(i).setUserDetailsExpose(rs4.getInt("D.id"));
				}
			}

			for (int i = 0; i < auctionList.size(); i++) {

				ResultSet rs5 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, users U "
						+ "WHERE A.Created_By = U.id and " + "A.id = "
						+ auctionList.get(i).getId());

				while (rs5.next()) {
					IdWithName createdBy = new IdWithName(rs5.getInt("U.id"),
							(rs5.getString("U.First_Name") + " " + rs5
									.getString("U.Last_Name")));
					auctionList.get(i).setCreatedBy(createdBy);
				}
			}

			for (int i = 0; i < auctionList.size(); i++) {

				ResultSet rs6 = stmt.executeQuery("SELECT * "
						+ "FROM auction A, businesses B "
						+ "WHERE A.Certain_Business = B.id and " + "A.id = "
						+ auctionList.get(i).getId());

				while (rs6.next()) {
					IdWithName certailBusiness = new IdWithName(
							rs6.getInt("B.id"), (rs6.getString("B.Name")));
					auctionList.get(i).setCertainBusiness(certailBusiness);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return auctionList;
	}

	public static AuctionData getAuctionById(String i_AuctionId) {

		AuctionData auctionData = new AuctionData();
		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * "
							+ "FROM auction A, users U, businesses B, event_type ET, areas AR, details_to_display D, auction_status S "
							+ "WHERE A.id = '" + i_AuctionId
							+ "' + U.id = A.Created_By and "
							+ "B.Business_Type = A.Business_Type and "
							+ "A.Event_Type = ET.id and "
							+ "A.Area = AR.id and "
							+ "A.Details_To_Display = D.id and "
							+ "A.Auction_Status = S.id");
			while (rs.next()) {
				auctionData.setId(rs.getInt("A.id"));
				auctionData.setMinAge(rs.getInt("A.Minimum_Age"));
				auctionData.setExceptionsDescription(rs
						.getString("A.Exceptions_Description"));
				auctionData.setGuestesQuantiny(rs.getInt("A.Guestes_Quantiny"));
				auctionData.setEventType(new IdWithName(rs.getInt("ET.id"), rs
						.getString("ET.Name")));
				auctionData.setEventDate(rs.getLong("A.Event_Date"));
				auctionData
						.setDateFlexible(rs.getBoolean("A.Is_Date_Flexible"));
				auctionData.setArea(new IdWithName(rs.getInt("AR.id"), rs
						.getString("AR.Name")));
				auctionData.setCertainBusiness(new IdWithName(
						rs.getInt("B.id"), rs.getString("B.Name")));
				auctionData.setDescription(rs.getString("A.Description"));
				auctionData.setUserDetailsExpose(rs.getInt("D.id"));
				auctionData.setSmoking(rs.getBoolean("A.Smoking"));
				auctionData.setAuctionStatus(new IdWithName(rs.getInt("S.id"),
						rs.getString("S.Name")));
				auctionData.setCreatedBy(new IdWithName(rs
						.getInt("A.Created_By"), rs.getString("U.First_Name")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return auctionData;
	}
	
	public static AuctionData getAuctionBaseDetailsById(Integer i_AuctionId) {

		AuctionData auctionData = new AuctionData();
		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("SELECT *"
							+ " FROM auction A, users U"
							+ " WHERE A.id = " + i_AuctionId
							+ " AND U.id = A.Created_By");
			while (rs.next()) {
				auctionData.setId(rs.getInt("A.id"));
				auctionData.setMinAge(rs.getInt("A.Minimum_Age"));
				auctionData.setExceptionsDescription(rs.getString("A.Exceptions_Description"));
				auctionData.setGuestesQuantiny(rs.getInt("A.Guestes_Quantiny"));
				auctionData.setEventDate(rs.getLong("A.Event_Date"));
				auctionData.setDateFlexible(rs.getBoolean("A.Is_Date_Flexible"));
				auctionData.setArea(new IdWithName(rs.getInt("A.Area"), null));
				auctionData.setCertainBusiness(new IdWithName(rs.getInt("A.Certain_Business"), null));
				auctionData.setDescription(rs.getString("A.Description"));
				auctionData.setSmoking(rs.getBoolean("A.Smoking"));
				auctionData.setCreatedBy(new IdWithName(rs.getInt("A.Created_By"), rs.getString("U.First_Name") + " " + rs.getString("U.Last_Name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return auctionData;
	}

	public static boolean addNewBusiness(BusinessData businessData) {
		boolean isSucceed = true;

		connectToDBServer();

		String sql1 = "INSERT INTO streets(City_id, Name) " + "VALUES ('"
				+ businessData.getM_CityId().getId() + "','"
				+ businessData.getM_StreetId().getName() + "')";
		try {

			stmt.executeUpdate(sql1);

			ResultSet rs = stmt
					.executeQuery("SELECT MAX( id ) AS MAX from streets");
			String BusinessImgUrl="/Clubber/images/BusinessImg.jpg";
			if (!businessData.getImageUrl().isEmpty() || businessData.getImageUrl() != null)
			{
				BusinessImgUrl=businessData.getImageUrl();
			}

			if (rs.next()) {
				Integer streetId = rs.getInt("MAX");

				String sql = "INSERT INTO businesses(Name, Street, Structure_Number, Business_Phone_Number, Description, Business_Type, City, Area, Photo) "
						+ "VALUES ('"
						+ businessData.getM_Name()
						+ "','"
						+ streetId
						+ "','"
						+ businessData.getM_HouseNumber()
						+ "','"
						+ businessData.getM_PhoneNumber()
						+ "','"
						+ businessData.getM_Description()
						+ "','"
						+ businessData.getM_BusinessTypeId().getId()
						+ "','"
						+ businessData.getM_CityId().getId()
						+ "','"
						+ businessData.getM_AreaId().getId()
						+ "','"
						+ BusinessImgUrl + "')";

				stmt.executeUpdate(sql);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			isSucceed = false;
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return isSucceed;
	}

	public static boolean addNewLine(LineData lineData) throws ParseException {
		boolean isSucceed = true;

		connectToDBServer();

		String sql = "INSERT INTO line(Business_id, PR_id, Name, Day_In_Week, Line_Start_Date, Line_End_Date, Min_Age, Description, Entrance_Fee, DJ, Opening_Hour, Line_Photo) "
				+ "VALUES ('"
				+ lineData.getBusiness().getId()
				+ "','"
				+ lineData.getPr().getId()
				+ "','"
				+ lineData.getM_LineName()
				+ "','"
				+ lineData.getM_DayInWeek()
				+ "','"
				+ lineData.getStartDate()
				+ "','"
				+ lineData.getEndDate()
				+ "','"
				+ lineData.getMinAge()
				+ "','"
				+ lineData.getDescription()
				+ "','"
				+ lineData.getEntranceFee()
				+ "','"
				+ lineData.getDj()
				+ "','"
				+ lineData.getOpeningHour()
				+ "','" + lineData.getLinePhotoURL() + "')";
		try {
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			isSucceed = false;
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return isSucceed;
	}

	public static ArrayList<UserMessagesData> getJoinLineRequestData(
			String email) {

		ArrayList<UserMessagesData> joinLineRequestList = new ArrayList<>();
		connectToDBServer();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ "FROM messages M, users U "
					+ "WHERE M.To_User_id = U.id and "
					+ "M.Line_id is not null and  "
					+ "M.Auction_id is null and " + "U.Email ='" + email + "'");
			while (rs.next()) {
				UserMessagesData messagesData = new UserMessagesData();
				messagesData.setId(rs.getInt("M.id"));
				messagesData.setFromUserId(new IdWithName(rs.getInt("M.From_User_id"),null));
				messagesData.setToUserId(rs.getInt("M.To_User_id"));
				messagesData.setLineId(rs.getInt("M.Line_id"));
				messagesData.setCreatedOn(rs.getDate("M.Created_On"));

				joinLineRequestList.add(messagesData);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return joinLineRequestList;
	}

	public static String getFirstName(String emailParam) {
		String firstName = "";
		connectToDBServer();
		try {
			ResultSet rs = stmt.executeQuery("SELECT first_name "
					+ "FROM users U " + "WHERE " + "U.Email ='" + emailParam
					+ "'");
			while (rs.next()) {

				firstName = rs.getString("first_name");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return firstName;
	}

	public static String getImageURL(String emailParam) {
		String url = "";
		connectToDBServer();
		try {
			ResultSet rs = stmt.executeQuery("SELECT User_Image "
					+ "FROM users U " + "WHERE " + "U.Email ='" + emailParam
					+ "'");
			while (rs.next()) {

				url = rs.getString("User_Image");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return url;
	}

	public static LineData getLineProfileData(Integer lineID) {
		LineData line = new LineData();
		connectToDBServer();

		try {
			ResultSet rs = stmt
					.executeQuery("select * from line, businesses, users where line.id ="
							+ lineID
							+ " and businesses.id= line.Business_id and users.Id= line.PR_id;");
			while (rs.next()) {
				line.setId(lineID);
				line.setLinePhotoURL(rs.getString("line.Line_Photo"));
				line.setBusiness(new IdWithName(rs.getInt("line.Business_id"),
						rs.getString("businesses.Name")));
				line.setDescription(rs.getString("line.Description"));
				line.setDj(rs.getString("line.DJ"));
				line.setStartDate(rs.getLong("line.Line_Start_Date"));
				line.setEndDate(rs.getLong("line.Line_End_Date"));
				line.setEntranceFee(rs.getString("line.Entrance_Fee"));
				line.setM_DayInWeek(rs.getInt("line.Day_In_Week"));
				line.setM_LineName(rs.getString("line.Name"));
				line.setOpeningHour(rs.getString("line.Opening_Hour"));
				line.setMinAge(rs.getInt("line.Min_Age"));
				line.setPr(new IdWithName(rs.getInt("line.PR_id"), rs
						.getString("users.First_Name")
						+ " "
						+ rs.getString("users.Last_Name")));
			}
			/* load music styles: */
			rs = stmt
					.executeQuery("select * from line_music_style lm, music_style where music_style.id= lm.Music_Style_Id and lm.Line_id="
							+ lineID);

			while (rs.next()) {
				line.getMusicStylesIds().add(
						new IdWithName(rs.getInt("lm.Music_Style_Id"), rs
								.getString("music_style.Name")));
			}

			/* load submitted prs: */
			rs = stmt
					.executeQuery("select * from line_prs lp, users where users.id= lp.Pr_id and lp.Line_id="
							+ lineID);

			while (rs.next()) {
				line.getPrs().add(
						new IdWithName(rs.getInt("lp.Pr_id"), rs
								.getString("users.First_Name")
								+ " "
								+ rs.getString("users.Last_Name")));
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return line;

	}

	public static UserData getUserObject(String emailParam) {

		ClubberLogic.UserData userData = null;
		String userType;

		connectToDBServer();

		try {
			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM users U "
					+ "WHERE " + "U.Email ='" + emailParam + "'");
			while (rs.next()) {
				userType = rs.getString("User_Type");
				if (userType.equals("PR"))
					userData = new PR();
				else
					userData = new Client();

				userData.setFirstName(rs.getString("first_Name"));
				userData.setBirthDate(rs.getLong("Birth_Date"));
				userData.setLastName(rs.getString("Last_Name"));
				userData.setGender((rs.getString("Gender")));
				userData.setUserType(rs.getString("User_Type"));
				userData.setId(rs.getInt("id"));
				userData.setImageUrl(rs.getString("User_Image"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return userData;
	}

	public static boolean updateLineDetails(LineData line)
			throws ParseException {
		// TODO Auto-generated method stub

		boolean isSucceed = true;

		connectToDBServer();

		String photoURL = "";
		if (line.getLinePhotoURL() != null && !line.getLinePhotoURL().isEmpty()) {
			photoURL = ", Line_Photo = '" + line.getLinePhotoURL() + "'";
		}

		String sql = "UPDATE clubber_db.line " + "SET Business_id = '"
				+ line.getBusiness().getId() + "'" + ", Name = '"
				+ line.getM_LineName() + "'" + ", Day_In_Week = '"
				+ line.getM_DayInWeek() + "'" + photoURL
				+ ", Line_Start_Date = '" + line.getStartDate() + "'"
				+ ", Line_End_Date = '" + line.getEndDate() + "'"
				+ ", Min_Age = '" + line.getMinAge() + "'"
				+ ", Description = '" + line.getDescription() + "'"
				+ ", Entrance_Fee = '" + line.getEntranceFee() + "'"
				+ ", DJ = '" + line.getDj() + "'" + ", Opening_Hour = '"
				+ line.getOpeningHour() + "'" + " WHERE id ='" + line.getId()
				+ "'";

		try {
			stmt.executeUpdate(sql);

			// remove all exists music style records
			stmt.executeUpdate("DELETE FROM line_music_style WHERE Line_Id="
					+ line.getId());

			// add relevant records to line music style table:
			for (IdWithName item : line.getMusicStylesIds()) {
				String sqlMusicStyles = String.format(
						"insert into line_music_style values(%d,%d,%d)", null,
						line.getId(), item.getId());
				stmt.executeUpdate(sqlMusicStyles);
			}

		} catch (SQLException e) {
			isSucceed = false;
			e.printStackTrace();

		} finally {
			disconnectFromDBServer();
		}

		return isSucceed;
	}

	public static LinkedList<IdWithName> getBusinessesNameAndID() {
		connectToDBServer();
		try {
			String query = "Select * from businesses;";
			return GetIdAndNameData(query);
		} finally {
			disconnectFromDBServer();
		}

	}

	public static Object getOffersAndAuctions(String i_UserID, String i_LineID,
			String i_Date, String i_Stataus) {
		List<Object> data;
		connectToDBServer();
		try {
			String query = "Select * from businesses;";
			return GetIdAndNameData(query);
		} finally {
			disconnectFromDBServer();
		}

	}

	@SuppressWarnings("null")
	public static Object getLineDay(String i_LineID) {
		ArrayList<Integer> data = new ArrayList<Integer>();
		connectToDBServer();
		try {
			ResultSet rs = stmt
					.executeQuery("Select Day_In_Week from line where id = '"
							+ i_LineID + "'");
			while (rs.next()) {
				data.add(rs.getInt("Day_In_Week"));
			}

		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return data;
	}

	public static MyLinesData getMyLinesData(String i_PRid) {
		MyLinesData data = new MyLinesData();
		connectToDBServer();
		try {
			String query = "Select * from line where (id in (select line_id from line_prs Lp where Lp.PR_id = " + i_PRid + ")) OR (Pr_id=" + i_PRid + ")";
			data.setLines(GetIdAndNameData(query));
		} finally {
			disconnectFromDBServer();
		}
		return data;
	}

	public static ArrayList<OfferPerAuction> getPROffersAndAuctions(String i_UserID,
			String i_LineID, String i_Status) {

		ArrayList<OfferPerAuction> data = new ArrayList<OfferPerAuction>();
		
		connectToDBServer();
		String query = "";
		try {
			if (i_Status.equals("0") && i_LineID.equals("0"))  {
				query = "SELECT * FROM auction A, businesses B, line L, offers O, offer_status OS, Users U"
								+ " WHERE O.Pr_id = "+ i_UserID	+ " AND U.id = O.Pr_id AND O.Offer_Status = OS.id AND O.Line_id=L.id AND "
								+ "L.Business_id = B.id AND O.Auction_id = A.id";
			} else if (i_Status.equals("0")) {
				query = "SELECT * FROM auction A, businesses B, line L, offers O, offer_status OS, Users U"
								+ " WHERE O.Pr_id = "
								+ i_UserID
								+ " AND U.id = O.Pr_id AND O.Offer_Status = OS.id AND O.Line_id = "+i_LineID+" AND O.Line_id=L.id AND "
								+ "l.Business_id = B.id AND O.Auction_id = A.id";
			}
			else if(i_LineID.equals("0"))
			{
				query = "SELECT * FROM auction A, businesses B, line L, offers O, offer_status OS, Users U"
								+ " WHERE O.Pr_id = "
								+ i_UserID
								+ " AND U.id = O.Pr_id AND O.Offer_Status = OS.id AND O.Line_id=L.id AND "
								+ "l.Business_id = B.id AND O.Auction_id = A.id"
								+ " AND O.Offer_Status = " + i_Status;
			}
			
		 else { //all params are not 0
			query = "SELECT * FROM auction A, businesses B, line L, offers O, offer_status OS, Users U"
							+ " WHERE O.Pr_id = "
							+ i_UserID
							+ " AND U.id = O.Pr_id AND O.Offer_Status = OS.id AND O.Line_id = "+i_LineID+" AND O.Line_id=L.id AND "
							+ "l.Business_id = B.id AND O.Auction_id = A.id"
							+" AND O.Offer_Status = " + i_Status;
		 }
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) 
			{
				OfferPerAuction currentOfferPerAuction = new OfferPerAuction();
				currentOfferPerAuction.getM_Auction().setId(rs.getInt("A.id"));
				currentOfferPerAuction.getM_Auction().setMinAge(rs.getInt("A.Minimum_Age"));
				currentOfferPerAuction.getM_Auction().setExceptionsDescription(rs.getString("A.Exceptions_Description"));
				currentOfferPerAuction.getM_Auction().setGuestesQuantiny(rs.getInt("A.Guestes_Quantiny"));
				currentOfferPerAuction.getM_Auction().setEventDate(rs.getLong("A.Event_Date"));
				currentOfferPerAuction.getM_Auction().setDateFlexible(rs.getBoolean("A.Is_Date_Flexible"));
						
				currentOfferPerAuction.getM_OfferData().setId(rs.getInt("O.id"));
				currentOfferPerAuction.getM_OfferData().setAuctionId(rs.getInt("O.Auction_id"));
				currentOfferPerAuction.getM_OfferData().setDescription(rs.getString("O.Description"));
				currentOfferPerAuction.getM_OfferData().setLineId(new IdWithName(rs.getInt("O.line_Id"), rs.getString("L.Name")));
				currentOfferPerAuction.getM_OfferData().setLinePhotoURL(rs.getString("L.line_photo"));
				currentOfferPerAuction.getM_OfferData().setPrId(new IdWithName(rs.getInt("O.Pr_Id"), rs.getString("U.First_Name") + rs.getString("U.Last_Name")));
				currentOfferPerAuction.getM_OfferData().setOfferStatusId(new IdWithName(rs.getInt("O.Offer_Status"), rs.getString("OS.displayName")));
				
				data.add(currentOfferPerAuction);
			}

		} 
		catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return data;

	}

public static boolean updateOfferDetails(OfferData offer) throws ParseException {
		
		boolean isSucceed = true;
		
		connectToDBServer();
		
		String sql = "UPDATE clubber_db.offers "
				   + "SET  Expiration_Date = '" + offer.getExpirationDate() + "'"
				   + ", Line_id = '" + offer.getLineId().getId()+ "'"
				   + ", Description = '" +offer.getDescription() + "'"
				   + ", Max_Arrival_Hour = '" +offer.getMaxArrivalHourAsLong() + "'"
				   + " WHERE id ='" + offer.getId() + "'";
		
		try {
			stmt.executeUpdate(sql);	
			
			//remove all exists treats records
			stmt.executeUpdate("DELETE FROM offer_treats WHERE Offer_id="+ offer.getId());
			
			//add relevant records to line music style table:
			for(IdWithName item: offer.getOfferTreats())
			{
				String sqlTreats= String.format("insert into offer_treats values(%d,%d,%d)", null,offer.getId() , item.getId());
				stmt.executeUpdate(sqlTreats);
			}
			
		} 
		catch (SQLException e) {
			isSucceed = false;
			e.printStackTrace();
			
		}
		finally{
			disconnectFromDBServer();
		}
		
		return isSucceed;
	}
	
	public static boolean updateOfferStatus(Integer offerId, Integer offerStatusId) throws ParseException {
		
		boolean isSucceed = true;
		
		connectToDBServer();
		
		String sql = "UPDATE clubber_db.offers "
				   + "SET  Offer_Status = '" + offerStatusId + "'"
				   + " WHERE id ='" + offerId + "'";		
		try
		{
			stmt.executeUpdate(sql);
		} 
		catch (SQLException e) {
			isSucceed = false;
			e.printStackTrace();
			
		}
		finally{
			disconnectFromDBServer();
		}
		return isSucceed;
	}
	
	public static boolean updateUserDetailsCode(Integer displayCode, Integer auctionId)
	{
		boolean isSucceed = true;
		
		connectToDBServer();	
		try
		{
			//set auction display code:
			String sql = "UPDATE clubber_db.Auction "
					   + "SET Details_To_Display = '" + displayCode + "'"
					   + " WHERE id ='" + auctionId + "'";
			stmt.executeUpdate(sql);
		} 
		catch (SQLException e) {
			isSucceed = false;
			e.printStackTrace();
			
		}
		finally{
			disconnectFromDBServer();
		}
		
		return isSucceed;
	}
	
public static boolean updateAuctionStatus(Integer auctionId, Integer auctionStatusId) throws ParseException {
		
		boolean isSucceed = true;
		
		connectToDBServer();
		
		try
		{
			//set auction status
			String sql = "UPDATE clubber_db.Auction "
					   + "SET  Auction_Status = '" + auctionStatusId + "'"
					   + " WHERE id ='" + auctionId + "'";
			stmt.executeUpdate(sql);
			
			if (auctionStatusId== AuctionStatusIds.NotRelevant.getValue() || auctionStatusId== AuctionStatusIds.InActive.getValue())
			{
				//set all auction offers as not relevant or already approved offer:
				sql = "UPDATE clubber_db.offers "
					   + "SET  Offer_Status = '" + OfferStatusIds.NotRelevant.getValue() + "'"
					   + " WHERE Auction_id ='" + auctionId + "'";
				stmt.executeUpdate(sql);
			}
		} 
		catch (SQLException e) {
			isSucceed = false;
			e.printStackTrace();
			
		}
		finally{
			disconnectFromDBServer();
		}
		return isSucceed;
	}

	public static String getUserEmailByID(Integer userIdToDisplay) {
	
		String userEmail = null;
		
		connectToDBServer();
		
		try {
			ResultSet rs = stmt.executeQuery("SELECT Email FROM clubber_db.users where id="+ userIdToDisplay +";");
			while (rs.next())
			{
				userEmail = rs.getString("Email");
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		finally{
			disconnectFromDBServer();
		}
				
		return userEmail;
	}

	public static List<AuctionData> getAuctionFilteredByEventType(
			Integer eventTypeId, Integer guestesQuantiny, Integer areaId) {
		List<AuctionData> auctionList = new LinkedList<AuctionData>();
		connectToDBServer();
		String sqlQuery;
		String guestesFilter = " auc.Guestes_Quantiny < " + guestesQuantiny;
		String areaFilter = " auc.area=" + areaId;
		String eventTypeFilter = " auc.Event_Type=" + eventTypeId;

		// build query:
		sqlQuery = "select auc.*, event_type.Name as event_type_name, auction_status.displayName,areas.Name,businesses.Name "
				+ "from auction auc LEFT JOIN businesses ON businesses.id = auc.Certain_Business "
				+ "LEFT JOIN event_type ON event_type.id = auc.Event_Type "
				+ "LEFT JOIN areas ON areas.id = auc.area "
				+ "LEFT JOIN auction_status ON auction_status.id = auc.Auction_Status "
				+ "where ";

		if (eventTypeId != 0) {
			sqlQuery = sqlQuery.concat(eventTypeFilter);
			// add "and" if needed:
			if (guestesQuantiny != 0 || areaId != 0) {
				sqlQuery = sqlQuery.concat(" and ");
			}
		}
		if (areaId != 0) {
			sqlQuery = sqlQuery.concat(areaFilter);
			// add "and" if needed:
			if (guestesQuantiny != 0) {
				sqlQuery = sqlQuery.concat(" and ");
			}
		}
		if (guestesQuantiny != 0) {
			sqlQuery = sqlQuery.concat(guestesFilter);
		}

		// no where in query- get all auctions
		if ((guestesQuantiny == 0) && (areaId == 0)
				&& (eventTypeId == 0)) {
			sqlQuery = "select auc.*, event_type.Name as event_type_name, auction_status.displayName,areas.Name,businesses.Name "
					+ "from auction auc LEFT JOIN businesses ON businesses.id = auc.Certain_Business "
					+ "LEFT JOIN event_type ON event_type.id = auc.Event_Type "
					+ "LEFT JOIN areas ON areas.id = auc.area "
					+ "LEFT JOIN auction_status ON auction_status.id = auc.Auction_Status ";
		}

		try {
			ResultSet rs = stmt.executeQuery(sqlQuery);

			while (rs.next()) {
				AuctionData auction = new AuctionData();
				auction.setEventDate(rs.getLong("Event_Date"));
				auction.setDescription(rs.getString("Description"));
				auction.setEventType(new IdWithName(
						rs.getInt("auc.Event_Type"), rs
								.getString("event_type_name")));
				auction.setId(rs.getInt("id"));
				auction.setAuctionStatus(new IdWithName(rs
						.getInt("auc.Auction_Status"), rs
						.getString("auction_status.displayName")));
				auction.setUserDetailsExpose(rs.getInt("Details_To_Display"));
				auction.setCreatedBy(new IdWithName(rs.getInt("Created_By"),
						null));
				String certainBusiness = rs.getString("businesses.Name");

				if (certainBusiness != null) {
					auction.setCertainBusiness(new IdWithName(rs
							.getInt("auc.Certain_Business"), certainBusiness));
				}
				auction.setDateFlexible(rs.getBoolean("Is_Date_Flexible"));
				auction.setExceptionsDescription(rs
						.getString("Exceptions_Description"));
				auction.setGuestesQuantiny(rs.getInt("Guestes_Quantiny"));
				auction.setSmoking(rs.getBoolean("Smoking"));
				auction.setMinAge(rs.getInt("Minimum_Age"));
				auction.setArea(new IdWithName(rs.getInt("auc.area"), rs
						.getString("areas.Name")));
				
				auctionList.add(auction);
			}

			for (int i = 0; i < auctionList.size(); i++) {
				auctionList.get(i).setMusicStyle(GetIdAndNameData("Select music_style.* from auction_music_style,music_style where auction_music_style.auction_id = "
						+ auctionList.get(i).getId()
						+ " and music_style.id = auction_music_style.music_style_id;"));
				auctionList.get(i).setBusinessType(GetIdAndNameData("Select * from auction_business_type,business_type where auction_business_type.auction_id = "
						+ auctionList.get(i).getId()
						+ " and business_type.id = auction_business_type.business_type_id;"));
				auctionList.get(i).setSittsType(GetIdAndNameData("Select sitts_type.* from auction_sits_type,sitts_type where auction_sits_type.auction_id = "
						+ auctionList.get(i).getId()
						+ "  and sitts_type.id = auction_sits_type.sits_id;"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}

		return auctionList;
	}
	
	public static LinkedList<IdWithName> getOfferStatus() {
		connectToDBServer();
		LinkedList<IdWithName> data = new LinkedList<IdWithName>();
		try {
			String query = "Select * from offer_status;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				data.add(new IdWithName(rs.getInt("Id"), rs
						.getString("displayName")));
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			disconnectFromDBServer();
		}
	}

	public static Integer addNewOffer(OfferData offer) {
		connectToDBServer();
		
		try {
			//created on = now :
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			
			//create new offer in db:
			String sqlOfferInsertion = String.format("insert into offers values(%d,'%s',%d,%d,%d,%s,%d,%d,%d,'%s')",
					null,offer.getDescription(),offer.getLineId().getId(),offer.getExpirationDate(),offer.getMaxArrivalHourAsLong(),null,offer.getAuctionId(), offer.getOfferStatusId().getId(), offer.getPrId().getId(), currentTimestamp);

			stmt.executeUpdate(sqlOfferInsertion,
					Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			Integer offerId = rs.getInt(1);

			// add relevant records to auction music style table:
			for (IdWithName item : offer.getOfferTreats()) {
				String sqlTreats = String.format(
						"insert into offer_treats values(%d,%d,%d)",
						null, offerId, item.getId());
				stmt.executeUpdate(sqlTreats);
			}

			return offerId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnectFromDBServer();
		}
	}

	public static UserData getOfferPR(Integer i_OfferId) {
		ClubberLogic.UserData userData = null;
		String userType;

		connectToDBServer();

		try {
			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM users U "
					+ "WHERE " + "U.id in (select Pr_id from offers where id='" + i_OfferId + "')");
			while (rs.next()) {
				userType = rs.getString("User_Type");
				if (userType.equals("PR"))
					userData = new PR();
				else
					userData = new Client();

				userData.setFirstName(rs.getString("first_Name"));
				userData.setBirthDate(rs.getLong("Birth_Date"));
				userData.setLastName(rs.getString("Last_Name"));
				userData.setGender((rs.getString("Gender")));
				userData.setUserType(rs.getString("User_Type"));
				userData.setEmail(rs.getString("Email"));
				userData.setId(rs.getInt("id"));
				userData.setImageUrl(rs.getString("User_Image"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectFromDBServer();
		}
		return userData;
	}
}
