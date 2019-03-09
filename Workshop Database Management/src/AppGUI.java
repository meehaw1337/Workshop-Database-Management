import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppGUI {
    JTextField lastNameTextField;
    JButton buttonSearch;
    JLabel labelxD;
    JPanel panelMain;
    JTable table1;
    private JLabel insertionLabel;
    private JTextField workshopNameField;
    private JTextField dayIDField;
    private JTextField numberOfSeatsField;
    private JLabel workshopNameLabel;
    private JLabel dayIDLabel;
    private JLabel numberOfSeatsLabel;
    private JButton dataInsertionButton;
    private JButton buttonWorkshopInsertionDialog;

    WorkshopDAO workshopDAO;


    public AppGUI() throws SQLException  {

        // Create the DAO
        try {
            this.workshopDAO = new WorkshopDAO("jdbc:sqlserver://dbadmin.iisg.agh.edu.pl", "szreter", "s9AynxjEzn");
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Failed to connect to the database. Check your VPN.");
            System.exit(0);
        }

        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                try {
                    // Get text from text field
                    String workshopName = lastNameTextField.getText();

                    // Get employees for the last name
                    List<Workshop> workshopList = new ArrayList<>();

                    if (workshopName != null && workshopName.trim().length() > 0) {
                        workshopList = workshopDAO.searchWorkshops(workshopName);
                    }
                    else
                        workshopList = workshopDAO.getAllWorkshops();

                    // Print out the result
                    WorkshopTableModel tableModel = new WorkshopTableModel(workshopList);
                    table1.setModel(tableModel);
                }
                catch(SQLException exc){
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + exc.getMessage());
                }

            }
        });
        dataInsertionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get text from text fields
                    String workshopName = workshopNameField.getText();
                    String dayID = dayIDField.getText();
                    String numberOfSeats = numberOfSeatsField.getText();

                    // If text fields aren't empty, insert data into the database
                    if(workshopName != null && dayID != null && numberOfSeats != null && workshopName.trim().length()>0 && dayID.trim().length()>0 && numberOfSeats.trim().length()>0){
                        workshopDAO.insertWorkshop(dayID, numberOfSeats, workshopName);
                        JOptionPane.showMessageDialog(null, "Data inserted successfully.");
                        buttonSearch.doClick();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "All text fields must be filled to insert data.");
                    }
                }
                catch(SQLException exc){
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Data insertion failed. " + exc.getMessage());
                }
            }
        });
        buttonSearch.doClick();

        buttonWorkshopInsertionDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorkshopInsertionDialog dialog = new WorkshopInsertionDialog(AppGUI.this);
                dialog.pack();
                dialog.setLocation(630, 330);
                dialog.setVisible(true);
            }
        });
    }

}
