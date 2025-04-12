package User_Settings;

/**
 * Manages constants used in the User_Settings package
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class UserConstants {

    /* Exit the concert selection menu */
    public static final int EXIT_USER_MODE = 0;

    /* String formatter for showing list of booking info*/
    public static final String VIEW_TICKET_FORMATTER = "%-5s%-15s%-15s%-10s%-15s%-15s%-10s%n";
    /* separator of the booking list*/
    public static final String VIEW_TICKET_SEPARATOR = "---------------------------------------------------------------------------------------------------------------------------";
    /* Initial size of the number of booking array */
    public static final int CUSTOMER_BOOKING_COUNT_INI_CAP = 10;
    /* Size of expansion of the number of booking array */
    public static final int CUSTOMER_BOOKING_COUNT_EXPAND_STEP = 5;
    /* adjust between id to index */
    public static final int ID_TO_INDEX = 1;

    /* customer select option 1 to view ticket price */
    public static final int CUSTOMER_VIEW_COST = 1;
    /* customer select option 2 to view the venue layout of this concert */
    public static final int CUSTOMER_VIEW_LAYOUT = 2;
    /* customer select option 3 to book seat */
    public static final int CUSTOMER_BOOK_SEAT = 3;
    /* customer select option 4 to view tickets they booked */
    public static final int CUSTOMER_VIEW_BOOK = 4;

    /* admin select option 1 to view list of concerts */
    public static final int ADMIN_VIEW_ALL_CONCERTS = 1;
    /* admin select option 2 to update the price of a concert */
    public static final int ADMIN_UPDATE_PRICE = 2;
    /* admin select option 3 to view all tickets booked for a concert */
    public static final int ADMIN_VIEW_BOOK = 3;
    /* admin select option 4 to see the total price booked for a concert */
    public static final int ADMIN_TOTAL_PAYMENT = 4;
    /* both customer and admin select option 5 to exit user mode */
    public static final int EXIT = 5;


}
