package Concert_Settings;

import File_Readers.SampleVenues;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stores information of one concert
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class Concert {

    private final int concertId;
    private final String date;
    private final String time;
    private final String artistName;
    private final String venueName;
    private double[] priceArray;
    private final List<Booking> allBookings;
    private int numBooking;
    private Venue venue;
    private int leftCol;
    private int middleCol;

    /**
     * Constructor
     * @param details information of one concert read from the file
     */
    public Concert(String[] details) {
        this.concertId = Integer.parseInt(details[ConcertConstants.ID_INDEX]);
        this.date = details[ConcertConstants.CONCERT_DATE_INDEX];
        this.time = details[ConcertConstants.CONCERT_TIME_INDEX];
        this.artistName = details[ConcertConstants.ARTIST_NAME_INDEX];
        this.venueName = details[ConcertConstants.VENUE_INDEX];
        String[] priceSubArray = Arrays.copyOfRange(details, ConcertConstants.PRICE_START_INDEX, details.length);
        setPriceArray(priceSubArray);
        this.allBookings = new ArrayList<>();
        this.numBooking = 0;
    }

    /**
     * Read in samples of venue layout, find the one that matches the venue name of this concert, if no match found,
     * we load the default venue, and copy the information
     * @param venueSamples list of venue samples
     */
    public void setVenue(List<SampleVenues> venueSamples) {
        SampleVenues defaultVenue = null;
        SampleVenues uniqueVenue = null;

        for (SampleVenues venueSample : venueSamples) {
            if (venueSample.getVenueType().equals(ConcertConstants.DEFAULT_VENUE_NAME)) {
                defaultVenue = venueSample;
            }
            if (venueSample.getVenueType().equals(this.venueName.toLowerCase())) {
                uniqueVenue = venueSample;
                break;
            }
        }
        if (uniqueVenue != null) {
            int sRowStartIndex = uniqueVenue.getVIPRowNum();
            int tRowStartIndex = uniqueVenue.getSEATRowNum() + sRowStartIndex;
            this.leftCol = uniqueVenue.getLeftCol();
            this.middleCol = uniqueVenue.getMiddleCol();
            this.venue = new Venue(uniqueVenue.copyVenueLayout(),sRowStartIndex,tRowStartIndex);
        } else if (defaultVenue != null) {
            int sRowStartIndex = defaultVenue.getVIPRowNum();
            int tRowStartIndex = defaultVenue.getSEATRowNum() + sRowStartIndex;
            this.leftCol = defaultVenue.getLeftCol();
            this.middleCol = defaultVenue.getMiddleCol();
            this.venue = new Venue(defaultVenue.copyVenueLayout(),sRowStartIndex,tRowStartIndex);
        }
    }


    /**
     * Add new booking to this concert, update booked seat in the venue layout and update the number of booking
     * @param booking this booking
     */
    public void addBooking(Booking booking) {
        allBookings.add(booking);
        venue.updateBookedSeat(booking);
        this.numBooking = numBooking + 1;
    }

    /**
     * Convert the information of this concert to a string
     */
    public void printString() {
        String output = String.format(ConcertConstants.TO_STRING_FORMAT, concertId, date, artistName, time,
                venueName, venue.getTotalSeat(), venue.getSeatBooked(), (venue.getTotalSeat() - venue.getSeatBooked()));
        System.out.println(output);
    }

    /**
     * Print the cost of tickets of this concert
     */
    public void showTicketCosts(){
        double VIP_left = priceArray[ConcertConstants.VIP_LEFT_IDX];
        double VIP_mid = priceArray[ConcertConstants.VIP_MID_IDX];
        double VIP_right = priceArray[ConcertConstants.VIP_RIGHT_IDX];
        double seat_left = priceArray[ConcertConstants.SEAT_LEFT_IDX];
        double seat_mid = priceArray[ConcertConstants.SEAT_MID_IDX];
        double seat_right = priceArray[ConcertConstants.SEAT_RIGHT_IDX];
        double stand_left = priceArray[ConcertConstants.STAND_LEFT_IDX];
        double stand_mid = priceArray[ConcertConstants.STAND_MID_IDX];
        double stand_right = priceArray[ConcertConstants.STAND_RIGHT_IDX];
        System.out.printf(ConcertConstants.TICKET_HEADER_FORMAT, ConcertConstants.STANDING_ZONE);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.LEFT_SEAT, stand_left);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.CENTRE_SEAT, stand_mid);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.RIGHT_SEAT, stand_right);
        System.out.println(ConcertConstants.TICKET_DELIMITER);
        System.out.printf(ConcertConstants.TICKET_HEADER_FORMAT, ConcertConstants.SEATING_ZONE);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.LEFT_SEAT,seat_left);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.CENTRE_SEAT, seat_mid);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.RIGHT_SEAT, seat_right);
        System.out.println(ConcertConstants.TICKET_DELIMITER);
        System.out.printf(ConcertConstants.TICKET_HEADER_FORMAT, ConcertConstants.VIP_ZONE);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.LEFT_SEAT, VIP_left);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.CENTRE_SEAT, VIP_mid);
        System.out.printf(ConcertConstants.TICKET_FORMATTER, ConcertConstants.RIGHT_SEAT, VIP_right);
        System.out.println(ConcertConstants.TICKET_DELIMITER);

    }

    /**
     * Print the layout of this venue
     */
    public void printLayoutDiagram(){
        venue.printLayoutDiagram();
    }

    /**
     * Admin can update the price of this concert
     * @param zone zone which admin wants to update
     * @param leftPrice new price of the left section
     * @param middlePrice new price of the centre section
     * @param rightPrice new price of the right section
     */
    public void updatePrice(String zone, double leftPrice, double middlePrice, double rightPrice){
        int priceIndex = 0;
        if(!zone.equals(ConcertConstants.STANDING_ZONE)){
            priceIndex += ConcertConstants.PRICE_ARRAY_INCREMENT;
        }
        if(zone.equals(ConcertConstants.VIP_ZONE)){
            priceIndex += ConcertConstants.PRICE_ARRAY_INCREMENT;
        }
        this.priceArray[priceIndex++] = leftPrice;
        this.priceArray[priceIndex++] = middlePrice;
        this.priceArray[priceIndex] = rightPrice;
    }

    /**
     * Calculate the sum of prices of tickets booked in this concert
     * @return sum of prices
     */
    public double sumOfPrice(){
        double sumofAllPrices = 0.0;
        for(Booking booking : allBookings){
            sumofAllPrices += booking.getTotalPrice();
        }
        return sumofAllPrices;
    }

    /**
     * Book a seat in this concert
     * @param bookingId auto-generated booking Id
     * @param customerId Id of the customer who's making this booking
     * @param customerName Name of the customer who's making this booking
     * @param bookingFilePath path to the booking file
     * @param keyboard scanner
     */
    public void bookSeat(int bookingId, int customerId, String customerName,String bookingFilePath, Scanner keyboard){
        Booking newList = new Booking(bookingId, customerId, customerName,concertId);
        printLayoutDiagram();
        System.out.print("Enter the aisle number: ");
        String aisle = keyboard.nextLine();
        System.out.print("Enter the seat number: ");
        int seatNumber = Integer.parseInt(keyboard.nextLine());
        System.out.print("Enter the number of seats to be booked: ");
        int numberBooked = Integer.parseInt(keyboard.nextLine());
        for(int i = 0; i<numberBooked;i++){
            double price = determinePrice(aisle, seatNumber+i);
            newList.addBooking(i+1,aisle,seatNumber+i, price);
        }
        addBooking(newList);
        writeNewBooking(newList, bookingFilePath);
    }

    /**
     * Getter of all booking stored in this concert
     * @return list of all bookings
     */
    public List<Booking> getAllBooking(){
        return allBookings;
    }

    /**
     * Getter of concert date
     * @return date of this concert
     */
    public String getDate(){
        return date;
    }

    /**
     * Getter of artist name
     * @return artist name
     */
    public String getArtistName(){
        return artistName;
    }

    /**
     * Getter of the name of this venue
     * @return venue name
     */
    public String getVenueName(){
        return venueName;
    }

    /**
     * Getter of the time of this concert
     * @return time of this concert
     */
    public String getTime(){
        return time;
    }

    /**
     * Determine the price of a seat
     * @param aisle aisle of this seat
     * @param seatNumber seat number of this seat
     * @return price of this seat
     */
    private double determinePrice(String aisle, int seatNumber){
        int priceIndex = 0;
        if(!aisle.startsWith(ConcertConstants.STANDING_LABEL)){
            priceIndex += ConcertConstants.PRICE_ARRAY_INCREMENT;
        }
        if(aisle.startsWith(ConcertConstants.VIP_LABEL)) {
            priceIndex += ConcertConstants.PRICE_ARRAY_INCREMENT;
        }
        if(seatNumber > leftCol){
            priceIndex++;
        }
        if(seatNumber > (middleCol + leftCol)){
            priceIndex++;
        }
        return priceArray[priceIndex];

    }

    /**
     * Turn loaded price information from the concert file to an array
     * @param priceSubArray array storing all prices
     */
    private void setPriceArray(String[] priceSubArray) {
        int priceIndex = 0;
        double[] priceArray = new double[ConcertConstants.PRICE_ARRAY_LENGTH];
        double price = 0.0;
        for (String prices : priceSubArray) {
            // Split the string on colon
            String[] parts = prices.split(ConcertConstants.PRICE_SEPARATOR);
            // The first part is the type, skip it using index 1 for the start of integers
            for (int i = 1; i < parts.length; i++) {
                // Parse each integer string to an integer and print it
                price = Double.parseDouble(parts[i]);
                priceArray[priceIndex] = price;
                priceIndex++;
            }
        }
        this.priceArray = priceArray;
    }

    /**
     * Write a new booking to the booking file
     * @param newBooking new booking
     * @param bookingFilePath file path to the booking file
     */
    private void writeNewBooking(Booking newBooking, String bookingFilePath){
        String csv = String.join(ConcertConstants.CSV_DELIMITER, newBooking.toFileArray());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingFilePath, true))) {
            writer.write(csv);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}