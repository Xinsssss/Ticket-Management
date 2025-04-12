package File_Readers;

import Concert_Settings.Booking;
import Concert_Settings.Concert;
import Concert_Settings.ConcertConstants;
import User_Settings.Admin;

import java.util.List;

public class AdminReader extends Readers {

    private final Admin currentAdmin;

    /**
     * Constructor
     * @param subArgs argument from command line with user mode removed
     */
    public AdminReader(String[] subArgs){
        args = subArgs;
        this.currentAdmin = new Admin();;
        checkArgsInput();
        readFilePaths();
        checkFilePaths();
    }

    /**
     * In admin mode, the file path always starts from index 0 in the sub-argument array
     */
    @Override
    public void checkArgsInput() {
        fileStartIndex = ReaderConstants.DEFAULT_FILE_PATH_BEGIN_IDX;
    }

    /**
     * Print the welcome message of admin mode
     */
    public void printWelcome(){
        System.out.println("Welcome to Ticket Management System Admin Mode.");
    }
    /**
     * Getter of the Admin object
     * @return
     */
    public Admin getUser(){
        return currentAdmin;
    }

    /**
     * Create Booking object for each booking and store at corresponding concert
     * @param allConcerts list of all concerts
     * @param details details of one booking stored as a string array
     */
    @Override
    protected void loadNewBooking(List<Concert> allConcerts, String[] details) {
        int bookingId = Integer.parseInt(details[ConcertConstants.ID_INDEX]);
        int customerId = Integer.parseInt(details[ConcertConstants.BOOKING_CUSTOMER_ID_INDEX]);
        String customerName = details[ConcertConstants.BOOKING_CUST_NAME_INDEX];
        int concertId = Integer.parseInt(details[ConcertConstants.BOOKING_CONCERT_ID_INDEX]);
        Booking newBooking = new Booking(bookingId, customerId, customerName, concertId);
        for (int i = ReaderConstants.TICKET_INFO_START_INDEX; i < details.length; i += ReaderConstants.TICKET_LENGTH) {
            String[] bookingInfo = new String[ReaderConstants.TICKET_LENGTH];
            System.arraycopy(details, i, bookingInfo, 0,ReaderConstants.TICKET_LENGTH);
            newBooking.addBooking(bookingInfo);
        }
        allConcerts.get(concertId - ReaderConstants.ID_TO_IDX).addBooking(newBooking);
    }


}
