package User_Settings;

import Concert_Settings.Booking;

import java.util.List;
import java.util.Scanner;
/**
 * Controller of admin mode
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class Admin extends User{

    /**
     * Default constructor
     */
    public Admin(){};
    /**
     * Main menu of admin mode
     * @param keyboard scanner
     */
    @Override
    public void mainMenu(Scanner keyboard) {

        int menuInput;
        // we continue to print the menu as long as the user didn't exit the admin mode
        boolean needMenu = true;
        while(needMenu) {
            System.out.println("Select an option to get started!");
            System.out.println("Press 1 to view all the concert details");
            System.out.println("Press 2 to update the ticket costs");
            System.out.println("Press 3 to view booking details");
            System.out.println("Press 4 to view total payment received for a concert");
            System.out.println("Press 5 to exit");
            System.out.print("> ");
            menuInput = Integer.parseInt(keyboard.nextLine());
            switch(menuInput){
                case UserConstants.ADMIN_VIEW_ALL_CONCERTS:
                    showTiming();
                    break;
                case UserConstants.ADMIN_UPDATE_PRICE:
                    updatePrice(keyboard);
                    break;
                case UserConstants.ADMIN_VIEW_BOOK:
                    viewTicketsBooked(keyboard);
                    break;
                case UserConstants.ADMIN_TOTAL_PAYMENT:
                    seeAllPrice(keyboard);
                    break;
                case UserConstants.EXIT:
                    needMenu = false;
                    System.out.println("Exiting admin mode");
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;

            }
        }
    }

    /**
     * Admin can update the price of zones in a concert
     * @param keyboard scanner
     */
    private void updatePrice(Scanner keyboard){
        // let admin choose which concert they want to update the price
        boolean needSelectConcert = selectConcert(keyboard);
        if(needSelectConcert){
            currentConcert.showTicketCosts();
            System.out.print("Enter the zone : VIP, SEATING, STANDING: \n");
            String zone = keyboard.nextLine();
            System.out.print("Left zone price: ");
            double leftPrice = Double.parseDouble(keyboard.nextLine());
            System.out.print("Centre zone price: ");
            double middlePrice = Double.parseDouble(keyboard.nextLine());
            System.out.print("Right zone price: ");
            double rightPrice = Double.parseDouble(keyboard.nextLine());
            currentConcert.updatePrice(zone, leftPrice, middlePrice, rightPrice);
        }

    }

    /**
     * Let admin view all prices of one concert
     * @param keyboard scanner
     */
    private void seeAllPrice(Scanner keyboard){
        boolean needSelectConcert = selectConcert(keyboard);
        if(needSelectConcert){
            System.out.printf("Total Price for this concert is AUD %.1f\n", currentConcert.sumOfPrice());
        }
    }

    /**
     * Let admin view tickets booked for a concert
     * @param keyboard scanner
     */
    @Override
    protected void viewTicketsBooked(Scanner keyboard){
        boolean needSelectConcert = selectConcert(keyboard);
        // terminate this method if we didn't select a valid concert
        if(!needSelectConcert){
            return;
        }
        List<Booking> allBooking = currentConcert.getAllBooking();
        if(allBooking.isEmpty()){
            System.out.println("No Bookings found for this concert\n");
            return;
        }
        System.out.println("Bookings");
        System.out.println(UserConstants.VIEW_TICKET_SEPARATOR);
        System.out.printf(UserConstants.VIEW_TICKET_FORMATTER,"Id","Concert Date","Artist Name","Timing","Venue Name","Seats Booked","Total Price");
        System.out.println(UserConstants.VIEW_TICKET_FORMATTER);
        for(Booking list : allBooking){
            System.out.printf(UserConstants.VIEW_TICKET_FORMATTER,list.getBookingId(), currentConcert.getDate(),currentConcert.getArtistName(),currentConcert.getTime(), currentConcert.getVenueName(),list.getTotalTickets(),list.getTotalPrice());
        }
        System.out.println(UserConstants.VIEW_TICKET_FORMATTER);
        System.out.println();
        System.out.println("Ticket Info");
        for(Booking list: allBooking){
            list.printTicket();
        }
        System.out.println();
    }

}
