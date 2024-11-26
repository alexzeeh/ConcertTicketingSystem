import java.io.*;
import java.util.Scanner;

public class Seats extends Tickets {
    private int VIPseats;
    private int REGseats;
    private String[][] seatArrangement;

    public Seats(double VIPTickets, double RegTickets, int VIPseats, int REGseats, int rows, int cols) {
        super(VIPTickets, RegTickets);
        this.VIPseats = VIPseats;
        this.REGseats = REGseats;
        seatArrangement = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                seatArrangement[i][j] = "[0]";
            }
        }
    }

    public boolean bookSeat(int row, int col, int type) {
        if (type == 1 && (row < 0 || row >= 2)) {
            System.out.println("Invalid row for VIP seats. Please choose a row between 1 and 2.");
            return false;
        } else if (type == 2 && (row < 2 || row >= 5)) {
            System.out.println("Invalid row for Regular seats. Please choose a row between 3 and 5.");
            return false;
        }

        if (row < 0 || row >= seatArrangement.length || col < 0 || col >= seatArrangement[row].length) {
            System.out.println("Invalid seat location. Please try again.");
            return false;
        }

        if (seatArrangement[row][col].equals("[0]")) {
            seatArrangement[row][col] = "[X]";
            if (type == 1) {
                VIPseats--;
            } else if (type == 2) {
                REGseats--;
            }
            System.out.println("Seat " + (row * 5 + col + 1) + " booked successfully.");
            return true;
        } else {
            System.out.println("Seat already booked. Please choose another.");
            return false;
        }
    }

    public void displaySeats() {
        System.out.println("\nVIP Section:");
        for (int i = 0; i < 2; i++) {
            for (String seat : seatArrangement[i]) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }

        System.out.println("\nRegular Section:");
        for (int i = 2; i < 5; i++) {
            for (String seat : seatArrangement[i]) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }
    }

    public void loadSeatsFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                String[] seatData = line.split(" ");
                for (int col = 0; col < seatData.length; col++) {
                    seatArrangement[row][col] = seatData[col];
                }
                row++;
            }
        } catch (IOException e) {
            System.out.println("Error reading seat data from file: " + e.getMessage());
        }
    }

    public int getVIPSeats() {
        return VIPseats;
    }

    public int getRegSeats() {
        return REGseats;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Seats seats = new Seats(10, 20, 10, 20, 5, 5);

        System.out.println("Enter your choice: 3");
        System.out.println("[1] - VIP");
        System.out.println("[2] - Regular");

        System.out.print("Select Ticket Type: ");
        int ticketType = scanner.nextInt();
        System.out.print("Number of tickets(Max 5): ");
        int numTickets = scanner.nextInt();

        for (int i = 0; i < numTickets; i++) {
            boolean booked = false;
            while (!booked) {
                System.out.print("Preferred row and column 1 (e.g., 1 2): ");
                int row = scanner.nextInt() - 1;
                int col = scanner.nextInt() - 1;

                if (ticketType == 1) {
                    if (row >= 0 && row < 2) {
                        booked = seats.bookSeat(row, col, 1);
                    } else {
                        System.out.println("Invalid row for VIP seats. Please choose a row between 1 and 2.");
                    }
                } else if (ticketType == 2) {
                    if (row >= 2 && row < 5) {
                        booked = seats.bookSeat(row, col, 2);
                    } else {
                        System.out.println("Invalid row for Regular seats. Please choose a row between 3 and 5.");
                    }
                }
            }
            System.out.println("Ticket " + (i + 1) + " booked successfully.");
        }

        seats.displaySeats();
    }
}