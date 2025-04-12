package Concert_Settings;

import java.util.List;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * Stores one booking, a booking can have multiple tickets
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class Booking {


    private final int bookingId;
    private final int customerId;
    private final String customerName;
    private final int concertId;
    private int totalTickets;
    private final List<Ticket> bookingList;
    private double totalPrice;

    /**
     * Constructor
     * @param bookingId id of this booking
     * @param customerId id of the customer who made this booking
     * @param customerName name of ths customer who made this booking
     * @param concertId id of the concert where this booking is for
     */
    public Booking(int bookingId, int customerId, String customerName, int concertId){
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.concertId = concertId;
        this.totalTickets = 0;
        this.bookingList = new ArrayList<>();
        this.totalPrice = 0;
    }

    /**
     * Print all tickets in this booking
     */
    public void printTicket(){
        System.out.printf(ConcertConstants.BOOKING_INFO_HEADER, bookingId);
        System.out.printf(ConcertConstants.BOOKING_INFO_HEADING, "Id","Aisle Number","Seat Number","Seat Type","Price");
        System.out.println(ConcertConstants.BOOKING_INTO_SEPARATOR);
        for(Ticket ticket :bookingList){
            ticket.printString();
        }
        System.out.println(ConcertConstants.BOOKING_INTO_SEPARATOR);
        System.out.println();
    }

    /**
     * Add a booking from customer input
     * @param ticketId id of this ticket
     * @param aisleNumber aisle number
     * @param seatNumber seat number
     * @param price price of this seat by the time it's booked
     */
    public void addBooking(int ticketId, String aisleNumber, int seatNumber, double price){
        Ticket newTicket = new Ticket(ticketId, aisleNumber, seatNumber, price);
        totalTickets ++;
        totalPrice = totalPrice + price;
        bookingList.add(newTicket);
    }

    /**
     * Add a booking read from file
     * @param bookingInfo string array of a booking read from a file
     */
    public void addBooking(String[] bookingInfo){
        Ticket newTicket = new Ticket(bookingInfo);
        totalTickets ++;
        totalPrice = totalPrice + newTicket.getPrice();
        bookingList.add(newTicket);
    }

    /**
     * Convert information of this booking to an array for the csv file
     * @return converted array
     */
    public String[] toFileArray(){
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        int arraySize = ConcertConstants.BOOKING_INFO_LENGTH+ConcertConstants.BOOKING_INFO_LENGTH*bookingList.size();
        String[] bookingArray = new String[arraySize];
        int arrayIndex = 0;
        bookingArray[arrayIndex++] = Integer.toString(bookingId);
        bookingArray[arrayIndex++] = Integer.toString(customerId);
        bookingArray[arrayIndex++] = customerName;
        bookingArray[arrayIndex++] = Integer.toString(concertId);
        bookingArray[arrayIndex++] = Integer.toString(totalTickets);
        for(Ticket ticket :bookingList){
            bookingArray[arrayIndex++] = Integer.toString(ticket.getTicketId());
            bookingArray[arrayIndex++] = Integer.toString(ticket.getRowNumber());
            bookingArray[arrayIndex++] = Integer.toString(ticket.getSeatNumber());
            bookingArray[arrayIndex++] = ticket.getZone();
            bookingArray[arrayIndex++] =  nf.format(ticket.getPrice());
        }

        return bookingArray;
    }

    /**
     * Getter of the id of customer who made this booking
     * @return customer id
     */
    public int getCustomerId(){
        return customerId;
    }

    /**
     * Getter of the list of tickets in this booking
     * @return list of tickets in this booking
     */
    public List<Ticket> getBookingList(){
        return bookingList;
    }

    /**
     * Getter of total number of tickets booked in this booking
     * @return total number of tickets
     */
    public int getTotalTickets(){
        return totalTickets;
    }

    /**
     * Getter of total price of this booking
     * @return total price of this booking
     */
    public double getTotalPrice(){
        return totalPrice;
    }

    /**
     * Getter of booking id
     * @return booking id
     */
    public int getBookingId(){
        return bookingId;
    }

}