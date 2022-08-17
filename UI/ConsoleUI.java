package UI;

import BL.Logic;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleUI {
    private final Logic logic;

    public ConsoleUI(Logic logic){
        this.logic = logic;
    }
    
    public void Start(){
        Scanner input= new Scanner(System.in);
        int userChoice =1;
        System.out.println("Choose what do you want to do:");
        while(userChoice !=0) {
            System.out.println("1- Add client to base");
            System.out.println("2- Add author to base");
            System.out.println("3- Add book to base");
            System.out.println("4- Display clients");
            System.out.println("5- Display books authors");
            System.out.println("6- Display books");
            System.out.println("7- Add new rent");
            System.out.println("8- Display full rent list");
            System.out.println("9- Terminate rent");
            try{
                userChoice = input.nextInt();
                switch (userChoice) {
                    case 1 -> {
                        System.out.println("Adding new client:");
                        System.out.println("Insert first name:");
                        String firstName = input.next() + input.nextLine();
                        System.out.println("Insert last name:");
                        String lastName = input.next() + input.nextLine();
                        System.out.println("Insert age:");
                        int age = input.nextInt();
                        System.out.println("Insert phone number:");
                        String phoneNumber = input.next() + input.nextLine();
                        System.out.println("Insert email:");
                        String email = input.next() + input.nextLine();
                        System.out.println("Insert city:");
                        String city = input.next() + input.nextLine();
                        System.out.println("Insert street:");
                        String street = input.next() + input.nextLine();
                        System.out.println("Insert house number:");
                        int houseNumber = input.nextInt();
                        logic.addPerson(firstName, lastName, age, phoneNumber, email, city, street, houseNumber);
                    }
                    case 2 -> {
                        System.out.println("Adding new author:");
                        System.out.println("Insert first name:");
                        String firstName = input.next() + input.nextLine();
                        System.out.println("Insert last name:");
                        String lastName = input.next() + input.nextLine();
                        System.out.println("Insert age:");
                        int age = input.nextInt();
                        logic.addAuthor(firstName, lastName, age);
                    }
                    case 3 -> {
                        System.out.println("Adding new book:");
                        System.out.println("Insert title:");
                        String title = input.next() + input.nextLine();
                        System.out.println("Insert book release year:");
                        int releaseYear = input.nextInt();
                        System.out.println("Insert genre:");
                        String genre = input.next() + input.nextLine();
                        System.out.println("Insert authors ID(one by one). If you finish inserting all of them press 0:");
                        StringBuilder authorsID = new StringBuilder();
                        int buf;
                        do {
                            buf = input.nextInt();
                            if (buf != 0) {
                                if (authorsID.toString().equals("")) {
                                    authorsID.append(buf);
                                } else {
                                    authorsID.append(",").append(buf);
                                }
                            }
                        }
                        while (buf != 0);
                        logic.addBook(title, releaseYear, genre, authorsID.toString());
                    }
                    case 4 -> System.out.println(logic.displayPeopleConsole(""));
                    case 5 -> System.out.println(logic.displayAuthorsConsole(""));
                    case 6 -> System.out.println(logic.displayBooksConsole(""));
                    case 7 -> {
                        System.out.println("Insert client ID:");
                        int clientID = input.nextInt();
                        System.out.println("Insert book ID:");
                        int bookID = input.nextInt();
                        System.out.println("Insert number of rental days:");
                        int rentalDaysLenght = input.nextInt();
                        logic.addNewRentConsole(clientID, bookID, rentalDaysLenght);
                    }
                    case 8 -> System.out.println(logic.displayRentConsole(""));
                    case 9 -> {
                        System.out.println("Insert rent ID to terminate it");
                        int idWypozyczenia = input.nextInt();
                        logic.terminateRentConsole(idWypozyczenia);
                    }
                    default -> System.out.println("Wrong number , try again: " + userChoice);
                }
            } catch (NoSuchElementException e){
                System.out.println("Only numbers are allowed!!!");
                input.next();
            }
            System.out.println("\n Wanna do something else? 0-NO,1-YES");
            userChoice =input.nextInt();
        }
        input.close();
    }
}
