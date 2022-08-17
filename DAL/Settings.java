package DAL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.io.*;
import java.util.Collections;

public class Settings {

    //Dane
    public String server;
    public String instance;
    public String login;
    public String password;

    //Kolor
    public int colorR;
    public int colorG;
    public int colorB;


    public void getSettings(){
        Settings settings = new Settings();
        XStream xStream= new XStream(new DomDriver());
        xStream.addPermission(NoTypePermission.NONE);
        xStream.addPermission(NullPermission.NULL);
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStream.allowTypeHierarchy(Collections.class);
        xStream.allowTypesByWildcard(new String[]{"DAL.Settings"});
        xStream.alias("Settings", Settings.class);

        try(FileInputStream fileInputStream= new FileInputStream(".\\settings.xml")){
            ObjectInputStream objectInputStream= xStream.createObjectInputStream(fileInputStream);
            try{
                settings = (Settings) objectInputStream.readObject();
            }
            catch (EOFException e){
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        this.instance = settings.instance;
        this.server = settings.server;
        this.login= settings.login;
        this.password = settings.password;
        this.colorR = settings.colorR;
        this.colorG = settings.colorG;
        this.colorB = settings.colorB;
    }

    public void saveSettings() {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("Settings", Settings.class);
        try (FileOutputStream fileOutputStream = new FileOutputStream(".\\settings.xml")) {
            Settings settings = new Settings();
            settings.server = server;
            settings.instance = instance;
            settings.login=login;
            settings.password = password;
            settings.colorB = colorB;
            settings.colorR = colorR;
            settings.colorG = colorG;
            ObjectOutputStream objectOutputStream = xStream.createObjectOutputStream(fileOutputStream, "settings");
            objectOutputStream.writeObject(settings);
            objectOutputStream.close();
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
    }
}
