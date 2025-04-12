package User_Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Concert_Settings.Booking;

/**
 * The controller of customer mode
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class Customer extends User{

    private final int customerId;
    private final String customerName;
    private final String bookingFilePath;
    private int[] numBooked;
    private int numBookedCapacity;

    /**
     * Constructor
     * @param customerId Integer Id that uniquely defines the customer
     * @param customerName Input name of this customer
     * @param bookingFilePath file path for booking file, we record this in the customer mode because customer
     *                        needs to write booking information in this file
     */
    public Customer(int customerId, String customerName,String bookingFilePath){
        this.customerId = customerId;
        this.customerName = customerName;
        this.bookingFilePath = bookingFilePath;
        // Ticket Id is unique for each concert and customer, thus we need to record the number of tickets
        // booked in a concert by a customer to assist future creation of new booking id
        this.numBookedCapacity = UserConstants.CUSTOMER_BOOKING_COUNT_INI_CAP;
        this.numBooked = new int[UserConstants.CUSTOMER_BOOKING_COUNT_INI_CAP];
    }

    /**
     * Main menu of customer mode
     * @param keyboard scanner
     */
    @Override
    public void mainMenu(Scanner keyboard) {
        // we need to print the concert list until the user exits the customer mode
        boolean needSelectConcert = selectConcert(keyboard);
        while(needSelectConcert){
            // we need to print the main menu iteratively until the user exist this concert
            boolean needMenu = true;
            int menuInput;
            while(needMenu) {
                System.out.println("Select an option to get started!");
                System.out.println("Press 1 to look at the ticket costs");
                System.out.println("Press 2 to view seats layout");
                System.out.println("Press 3 to book seats");
                System.out.println("Press 4 to view booking details");
                System.out.println("Press 5 to exit");
                System.out.print("> ");
                menuInput = Integer.parseInt(keyboard.nextLine());
                switch(menuInput){
                    case UserConstants.CUSTOMER_VIEW_COST:
                        currentConcert.showTicketCosts();
                        break;
                    case UserConstants.CUSTOMER_VIEW_LAYOUT:
                        currentConcert.printLayoutDiagram();
                        break;
                    case UserConstants.CUSTOMER_BOOK_SEAT:
                        bookSeat(keyboard);
                        break;
                    case UserConstants.CUSTOMER_VIEW_BOOK:
                        viewTicketsBooked(keyboard);
                        break;
                    case UserConstants.EXIT:
                        needMenu = false;
                        System.out.println("Exiting this concert");
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
            needSelectConcert = selectConcert(keyboard);
        }
        System.out.println("Exiting customer mode");
    }

    /**
     * Getter of customer name
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * getter of customer id
     * @return customer id
     */
    public int getCustomerId(){
        return customerId;
    }

    /**
     * setter of the numBooked array, where the ith element corresponds to the number of tickets booked for the ith
     * concert by this user
     * @param loadedId the booking id we read from the booking file
     * @param concertId id of the concert we want to update
     */
    public void setNumBooked(int loadedId, int concertId){
        // if the number of concert exceeds the limit of our current array, we then expand the size by 5
        if(concertId >= numBookedCapacity){
            int[] newNumBooked = new int[numBookedCapacity+UserConstants.CUSTOMER_BOOKING_COUNT_EXPAND_STEP];
            if (numBookedCapacity >= 0) System.arraycopy(numBooked, 0, newNumBooked, 0, numBookedCapacity);
            this.numBooked = newNumBooked;
            this.numBookedCapacity = numBookedCapacity + UserConstants.CUSTOMER_BOOKING_COUNT_EXPAND_STEP;
        }
        //minus the concertId by 1 to concert from Id to index
        this.numBooked[concertId - UserConstants.ID_TO_INDEX] = loadedId;
    }

    /**
     * generate booking ID and book seat
     * @param keyboard scanner
     */
    private void bookSeat(Scanner keyboard){
        // booking id is the current number of booking for this cocnert + 1
        int bookingId = numBooked[currentConcertId- UserConstants.ID_TO_INDEX] + 1;
        currentConcert.bookSeat(bookingId, customerId, customerName,bookingFilePath, keyboard);
        // update the number of booking
        numBooked[currentConcertId- UserConstants.ID_TO_INDEX]++;
    }

    /**
     * view tickets booked by this customer for the selected concert
     */
    @Override
    protected void viewTicketsBooked(Scanner keyboard){
        List<Booking> allBooking = currentConcert.getAllBooking();
        List<Booking> currentCustomerBooking = new ArrayList<>();
        for(Booking list : allBooking){
            if(list.getCustomerId()==customerId){
                currentCustomerBooking.add(list);
            }
        }
        if(currentCustomerBooking.isEmpty()){
            System.out.println("No Bookings found for this concert\n");
            return;
        }
        System.out.println("Bookings");
        System.out.println(UserConstants.VIEW_TICKET_SEPARATOR);
        System.out.printf(UserConstants.VIEW_TICKET_FORMATTER,"Id","Concert Date","Artist Name","Timing","Venue Name","Seats Booked","Total Price");
        System.out.println(UserConstants.VIEW_TICKET_SEPARATOR);
        for(Booking list : currentCustomerBooking){
            System.out.printf(UserConstants.VIEW_TICKET_FORMATTER, list.getBookingId(), currentConcert.getDate(),
                    currentConcert.getArtistName(), currentConcert.getTime(), currentConcert.getVenueName(),
                    list.getTotalTickets(), list.getTotalPrice());
        }
        System.out.println(UserConstants.VIEW_TICKET_SEPARATOR);
        System.out.println();
        System.out.println("Ticket Info");
        for(Booking list: allBooking){
            if(list.getCustomerId()==customerId) {
                list.printTicket();
            }
        }
        System.out.println();
    }

}
