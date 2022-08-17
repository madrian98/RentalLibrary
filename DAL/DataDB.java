package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import BL.Author;
import BL.Book;
import BL.Person;
import BL.Rent;

public class DataDB {

private Connection connection;

private final Settings settings;

    public DataDB(){
        settings = new Settings();
        settings.getSettings();
        String serwer = settings.server;
        String instancja = settings.instance;
        String login = settings.login;
        String haslo = settings.password;
        String connectionUrl ="jdbc:sqlserver://"+ serwer +"\\"+ instancja +";";
        try{
            connection= DriverManager.getConnection(connectionUrl, login, haslo);
        } 
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public void setColorSettings(int kolorR, int kolorG, int kolorB){
        settings.colorR =kolorR;
        settings.colorG =kolorG;
        settings.colorB =kolorB;
    }

    //#region saving into DB

    public void savePerson(Person person){
        String insertSQLString="INSERT INTO PRObiblioteka.dbo.klienci (imie,nazwisko,wiek,numerTelefonu,email,miasto,ulica,nrDomu) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getAge());
            statement.setString(4, person.getPhoneNumber());
            statement.setString(5, person.getEmail());
            statement.setString(6, person.getAddress().getCity());
            statement.setString(7, person.getAddress().getStreet());
            statement.setInt(8, person.getAddress().getHouseNumber());
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAuthor(Author author){
        String insertSQLString="INSERT INTO PRObiblioteka.dbo.autorzy (imie,nazwisko,wiek) VALUES(?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setInt(3, author.getAge());
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveBook(Book book){
        //1. saving book data into bookTable
        String insertSQLString="INSERT INTO PRObiblioteka.dbo.ksiazki (tytul,rok_wydania,gatunek) VALUES(?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getRelease_year());
            statement.setString(3, book.getGenre());
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        //2. searching for bookID
        insertSQLString="SELECT TOP 1 id_ksiazki FROM PRObiblioteka.dbo.ksiazki ORDER BY id_ksiazki DESC";
        int id_ksiazki=0;
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            ResultSet resultSet= statement.executeQuery();
            while(resultSet.next()){
                id_ksiazki=resultSet.getInt("id_ksiazki");
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        //3. loop for saving more than 1 book author
        for (Author author : book.getAuthors()) {
            insertSQLString = "INSERT INTO PRObiblioteka.dbo.autorzy_ksiazki (id_ksiazki,id_autora) VALUES(?,?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertSQLString);
                statement.setInt(1, id_ksiazki);
                statement.setInt(2, author.getId());
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveRentStart(Rent rent){
        String insertSQLString="INSERT INTO PRObiblioteka.dbo.wypozyczenia (id_klienta,id_ksiazki,data_wypozyczenia,termin_oddania,oddano) VALUES(?,?,?,?,?)";
        Timestamp dataWypozyczenia= Timestamp.from(rent.getRent_startingDate());
        Timestamp terminOddania= Timestamp.from(rent.getRent_DeadLine());
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            statement.setInt(1, rent.getPerson().getId());
            statement.setInt(2, rent.getBook().getId());
            statement.setTimestamp(3,dataWypozyczenia);
            statement.setTimestamp(4,terminOddania);
            statement.setNull(5, java.sql.Types.NULL);
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void terminateRent(Rent rent){
        String insertSQLString="UPDATE PRObiblioteka.dbo.wypozyczenia SET oddano=? WHERE id_wypozyczenia=?";
        Timestamp dataOddania= Timestamp.from(rent.getRent_EndDate());
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            statement.setTimestamp(1, dataOddania);
            statement.setInt(2, rent.getId());
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //#endregion

    //#region reading from DB

    public List<Person> getPeopleFromDB(String condition){
        List<Person> peopleList = new ArrayList<>();
        if(!condition.equals("")){
            String where=" WHERE ";
            condition =where+ condition;
        }
        String insertSQLString="SELECT * FROM PRObiblioteka.dbo.klienci" + condition;
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            ResultSet resultSet= statement.executeQuery();
            while(resultSet.next()){
                Person person = new Person(
                    resultSet.getInt("id_klienta"),
                    resultSet.getString("imie"),
                    resultSet.getString("nazwisko"),
                    resultSet.getInt("wiek"),
                    resultSet.getString("numerTelefonu"),
                    resultSet.getString("email"),
                    resultSet.getString("miasto"),
                    resultSet.getString("ulica"),
                    resultSet.getInt("nrDomu")
                    );
                peopleList.add(person);
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return peopleList;
    }

    public List<Author> getAuthorsFromDB(String condition){
        List<Author> authorsList = new ArrayList<>();
        if(!condition.equals("")){
            String where=" WHERE ";
            condition=where+condition;
        }
        String insertSQLString="SELECT * FROM PRObiblioteka.dbo.autorzy" + condition;
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            ResultSet resultSet= statement.executeQuery();
            while(resultSet.next()){
                Author author = new Author(
                    resultSet.getInt("id_autora"),
                    resultSet.getString("imie"),
                    resultSet.getString("nazwisko"),
                    resultSet.getInt("wiek")
                    );
                authorsList.add(author);
            }
            resultSet.close();
            statement.close(); 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return authorsList;
    }
    
    public List<Book> getBooksFromDB(String condition){
        List<Book> bookList = new ArrayList<>();
        List<Author> listOfAllAuthors = new ArrayList<>(getAuthorsFromDB(""));
        if(!condition.equals("")){
            String where=" WHERE ";
            condition=where+condition;
        }
        String insertSQLString="SELECT k.id_ksiazki,tytul,rok_wydania,gatunek,ak.id_autora FROM PRObiblioteka.dbo.ksiazki k JOIN PRObiblioteka.dbo.autorzy_ksiazki ak ON k.id_ksiazki=ak.id_ksiazki" + condition;
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            ResultSet resultSet= statement.executeQuery();
            boolean firstSearch =true;
            while(resultSet.next()){
                List<Author> listaAutorow= new ArrayList<>();
                int id_autora=resultSet.getInt("id_autora");
                Author author =null;
                for (Author authorElement : listOfAllAuthors) {
                    if (id_autora == authorElement.getId()) {
                        author = authorElement;
                        break;
                    }
                }
                listaAutorow.add(author);
                Book book = new Book(
                    resultSet.getInt("id_ksiazki"),
                    listaAutorow,
                    resultSet.getString("tytul"),
                    resultSet.getInt("rok_wydania"),
                    resultSet.getString("gatunek")
                    );
                if(!firstSearch){
                    boolean doesBookExist =false;
                    for (Book value : bookList) {
                        if (value.getId() != book.getId()) {
                            doesBookExist = true;
                        } else {
                            value.getAuthors().add(author);
                            doesBookExist = false;
                        }
                    }
                    if(doesBookExist){
                        bookList.add(book);
                    }
                    
                }
                else{
                    bookList.add(book);
                    firstSearch =false;
                }
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public List<Rent> getRentFromDB(String condition){
        List<Rent> rentList = new ArrayList<>();
        List<Book> fullBookList = new ArrayList<>(getBooksFromDB(""));
        List<Person> fullPeopleList = new ArrayList<>(getPeopleFromDB(""));
        if(!condition.equals("")){
            String where=" WHERE ";
            condition=where+condition;
        }
        String insertSQLString="SELECT * FROM PRObiblioteka.dbo.wypozyczenia" + condition;
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQLString);
            ResultSet resultSet= statement.executeQuery();
            while(resultSet.next()){
                Person person =null;
                Book book = null;
                Instant dataWypozyczenia= resultSet.getTimestamp("data_wypozyczenia").toInstant();
                Instant terminOddania= resultSet.getTimestamp("termin_oddania").toInstant();
                Instant dataOddania;
                if(resultSet.getTimestamp("oddano")!=null){
                    dataOddania= resultSet.getTimestamp("oddano").toInstant();
                }
                else{
                    dataOddania=null;
                }
                for (Book element : fullBookList) {
                    if (element.getId() == resultSet.getInt("id_ksiazki")) {
                        book =element;
                    }
                }
                for (Person element : fullPeopleList) {
                    if (element.getId() == resultSet.getInt("id_klienta")) {
                        person =element;
                    }
                }
                Rent rent = new Rent(
                    resultSet.getInt("id_wypozyczenia"),
                        person,
                        book,
                    dataWypozyczenia,
                    terminOddania,
                    dataOddania
                    );
                rentList.add(rent);
            }
            resultSet.close();
            statement.close(); 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return rentList;
    }

    //#endregion

}
