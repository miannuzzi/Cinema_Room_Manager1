package cinema;

import java.util.Scanner;

public class Cinema {

    private static final int    SMALL_ROOM_LIMIT           = 60;
    private static final int    SMALL_ROOM_TICKET_PRICE    = 10;
    private static final int    BIG_ROOM_TICKET_PRICE      = 8;
    private static final String BUYED_TICKET_MARK       = "B";

    private static int rows;
    private static int columns;
    private static String[][] room;
    private static int rowTicket;
    private static int seatTicket;

    private static boolean  isSmall;
    private static int      occupancy       = 0;
    private static int      roomSize        = 0;


    public static void main(String[] args) {
        Cinema.inputRoom();
        Cinema.initializeRoom();
        Cinema.printRoom();

        int option = -1;

        while (option != 0){
            option = menu();
            switch (option) {
                case 1:
                    Cinema.printRoom();
                    break;
                case 2:

                    Cinema.buyTicket();
                    Cinema.printTicketPrice();
                    break;
                case 3:
                    Cinema.getStatistics();
                    break;
            }
        }

       // Cinema.printTotalPrice();
    }


    private static void initializeRoom() {
        Cinema.roomSize = Cinema.rows * Cinema.columns;
        Cinema.isSmall = Cinema.roomSize <= Cinema.SMALL_ROOM_LIMIT;

        room = new String[Cinema.rows][Cinema.columns];

        for (int i = 0; i < Cinema.rows; i++) {
            for (int j = 0; j < Cinema.columns; j++) {
                room[i][j] = "S";
            }
        }
    }

    private static void getStatistics(){
        System.out.printf("Number of purchased tickets: %d%n", Cinema.occupancy );
        System.out.printf("Percentage: %3.2f%%%n", Cinema.getOccupancyPercentage());
        System.out.printf("Current income: $%d%n",Cinema.getCurrentIncome());
        System.out.printf("Total income: $%d%n", Cinema.getTotalIncome());
    }

    private static void buyTicket() {
        boolean validInput = false;

        while (!validInput) {
            Cinema.inputTicket();

            if (validSeat()) {
                if (!room[Cinema.rowTicket][Cinema.seatTicket].equals(Cinema.BUYED_TICKET_MARK)) {
                    room[Cinema.rowTicket][Cinema.seatTicket] = Cinema.BUYED_TICKET_MARK;
                    Cinema.occupancy++;
                    validInput = true;
                } else {
                    System.out.println("That ticket has already been purchased!");
                }
            } else {
                System.out.println("Wrong input!");
            }
        }

    }

    private static boolean validSeat() {
        return 0 <= Cinema.rowTicket && Cinema.rowTicket < Cinema.rows
                && 0 <= Cinema.seatTicket && Cinema.seatTicket < Cinema.columns;
    }

    private static long getCurrentIncome() {
        long currentIncome = 0;

        for (int i = 0; i < Cinema.rows; i++) {
            for (int j = 0; j < Cinema.columns; j++) {
                if (Cinema.BUYED_TICKET_MARK.equals(room[i][j])) {
                    currentIncome += Cinema.getTicketPrice(i,j);
                }
            }
        }

        return currentIncome;
    }
    private static long getTotalIncome() {
        long totalIncome = 0;

        for (int i = 0; i < Cinema.rows; i++) {
            for (int j = 0; j < Cinema.columns; j++) {
                totalIncome += Cinema.getTicketPrice(i,j);
            }
        }

        return totalIncome;
    }

    private static float getOccupancyPercentage() {
        return ((float) Cinema.occupancy / (float) Cinema.roomSize) * 100f;
    }


    private static void printRoom() {

        System.out.println("Cinema:");
        //Print Column indexes
        StringBuffer printableRow = new StringBuffer("  ");

        for (int i = 0; i < Cinema.columns; i++) {
            printableRow.append(String.format("%d ",i+1));
        }
        System.out.println(printableRow);


        //Print content
        for (int i = 0; i < Cinema.rows; i++) {
            printableRow = new StringBuffer();
            printableRow.append(i+1);
            printableRow.append(" ");

            for (int j = 0; j < Cinema.columns; j++) {
                printableRow.append(String.format("%s ",room[i][j]));
            }

            System.out.println(printableRow);
        }
    }

    private static void printIncome() {

        long income;

        if (Cinema.isSmall) {
            income = Cinema.rows * Cinema.columns * Cinema.SMALL_ROOM_TICKET_PRICE;
        } else {
            int frontRows = (int) Cinema.rows / 2;

            income = (frontRows * Cinema.SMALL_ROOM_TICKET_PRICE
                    + (Cinema.rows - frontRows) * Cinema.BIG_ROOM_TICKET_PRICE) * Cinema.columns;
        }

        System.out.println("Total income:");
        System.out.println("$" + income);
    }

    private static int menu(){
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }

    private static void printTotalPrice() {
        System.out.println("Ticket Price: $" + getCurrentIncome());
    }

    private static void printTicketPrice() {
        System.out.println("Ticket Price: $" + getTicketPrice(Cinema.rowTicket,Cinema.seatTicket));
    }

    private static void inputRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        Cinema.rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        Cinema.columns = scanner.nextInt();
    }

    private static void inputTicket() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a row number:");
        Cinema.rowTicket = scanner.nextInt() - 1;

        System.out.println("Enter a seat number in that row:");
        Cinema.seatTicket = scanner.nextInt() - 1;
    }

    private static int getTicketPrice(int row, int seat) {
        int price = Cinema.SMALL_ROOM_TICKET_PRICE;

        if (!isSmall) {
            int frontRows = (int) Cinema.rows / 2;

            if (row < frontRows) {
                price = Cinema.SMALL_ROOM_TICKET_PRICE;
            } else {
                price = Cinema.BIG_ROOM_TICKET_PRICE;
            }
        }

        return price;
    }
    private static int getAvailableSeats() {
        return Cinema.roomSize - Cinema.occupancy;
    }

}