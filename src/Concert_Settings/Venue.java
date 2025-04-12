package Concert_Settings;
import java.util.List;
/**
 * The venue of a concert
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class Venue {

    private int totalSeat;
    private int seatBooked;
    private String[][] venueLayout;
    private int rowNum;
    private int colNum;
    private int sRowStartIndex;
    private int tRowStartIndex;

    /**
     * Constructor
     * @param venueLayout seat layout of this venue
     * @param sRowStartIndex index of the first row of the seating zone
     * @param tRowStartIndex index of the first row of the standing zone
     */
    public Venue(String[][] venueLayout, int sRowStartIndex, int tRowStartIndex){
        this.venueLayout = venueLayout;
        this.sRowStartIndex = sRowStartIndex;
        this.tRowStartIndex = tRowStartIndex;
        calculateTotalSeat(venueLayout);
        this.seatBooked = 0;
    }

    /**
     * Print the venue seat layout
     */
    public void printLayoutDiagram(){
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                if(venueLayout[i][j].equals(ConcertConstants.FIRST_SEATING_ROW)&& j!=colNum-1){
                    System.out.println();
                }
                else if(venueLayout[i][j].equals(ConcertConstants. FIRST_STANDING_ROW) && j!=colNum-1){
                    System.out.println();
                }
                System.out.print(venueLayout[i][j]);
            }
            System.out.print("\n");
        }
    }

    /**
     * Once we make a new booking in this concert, we need to update this info on the venue
     * @param booking one booking consisting of at least one ticket
     */
    public void updateBookedSeat(Booking booking){
        List<Ticket> tickets = booking.getBookingList();
        for(Ticket ticket : tickets){
            updateSingleBooking(ticket);
        }
    }

    /**
     * Getter of number of seats booked in this concert
     * @return number of seats booked
     */
    public int getSeatBooked(){
        return seatBooked;
    }

    /**
     * Get the total number of seats in this venue;
     * @return total number of seats
     */
    public int getTotalSeat(){
        return totalSeat;
    }

    /**
     * Update one ticket in the venue layout
     * @param ticket information of this ticket
     */
    private void updateSingleBooking(Ticket ticket){
        String zone = ticket.getZone();
        int rowIndex = ticket.getRowNumber() - 1;
        if(zone.equals(ConcertConstants.SEATING_ZONE)){
            rowIndex = rowIndex + sRowStartIndex;
        }
        else if(zone.equals(ConcertConstants.STANDING_ZONE )){
            rowIndex = rowIndex + tRowStartIndex;
        }
        int seatNumber = ticket.getSeatNumber();
        String seatIcon = ConcertConstants.SEAT_ICON_LEFT + seatNumber + ConcertConstants.SEAT_ICON_RIGHT;
        for(int i = 1; i< venueLayout[rowIndex].length; i++){
            if(venueLayout[rowIndex][i].equals(seatIcon)){
                venueLayout[rowIndex][i] = ConcertConstants.BOOKED_SEAT_ICON;
                seatBooked++;
                // terminate the method once we made this update
                return;
            }
        }
    }

    /**
     * Calculate the total number of seats avaliable in this venue
     * @param venueLayout string array storing the layout of this venue
     */
    private void calculateTotalSeat(String[][] venueLayout){
        this.rowNum = venueLayout.length;
        this.colNum = venueLayout[0].length;
        int validSeatCol = colNum - ConcertConstants.NUM_NOT_SEAT_ICON;
        this.totalSeat = rowNum * validSeatCol;
    }







}