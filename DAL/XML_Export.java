package DAL;

import BL.Author;
import BL.Book;
import BL.Person;
import BL.Rent;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class XML_Export {
    public XML_Export(List<Rent> rentList) {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("Rent", Rent.class);
        xStream.alias("Person", Person.class);
        xStream.alias("Book", Book.class);
        xStream.alias("Author", Author.class);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("Exported_Rents.xml")) {
            ObjectOutputStream objectOutputStream = xStream.createObjectOutputStream(fileOutputStream, "Rents");
            for (Rent rent : rentList) {
                objectOutputStream.writeObject(rent);
            }
            objectOutputStream.close();
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
    }
}
