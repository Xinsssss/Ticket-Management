package User_Settings;
import Concert_Settings.Concert;

import java.util.List;
import java.util.Scanner;
/**
 * The abstract class for Customer and Admin
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public abstract class User {


    protected List<Concert> allConcerts;
    protected Concert currentConcert;
    protected int currentConcertId;

    /**
     * Print the main customer/admin menu and read input, direct to the correct actions
     * @param keyboard scanner
     */
    public abstract void mainMenu(Scanner keyboard);

    /**
     * view tickets booked by this customer for the selected concert
     */
    protected abstract void viewTicketsBooked(Scanner keyboard);



    public void showTiming(){


        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s%-15s%-15s%-15s%-30s%-15s%-15s%-15s\n", "#","Date","Artist Name","Timing","Venue Name","Total Seats","Seats Booked","Seats Left");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");

        for(int i=0; i<allConcerts.size();i++){
            allConcerts.get(i).printString();
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Set protected variable allConcerts
     * @param allConcerts the list of concert objects
     */
    public void setConcerts(List<Concert> allConcerts){
        this.allConcerts = allConcerts;
    }

    /**
     * Print the list of concerts and allows user to select which concert they want to perform their actions on
     * @param keyboard scanner
     * @return return true if we have selected a valid concert, otherwise false
     */
    protected boolean selectConcert(Scanner keyboard){
        System.out.println("Select a concert or 0 to exit");
        showTiming();
        System.out.print("> ");
        currentConcertId = Integer.parseInt(keyboard.nextLine());
        // select 0 out of all concerts means we want to exit the program
        if(currentConcertId==UserConstants.EXIT_USER_MODE){
            return false;
        }
        else{
            currentConcert = allConcerts.get(currentConcertId-1);
        }
        return true;
    }


}
