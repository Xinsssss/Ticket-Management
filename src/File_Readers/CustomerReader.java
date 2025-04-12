package File_Readers;
import Concert_Settings.Booking;
import Concert_Settings.Concert;
import Concert_Settings.ConcertConstants;
import User_Settings.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

/**
 * File reader in customer mode
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class CustomerReader extends Readers {

    private String customerId;
    private String password;
    private final List<String[]> customerData;
    private final Scanner keyboard;
    private Customer currentCustomer;

    /**
     * Constructor
     * @param subArgs argument from command line with user mode removed
     * @param keyboard scanner
     * @throws InvalidUserException when the customer id or password is incorrect
     */
    public CustomerReader(String[] subArgs, Scanner keyboard) throws InvalidUserException {
        args = subArgs;
        this.customerId = null;
        this.password = null;
        this.keyboard = keyboard;
        this.venueNames = new ArrayList<>();
        checkArgsInput();
        readFilePaths();
        checkFilePaths();
        this.customerData = loadCustomerData();
        setUser();

    }

    /**
     * If we received an input for customer id and password, then we check if they are correct
     */
    @Override
    public void checkArgsInput() {
        // check if customer id and password present in the command line
        if (!args[0].contains("/") && !args[0].contains("\\")) {
            customerId = args[ReaderConstants.CUSTOMER_ID_IDX];
            password = args[ReaderConstants. PASSWORD_IDX];
            fileStartIndex = ReaderConstants.CUSTOMER_FILE_PATH_BEGIN_IDX;
        }
        else{
            fileStartIndex = ReaderConstants.DEFAULT_FILE_PATH_BEGIN_IDX;
        }
    }

    /**
     * Print the welcome message of customer mode
     */
    public void printWelcome(){
        System.out.println("Welcome " + currentCustomer.getCustomerName() + " to Ticket Management System");
    }
    

    /**
     * Getter that returns the current user to the main runner.
     * @return current customer who's running this ticket management engine
     */
    public Customer getUser() {
        return currentCustomer;
    }

    /**
     * Create a Customer object. If correct customer ID and password are given in the command line, then we
     * use them. If not, we need to prompt the user to create a new customer and save it in the customer file.
     * @throws InvalidUserException if the ID or password is incorrect
     */
    private void setUser() throws InvalidUserException {
        try {
            if (customerId == null && password == null) {
                System.out.print("Enter your name: ");
                String customerName = keyboard.nextLine();
                System.out.print("Enter your password: ");
                String password = keyboard.nextLine();
                int intCustomerId = customerData.size() + 1;
                appendCustomerToFile(intCustomerId, customerName, password);
                this.currentCustomer = new Customer(intCustomerId, customerName,bookingFilePath);
            } else {
                int intCustomerId = Integer.parseInt(customerId);
                if (intCustomerId > customerData.size()) {
                    throw new InvalidUserException("Customer does not exist. Terminating Program");
                }
                int IdToIndex = intCustomerId - 1;
                if (!customerData.get(IdToIndex)[2].equals(password)) {
                    throw new InvalidUserException("Incorrect Password. Terminating Program");
                }
                this.currentCustomer = new Customer(intCustomerId, customerData.get(IdToIndex)[1],bookingFilePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        if(currentCustomer!=null){
            if(customerId == currentCustomer.getCustomerId()){
                currentCustomer.setNumBooked(bookingId, concertId);
            }
        }
        Booking newBooking = new Booking(bookingId, customerId, customerName, concertId);
        for (int i = ReaderConstants.TICKET_INFO_START_INDEX; i < details.length; i += ReaderConstants.TICKET_LENGTH) {
            String[] bookingInfo = new String[ReaderConstants.TICKET_LENGTH];
            System.arraycopy(details, i, bookingInfo, 0,ReaderConstants.TICKET_LENGTH);
            newBooking.addBooking(bookingInfo);
        }
        allConcerts.get(concertId - ReaderConstants.ID_TO_IDX).addBooking(newBooking);
    }

    /**
     * If we created a new customer, then we need to save it to the customer file
     * @param customerId auto-generated customer Id
     * @param name inputted customer name
     * @param password inputted password
     * @throws IOException if the file path to the customer file is incorrect
     */
    private void appendCustomerToFile(int customerId, String name, String password) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(customerFilePath, true));
        writer.write(customerId+ReaderConstants.CSV_DELIMITER+name+ReaderConstants.CSV_DELIMITER+password);
        writer.newLine();
    }

}