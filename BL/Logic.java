package BL;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import DAL.DataDB;
import DAL.XML_Import;
import DAL.Settings;
import DAL.XML_Export;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Logic {
    private final DataDB data;
    private List<Book> bookList;
    private List<Person> peopleList;
    private List<Author> authorList;
    private List<Rent> rentList;

    public Logic(DataDB data){
        this.data = data;
    }

    public Color getBackground(){
        Settings settings = data.getSettings();
        return new Color(settings.colorR, settings.colorG, settings.colorB);

    }

    public void saveBackground(Color background){
        int colorR = background.getRed();
        int colorG = background.getGreen();
        int colorB = background.getBlue();
        data.setColorSettings(colorR, colorG, colorB);
    }

    //#region getting data into logic

    private void getPeopleList(String warunek){
        this.peopleList = data.getPeopleFromDB(warunek);
    }

    private void getAuthorsList(String warunek){
        this.authorList = data.getAuthorsFromDB(warunek);
    }

    private void getBookList(String warunek){
        this.bookList = data.getBooksFromDB(warunek);
    }

    private void getRentList(String warunek){
        this.rentList = data.getRentFromDB(warunek);
    }

    //#endregion

    //#region adding/modifying records

    public void addPerson(String firstName, String lastname, int age, String phoneNumber, String email, String city, String street, int houseNumber){
        Person os= new Person(firstName, lastname, age, phoneNumber,email, city, street, houseNumber);
        data.savePerson(os);
    }
    
    public void addAuthor(String firstName, String lastName, int age){
        Author author = new Author(firstName, lastName, age);
        data.saveAuthor(author);
    }

    public void addBook(String title, int release_year, String genre, String authors_id ){
        getAuthorsList("");
        List<Author> bookAuthorsList = new ArrayList<>();
        String[] items = authors_id.replaceAll("\\s", "").split(",");
        int[] id_authors = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            try{
                id_authors[i] = Integer.parseInt(items[i]);
            } 
            catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        for(int i=0; i<items.length;i++){
            for (Author author : authorList) {
                if (author.getId() == id_authors[i]) {
                    bookAuthorsList.add(author);
                    break;
                }
            }    
        }
        Book book = new Book(bookAuthorsList, title, release_year, genre);
        data.saveBook(book);
    }

    public void addRent(Person person, Book book, Instant rentStartDate, Instant rentDeadlineDate){
        Rent rent = new Rent(person, book, rentStartDate, rentDeadlineDate);
        data.saveRentStart(rent);
    }

    public void terminateRent(Rent rent){
        Instant rent_EndDate = Instant.now();
        rent.setRent_EndDate(rent_EndDate);
        data.terminateRent(rent);
    }

    public void terminateRentGUI(int rentID){
        for (Rent rent : rentList) {
            if (rentID == rent.getId()) {
                Instant rentEndDate = Instant.now();
                rent.setRent_EndDate(rentEndDate);
                data.terminateRent(rent);
                break;
            }
        }
    }

    //#endregion

    //#region UI- filling tables & lists

    public DefaultTableModel fillAuthorTable(JTable authorTable, String condition){
        getAuthorsList(condition);
        DefaultTableModel model= (DefaultTableModel) authorTable.getModel();
        Object[] rowData = new Object[4];
        for (Author author : authorList) {
            rowData[0] = author.getId();
            rowData[1] = author.getFirstName();
            rowData[2] = author.getLastName();
            rowData[3] = author.getAge();
            model.addRow(rowData);
        }
        return model;
    }

    public DefaultComboBoxModel<Author> fillAuthorsComboBox(String condition){
        getAuthorsList(condition);
        DefaultComboBoxModel<Author> model= new DefaultComboBoxModel<>();
        for (Author author : authorList) {
            model.addElement(author);
        }
        return model;
    }


    public DefaultTableModel fillClientsTable(JTable clientsTable, String condition){
        getPeopleList(condition);
        DefaultTableModel model= (DefaultTableModel) clientsTable.getModel();
        Object[] rowData = new Object[9];
        for (Person person : peopleList) {
            rowData[0] = person.getId();
            rowData[1] = person.getFirstName();
            rowData[2] = person.getLastName();
            rowData[3] = person.getAge();
            rowData[4] = person.getPhoneNumber();
            rowData[5] = person.getEmail();
            rowData[6] = person.getAddress().getCity();
            rowData[7] = person.getAddress().getStreet();
            rowData[8] = person.getAddress().getHouseNumber();
            model.addRow(rowData);
        }
        return model;
    }
    public DefaultListModel<Person> fillClientList(String condition) {
        getPeopleList(condition);
        DefaultListModel<Person> model = new DefaultListModel<>();
        for (Person person : peopleList) {
            model.addElement(person);
        }
        return model;
    }
    public DefaultComboBoxModel<Person> fillClientsComboBox(String condition){
        getPeopleList(condition);
        DefaultComboBoxModel<Person> model= new DefaultComboBoxModel<>();
        for (Person person : peopleList) {
            model.addElement(person);
        }
        return model;
    }

    public DefaultTableModel fillRentTable(JTable rentTable, String condition){
        getRentList(condition);
        DefaultTableModel model= (DefaultTableModel) rentTable.getModel();
        Object[] rowData = new Object[6];
        for (Rent rent : rentList) {
            rowData[0] = rent.getId();
            rowData[1] = rent.getPerson();
            rowData[2] = rent.getBook();
            rowData[3] = rent.getRent_startingDate();
            rowData[4] = rent.getRent_DeadLine();
            rowData[5] = rent.getRent_EndDate();
            model.addRow(rowData);
        }
        return model;
    }
    public DefaultListModel<Rent> fillRentList(String condition) {
        getRentList(condition);
        DefaultListModel<Rent> model = new DefaultListModel<>();
        for (Rent rent : rentList) {
            model.addElement(rent);
        }
        return model;
    }

    public DefaultTableModel fillBookTable(JTable bookTable, String condition){
        getBookList(condition);
        DefaultTableModel model= (DefaultTableModel) bookTable.getModel();
        Object[] rowData = new Object[5];
        for (Book book : bookList) {
            rowData[0] = book.getId();
            rowData[1] = book.getTitle();
            rowData[2] = book.getRelease_year();
            rowData[3] = book.getGenre();
            rowData[4]="";
            int sizeOfAuthorList = book.getAuthors().size();
            for(int i = 0; i< sizeOfAuthorList; i++){
                rowData[4]+= book.getAuthors().get(i).getFirstName()+" "+ book.getAuthors().get(i).getLastName()+"\n";
                if(sizeOfAuthorList >1){
                    rowData[4]+="\n";
                }
            }
            model.addRow(rowData);
        }
        return model;
    }
    public DefaultListModel<Book> fillBookList(String condition) {
        getBookList(condition);
        DefaultListModel<Book> model = new DefaultListModel<>();
        for (Book book : bookList) {
            model.addElement(book);
        }
        return model;
    }

    public DefaultComboBoxModel<Book> fillBookComboBox(String condition){
        getBookList(condition);
        DefaultComboBoxModel<Book> model= new DefaultComboBoxModel<>();
        for (Book book : bookList) {
            model.addElement(book);
        }
        return model;
    }

    //#endregion

    //#region console UI display section

    public String displayPeopleConsole(String condition){
        getPeopleList(condition);
        StringBuilder sb= new StringBuilder();
        sb.append("Displaying people list").append("\n");
        for (Person element : peopleList) {
            sb.append("Displaying person:").append("\n");
            sb.append(element.toString());
        }
        return sb.toString();
    }

    public String displayAuthorsConsole(String condition){
        getAuthorsList(condition);
        StringBuilder sb= new StringBuilder();
        sb.append("Displaying authors list").append("\n");
        for(Author element: authorList){
            sb.append("Displaying author: ").append("\n");
            sb.append(element.toString());
        }
        return sb.toString();
    }

    public String displayBooksConsole(String condition){
        getBookList(condition);
        StringBuilder sb= new StringBuilder();
        sb.append("Displaying books list").append("\n");
        for (Book element : bookList) {
            sb.append("Displaying book: ").append("\n");
            sb.append(element.toString());
        }
        return sb.toString();
    }

    public String displayRentConsole(String condition){
        getRentList(condition);
        StringBuilder sb= new StringBuilder();
        sb.append("Displaying list of rents").append("\n");
        for(Rent element: rentList){
            sb.append("Displaying rent:").append("\n");
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }

    //#endregion

    //#region console UI rent manager

    public void addNewRentConsole(int clientID, int bookID, int rentalLenghtDays){
        getPeopleList("");
        getBookList("");
        Person person =null;
        Book book =null;
        for (Person element : peopleList) {
            if (element.getId() == clientID) {
                person =element;
                break;
            }
        }
        for (Book element : bookList) {
            if (element.getId() == bookID) {
                book =element;
                break;
            }
        }
        Instant rentStartDate = Instant.now();
        Instant rentDeadlineDate = rentStartDate.plusSeconds(rentalLenghtDays * 86400L);
        addRent(person, book, rentStartDate, rentDeadlineDate);
    }

    public void terminateRentConsole(int rentID){
        getRentList("");
        Rent rent =null;
        for(Rent element: rentList){
            if(element.getId()== rentID){
                rent =element;
                break;
            }
        }
        if (rent != null) {
            terminateRent(rent);
        }
        else{
            System.out.println("Rent with inserted ID not found!");
        }
    }

    //#endregion

    //#region RENTLIST to/from XML - SAVING&LOADING

    public void saveDataIntoXML(){
        XML_Export XML_Export = new XML_Export(rentList);
    }

    public int loadDataFromXML(String path){
        getRentList("");
        List<Rent> newRentList = new ArrayList<>();
        XML_Import XML_Import = new XML_Import(rentList, newRentList, path);
        for(Rent rent : newRentList){
            data.saveRentStart(rent);
        }
        return newRentList.size();
    }

    //#endregion
}
