package File_Readers;

/**
 * Manage constants used in the File_Readers package
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class ReaderConstants {

    /* csv files are separated by comma */
    public static final String CSV_DELIMITER = ",";
    /* Regex used to match if Id in csv files are numeric */
    public static final String NUMERIC_REGEX = "\\d+";
    /* Path to default venue layout */
    public static final String DEFAULT_VENUE_PATH = "../assets/venue_default.txt";
    /* Correct length of concert information */
    public static final int CONCERT_DETAIL_LENGTH = 8;
    /* Match the type of venue in the file path */
    public static final String VENUE_TYPE_REGEX = ".*_(.*?)\\.";
    /* Index extracting the type of venue from the regex matched phrase */
    public static final int VENUE_TYPE_IDX = 1;
    /* Default venue type */
    public static final String DEFAULT_VENUE_HEADER = "default";
    /* Correct length of customer information */
    public static final int CUSTOMER_LENGTH = 3;
    /* Minimum length of one booking detail is 10, basic info of one booking (length 5) + one ticket (length 5) */
    public static final int MIN_BOOKING_LENGTH = 10;
    /* Index of number of ticket in the booking detail */
    public static final int NUM_TICKET_IDX = 4;
    /* Length of info of one ticket */
    public static final int TICKET_LENGTH = 5;
    /* Index of ids (booking, concert, customer) */
    public static final int ID_IDX = 1;
    /* Index to Id increment */
    public static final int ID_TO_IDX = 1;
    /* Starting index of ticket info in the booking details */
    public static final int TICKET_INFO_START_INDEX = 5;
    /* Dimension length */
    public static final int DIMENSION_LENGTH = 6;
    /* Index of number of rows in the VIP zone in the dimension array */
    public static final int VIP_ZONE_ROW_IDX = 0;
    /* Index of number of rows in the SEATING zone in the dimension array */
    public static final int SEAT_ZONE_ROW_IDX = 1;
    /* Index of number of rows in the STADNING zone in the dimension array */
    public static final int STAND_ZONE_ROW_IDX = 2;
    /* Index of number of columns in the left section in the dimension array */
    public static final int LEFT_COL_IDX = 3;
    /* Index of number of columns in the left section in the dimension array */
    public static final int MID_COL_IDX = 4;
    /* Index of number of columns in the left section in the dimension array */
    public static final int RIGHT_COL_IDX = 5;
    /* Venue layout text delimiter */
    public static final String VENUE_DELIMITER = "\\s+";
    /* Regex matching seats in a venue text */
    public static final String SEAT_ICON_REGEX = "\\]\\[";
    /* Starting index of seats in the venue row separated by space*/
    public static final int ROW_START_IDX = 1;
    /* Index of customer id in the sub argument */
    public static final int CUSTOMER_ID_IDX = 0;
    /* Index of customer password in the sub argument */
    public static final int PASSWORD_IDX = 1;
    /* The index of first file path in the argument array if there's no id and password before it */
    public static final int DEFAULT_FILE_PATH_BEGIN_IDX = 0;
    /* The index of first file path in the argument array if there is id and password before it */
    public static final int CUSTOMER_FILE_PATH_BEGIN_IDX = 2;

}
