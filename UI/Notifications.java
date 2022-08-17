

package UI;

import java.awt.*;
import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.*;

import BL.Logic;
import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class Notifications {

    //#region all default elements
    
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel mainWindow;
    private JLabel label1;
    private JScrollPane scrollPaneCalendarRentTable;
    private JTable notificationRentTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    
    //#endregion

    private final Logic logic;
    private Clip klip;

    
    public Notifications(Logic logic) {
        this.logic = logic;
        initComponents();
        Instant todayInstant =Instant.now();
        Instant rentEndDateIntant = todayInstant.plusSeconds(3*86400L);
        String rentEndDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("GMT+1")).format(rentEndDateIntant);
        String dzisiaj= DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("GMT+1")).format(todayInstant);
        String where="Oddano is NULL AND termin_oddania BETWEEN '"+dzisiaj+" 00:00:00' AND '"+ rentEndDate +" 23:59:59'";
        cleanTable(notificationRentTable);
        DefaultTableModel model= logic.fillRentTable(notificationRentTable,where);
        notificationRentTable.setModel(model);
        playNotificationSound();
    }

    public void Start(JFrame parentFrame){
        JDialog frame= new JDialog(parentFrame,"Notification",true);
        //JFrame frame= new JFrame("Powiadomienia");
        frame.setContentPane(new Notifications(logic).mainWindow);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void cleanTable(JTable table){
        DefaultTableModel modelClear=(DefaultTableModel) table.getModel();
        modelClear.setRowCount(0);
    }

    public void playNotificationSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(".\\UI\\NotificationSound.wav"));
            klip = AudioSystem.getClip();
            klip.open(audioInputStream);
            klip.start();
        }
        catch(Exception ex)
        {
            System.out.println("Something wrong with playing the sound");
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        mainWindow = new JPanel();
        label1 = new JLabel();
        scrollPaneCalendarRentTable = new JScrollPane();
        notificationRentTable = new JTable();

        //======== mainWindow ========
        {
            mainWindow.setPreferredSize(new Dimension(900, 500));
            mainWindow.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing
            .border.EmptyBorder(0,0,0,0), "Notification",javax.swing.border.TitledBorder
            .CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("",java.
            awt.Font.BOLD,12),java.awt.Color.red), mainWindow. getBorder()))
            ;
            mainWindow. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e
            ){if("\u0062order".equals(e.getPropertyName()))throw new RuntimeException();}})
            ;
            mainWindow.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[886,fill]",
                // rows
                "[]" +
                "[]"));

            //---- label1 ----
            label1.setText("Unreturned books with deadline coming soon");
            label1.setHorizontalTextPosition(SwingConstants.CENTER);
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            label1.setFont(new Font("Segoe UI", Font.PLAIN, 26));
            mainWindow.add(label1, "cell 0 0");

            //======== scrollPaneCalendarRentTable ========
            {
                scrollPaneCalendarRentTable.setPreferredSize(new Dimension(800, 400));

                //---- powiadomieniatabelaWypozyczen ----
                notificationRentTable.setPreferredSize(new Dimension(700, 400));
                notificationRentTable.setModel(new DefaultTableModel(
                    new Object[][] {
                    },
                    new String[] {
                        "ID", "Client", "Book", "Rent Start Date", "Rent Deadline Date", "Rent End Date"
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
                notificationRentTable.setPreferredScrollableViewportSize(new Dimension(450, 0));
                notificationRentTable.setOpaque(false);
                scrollPaneCalendarRentTable.setViewportView(notificationRentTable);
            }
            mainWindow.add(scrollPaneCalendarRentTable, "cell 0 1");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    
}
