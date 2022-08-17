package DAL;

import BL.Author;
import BL.Book;
import BL.Person;
import BL.Rent;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class XML_Import {
    public XML_Import(List<Rent> rentTable, List<Rent> newRent, String path) {
        XStream xStream = new XStream(new DomDriver());
        xStream.addPermission(NoTypePermission.NONE);
        xStream.addPermission(NullPermission.NULL);
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStream.allowTypeHierarchy(Collections.class);
        xStream.allowTypesByWildcard(new String[]{"BL.Rent", "BL.Person", "BL.Book", "BL.Author"});
        xStream.alias("Rent", Rent.class);
        xStream.alias("Person", Person.class);
        xStream.alias("Book", Book.class);
        xStream.alias("Author", Author.class);

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            ObjectInputStream objectInputStream = xStream.createObjectInputStream(fileInputStream);
            try {
                while (true) {
                    newRent.add((Rent) objectInputStream.readObject());
                    if (compareNewData(rentTable, newRent.get(newRent.size() - 1))) {
                        newRent.remove(newRent.get(newRent.size() - 1));
                    }
                }
            } catch (EOFException e) {
                //EOF
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

        public boolean compareNewData (List <Rent> rentTable, Rent record){
            for (Rent rent : rentTable) {
                if (Objects.equals(rent.getId(), record.getId())) {
                    return true;
                }
            }
            return false;
        }
    }