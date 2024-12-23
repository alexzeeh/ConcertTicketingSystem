import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountManager accountManager = new AccountManager();
        Queueing queue = new Queueing();
        boolean isRunning = true;

        Seats seats = new Seats(5000, 3500, 10, 15, 5, 5); 
        seats.loadSeatsFromFile("seats.txt");

        while (isRunning) {
            System.out.println("\n*************************************");
            System.out.println("*      CONCERT TICKETING SYSTEM     *");
            System.out.println("*************************************");
            System.out.println("* [1] - Login                       *");
            System.out.println("* [2] - Create a new account        *");
            System.out.println("* [3] - Exit                        *");
            System.out.println("*************************************");
            System.out.print("\nEnter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("\nEnter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    if (accountManager.authenticateUser(username, password)) {
                        System.out.println("\n+--------------------------------------------------------+");
                        System.out.println("|                     SELECT A CONCERT                   |");
                        System.out.println("+--------------------------------------------------------+");
                        System.out.println("| [1] - Niki - Buzz World Tour                           |");
                        System.out.println("| [2] - Wave to Earth - Play With Earth! 0.03 World Tour |");
                        System.out.println("+--------------------------------------------------------+");
                        System.out.print("\nEnter your choice: ");
                        int concertChoice = sc.nextInt();
                        sc.nextLine();

                        String concert = "";
                        switch (concertChoice) {
                            case 1:
                                concert = "Niki - Buzz World Tour";
                                break;
                            case 2:
                                concert = "Wave to Earth - Play With Earth! 0.03 World Tour";
                                break;
                            default:
                                System.out.println("\nInvalid: Returning to main menu.");
                                continue;
                        }

                        System.out.println("\nOn queue for: " + concert + "\n");
                        queue.addUser(username);

                        while (!queue.isEmpty() && queue.processNextUser().equals(username)) {
                            System.out.println("\nYou're next!");

                            boolean inMenu = true;

                            while (inMenu) {
                                System.out.println("\n+------------ TICKETING SYSTEM -----------+");
                                System.out.println("| [1] - View ticket types and prices      |");
                                System.out.println("| [2] - Check for seats                   |");
                                System.out.println("| [3] - Book a ticket                     |");
                                System.out.println("| [4] - Exit                              |");
                                System.out.println("+-----------------------------------------+\n");
                                System.out.print("\nEnter your choice: ");

                                try {
                                    int menuChoice = sc.nextInt();
                                    switch (menuChoice) {
                                        case 1:
                                            System.out.println("\n[1] - VIP Ticket prices");
                                            System.out.println("[2] - Regular Ticket prices");
                                            System.out.print("\nEnter your choice: ");
                                            int ticketChoice = sc.nextInt();
                                            if (ticketChoice == 1) {
                                                System.out.println("\nVIP ticket price is " + seats.getVIPValue());
                                            } else if (ticketChoice == 2) {
                                                System.out.println("\nRegular ticket price is " + seats.getRegValue());
                                            } else {
                                                System.out.println("\nInvalid: Please enter 1 or 2.");
                                            }
                                            break;
                                        case 2:
                                            seats.displaySeats();
                                            break;
                                        case 3:
                                            System.out.println("\n[1] - VIP");
                                            System.out.println("[2] - Regular");
                                            System.out.print("\nSelect Ticket Type: ");
                                            int ticketType = sc.nextInt();
                                            if (ticketType == 1 || ticketType == 2) {
                                                System.out.print("\nNumber of tickets(Max 5): ");
                                                int numTickets = sc.nextInt();
                                                if (numTickets > 5) {
                                                    System.out.println("\nError: Maximum purchase reached.");
                                                } else {
                                                    for (int i = 0; i < numTickets; i++) {
                                                        System.out.print("\nPreferred row and column " + (i + 1) + " (e.g., 1 2): ");
                                                        int row = sc.nextInt() - 1; 
                                                        int col = sc.nextInt() - 1;
                                                        if (seats.bookSeat(row, col, ticketType)) {
                                                            System.out.println("\nTicket " + (i + 1) + " booked successfully.");
                                                        } else {
                                                            System.out.println("\nBooking failed for ticket " + (i + 1) + ". Try another seat.");
                                                        }
                                                    }
                                                }
                                            } else {
                                                System.out.println("\nInvalid ticket type.");
                                            }
                                            break;
                                        case 4:
                                            System.out.println("\nExiting ticketing system...");
                                            inMenu = false;
                                            break;
                                        default:
                                            System.out.println("\nInvalid: Try again.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("\nInvalid: Please enter a number.");
                                    sc.nextLine();
                                }
                            }
                        }
                    } else {
                        System.out.println("\nInvalid username or password. Please try again.");
                    }
                    break;
                case 2:
                    System.out.print("\nEnter new username: ");
                    String newUsername = sc.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = sc.nextLine();
                    accountManager.addUser(newUsername, newPassword);
                    break;
                case 3:
                    isRunning = false;
                    break;
                default:
                    System.out.println("\nInvalid: Try again.");
            }
        }

        sc.close();
    }
}