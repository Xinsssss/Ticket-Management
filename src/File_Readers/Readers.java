package File_Readers;

import User_Settings.*;
import Concert_Settings.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The abstract class of file readers
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public abstract class Readers {

    protected String[] args;
    protected String customerFilePath;
    protected String concertFilePath;
    protected String bookingFilePath;
    protected String[] venueFilePaths;
    protected List<String> venueNames;
    protected int fileStartIndex;

    /**
     * check if the argument input is valid
     */
    public abstract void checkArgsInput();

    /**
     * Getter of current user, either customer or admin
     */
    public abstract User getUser();

    /**
     * print welcome message of user mode
     */
    public abstract void printWelcome();

    /**
     * Create Booking object for each booking and store at corresponding concert
     * @param allConcerts list of all concerts
     * @param details details of one booking stored as a string array
     */
    protected abstract void loadNewBooking(List<Concert> allConcerts, String[] details);


    /**
     * Read, verify and store concert data
     * @return return a list of valid concert stored as Concert objects
     */
    public List<Concert> loadConcertData() {
        List<Concert> allConcerts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(concertFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(ReaderConstants.CSV_DELIMITER);
                if (details.length != ReaderConstants.CONCERT_DETAIL_LENGTH) {
                    System.out.println("Invalid Concert Files. Skipping this line.");
                    continue;
                }
                if (!details[ConcertConstants.ID_INDEX].matches(ReaderConstants.NUMERIC_REGEX)) {
                    System.out.println("Concert Id is in incorrect format. Skipping this line.");
                    continue;
                }
                Concert newConcert = new Concert(details);
                allConcerts.add(newConcert);
                if(!venueNames.contains(newConcert.getVenueName().toLowerCase())){
                    venueNames.add(newConcert.getVenueName().toLowerCase());
                }

            }
            return allConcerts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read and verify booking data, store them as list of string array, which we will later turn into
     * Booking and Ticket objects
     * @return return a list of string array, each array represents one booking
     */
    public List<String[]> loadBookingData(){
        List<String[]> preloadBookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(bookingFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(ReaderConstants.CSV_DELIMITER);
                if (details.length < ReaderConstants.MIN_BOOKING_LENGTH) {
                    System.out.println("Invalid booking Files. Skipping this line.");
                    continue;
                }
                if(!details[ReaderConstants.ID_IDX].matches(ReaderConstants.NUMERIC_REGEX)){
                    System.out.println("Ticket Id is in incorrect format. Skipping this line.");
                    continue;
                }
                int numberOfTickets = Integer.parseInt(details[ReaderConstants.NUM_TICKET_IDX]);
                int expectedLength = ReaderConstants.TICKET_LENGTH + numberOfTickets * ReaderConstants.TICKET_LENGTH;
                if (!(details.length == expectedLength)) {
                    System.out.println("Incorrect Number of Tickets. Skipping this line.");
                    continue;
                }
                preloadBookings.add(details);
            }
            return preloadBookings;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load samples of venue layout, we store them as SampleVenue object
     * @return return a list of sample venue layouts
     */
    public List<SampleVenues> loadVenueSamples(){
        List<SampleVenues> venueSamples = new ArrayList<>();
        String header = ReaderConstants.DEFAULT_VENUE_HEADER;
        int[] dimension = getDimension(ReaderConstants.DEFAULT_VENUE_PATH);
        SampleVenues defaultVenue = new SampleVenues(header, dimension);
        venueSamples.add(defaultVenue);
        if (venueFilePaths.length > 0) {
            for (String filePath : venueFilePaths) {
                Pattern pattern = Pattern.compile(ReaderConstants.VENUE_TYPE_REGEX);
                Matcher matcher = pattern.matcher(filePath);
                if (matcher.find()){
                    header = matcher.group(ReaderConstants.VENUE_TYPE_IDX);
                    if(venueNames.contains(header.toLowerCase())){
                        dimension = getDimension(filePath);
                        SampleVenues newVenue = new SampleVenues(header, dimension);
                        venueSamples.add(newVenue);
                    }
                }
            }
        }

        return venueSamples;
    }

    /**
     * Match and copy venue layout to concerts
     * @param venueSamples list of venue samples
     * @param allConcerts list of all concerts loaded
     * @return return the list of all concerts where each concert now has the correct venue layout
     */
    public List<Concert> loadVenueToConcert(List<SampleVenues> venueSamples, List<Concert> allConcerts){
        for(Concert concert : allConcerts){
            concert.setVenue(venueSamples);
        }
        return allConcerts;
    }

    /**
     * Load booking data to concerts
     * @param bookingDetails list of booking details
     * @param allConcerts list of all concerts
     * @return return list of all concerts where each concert now has corresponding bookings loaded
     */
    public List<Concert> loadBookingToConcert(List<String[]> bookingDetails, List<Concert> allConcerts){
        for(String[] detail : bookingDetails){
            loadNewBooking(allConcerts, detail);
        }
        return allConcerts;
    }

    /**
     * Read and store file paths
     */
    protected void readFilePaths() {
        this.customerFilePath = args[fileStartIndex];
        this.concertFilePath = args[++fileStartIndex];
        this.bookingFilePath = args[++fileStartIndex];
        this.venueFilePaths = Arrays.copyOfRange(args, ++fileStartIndex, args.length);
    }

    /**
     * Check if all file paths are valid
     */
    protected void checkFilePaths() {
        // Check if customer file exists
        if (fileNotExists(customerFilePath)) {
            throw new RuntimeException(customerFilePath + " (No such file or directory)");
        }
        // Check if concert file exists
        if (fileNotExists(concertFilePath)) {
            throw new RuntimeException(concertFilePath + " (No such file or directory)");
        }
        // Check if booking file exists
        if (fileNotExists(bookingFilePath)) {
            throw new RuntimeException(bookingFilePath + " (No such file or directory)");
        }
        // Check if venue files exist
        for (String filePath : venueFilePaths) {
            if (fileNotExists(filePath)) {
                throw new RuntimeException(filePath + " (No such file or directory)");
            }
        }
    }

    /**
     * Check if the file path is incorrect (doesn't exist)
     * @param filePath file path
     * @return true if doesn't exist, false if exist
     */
    private boolean fileNotExists(String filePath) {
        File file = new File(filePath);
        return !file.exists();
    }

    /**
     * Load and store customer data
     * @return a list of String array where each array represent one customer
     */
    protected List<String[]> loadCustomerData(){

        try (BufferedReader br = new BufferedReader(new FileReader(customerFilePath))) {
            List<String[]> customerData = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(ReaderConstants.CSV_DELIMITER);
                if (details.length != ReaderConstants.CUSTOMER_LENGTH) {
                    System.out.println("Invalid Customer Files. Skipping this line.");
                    continue;
                }
                if (!details[ConcertConstants.ID_INDEX].matches(ReaderConstants.NUMERIC_REGEX)) {
                    System.out.println("Customer Id is in incorrect format. Skipping this line.");
                    continue;
                }
                customerData.add(details);
            }
            return customerData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create Booking object for each booking and store at corresponding concert
     * @param allConcerts list of all concerts
     * @param details details of one booking stored as a string array
     */
    /**
    private void loadNewBooking(List<Concert> allConcerts, String[] details) {
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
    }**/

    /**
     * Get the dimension of a concert
     * @param filePath file path to a venue layout file
     * @return an integer array storing: number of rows in each zone and number of columns in each section
     */
    private int[] getDimension (String filePath){
        int[] dimension = new int[ReaderConstants.DIMENSION_LENGTH];
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            boolean firstLine = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if(!line.isEmpty()) {
                    if (line.startsWith("V")) dimension[ReaderConstants.VIP_ZONE_ROW_IDX]++;
                    else if (line.startsWith("S")) dimension[ReaderConstants.SEAT_ZONE_ROW_IDX]++;
                    else if (line.startsWith("T")) dimension[ReaderConstants.STAND_ZONE_ROW_IDX]++;
                    else {
                        System.out.println("Invalid Zone Type. Skipping this line.");
                        continue;
                    }
                    if (firstLine) {
                        firstLine = false;
                        String[] row = line.split(ReaderConstants.VENUE_DELIMITER);
                        int rowStartIndex = ReaderConstants.ROW_START_IDX;
                        dimension[ReaderConstants.LEFT_COL_IDX] =
                                row[rowStartIndex].split(ReaderConstants.SEAT_ICON_REGEX).length;
                        dimension[ReaderConstants.MID_COL_IDX]
                                = row[++rowStartIndex].split(ReaderConstants.SEAT_ICON_REGEX).length;
                        dimension[ReaderConstants.RIGHT_COL_IDX]
                                = row[++rowStartIndex].split(ReaderConstants.SEAT_ICON_REGEX).length;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return dimension;
    }
}