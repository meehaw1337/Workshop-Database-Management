import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class WorkshopInsertionDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel workshopNameLabel;
    private JLabel dayIDLabel;
    private JLabel noOfSeatsLabel;
    private JTextField textFieldName;
    private JTextField textFieldDayID;
    private JTextField textFieldNoOfSeats;

    private AppGUI appGUI;

    public WorkshopInsertionDialog(AppGUI appGUI) {
        this.appGUI = appGUI;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            // Get text from text fields
            String workshopName = textFieldName.getText();
            String dayID = textFieldDayID.getText();
            String numberOfSeats = textFieldNoOfSeats.getText();

            // If text fields aren't empty, insert data into the database
            if(workshopName != null && dayID != null && numberOfSeats != null && workshopName.trim().length()>0 && dayID.trim().length()>0 && numberOfSeats.trim().length()>0){
                this.appGUI.workshopDAO.insertWorkshop(dayID, numberOfSeats, workshopName);
                JOptionPane.showMessageDialog(null, "Data inserted successfully.");
                this.appGUI.buttonSearch.doClick();
            }
            else{
                JOptionPane.showMessageDialog(null, "All text fields must be filled to insert data.");
            }
        }
        catch(SQLException exc){
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Data insertion failed. " + exc.getMessage());
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        WorkshopInsertionDialog dialog = new WorkshopInsertionDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
