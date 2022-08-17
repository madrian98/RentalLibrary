package BL;

import java.time.Instant;


public class Rent {

    private int id;
    private final Person person;
    private final Book book;
    private final Instant rent_startingDate;
    private final Instant rent_DeadLine;
    private Instant rent_EndDate;

    public Rent(Person person, Book book, Instant rent_startingDate, Instant rent_DeadLine) {
        this.person = person;
        this.book = book;
        this.rent_startingDate = rent_startingDate;
        this.rent_DeadLine = rent_DeadLine;
    }

    public Rent(int id, Person person, Book book, Instant rent_startingDate, Instant rent_DeadLine, Instant rent_EndDate) {
        this.id = id;
        this.person = person;
        this.book = book;
        this.rent_startingDate = rent_startingDate;
        this.rent_DeadLine = rent_DeadLine;
        this.rent_EndDate = rent_EndDate;
    }



    public int getId() {
        return id;
    }

    public Person getPerson(){
        return person;
    }

    public Book getBook(){
        return book;
    }

    public Instant getRent_startingDate() {
        return rent_startingDate;
    }

    public Instant getRent_DeadLine() {
        return rent_DeadLine;
    }

    public Instant getRent_EndDate(){
        return rent_EndDate;
    }

    public void setRent_EndDate(Instant rent_EndDate){
        this.rent_EndDate = rent_EndDate;
    }


    @Override
    public String toString(){
        return "ID: " + id + "\n" +
                "Person data: " + "\n" +
                person.toString() + "\n" +
                "Book data: " + "\n" +
                book.toString() + "\n" +
                "Rent Startingdate: " + rent_startingDate + "\n" +
                "Rent Deadline: " + rent_DeadLine + "\n" +
                "Rent EndDate: " + rent_EndDate + "\n" +
                "\n";
    }
}

