import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import File_Readers.*;
import User_Settings.*;
import Concert_Settings.*;
import java.util.Scanner;
/**
 * The main runner of this ticket management engine
 * @author Xin Shen 1266811 xsshen2@student.unimelb.edu.au
 * @version 1.0
 */
public class TicketManagementEngine {

    /**
     * Default constructor
     */
    public TicketManagementEngine(){}

    /**
     * Main method
     * @param args command line arguments to take mode, customer id and password (optional) and file paths
     */
    public static void main(String[] args) {

        TicketManagementEngine tme = new TicketManagementEngine();

        Scanner keyboard = new Scanner(System.in);

        // We centralise data loaded in the main function
        User user = null;
        Readers reader = null;
        List<Concert> allConcerts = null;
        List<SampleVenues> venuesSamples = null;
        List<String[]> preloadBookings = null;

        try {
            // reader can be either admin or customer, or null if we received an invalid mode
            reader = tme.handleMode(args,keyboard);
            if (reader == null) {
                System.out.println("Invalid user mode. Terminating program now.");
                return;  // terminates program if the user mode is incorrect
            }

            allConcerts = reader.loadConcertData();
            preloadBookings = reader.loadBookingData();
            venuesSamples = reader.loadVenueSamples();
            allConcerts = reader.loadVenueToConcert(venuesSamples,allConcerts);
            allConcerts = reader.loadBookingToConcert(preloadBookings,allConcerts);
            reader.printWelcome();

        } catch (RuntimeException | IOException | InvalidLineException | InvalidUserException | InvalidFormatException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return; // terminate program if we detect any error mentioned above
        }

        // the welcome message need to have the customer name or admin mode mentioned.
        tme.displayMessage();

        // run the user menu
        user = reader.getUser();
        user.setConcerts(allConcerts);
        user.mainMenu(keyboard);

        keyboard.close();
    }

    /**
     * Display welcome message "TMS“
     */
    public void displayMessage() {


        System.out.print("\n" +
                " ________  ___ _____ \n" +
                "|_   _|  \\/  |/  ___|\n" +
                "  | | | .  . |\\ `--. \n" +
                "  | | | |\\/| | `--. \\\n" +
                "  | | | |  | |/\\__/ /\n" +
                "  \\_/ \\_|  |_/\\____/ \n" +
                "                    \n" +
                "                    \n");
    }

    /**
     * Detect the user mode and initialise corresponding file reader object
     * @param args command line arguments to take mode, customer id and password (optional) and file paths
     * @param keyboard scanner
     * @return return a customer mode or admin mode file reader given the input, or return null if the input mode
     * is invalid
     * @throws IOException if there's error in the file path
     * @throws InvalidLineException if line(s) in a file is incorrect
     * @throws InvalidUserException if customer id or password is incorrect
     * @throws InvalidFormatException if some id is in an incorrect format
     */
    public Readers handleMode(String[] args, Scanner keyboard) throws IOException, InvalidLineException, InvalidUserException, InvalidFormatException {
        // slice out the segment for mode
        String[] subArgs = Arrays.copyOfRange(args, MainConstants.SUB_ARGS_STARTING_IDX, args.length);
        // get the mode
        String mode = args[MainConstants.MODE_IN_ARGS];
        return switch (mode) {
            case MainConstants.CUSTOMER_MODE_ARGS -> new CustomerReader(subArgs,keyboard);
            case MainConstants.ADMIN_MODE_ARGS -> new AdminReader(subArgs);
            default -> null;
        };

    }
}