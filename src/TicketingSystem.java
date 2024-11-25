import java.util.*;

public class TicketingSystem {
    private final User user;
    private final Queue queue;
    private final Map<String, Double> ticketTypes;
    private final int totalSeats;
    private int availableSeats;

    public TicketingSystem() {
        this.user = new User();
        this.queue = new Queue();
        this.ticketTypes = Map.of(
                "VIP", 200.00,
                "General", 100.00,
                "Balcony", 150.00
        );
        this.totalSeats = 100;
        this.availableSeats = totalSeats;
    }

    public User getUser() {
        return user;
    }

    public Queue getQueue() {
        return queue;
    }

    // Create an account
    public void createAccount(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        user.createAccount(username, password);
    }

    // Log in
    public boolean logIn(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        return user.logIn(username, password);
    }

    // Process the queue
    public void processQueue(Scanner scanner) {
        while (!queue.isEmpty()) {
            String currentUser = queue.processNextUser();
            if (currentUser != null) {
                userQueue();
                ticketingMenu(scanner);
            }
        }
    }

    // Simulates a queue with a 3-second wait
    public void userQueue() {
        System.out.println("You are being added to the system...");
        try {
            Thread.sleep(3000); // Waits for 3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("An error occurred while queueing.");
        }
        System.out.println("You are now in the ticketing system!");
    }

    // Ticketing menu
    public void ticketingMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nTicketing Menu:");
            System.out.println("1. View Ticket Types and Prices");
            System.out.println("2. Check for Seats");
            System.out.println("3. Book a Ticket");
            System.out.println("4. Log Out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewTicketTypes();
                    break;
                case 2:
                    checkForSeats();
                    break;
                case 3:
                    bookTicket(scanner);
                    break;
                case 4:
                    user.logOut();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Display ticket types and prices
    private void viewTicketTypes() {
        System.out.println("\nTicket Types and Prices:");
        ticketTypes.forEach((type, price) -> System.out.println(type + ": $" + price));
    }

    // Check for available seats
    private void checkForSeats() {
        System.out.println("\nAvailable Seats: " + availableSeats + "/" + totalSeats);
    }

    // Book a ticket
    private void bookTicket(Scanner scanner) {
        if (availableSeats == 0) {
            System.out.println("Sorry, all tickets are sold out!");
            return;
        }

        System.out.println("\nAvailable Ticket Types:");
        int i = 1;
        for (String type : ticketTypes.keySet()) {
            System.out.println(i + ". " + type + " - $" + ticketTypes.get(type));
            i++;
        }

        System.out.print("Choose a ticket type (1-" + ticketTypes.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String ticketType = null;
        if (choice >= 1 && choice <= ticketTypes.size()) {
            ticketType = new ArrayList<>(ticketTypes.keySet()).get(choice - 1);
        }

        if (ticketType != null) {
            System.out.println("Booking a " + ticketType + " ticket...");
            availableSeats--;
            System.out.println("Ticket booked successfully! Remaining seats: " + availableSeats);
        } else {
            System.out.println("Invalid ticket type selected. Please try again.");
        }
    }
}
