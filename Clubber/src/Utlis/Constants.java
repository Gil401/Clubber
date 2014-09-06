package Utlis;

public class Constants {
	
	 //DB-Data types:
	 public static final String DB_DATA_GET_LINE_DAY = "GetDBData-getAvailableDay";
	 public static final String DB_DATA_OFFER_PER_AUCION = "GetDBData-offerPerAuction";
	 public static final String DB_DATA_MY_PR_LINES = "GetDBData-loadMyLinesByPRId";
	 public static final String DB_DATA_NEW_AUCTION= "GetDBData-NewAuction";
	 public static final String DB_DATA_PR_MY_LINES_SERACH = "GetDBData-loadOffersByPrData";
	 public static final String DB_DATA_PR_LINES = "GetDBData-PRLines";
	 public static final String DB_DATA_NEW_OFFER= "GetDBData-NewOffer";
	 public static final String DB_DATA_MY_AUCTIONS= "GetDBData-MyAuctions";
	 public static final String DB_DATA_AUCTION_MANAGEMENT ="GetDBData-AuctionManagement";
	 public static final String DB_DATA_OFFER_REVIEW= "GetDBData-OfferReview";
	 public static final String DB_DATA_MESSAGES="GetDBData-Messages";
	 public static final String DB_DATA_ADD_MESSAGE="GetDBData-AddMessage";
	 public static final String DB_DATA_MAIN_LINES = "GetDBData-WelcomeLines";
	 public static final String DB_DATA_AUCTION_BY_ID = "GetDBData-GetAuctionById";
	 public static final String DB_DATA_LINE_PROFILE= "GetDBData-LineProfile";
	 public static final String DB_DATA_ALL_BUSINESSES= "DBDataAllBusinesses";
	 public static final String DB_DATA_GET_BUSINESS_AREAS_DATA= "DBDataGetBusinessAreasData";
	 public static final String DB_DATA_GET_BUSINESS_CITIES_DATA= "DBDataGetBusinessCitiesData";
	 public static final String DB_DATA_GET_BUSINESSES_TYPE_DATA= "DBDataGetBusinessesTypeData";
	 public static final String DB_DATA_USER_PROFILE= "DBDataUserProfile";
	 public static final String DB_DATA_PR_PROFILE_REVIEW = "DBDataPrProfileReview";
	 public static final String DB_DATA_CLIENT_PROFILE_REVIEW = "DBDataClientProfileReview";
	 public static final String DB_DATA_RECOMENDED_LINES = "DBDataRecomendedLines";	
	 public static final String DB_DATA_GET_BUSINESS_DATA = "DBDataGetBusinessData";
	 public static final String DB_DATA_GET_MUSIC_STYLE_DATA = "DBDataGetMusicStyleData";
	 public static final String DB_DATA_GET_JOIN_LINE_REQUEST_DATA = "DBDataGetJoinRequestData";
	 public static final String DB_DATA_AUCTION_REVIEW = "GetDBData-AuctionReview";
	 public static final String DB_DATA_BUSINESS_LST = "GetDBData-BusinessLst";
	 
	 // PrProfile fields
	 public static final String USER_DATA= "userData";
	 public static final String PR_PROFILE_VISIT_REQUEST= "visitPrProfile";
		 
	 //Session data:
	 public static final String AUCTION_TO_DISPLAY_ID= "AuctionToDisplayId";
	 public static final String OFFER_TO_DISPLAY_ID= "OfferToDisplayId";
	 public static final String USER_TO_DISPLAY_ID= "UserToDisplayId";
	 
	 //Item types:
	 public static final String  OFFER_ITEM_CLICKED= "OfferItemClicked";
	 public static final String  AUCTION_ITEM_CLICKED= "AuctionItemClicked";
	 
	 //Request parameters:
	 public static final String ITEM_ID= "ItemID";
	 public static final String CLICKED_ITEM_TYPE= "ClickedItemType";
	 
	 //New auction fields:
	 public static final String EVENT_TYPE= "EventType";
	 public static final String MUSIC_STYLE= "MusicStyle";
	 public static final String MUSIC_STYLE_LIST= "MusicStyleList";
	 public static final String DATEPICKER= "Datepicker";
	 public static final String FLEXIBLE_DATE= "IsFlexibleDate";
	 public static final String GUESTS_QUANTITY= "GuestsQuantity";
	 public static final String EXCEPTION_DESCRIPTION= "ExceptionsDescription";
	 public static final String MIN_AGE= "MinAge";
	 public static final String AREA= "Area";
	 public static final String BUSINESS_TYPE= "BusinessType";
	 public static final String BUSINESS_TYPE_LIST= "BusinessTypeList";
	 public static final String CERTAIN_BUSINESS= "CertainBusiness";
	 public static final String SMOKING= "Smoking";
	 public static final String SITTS_TYPE= "SittsType";
	 public static final String SITS_TYPE_LIST= "SitsTypeList";
	 public static final String GENERAL_DESCRIPTION= "GeneralDescription";
	 
	// Sign Up fields 
	 public static final String WHO_AM_I= "whoAmI";
	 public static final String USER_NAME= "userName";
	 public static final String FIRST_NAME= "firstName";
	 public static final String LAST_NAME= "lastName";
	 public static final String GENDER= "gender";
	 public static final String BIRTHDATE= "birthdate";
	 public static final String PHONE_NUMBER= "phoneNumber";
	 public static final String EMAIL= "email";
	 public static String PASSWORD = "Password";
	 public static String IMAGE = "User_Image";
	 
	 // Message Password fields
	 public static final String MESSAGE_TEXT = "messageText";
	 
	 // Login fields
	 public static final String LOGIN_FAILED = "loginFailed";
	 
	 // Password Retrival fields
	 public static String STATUS_MESSAGE = "statusMessageLabel";
	 public static String CLUBBER_USER_NAME = "clubber.cop";
	 public static String CLUBBER_USER_PASSWORD = "clubbermta";
	 
	 // ChangePassword fields
	 public static String OLD_PASSWORD = "oldPassword";
	 public static final String NEW_PASSWORD= "newPassword";
	 
	 //outgoing message fields
	 public static String OUTGOING_MESSAGE_DESCRIPITON= "OutGoingMessageDescription";
	 
	 //search Auction fields
	 public static String SEARCH_BY_MY_LINES= "searchByMyLines";
	 public static String DB_DATA_ALL_AUCTIONS = "DBDataAllAuctions";
	 
	 // business profile fields	 
	 public static String BUSINESS_ID= "id";
	 public static String BUSINESS_NAME= "name";
	 public static String BUSINESS_TYPE_ID= "BusinessTypeId";
	 public static String BUSINESS_TYPE_NAME= "BusinessTypeName";
	 public static String BUSINESS_AREA_ID= "areaId";
	 public static String BUSINESS_AREA_NAME= "areaName";
	 public static String BUSINESS_CITY_ID= "cityId";
	 public static String BUSINESS_CITY_NAME= "cityName";
	 public static String BUSINESS_STREET_ID= "streetId";
	 public static String BUSINESS_STREET_NAME= "streetName";
	 public static String BUSINESS_HOME_NUMBER= "homeNumber";
	 public static String BUSINESS_PHONE_NUMBER= "phoneNumber";
	 public static String BUSINESS_DESCRIPTION= "description";
	 public static String BUSINESS_PHOTO= "photo";
	 //business photo

	 //line fields
	 public static String LINE_ID= "id";
	 public static String LINE_NAME= "name";
	 public static String LINE_BUSINEES_ID= "businessId";
	 public static String LINE_BUSINEES_NAME= "businessName";
	 public static String LINE_MUSIC_STYLE_NAME= "musicStyle";
	 public static String LINE_DAY_IN_WEEK= "dayInWeek";
	 public static String LINE_START_DATE= "startDate";
	 public static String LINE_END_DATE= "endDate";
	 public static String LINE_MIN_AGE= "minAge";
	 public static String LINE_DESCRIPTION= "description";
	 public static String LINE_ETRANCEFEE= "etranceFee";
	 public static String LINE_DJ= "DJ";
	 public static String LINE_PHOTO= "photo";
	 
	 //line update fields
	 public static String MUSIC_STYLE_EDT= "musicStyleEdt";
	 public static String LINE_NAME_EDT="LineName";
	 public static String BUSINESSS_ID_EDT="BusinessId";
	 public static String START_DATE_EDT="StartDate";
	 public static String END_DATE_EDT= "EndDate";
	 public static String MIN_AGE_EDT="MinAge";
	 public static String DESCRIPTION_EDT= "Description";
	 public static String ENTRANCE_EDT= "EtranceFee";
	 public static String DJ_EDT= "DJ";
	 public static String ID_EDT="Id";
	 public static String DAY_EDT="Day";
	 
	 //Offer update fields

	 public static String END_DATE_OFFER_EDT= "EndDate";
	 public static String DESCRIPTION_OFFER__EDT= "Description";
	 public static String ID_OFFER__EDT="Id";
	 public static String MAX_ARRIVAL_OFFER_EDT="MaxArrivalTimeAsLong";
	 public static String LINE_NAME_OFFER_EDT="LineName";
	 public static String TREATS_OFFER_EDT="Treats";
	 
	 //accept Offer
	 public static String ACCEPTED_OFFER_ID="AccptedOfferId";
	
	 //Offer Review
	 public static String CURR_AUCTION_ID="currAuctionID";
	 
	 //images
	 public static final String IMAGES_DIR = "images";
	 public static final String USER_IMAGES_DIR = "/Uploaded/userImages/";
	 public static final String LINE_IMAGES_DIR = "/Uploaded/lineImages/";
	 public static final String BUSSINESS_IMAGES_DIR = "/Uploaded/bussinessImages/";
		
	 //auction management
	 public static final String AUCTION_MANAGEMENT_OFFER_NOT_RELEVANT= "AuctionManagementOfferNotRelevant";
	 public static final String AUCTION_MANAGEMENT_AUCTION_NOT_RELEVANT= "AuctionManagementAuctionNotRelevant";
	 public static final String AUCTION_MANAGEMENT_ACTIVATE_AUCTION= "AuctionManagementActivateAuction";

	//session actions
	 public static final String SESSION_ACTIONS_SET_USER_IN_SESSION = "SessionActions-SetUserInSession";

}

