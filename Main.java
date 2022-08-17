import BL.Logic;
import DAL.DataDB;
import UI.ConsoleUI;
import UI.GUI_Menu;

public class Main {
    public static void main(String[] args) {
        DataDB dataDB = new DataDB();
        Logic logic = new Logic(dataDB);
        boolean tekstowy=false;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> dataDB.getSettings().saveSettings()));
        try {
            if (args[0].equals("Console")) {
                ConsoleUI consoleUI = new ConsoleUI(logic);
                consoleUI.Start();
                tekstowy=true;
            }
        }
        catch (ArrayIndexOutOfBoundsException ignored){
        }
        if(!tekstowy){
            GUI_Menu GUIMenu = new GUI_Menu(logic);
            GUIMenu.Start();
        }
    }
}
