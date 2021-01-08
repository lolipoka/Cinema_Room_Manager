package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    private static final Scanner scanner = new Scanner(System.in);

    private static final char VACANT_SEAT = 'S';
    private static final char BOOKED_SEAT = 'B';

    private static final int SMALL_CINEMA_SIZE = 60;
    private static final int PRICE_FRONT_SEATS = 10;
    private static final int PRICE_BACK_SEATS = 8;

    public static final int CHOICE_PRINT_SEATS = 1;
    public static final int CHOICE_BOOK_SEAT = 2;
    public static final int CHOICE_STATISTICS = 3;
    public static final int CHOICE_EXIT = 0;

    private static int numberOfRows = 0;
    private static int numberOfSeats = 0;

    private static int numberOfPurchasedTickets = 0;
    private static float percentageOfPurchasedTickets = 0.0f;
    private static int currentIncome = 0;
    private static int totalIncome = 0;

    private static char[][] seats;

    public static void main(String[] args) {

        fillSeats();
        processUserChoice();
    }

    private static void fillSeats() {

        System.out.println("Enter the number of rows:");
        numberOfRows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        numberOfSeats = scanner.nextInt();

        seats = new char[numberOfRows][numberOfSeats];

        for (char[] row : seats) {
            Arrays.fill(row, VACANT_SEAT);
        }

        calculateTotalIncome();
    }

    private static void calculateTotalIncome() {
        int totalNumberOfSeats = getTotalNumberOfSeats();

        if (totalNumberOfSeats <= SMALL_CINEMA_SIZE) {
            totalIncome = totalNumberOfSeats * PRICE_FRONT_SEATS;
        } else {
            int frontRows = numberOfRows / 2;
            int frontSeats = frontRows * numberOfSeats;
            int backRows = numberOfRows - frontRows;
            int backSeats = backRows * numberOfSeats;
            totalIncome = (frontSeats * PRICE_FRONT_SEATS) + (backSeats * PRICE_BACK_SEATS);
        }
    }

    private static int getTotalNumberOfSeats() {
        return numberOfRows * numberOfSeats;
    }

    private static void processUserChoice() {
        int userChoice;
        do {
            printMenu();
            userChoice = scanner.nextInt();
            switch (userChoice) {
                case CHOICE_PRINT_SEATS:
                    printSeats();
                    break;
                case CHOICE_BOOK_SEAT:
                    bookSeat();
                    break;
                case CHOICE_STATISTICS:
                    printStatistics();
                    break;
                default:
                    break;
            }
        } while (userChoice != CHOICE_EXIT);
    }

    private static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private static void printSeats() {

        System.out.println("Cinema:");
        printSeatNumbers();

        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {

            int rowNumber = rowIndex + 1;
            StringBuilder rowSeatsStatus = new StringBuilder("" + rowNumber);

            char[] row = seats[rowIndex];

            for (char seatStatus : row) {
                rowSeatsStatus.append(" ").append(seatStatus);
            }
            System.out.println(rowSeatsStatus);
        }
        System.out.println();
    }

    private static void printSeatNumbers() {

        int rowLength = seats[0].length;
        StringBuilder seatNumbers = new StringBuilder(" ");

        for (int seatNumber = 1; seatNumber <= rowLength; seatNumber++) {
            seatNumbers.append(" ").append(seatNumber);
        }
        System.out.println(seatNumbers);
    }

    private static void bookSeat() {

        int rowNumber;
        int seatNumber;
        boolean isInvalidRowNumber;
        boolean isInvalidSeatNumber;
        int rowIndex;
        int seatIndex;

        do {
            do {
                System.out.println("Enter a row number:");
                rowNumber = scanner.nextInt();

                System.out.println("Enter a seat number in that row:");
                seatNumber = scanner.nextInt();

                isInvalidRowNumber = (rowNumber < 1 || rowNumber > numberOfRows);
                isInvalidSeatNumber = (seatNumber < 1 || seatNumber > numberOfSeats);

                if (isInvalidRowNumber || isInvalidSeatNumber) {
                    System.out.println("Wrong input!\n");
                }
            } while (isInvalidRowNumber || isInvalidSeatNumber);

            rowIndex = rowNumber - 1;
            seatIndex = seatNumber - 1;
            if (seats[rowIndex][seatIndex] == BOOKED_SEAT) {
                System.out.println("That ticket has already been purchased!\n");
            }
        } while (seats[rowIndex][seatIndex] == BOOKED_SEAT);

        seats[rowIndex][seatIndex] = BOOKED_SEAT;

        int ticketPrice = getTicketPrice(rowNumber);
        System.out.printf("Ticket price: $%d\n\n", ticketPrice);

        numberOfPurchasedTickets++;
        currentIncome += ticketPrice;
        recalculatePercentage();
    }

    private static int getTicketPrice(int rowNumber) {
        int ticketPrice;
        int totalNumberOfSeats = getTotalNumberOfSeats();
        int frontRows = numberOfRows / 2;
        if ((totalNumberOfSeats <= SMALL_CINEMA_SIZE) || (rowNumber <= frontRows)) {
            ticketPrice = PRICE_FRONT_SEATS;
        } else {
            ticketPrice = PRICE_BACK_SEATS;
        }
        return ticketPrice;
    }

    private static void recalculatePercentage() {
        percentageOfPurchasedTickets = ((float) numberOfPurchasedTickets / getTotalNumberOfSeats() * 100);
    }

    private static void printStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", numberOfPurchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percentageOfPurchasedTickets);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d", totalIncome);
        System.out.println("\n");
    }
}