package Concert_Settings;
/**
 * Manages constants used in the Concert_Settings package
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class ConcertConstants {

    /* Index of concert id in the array */
    public static final int ID_INDEX = 0;
    /* Index of concert date in the array */
    public static final int CONCERT_DATE_INDEX = 1;
    /* Index of concert time in the array */
    public static final int CONCERT_TIME_INDEX = 2;
    /* Index of artist name in the array */
    public static final int ARTIST_NAME_INDEX = 3;
    /* Index of venue type in the array */
    public static final int VENUE_INDEX = 4;
    /* Index of first price in the array */
    public static final int PRICE_START_INDEX = 5;
    /* Total number of price in the array */
    public static final int PRICE_ARRAY_LENGTH = 9;
    /* Index of customer id in the booking array */
    public static final int BOOKING_CUSTOMER_ID_INDEX = 1;
    /* Index of customer name in the booking array */
    public static final int BOOKING_CUST_NAME_INDEX = 2;
    /* Index of concert id in the booking array */
    public static final int BOOKING_CONCERT_ID_INDEX = 3;
    /* Label of the first row in the seating zone */
    public static final String FIRST_SEATING_ROW = "S1";
    /* Label of the first row in the standing zone */
    public static final String FIRST_STANDING_ROW = "T1";
    /* String label of VIP zone */
    public static final String VIP_ZONE = "VIP";
    /* String label of seating zone */
    public static final String SEATING_ZONE = "SEATING";
    /* String label of standing zone */
    public static final String STANDING_ZONE = "STANDING";
    /* Left bracket of the seat icon */
    public static final String SEAT_ICON_LEFT = "[";
    /* Right bracket of the seat icon */
    public static final String SEAT_ICON_RIGHT = "]";
    /* Icon of booked seat */
    public static final String BOOKED_SEAT_ICON = "[X]";
    /* Number of non-seat element in a row in the venue layout, including row label and aisle */
    public static final int NUM_NOT_SEAT_ICON = 6;
    /* Name of default venue */
    public static final String DEFAULT_VENUE_NAME = "default";
    /* Format of ticket zone heading */
    public static final String TICKET_HEADER_FORMAT = "---------- %8s ----------\n";
    /* Ticket zone delimiter */
    public static final String TICKET_DELIMITER = "------------------------------";
    /* Ticket info formatter */
    public static final String TICKET_FORMATTER = "%-14s%.1f\n";
    /* Left seat */
    public static final String LEFT_SEAT = "Left Seats:";
    /* Centre seat */
    public static final String CENTRE_SEAT = "Center Seats:";
    /* Right seat */
    public static final String RIGHT_SEAT = "Right Seats:";
    /* Index of VIP left section in the price array */
    public static final int VIP_LEFT_IDX = 6;
    /* Index of VIP mid section in the price array */
    public static final int VIP_MID_IDX = 7;
    /* Index of VIP right section in the price array */
    public static final int VIP_RIGHT_IDX = 8;
    /* Index of seat left section in the price array */
    public static final int SEAT_LEFT_IDX = 3;
    /* Index of seat mid section in the price array */
    public static final int SEAT_MID_IDX = 4;
    /* Index of seat right section in the price array */
    public static final int SEAT_RIGHT_IDX = 5;
    /* Index of stand left section in the price array */
    public static final int STAND_LEFT_IDX = 0;
    /* Index of stand mid section in the price array */
    public static final int STAND_MID_IDX = 1;
    /* Index of stand right section in the price array */
    public static final int STAND_RIGHT_IDX = 2;
    /* To string format */
    public static final String TO_STRING_FORMAT = "%-5d%-15s%-15s%-15s%-30s%-15d%-15d%-15d\n";
    /* Price array index increment */
    public static final int PRICE_ARRAY_INCREMENT = 3;
    /* Standing zone starting symbol */
    public static final String STANDING_LABEL = "T";
    /* Seating zone starting symbol */
    public static final String SEATING_LABEL = "S";
    /* Vip zone starting symbol */
    public static final String VIP_LABEL = "V";
    /* Price file separator */
    public static final String PRICE_SEPARATOR = ":";
    /* CSV file delimiter */
    public static final String CSV_DELIMITER = ",";
    /* Ticket information heading formatter */
    public static final String BOOKING_INFO_HEADER = "############### Ticket Id: %d ####################\n";
    /* Ticket information */
    public static final String BOOKING_INFO_HEADING = "%-5s%-15s%-15s%-10s%-10s%n";
    /* Ticket info separator */
    public static final String BOOKING_INTO_SEPARATOR = "##################################################";
    /* Length of information of booking */
    public static final int BOOKING_INFO_LENGTH = 5;
    /* Index of ticket id in the booking array */
    public static final int TICKET_ID_IDX = 0;
    /* Index of row number in the booking array */
    public static final int ROW_NUM_IDX = 1;
    /* Index of seat number in the booking array */
    public static final int SEAT_NUM_IDX = 2;
    /* Index of zone in the booking array */
    public static final int ZONE_IDX = 3;
    /* Index of price in the booking array */
    public static final int PRICE_IDX = 4;


}
