

package UI;

import BL.Author;
import BL.Book;
import BL.Logic;
import BL.Person;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.ComponentTextDefaults;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Locale;



public class GUI_Menu {

    //#region wszystkie domyślne elementy

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel mainMenu;
    private JPanel upperPanel;
    private JButton settings;
    private JPanel hSpacer1;
    private JTabbedPane bookmarks;
    private JTabbedPane indexes;
    private JPanel indexClients;
    private JScrollPane scrollPaneClientsTable;
    private JTable clientsTable;
    private JPanel clientsPanel;
    private JLabel labelClientFirstName;
    private JTextField textFieldClientFirstName;
    private JLabel labelClientLastName;
    private JTextField textFieldClientLastName;
    private JLabel labelClientAge;
    private JTextField textFieldClientAge;
    private JLabel labelClientPhoneNumber;
    private JTextField textFieldClientPhoneNumber;
    private JLabel labelClientEmail;
    private JTextField textFieldClientEmail;
    private JLabel labelClientCity;
    private JTextField textFieldClientCity;
    private JLabel labelClientStreet;
    private JTextField textFieldClientStreet;
    private JLabel labelClientHouseNumber;
    private JTextField textFieldClientHouseNumber;
    private JButton buttonAddClient;
    private JButton buttonFiltrClients;
    private JPanel AuthorsBookmark;
    private JScrollPane scrollPaneAuthorsTable;
    private JTable AuthorsTable;
    private JPanel AuthorsPanel;
    private JLabel labelAuthorsFirstName;
    private JTextField textFieldAuthorsFirstName;
    private JLabel labelAuthorsLastname;
    private JTextField textFieldAuthorsLastName;
    private JLabel labelAuthorsAge;
    private JTextField textFieldAuthorsAge;
    private JButton buttonAddAuthor;
    private JButton buttonFiltrAuthors;
    private JPanel BooksBookmark;
    private JScrollPane scrollPaneBooksTable;
    private JTable BooksTable;
    private JPanel ClientsPanel;
    private JLabel labelBooksTitle;
    private JTextField textFieldBooksTitle;
    private JLabel labelBooksReleaseYear;
    private JTextField textFieldBooksReleaseYear;
    private JLabel labelBooksGenre;
    private JTextField textFieldBooksGenre;
    private JLabel labelBooksAuthors;
    private JLabel labelFindAuthorByLastName;
    private JTextField textFieldFindAuthorByLastName;
    private JComboBox comboBoxAuthorsListToAdd;
    private JScrollPane scrollPaneBooksAuthors;
    private JList listBooksAuthors;
    private JButton buttonResetAuthorsList;
    private JButton buttonAddBook;
    private JButton buttonFiltrBook;
    private JPanel calendar;
    private JDatePanel datePanelCalendar;
    private JScrollPane scrollPaneCalendarRentTable;
    private JTable rentTableCalendar;
    private JPanel rentsPanel;
    private JButton XML_ExportButton;
    private JButton XML_ImportButton;
    private JScrollPane scrollPaneRentTable;
    private JTable rentsTable;
    private JPanel rentPanel;
    private JLabel labelClientRent;
    private JLabel labelSearchClientByLastName;
    private JTextField FieldSearchClientByLastName;
    private JComboBox comboBoxClientListToAdd;
    private JScrollPane scrollPaneClientRents;
    private JList clientRentsList;
    private JButton ButtonResetClientList;
    private JLabel labelBookRent;
    private JLabel labelSearchBookByTitle;
    private JTextField FieldSearchBookByTitle;
    private JComboBox comboBoxBookListToAdd;
    private JScrollPane scrollPaneBooksRents;
    private JList booksRentsList;
    private JButton ButtonResetBooksList;
    private JLabel labelRentStartTime;
    private JDatePicker datePickerRentStartTime;
    private JLabel labelRentDeadlineTime;
    private JDatePicker datePickerRentDeadlineTime;
    private JLabel labelRentEndTime;
    private JDatePicker datePickerRentEndTime;
    private JButton buttonAddNewRent;
    private JButton buttonRentFilter;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //#endregion

    private final Logic logic;
    private Color background;

    //#region metody ogólne

    public GUI_Menu(Logic logic) {
        this.logic = logic;
        this.background = logic.getBackground();
        initComponents();
        mainMenu.setBackground(background);
        upperPanel.setBackground(background);
        hSpacer1.setBackground(background);
        //#region setting up correct headlines w JDatePicker
        ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.MON,"mon.");
        ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.TUE,"tue.");
        ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.WED,"wed.");
        ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.THU,"thu.");
        ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.FRI,"fri.");
        ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.SAT,"sat.");
        ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.SUN,"sun.");
        //#endregion
    }

    public void Start(){
        JFrame frame= new JFrame("Library");
        frame.setContentPane(new GUI_Menu(logic).mainMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        triggerNotification(frame);
        startKeyboardListener();

    }

    //cleaning table for each time before loading new data
    private void tableCleaner(JTable table){
        DefaultTableModel modelClear=(DefaultTableModel) table.getModel();
        modelClear.setRowCount(0);
    }

    //triggers if we don't fill up every parameter for new book,author,client or rent
    private void triggerErrorMessage(String message){
        JOptionPane.showMessageDialog(null,message,"Error!",JOptionPane.ERROR_MESSAGE);
    }

    private void InfoNotification(String message){
        JOptionPane.showMessageDialog(null,message,"Info",JOptionPane.INFORMATION_MESSAGE);
    }

    // triggers notification 5 seconds after program starts
    void triggerNotification(JFrame frame){
        ActionListener actionListener= e -> {
            Notifications notifications = new Notifications(logic);
            notifications.Start(frame);

        };
        Timer timer= new Timer(5000,actionListener);
        timer.setCoalesce(false);
        timer.setRepeats(false);
        timer.start();

    }

    JPopupMenu createPopupMenuTerminateRent(int rentID){
        JPopupMenu menu= new JPopupMenu();
        JMenuItem menuItem1= new JMenuItem(new AbstractAction("Terminate Rent") {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.terminateRentGUI(rentID);
            }
        });
        menu.add(menuItem1);
        return  menu;
    }

    //#endregion

    //#region Authors bookmark

    private void buttonAuthorFilterActionPerformed(ActionEvent e) {
        String condition ="";
        if(!textFieldAuthorsFirstName.getText().equals("")){
            condition +="imie='"+ textFieldAuthorsFirstName.getText()+"' ";
        }
        if(!textFieldAuthorsLastName.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="nazwisko='"+ textFieldAuthorsLastName.getText()+"' ";
        }
        if(!textFieldAuthorsAge.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="wiek="+ textFieldAuthorsAge.getText()+" ";
        }
        tableCleaner(AuthorsTable);
        DefaultTableModel authorsModel = logic.fillAuthorTable(AuthorsTable, condition);
        AuthorsTable.setModel(authorsModel);
    }

    private void buttonAddAuthorActionPerformed(ActionEvent e) {
        if(!textFieldAuthorsFirstName.getText().equals("") && !textFieldAuthorsLastName.getText().equals("") && !textFieldAuthorsAge.getText().equals("")) {
            try {
                logic.addAuthor(textFieldAuthorsFirstName.getText(), textFieldAuthorsLastName.getText(), Integer.parseInt(textFieldAuthorsAge.getText()));
                textFieldAuthorsFirstName.setText("");
                textFieldAuthorsLastName.setText("");
                textFieldAuthorsAge.setText("");
            }
            catch (NumberFormatException exception){
                triggerErrorMessage("Age is required as an Integer value!");
            }
        }
        else{
            triggerErrorMessage("All fields are required to be filled!Try again");
        }
    }
    //#endregion

    //#region Clients Bookmark

    private void buttonAddClientActionPerformed(ActionEvent e) {
        if(!textFieldClientFirstName.getText().equals("") && !textFieldClientLastName.getText().equals("") && !textFieldClientAge.getText().equals("")
                && !textFieldClientPhoneNumber.getText().equals("") && !textFieldClientEmail.getText().equals("") && !textFieldClientCity.getText().equals("")
                && !textFieldClientStreet.getText().equals("") && !textFieldClientHouseNumber.getText().equals("")) {
            if(textFieldClientEmail.getText().contains("@")) {
                try {
                    logic.addPerson(textFieldClientFirstName.getText(), textFieldClientLastName.getText(), Integer.parseInt(textFieldClientAge.getText()), textFieldClientPhoneNumber.getText(),
                            textFieldClientEmail.getText(), textFieldClientCity.getText(), textFieldClientStreet.getText(), Integer.parseInt(textFieldClientHouseNumber.getText()));
                    textFieldClientFirstName.setText("");
                    textFieldClientLastName.setText("");
                    textFieldClientAge.setText("");
                    textFieldClientPhoneNumber.setText("");
                    textFieldClientEmail.setText("");
                    textFieldClientCity.setText("");
                    textFieldClientStreet.setText("");
                    textFieldClientHouseNumber.setText("");
                } catch (NumberFormatException exception) {
                    triggerErrorMessage("Age and house number are required as an Integer values!");
                }
            }
            else{
                triggerErrorMessage("Incorrect email adress detected! Try again");
            }
        }
        else{
            triggerErrorMessage("All fields are required to be filled! Try again");
        }
    }

    private void buttonClientFilterActionPerformed(ActionEvent e) {
        String condition ="";
        if(!textFieldClientFirstName.getText().equals("")){
            condition +="imie='"+ textFieldClientFirstName.getText()+"' ";
        }
        if(!textFieldClientLastName.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="nazwisko='"+ textFieldClientLastName.getText()+"' ";
        }
        if(!textFieldClientAge.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="wiek="+ textFieldClientAge.getText()+" ";
        }
        if(!textFieldClientPhoneNumber.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="numertelefonu='"+ textFieldClientPhoneNumber.getText()+"' ";
        }
        if(!textFieldClientEmail.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="email='"+ textFieldClientEmail.getText()+"' ";
        }
        if(!textFieldClientCity.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="miasto='"+ textFieldClientCity.getText()+"' ";
        }
        if(!textFieldClientStreet.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="ulica='"+ textFieldClientStreet.getText()+"' ";
        }
        if(!textFieldClientHouseNumber.getText().equals("")){
            if(!condition.equals("")){
                condition +="AND ";
            }
            condition +="NrDomu="+ textFieldClientHouseNumber.getText()+" ";
        }

        tableCleaner(clientsTable);
        DefaultTableModel clientModel = logic.fillClientsTable(clientsTable, condition);
        clientsTable.setModel(clientModel);
    }
    //#endregion

    //#region Book Bookmark

    private final DefaultListModel<Author> authorsListModel = new DefaultListModel<>();
    private final DefaultListModel<Person> clientListModel = new DefaultListModel<>();
    private final DefaultListModel<Book> bookListModel = new DefaultListModel<>();

    private void buttonAddBookActionPerformed(ActionEvent e) {
        if(!textFieldBooksTitle.getText().equals("") && !textFieldBooksReleaseYear.getText().equals("") && !textFieldBooksGenre.getText().equals("") && !authorsListModel.isEmpty()) {
            try {
                int sizeAuthorsList = authorsListModel.getSize();
                StringBuilder AuthorsID = new StringBuilder();
                for(int i = 0; i< sizeAuthorsList; i++){
                    if(sizeAuthorsList >1 && i!=0){
                        AuthorsID.append(",");
                    }
                    AuthorsID.append(authorsListModel.get(i).getId());
                }
                logic.addBook(textFieldBooksTitle.getText(), Integer.parseInt(textFieldBooksReleaseYear.getText()), textFieldBooksGenre.getText(), AuthorsID.toString());
                authorsListModel.clear();
                textFieldBooksTitle.setText("");
                textFieldBooksReleaseYear.setText("");
                textFieldBooksGenre.setText("");
            }
            catch (NumberFormatException exception){
                triggerErrorMessage("Year is required as an integer value!");
            }
        }
        else{
            triggerErrorMessage("All fields are required to be filled! Try again");
        }
    }

    private void buttonBookFilterActionPerformed(ActionEvent e) {
        StringBuilder condition = new StringBuilder();
        if(!textFieldBooksTitle.getText().equals("")){
            condition.append("tytul='").append(textFieldBooksTitle.getText()).append("' ");
        }
        if(!textFieldBooksReleaseYear.getText().equals("")){
            if(!condition.toString().equals("")){
                condition.append("AND ");
            }
            condition.append("rok_wydania=").append(textFieldBooksReleaseYear.getText()).append(" ");
        }
        if(!textFieldBooksGenre.getText().equals("")){
            if(!condition.toString().equals("")){
                condition.append("AND ");
            }
            condition.append("gatunek='").append(textFieldBooksGenre.getText()).append("' ");
        }
        if(!authorsListModel.isEmpty()){
            if(!condition.toString().equals("")){
                condition.append("AND ");
            }
            int sizeAuthorsList = authorsListModel.getSize();
            for(int i = 0; i< sizeAuthorsList; i++){
                if(sizeAuthorsList >1 && i!=0){
                    condition.append(" or ");
                }
                condition.append("id_autora=").append(authorsListModel.get(i).getId());
            }
        }
        tableCleaner(BooksTable);
        DefaultTableModel bookModel = logic.fillBookTable(BooksTable, condition.toString());
        BooksTable.setModel(bookModel);
    }
    
    private void comboBoxAuthorsListToAddActionPerformed(ActionEvent e) {
        Author author = (Author) comboBoxAuthorsListToAdd.getSelectedItem();
        authorsListModel.addElement(author);
        listBooksAuthors.setModel(authorsListModel);
    }

    private void buttonResetAuthorsListActionPerformed(ActionEvent e) {
        authorsListModel.clear();
    }
    
    private void textFieldSearchAuthorsByLastNameCaretUpdate(CaretEvent e) {
        String condition ="";
        if(!textFieldFindAuthorByLastName.getText().equals("")){
            condition ="nazwisko like '" + textFieldFindAuthorByLastName.getText() + "%'";
        }
        DefaultComboBoxModel<Author> modelBookAuthors = logic.fillAuthorsComboBox(condition);
        comboBoxAuthorsListToAdd.setModel(modelBookAuthors);
    }
    
    //#endregion

    //#region calendar

    private void datePanelCalendarActionPerformed(ActionEvent e) {
        Instant instant= ((GregorianCalendar) datePanelCalendar.getModel().getValue()).toInstant();
        String date= DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("GMT+1")).format(instant);
        tableCleaner(rentTableCalendar);
        String where="termin_oddania BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'";
        DefaultTableModel tabelaWypozyczenModel= logic.fillRentTable(rentTableCalendar,where);
        rentTableCalendar.setModel(tabelaWypozyczenModel);
    }

    private void CalendarRentListMouseReleased(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            int r= rentTableCalendar.rowAtPoint(e.getPoint());
            if(r>=0 && r< rentTableCalendar.getRowCount()){
                rentTableCalendar.setRowSelectionInterval(r,r);
            }
            else{
                rentTableCalendar.clearSelection();
            }
            int rowIndex= rentTableCalendar.getSelectedRow();
            if(rowIndex<0){
                return;
            }
            int rentID = (int) rentTableCalendar.getValueAt(rowIndex,0);
            JPopupMenu popupMenu= createPopupMenuTerminateRent(rentID);
            popupMenu.show(e.getComponent(),e.getX(),e.getY());
            buttonRentFilterActionPerformed(null);
        }
    }

    //#endregion

    //#region Rent

    private void buttonAddRentActionPerformed(ActionEvent e) {
        if(!clientListModel.isEmpty() && !bookListModel.isEmpty() && datePickerRentStartTime.getModel().getValue()!=null && datePickerRentDeadlineTime.getModel().getValue()!=null) {
            Instant instantRentStartDate = ((GregorianCalendar) datePickerRentStartTime.getModel().getValue()).toInstant();
            Instant instantRentDeadlineDate = ((GregorianCalendar) datePickerRentDeadlineTime.getModel().getValue()).toInstant();
            if(instantRentDeadlineDate.getEpochSecond()> instantRentStartDate.getEpochSecond()) {
                logic.addRent(clientListModel.get(0), bookListModel.get(0), instantRentStartDate, instantRentDeadlineDate);
                clientListModel.clear();
                bookListModel.clear();
                datePickerRentStartTime.getModel().setValue(null);
                datePickerRentEndTime.getModel().setValue(null);
            }
            else {
                triggerErrorMessage("Deadline date can't be earlier than rent start date!");
            }
        }
        else{
            triggerErrorMessage("All fields are required to be filled! Try again");
        }
    }

    private void buttonRentFilterActionPerformed(ActionEvent e) {
        StringBuilder condition = new StringBuilder();
        if(!clientListModel.isEmpty()){
            int sizeClientList = clientListModel.getSize();
            for(int i = 0; i< sizeClientList; i++){
                if(sizeClientList >1 && i!=0){
                    condition.append(" or ");
                }
                condition.append("id_klienta=").append(clientListModel.get(i).getId());
            }
        }
        if(!bookListModel.isEmpty()){
            if(!condition.toString().equals("")){
                condition.append("AND ");
            }
            int sizeBookList = bookListModel.getSize();
            for(int i = 0; i< sizeBookList; i++){
                if(sizeBookList >1 && i!=0){
                    condition.append(" or ");
                }
                condition.append("id_ksiazki=").append(bookListModel.get(i).getId());
            }
        }
        if(datePickerRentStartTime.getModel().getValue()!=null){
            if(!condition.toString().equals("")){
                condition.append("AND ");
            }
            Instant instantRentStartTime = ((GregorianCalendar) datePickerRentStartTime.getModel().getValue()).toInstant();
            String date= DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("GMT+1")).format(instantRentStartTime);
            condition.append("data_wypozyczenia BETWEEN '").append(date).append(" 00:00:00' AND '").append(date).append(" 23:59:59'");
        }
        if(datePickerRentDeadlineTime.getModel().getValue()!=null){
            if(!condition.toString().equals("")){
                condition.append("AND ");
            }
            Instant instantRentDeadlineTime = ((GregorianCalendar) datePickerRentDeadlineTime.getModel().getValue()).toInstant();
            String date= DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("GMT+1")).format(instantRentDeadlineTime);
            condition.append("termin_oddania BETWEEN '").append(date).append(" 00:00:00' AND '").append(date).append(" 23:59:59'");
        }
        if(datePickerRentEndTime.getModel().getValue()!=null){
            if(!condition.toString().equals("")){
                condition.append("AND ");
            }
            Instant instantRentDeadlineTime = ((GregorianCalendar) datePickerRentEndTime.getModel().getValue()).toInstant();
            String date= DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("GMT+1")).format(instantRentDeadlineTime);
            condition.append("oddano BETWEEN '").append(date).append(" 00:00:00' AND '").append(date).append(" 23:59:59'");
        }
        tableCleaner(rentsTable);
        DefaultTableModel rentTableModel = logic.fillRentTable(rentsTable, condition.toString());
        rentsTable.setModel(rentTableModel);
    }

    private void comboBoxClientListToAddActionPerformed(ActionEvent e) {
        Person person = (Person) comboBoxClientListToAdd.getSelectedItem();
        clientListModel.addElement(person);
        clientRentsList.setModel(clientListModel);
    }

    private void comboBoxBookListToAddActionPerformed(ActionEvent e) {
        Book book = (Book) comboBoxBookListToAdd.getSelectedItem();
        bookListModel.addElement(book);
        booksRentsList.setModel(bookListModel);
    }

    private void FieldSearchClientByLastNameCaretUpdate(CaretEvent e) {
        String condition ="";
        if(!FieldSearchClientByLastName.getText().equals("")){
            condition ="nazwisko like '" + FieldSearchClientByLastName.getText() + "%'";
        }
        DefaultComboBoxModel<Person> modelClientRents = logic.fillClientsComboBox(condition);
        comboBoxClientListToAdd.setModel(modelClientRents);
    }

    private void FieldSearchBookByTitleCaretUpdate(CaretEvent e) {
        String condition ="";
        if(!FieldSearchBookByTitle.getText().equals("")){
            condition ="tytul like '" + FieldSearchBookByTitle.getText() + "%'";
        }
        DefaultComboBoxModel<Book> bookRentsModel = logic.fillBookComboBox(condition);
        comboBoxBookListToAdd.setModel(bookRentsModel);
    }

    private void ButtonResetClientListActionPerformed(ActionEvent e) {
        clientListModel.clear();
    }

    private void ButtonResetBookListActionPerformed(ActionEvent e) {
        bookListModel.clear();
    }

    private void exportToXMLActionPerformed(ActionEvent e) {
        logic.saveDataIntoXML();
    }

    private void importFromXMLActionPerformed(ActionEvent e) {
        JFileChooser chooser= new JFileChooser();
        FileFilter filter= new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.isDirectory()){
                    return true;
                }
                else {
                    String filename= pathname.getName().toLowerCase(Locale.ROOT);
                    return filename.endsWith(".xml");
                }
            }
            @Override
            public String getDescription() {
                return null;
            }
        };
        chooser.setFileFilter(filter);
        chooser.showDialog(null,"Select");
        String path =chooser.getSelectedFile().getAbsolutePath();
        int importedRents = logic.loadDataFromXML(path);
        String string ="Number of new rents imported: " + importedRents;
        InfoNotification(string);
    }


    private void rentsTableMouseReleased(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            int r= rentsTable.rowAtPoint(e.getPoint());
            if(r>=0 && r< rentsTable.getRowCount()){
                rentsTable.setRowSelectionInterval(r,r);
            }
            else{
                rentsTable.clearSelection();
            }
            int rowIndex= rentsTable.getSelectedRow();
            if(rowIndex<0){
                return;
            }
            int rentsID = (int) rentsTable.getValueAt(rowIndex,0);
            JPopupMenu popupMenu= createPopupMenuTerminateRent(rentsID);
            popupMenu.show(e.getComponent(),e.getX(),e.getY());
            buttonRentFilterActionPerformed(null);
        }
    }

    //#endregion

    //#region upper panel

    private void backgroundPersonalization() {
        background = JColorChooser.showDialog(null, "Choose background color", background);
        mainMenu.setBackground(background);
        upperPanel.setBackground(background);
        hSpacer1.setBackground(background);
        if(background !=null) {
            logic.saveBackground(background);
        }
    }

    private void settingsBackgroundActionPerformed(ActionEvent e) {
        backgroundPersonalization();
    }


    //#endregion

    //#region keylisteners

    private void startKeyboardListener(){
        KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
         keyboardFocusManager.addKeyEventDispatcher(e -> {
             if(e.getID()==KeyEvent.KEY_PRESSED){
                 if(e.getKeyCode() == KeyEvent.VK_F1)
                 {
                     backgroundPersonalization();
                 }
                 if(e.getKeyCode() == KeyEvent.VK_F3)
                 {
                     logic.saveDataIntoXML();
                 }
             }
             return false;
         });
    }

    //#endregion



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        mainMenu = new JPanel();
        upperPanel = new JPanel();
        settings = new JButton();
        hSpacer1 = new JPanel(null);
        bookmarks = new JTabbedPane();
        indexes = new JTabbedPane();
        indexClients = new JPanel();
        scrollPaneClientsTable = new JScrollPane();
        clientsTable = new JTable();
        clientsPanel = new JPanel();
        labelClientFirstName = new JLabel();
        textFieldClientFirstName = new JTextField();
        labelClientLastName = new JLabel();
        textFieldClientLastName = new JTextField();
        labelClientAge = new JLabel();
        textFieldClientAge = new JTextField();
        labelClientPhoneNumber = new JLabel();
        textFieldClientPhoneNumber = new JTextField();
        labelClientEmail = new JLabel();
        textFieldClientEmail = new JTextField();
        labelClientCity = new JLabel();
        textFieldClientCity = new JTextField();
        labelClientStreet = new JLabel();
        textFieldClientStreet = new JTextField();
        labelClientHouseNumber = new JLabel();
        textFieldClientHouseNumber = new JTextField();
        buttonAddClient = new JButton();
        buttonFiltrClients = new JButton();
        AuthorsBookmark = new JPanel();
        scrollPaneAuthorsTable = new JScrollPane();
        AuthorsTable = new JTable();
        AuthorsPanel = new JPanel();
        labelAuthorsFirstName = new JLabel();
        textFieldAuthorsFirstName = new JTextField();
        labelAuthorsLastname = new JLabel();
        textFieldAuthorsLastName = new JTextField();
        labelAuthorsAge = new JLabel();
        textFieldAuthorsAge = new JTextField();
        buttonAddAuthor = new JButton();
        buttonFiltrAuthors = new JButton();
        BooksBookmark = new JPanel();
        scrollPaneBooksTable = new JScrollPane();
        BooksTable = new JTable();
        BooksTable.setDefaultRenderer(String.class,new MultiLineCellRenderer());
        ClientsPanel = new JPanel();
        labelBooksTitle = new JLabel();
        textFieldBooksTitle = new JTextField();
        labelBooksReleaseYear = new JLabel();
        textFieldBooksReleaseYear = new JTextField();
        labelBooksGenre = new JLabel();
        textFieldBooksGenre = new JTextField();
        labelBooksAuthors = new JLabel();
        labelFindAuthorByLastName = new JLabel();
        textFieldFindAuthorByLastName = new JTextField();
        comboBoxAuthorsListToAdd = new JComboBox();
        DefaultComboBoxModel<Author> modelBooksAuthors = logic.fillAuthorsComboBox("");
        comboBoxAuthorsListToAdd.setModel(modelBooksAuthors);
        scrollPaneBooksAuthors = new JScrollPane();
        listBooksAuthors = new JList();
        buttonResetAuthorsList = new JButton();
        buttonAddBook = new JButton();
        buttonFiltrBook = new JButton();
        calendar = new JPanel();
        datePanelCalendar = new JDatePanel();
        scrollPaneCalendarRentTable = new JScrollPane();
        rentTableCalendar = new JTable();
        rentsPanel = new JPanel();
        XML_ExportButton = new JButton();
        XML_ImportButton = new JButton();
        scrollPaneRentTable = new JScrollPane();
        rentsTable = new JTable();
        rentPanel = new JPanel();
        labelClientRent = new JLabel();
        labelSearchClientByLastName = new JLabel();
        FieldSearchClientByLastName = new JTextField();
        comboBoxClientListToAdd = new JComboBox();
        DefaultComboBoxModel<Person> modelClientsRents = logic.fillClientsComboBox("");
                comboBoxClientListToAdd.setModel(modelClientsRents);
                comboBoxClientListToAdd.setRenderer(new DefaultListCellRenderer(){
                    @Override
                    public Component getListCellRendererComponent(final JList list, Object value, final int index, final boolean isSelected,
                                                                  final boolean cellHasFocus) {
                        Person person =(Person) value;
                        if(person !=null){
                            value="ID: " + person.getId() + " First name: " + person.getFirstName() + " Last name: " + person.getLastName();
                        }
                        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    }
                });
        scrollPaneClientRents = new JScrollPane();
        clientRentsList = new JList();
        ButtonResetClientList = new JButton();
        labelBookRent = new JLabel();
        labelSearchBookByTitle = new JLabel();
        FieldSearchBookByTitle = new JTextField();
        comboBoxBookListToAdd = new JComboBox();
        DefaultComboBoxModel<Book> modelBooksRents = logic.fillBookComboBox("");
                comboBoxBookListToAdd.setModel(modelBooksRents);
        comboBoxBookListToAdd.setRenderer(new DefaultListCellRenderer(){
                    @Override
                    public Component getListCellRendererComponent(final JList list, Object value, final int index, final boolean isSelected,
                                                                  final boolean cellHasFocus) {
                        Book book =(Book) value;
                        if(book !=null){
                        	value="ID: " + book.getId() + " Title: " + book.getTitle();
                        }
                        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    }
                });
        scrollPaneBooksRents = new JScrollPane();
        booksRentsList = new JList();
        ButtonResetBooksList = new JButton();
        labelRentStartTime = new JLabel();
        datePickerRentStartTime = new JDatePicker();
        labelRentDeadlineTime = new JLabel();
        datePickerRentDeadlineTime = new JDatePicker();
        labelRentEndTime = new JLabel();
        datePickerRentEndTime = new JDatePicker();
        buttonAddNewRent = new JButton();
        buttonRentFilter = new JButton();

        //======== mainMenu ========
        {
            mainMenu.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
            swing. border. EmptyBorder( 0, 0, 0, 0) , "Library", javax. swing. border
            . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font (""
            ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) , mainMenu. getBorder
            ( )) ); mainMenu. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
            .beans .PropertyChangeEvent e) {if ("" .equals (e .getPropertyName () )) throw new RuntimeException
            ( ); }} );
            mainMenu.setLayout(new BorderLayout());

            //======== UpperBound ========
            {
                upperPanel.setLayout(new BorderLayout());

                //---- ustawienia ----
                settings.setText("ColorSettings");
                settings.addActionListener(e -> settingsBackgroundActionPerformed(e));
                upperPanel.add(settings, BorderLayout.WEST);


                //---- hSpacer1 ----
                hSpacer1.setMinimumSize(new Dimension(1000, 12));
                hSpacer1.setPreferredSize(new Dimension(700, 10));
                upperPanel.add(hSpacer1, BorderLayout.EAST);
            }
            mainMenu.add(upperPanel, BorderLayout.NORTH);

            //======== Tabs ========
            {

                //======== Bookmarks ========
                {

                    //======== clientsBookmark ========
                    {
                        indexClients.setLayout(new MigLayout(
                            "insets 0,hidemode 3,gap 0 0",
                            // columns
                            "[grow,fill]",
                            // rows
                            "[grow,fill]"));

                        //======== scrollPaneTabelaKlientow ========
                        {

                            //---- tabelaKlientow ----
                            clientsTable.setPreferredSize(new Dimension(700, 800));
                            clientsTable.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                    "ID", "First name", "Last name", "Age", "Phone number", "E-mail", "City", "Street", "House number"
                                }
                            ) {
                                Class<?>[] columnTypes = new Class<?>[] {
                                    Integer.class, String.class, String.class, Integer.class, String.class, String.class, String.class, String.class, Integer.class
                                };
                                @Override
                                public Class<?> getColumnClass(int columnIndex) {
                                    return columnTypes[columnIndex];
                                }
                            });
                            clientsTable.setAutoCreateRowSorter(true);
                            scrollPaneClientsTable.setViewportView(clientsTable);
                        }
                        indexClients.add(scrollPaneClientsTable, "cell 0 0");

                        //======== clientPanel ========
                        {
                            clientsPanel.setLayout(new MigLayout(
                                "hidemode 3",
                                // columns
                                "[fill]",
                                // rows
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]"));

                            //---- labelClientFirstName ----
                            labelClientFirstName.setText("First name");
                            clientsPanel.add(labelClientFirstName, "cell 0 0");
                            clientsPanel.add(textFieldClientFirstName, "cell 0 1");

                            //---- labelClientLastName ----
                            labelClientLastName.setText("Last name");
                            clientsPanel.add(labelClientLastName, "cell 0 2");
                            clientsPanel.add(textFieldClientLastName, "cell 0 3");

                            //---- labelClientAge ----
                            labelClientAge.setText("Age");
                            clientsPanel.add(labelClientAge, "cell 0 4");
                            clientsPanel.add(textFieldClientAge, "cell 0 5");

                            //---- labelClientPhoneNumber ----
                            labelClientPhoneNumber.setText("Phone number");
                            clientsPanel.add(labelClientPhoneNumber, "cell 0 6");
                            clientsPanel.add(textFieldClientPhoneNumber, "cell 0 7");

                            //---- labelClientEmail ----
                            labelClientEmail.setText("E-mail");
                            clientsPanel.add(labelClientEmail, "cell 0 8");
                            clientsPanel.add(textFieldClientEmail, "cell 0 9");

                            //---- labelClientCity ----
                            labelClientCity.setText("City");
                            clientsPanel.add(labelClientCity, "cell 0 10");
                            clientsPanel.add(textFieldClientCity, "cell 0 11");

                            //---- labelClientStreet ----
                            labelClientStreet.setText("Street");
                            clientsPanel.add(labelClientStreet, "cell 0 12");
                            clientsPanel.add(textFieldClientStreet, "cell 0 13");

                            //---- labelClientHouseNumber ----
                            labelClientHouseNumber.setText("House number");
                            clientsPanel.add(labelClientHouseNumber, "cell 0 14");
                            clientsPanel.add(textFieldClientHouseNumber, "cell 0 15");

                            //---- buttonAddClient ----
                            buttonAddClient.setText("Add client");
                            buttonAddClient.addActionListener(e -> buttonAddClientActionPerformed(e));
                            clientsPanel.add(buttonAddClient, "cell 0 16");

                            //---- buttonFiltrujKlientow ----
                            buttonFiltrClients.setText("Filtr clients");
                            buttonFiltrClients.addActionListener(e -> buttonClientFilterActionPerformed(e));
                            clientsPanel.add(buttonFiltrClients, "cell 0 16");
                        }
                        indexClients.add(clientsPanel, "cell 1 0");
                    }
                    indexes.addTab("Clients", indexClients);

                    //======== AuthorsBookmark ========
                    {
                        AuthorsBookmark.setLayout(new MigLayout(
                            "insets 0,hidemode 3,gap 0 0",
                            // columns
                            "[grow,fill]",
                            // rows
                            "[grow,fill]"));

                        //======== scrollPaneAuthorsTable ========
                        {

                            //---- authorsTable ----
                            AuthorsTable.setPreferredSize(new Dimension(700, 800));
                            AuthorsTable.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                    "ID", "First name", "Last name", "Age"
                                }
                            ) {
                                Class<?>[] columnTypes = new Class<?>[] {
                                    Integer.class, String.class, String.class, Integer.class
                                };
                                @Override
                                public Class<?> getColumnClass(int columnIndex) {
                                    return columnTypes[columnIndex];
                                }
                            });
                            AuthorsTable.setPreferredScrollableViewportSize(new Dimension(450, 800));
                            AuthorsTable.setAutoCreateRowSorter(true);
                            scrollPaneAuthorsTable.setViewportView(AuthorsTable);
                        }
                        AuthorsBookmark.add(scrollPaneAuthorsTable, "cell 0 0");

                        //======== panelAuthor ========
                        {
                            AuthorsPanel.setLayout(new MigLayout(
                                "hidemode 3",
                                // columns
                                "[fill]" +
                                "[fill]",
                                // rows
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]"));

                            //---- labelAuthorsFirstName ----
                            labelAuthorsFirstName.setText("First name");
                            AuthorsPanel.add(labelAuthorsFirstName, "cell 0 0");

                            //---- textFieldAuthorsFirstName ----
                            textFieldAuthorsFirstName.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                            AuthorsPanel.add(textFieldAuthorsFirstName, "cell 0 1");

                            //---- labelAuthorsLastName ----
                            labelAuthorsLastname.setText("Last name");
                            AuthorsPanel.add(labelAuthorsLastname, "cell 0 2");

                            //---- textFieldAuthorsLastName ----
                            textFieldAuthorsLastName.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                            AuthorsPanel.add(textFieldAuthorsLastName, "cell 0 3");

                            //---- labelAuthorsAge ----
                            labelAuthorsAge.setText("Age");
                            AuthorsPanel.add(labelAuthorsAge, "cell 0 4");

                            //---- textFieldAuthorsAge ----
                            textFieldAuthorsAge.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                            AuthorsPanel.add(textFieldAuthorsAge, "cell 0 5");

                            //---- buttonAddAuthor ----
                            buttonAddAuthor.setText("Add author");
                            buttonAddAuthor.addActionListener(e -> buttonAddAuthorActionPerformed(e));
                            AuthorsPanel.add(buttonAddAuthor, "cell 0 6");

                            //---- buttonAuhorsFilter ----
                            buttonFiltrAuthors.setText("Filtr authors");
                            buttonFiltrAuthors.addActionListener(e -> buttonAuthorFilterActionPerformed(e));
                            AuthorsPanel.add(buttonFiltrAuthors, "cell 0 6");
                        }
                        AuthorsBookmark.add(AuthorsPanel, "cell 1 0");
                    }
                    indexes.addTab("Authors", AuthorsBookmark);

                    //======== booksBookmark ========
                    {
                        BooksBookmark.setLayout(new GridLayout());

                        //======== scrollPaneBooksTable ========
                        {
                            scrollPaneBooksTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scrollPaneBooksTable.setPreferredSize(new Dimension(500, 1500));
                            scrollPaneBooksTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

                            //---- tabelaKsiazek ----
                            BooksTable.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                    "ID", "Title", "Release Year", "Genre", "Authors"
                                }
                            ) {
                                Class<?>[] columnTypes = new Class<?>[] {
                                    Integer.class, Object.class, Integer.class, Object.class, String.class
                                };
                                @Override
                                public Class<?> getColumnClass(int columnIndex) {
                                    return columnTypes[columnIndex];
                                }
                            });
                            {
                                TableColumnModel cm = BooksTable.getColumnModel();
                                cm.getColumn(4).setPreferredWidth(300);
                            }
                            BooksTable.setPreferredScrollableViewportSize(new Dimension(450, 1500));
                            BooksTable.setAutoCreateRowSorter(true);
                            BooksTable.setMinimumSize(new Dimension(75, 800));
                            BooksTable.setMaximumSize(new Dimension(2147483647, 2000));
                            BooksTable.setOpaque(false);
                            scrollPaneBooksTable.setViewportView(BooksTable);
                        }
                        BooksBookmark.add(scrollPaneBooksTable);

                        //======== panelBooks ========
                        {
                            ClientsPanel.setLayout(new MigLayout(
                                "hidemode 3",
                                // columns
                                "[grow,fill]",
                                // rows
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]"));

                            //---- labelBookTitle ----
                            labelBooksTitle.setText("Title");
                            ClientsPanel.add(labelBooksTitle, "cell 0 0");

                            //---- textFieldBookTitle ----
                            textFieldBooksTitle.setNextFocusableComponent(textFieldBooksReleaseYear);
                            ClientsPanel.add(textFieldBooksTitle, "cell 0 1");

                            //---- labelBookReleaseYear ----
                            labelBooksReleaseYear.setText("Release year");
                            ClientsPanel.add(labelBooksReleaseYear, "cell 0 2");

                            //---- textFieldBookReleaseYear ----
                            textFieldBooksReleaseYear.setNextFocusableComponent(textFieldBooksGenre);
                            ClientsPanel.add(textFieldBooksReleaseYear, "cell 0 3");

                            //---- labelBookGenre ----
                            labelBooksGenre.setText("Genre");
                            ClientsPanel.add(labelBooksGenre, "cell 0 4");

                            //---- textFieldBookGenre ----
                            textFieldBooksGenre.setNextFocusableComponent(textFieldFindAuthorByLastName);
                            ClientsPanel.add(textFieldBooksGenre, "cell 0 5");

                            //---- labelBookAuthors ----
                            labelBooksAuthors.setText("Authors");
                            ClientsPanel.add(labelBooksAuthors, "cell 0 6");

                            //---- labelSearchAuthorByLastName ----
                            labelFindAuthorByLastName.setText("Search author by lastname");
                            ClientsPanel.add(labelFindAuthorByLastName, "cell 0 7");

                            //---- textFieldSearchAuthorByLastName ----
                            textFieldFindAuthorByLastName.setPreferredSize(new Dimension(200, 30));
                            textFieldFindAuthorByLastName.addCaretListener(e -> textFieldSearchAuthorsByLastNameCaretUpdate(e));
                            ClientsPanel.add(textFieldFindAuthorByLastName, "cell 0 8");

                            //---- comboBoxAuthorsListToAdd ----
                            comboBoxAuthorsListToAdd.addActionListener(e -> comboBoxAuthorsListToAddActionPerformed(e));
                            ClientsPanel.add(comboBoxAuthorsListToAdd, "cell 0 10");

                            //======== scrollPaneAuthorsBook ========
                            {
                                scrollPaneBooksAuthors.setViewportView(listBooksAuthors);
                            }
                            ClientsPanel.add(scrollPaneBooksAuthors, "cell 0 11 1 9");

                            //---- buttonResetAuthorsList ----
                            buttonResetAuthorsList.setText("Reset authors list");
                            buttonResetAuthorsList.addActionListener(e -> buttonResetAuthorsListActionPerformed(e));
                            ClientsPanel.add(buttonResetAuthorsList, "cell 0 20");

                            //---- buttonAddBook ----
                            buttonAddBook.setText("Add new book");
                            buttonAddBook.addActionListener(e -> buttonAddBookActionPerformed(e));
                            ClientsPanel.add(buttonAddBook, "cell 0 21");

                            //---- buttonBookFilter ----
                            buttonFiltrBook.setText("Filtr books");
                            buttonFiltrBook.addActionListener(e -> buttonBookFilterActionPerformed(e));
                            ClientsPanel.add(buttonFiltrBook, "cell 0 21");
                        }
                        BooksBookmark.add(ClientsPanel);
                    }
                    indexes.addTab("Books", BooksBookmark);

                    indexes.setSelectedIndex(0);
                }
                bookmarks.addTab("Tabs", indexes);

                //======== Calendar ========
                {
                    calendar.setLayout(new MigLayout(
                        "insets 0,hidemode 3,gap 0 0",
                        // columns
                        "[grow,fill]" +
                        "[fill]" +
                        "[fill]",
                        // rows
                        "[grow,fill]" +
                        "[]"));

                    //---- datePanelCalendar ----
                    datePanelCalendar.addActionListener(e -> datePanelCalendarActionPerformed(e));
                    calendar.add(datePanelCalendar, "cell 0 0");

                    //======== scrollPaneRentTableCalendar ========
                    {
                        scrollPaneCalendarRentTable.setPreferredSize(new Dimension(600, 827));

                        //---- rentTableCalendar ----
                        rentTableCalendar.setPreferredSize(new Dimension(700, 800));
                        rentTableCalendar.setModel(new DefaultTableModel(
                            new Object[][] {
                            },
                            new String[] {
                                "ID", "Client", "Book", "RentStartTimeDate", "RentDeadlineDate", "RentEndTimeDate"
                            }
                        ) {
                            Class<?>[] columnTypes = new Class<?>[] {
                                Integer.class, String.class, String.class, Object.class, Object.class, Object.class
                            };
                            @Override
                            public Class<?> getColumnClass(int columnIndex) {
                                return columnTypes[columnIndex];
                            }
                        });
                        rentTableCalendar.setPreferredScrollableViewportSize(new Dimension(450, 800));
                        rentTableCalendar.setAutoCreateRowSorter(true);
                        rentTableCalendar.setInheritsPopupMenu(true);
                        rentTableCalendar.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                CalendarRentListMouseReleased(e);
                            }
                        });
                        scrollPaneCalendarRentTable.setViewportView(rentTableCalendar);
                    }
                    calendar.add(scrollPaneCalendarRentTable, "cell 1 0");
                }
                bookmarks.addTab("Calendar", calendar);

                //======== Rents ========
                {
                    rentsPanel.setLayout(new MigLayout(
                        "insets 0,hidemode 3",
                        // columns
                        "[grow,left]" +
                        "[fill]",
                        // rows
                        "[]" +
                        "[fill]" +
                        "[]"));

                    //---- XML_Export ----
                    XML_ExportButton.setText("Export rents to XML");
                    XML_ExportButton.addActionListener(e -> exportToXMLActionPerformed(e));
                    rentsPanel.add(XML_ExportButton, "cell 0 1");

                    //---- XML_Import ----
                    XML_ImportButton.setText("Import rents from XML");
                    XML_ImportButton.addActionListener(e -> importFromXMLActionPerformed(e));
                    rentsPanel.add(XML_ImportButton, "cell 0 1");

                    //======== scrollPaneRentsTable ========
                    {
                        scrollPaneRentTable.setPreferredSize(new Dimension(0, 0));
                        scrollPaneRentTable.setMinimumSize(new Dimension(1200, 827));

                        //---- rentsTable ----
                        rentsTable.setModel(new DefaultTableModel(
                            new Object[][] {
                            },
                            new String[] {
                                "ID", "Client", "Book", "RentStartTimeDate", "RentDeadlineDate", "RentEndTimeDate"
                            }
                        ) {
                            Class<?>[] columnTypes = new Class<?>[] {
                                Integer.class, String.class, String.class, Object.class, Object.class, Object.class
                            };
                            @Override
                            public Class<?> getColumnClass(int columnIndex) {
                                return columnTypes[columnIndex];
                            }
                        });
                        rentsTable.setPreferredScrollableViewportSize(new Dimension(450, 800));
                        rentsTable.setAutoCreateRowSorter(true);
                        rentsTable.setInheritsPopupMenu(true);
                        rentsTable.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                rentsTableMouseReleased(e);
                            }
                        });
                        scrollPaneRentTable.setViewportView(rentsTable);
                    }
                    rentsPanel.add(scrollPaneRentTable, "cell 0 2");

                    //======== panelRents ========
                    {
                        rentPanel.setLayout(new MigLayout(
                            "hidemode 3",
                            // columns
                            "[fill]" +
                            "[fill]",
                            // rows
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]"));

                        //---- labelClientRents ----
                        labelClientRent.setText("Client");
                        rentPanel.add(labelClientRent, "cell 0 0");

                        //---- labelSearchClientByLastName ----
                        labelSearchClientByLastName.setText("Search client by last name");
                        rentPanel.add(labelSearchClientByLastName, "cell 0 1");

                        //---- FieldSearchClientByLastName ----
                        FieldSearchClientByLastName.addCaretListener(e -> FieldSearchClientByLastNameCaretUpdate(e));
                        rentPanel.add(FieldSearchClientByLastName, "cell 0 2");

                        //---- comboBoxClientListToAdd ----
                        comboBoxClientListToAdd.addActionListener(e -> comboBoxClientListToAddActionPerformed(e));
                        rentPanel.add(comboBoxClientListToAdd, "cell 0 3");

                        //======== scrollPaneClientRents ========
                        {
                            scrollPaneClientRents.setViewportView(clientRentsList);
                        }
                        rentPanel.add(scrollPaneClientRents, "cell 0 5");

                        //---- ButtonResetClientList ----
                        ButtonResetClientList.setText("Reset client list");
                        ButtonResetClientList.addActionListener(e -> ButtonResetClientListActionPerformed(e));
                        rentPanel.add(ButtonResetClientList, "cell 0 6");

                        //---- labelBookRents ----
                        labelBookRent.setText("Book");
                        rentPanel.add(labelBookRent, "cell 0 7");

                        //---- labelSearchBookByTitle ----
                        labelSearchBookByTitle.setText("Search book by title");
                        rentPanel.add(labelSearchBookByTitle, "cell 0 8");

                        //----  FieldSearchBookByTitle ----
                        FieldSearchBookByTitle.addCaretListener(e -> FieldSearchBookByTitleCaretUpdate(e));
                        rentPanel.add(FieldSearchBookByTitle, "cell 0 9");

                        //---- comboBoxBookListToAdd ----
                        comboBoxBookListToAdd.addActionListener(e -> comboBoxBookListToAddActionPerformed(e));
                        rentPanel.add(comboBoxBookListToAdd, "cell 0 10");

                        //======== scrollPaneBooksRents ========
                        {
                            scrollPaneBooksRents.setViewportView(booksRentsList);
                        }
                        rentPanel.add(scrollPaneBooksRents, "cell 0 11");

                        //---- ButtonResetBooksList ----
                        ButtonResetBooksList.setText("Reset books list");
                        ButtonResetBooksList.addActionListener(e -> ButtonResetBookListActionPerformed(e));
                        rentPanel.add(ButtonResetBooksList, "cell 0 12");

                        //---- labelRentStartTime ----
                        labelRentStartTime.setText("Rent Start Date");
                        rentPanel.add(labelRentStartTime, "cell 0 13");
                        rentPanel.add(datePickerRentStartTime, "cell 0 15");

                        //---- labelRentDeadlineTime ----
                        labelRentDeadlineTime.setText("Rent Deadline Date");
                        rentPanel.add(labelRentDeadlineTime, "cell 0 17");
                        rentPanel.add(datePickerRentDeadlineTime, "cell 0 18");

                        //---- labelRentEndTime ----
                        labelRentEndTime.setText("Rent End  Date");
                        rentPanel.add(labelRentEndTime, "cell 0 19");
                        rentPanel.add(datePickerRentEndTime, "cell 0 20");

                        //---- buttonAddNewRen ----
                        buttonAddNewRent.setText("Add new rent");
                        buttonAddNewRent.addActionListener(e -> buttonAddRentActionPerformed(e));
                        rentPanel.add(buttonAddNewRent, "cell 0 21");

                        //---- buttonRentFilter ----
                        buttonRentFilter.setText("Filtr rents");
                        buttonRentFilter.addActionListener(e -> buttonRentFilterActionPerformed(e));
                        rentPanel.add(buttonRentFilter, "cell 0 21");
                    }
                    rentsPanel.add(rentPanel, "cell 1 2");
                }
                bookmarks.addTab("Rents", rentsPanel);
            }
            mainMenu.add(bookmarks, BorderLayout.CENTER);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
