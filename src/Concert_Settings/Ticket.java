package Concert_Settings;

/**
 * Stores one ticket
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class Ticket {

    private final int ticketId;
    private final int rowNumber;
    private final int seatNumber;
    private final String zone;
    private final double price;

    /**
     * Constructor reading array
     * @param bookingInfo booking information loaded from the booking file, stored as an array of string
     */
    public Ticket(String[] bookingInfo){
        this.ticketId = Integer.parseInt(bookingInfo[ConcertConstants.TICKET_ID_IDX]);
        this.rowNumber = Integer.parseInt(bookingInfo[ConcertConstants.ROW_NUM_IDX]);
        this.seatNumber = Integer.parseInt(bookingInfo[ConcertConstants.SEAT_NUM_IDX]);
        this.zone = bookingInfo[ConcertConstants.ZONE_IDX];
        this.price = Double.parseDouble(bookingInfo[ConcertConstants.PRICE_IDX]);
    }

    /**
     * Constructor using parameters inputted by a customer
     * @param ticketId auto-generated id of this ticket
     * @param aisleNumber aisle number of this ticket
     * @param seatNumber seat number of this ticket
     * @param price price of this ticket by the time it's booked
     */
    public Ticket(int ticketId, String aisleNumber, int seatNumber, double price){
        this.ticketId = ticketId;
        this.seatNumber = seatNumber;
        this.rowNumber = aisleToRow(aisleNumber);
        this.zone = aisleToZone(aisleNumber);
        this.price = price;
    }

    /**
     * Print the information of this ticket
     */
    public void printString(){
        System.out.printf("%-5s%-15d%-15d%-10s%-10.1f%n",ticketId,rowNumber,seatNumber,zone,price);
    }

    /**
     * Getter of the price of this ticket
     * @return price
     */
    public double getPrice(){
        return price;
    }

    /**
     * Getter of the row number of this ticket
     * @return row number
     */
    public int getRowNumber(){
        return rowNumber;
    }

    /**
     * Getter of the zone number of this ticket
     * @return zone
     */
    public String getZone(){
        return zone;
    }

    /**
     * Getter of the seat number of this ticket
     * @return seat number
     */
    public int getSeatNumber(){
        return seatNumber;
    }

    /**
     * Getter of the ticket id of this ticket
     * @return ticket id
     */
    public int getTicketId(){
        return ticketId;
    }

    /**
     * Extract row number from the aisle number
     * @param aisleNumber aisle number
     * @return row number
     */
    private int aisleToRow(String aisleNumber){
        return Integer.parseInt(aisleNumber.substring(1));
    }

    /**
     * Extract zone from the aisle number
     * @param aisleNumber aisle number
     * @return zone
     */
    private String aisleToZone(String aisleNumber){
        if(aisleNumber.startsWith(ConcertConstants.VIP_LABEL)){
            return ConcertConstants.VIP_ZONE;
        }
        else if(aisleNumber.startsWith(ConcertConstants.SEATING_LABEL)){
            return ConcertConstants.SEATING_ZONE;
        }
        else if(aisleNumber.startsWith(ConcertConstants.STANDING_LABEL)){
            return ConcertConstants.STANDING_ZONE;
        }
        else{
            return null;
        }
    }


}