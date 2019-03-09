import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class WorkshopDAO {

    private Connection myConnection;

    public WorkshopDAO(String dbUrl, String user, String password) throws SQLException {

        // Connect to the database
        this.myConnection = DriverManager.getConnection(dbUrl, user, password);

        System.out.println("Connected to database: " + dbUrl);

        // Switch catalog
        this.myConnection.setCatalog("szreter_a");

        System.out.println("Changing catalog to szreter_a");


    }

    public List<Workshop> getAllWorkshops() throws SQLException {
        List<Workshop> workshopList = new ArrayList<>();

            // Create a statement
            Statement myStatement = this.myConnection.createStatement();

            // Create a set of results from the executed query
            ResultSet myResult = myStatement.executeQuery("SELECT WorkshopID, DayID, NumberOfSeats, WorkshopName FROM Workshops");

            // Add each row from the result set to the result list
            while (myResult.next()) {
                Workshop workshopToAdd = rowToWorkshop(myResult);
                workshopList.add(workshopToAdd);
            }

        return workshopList;
    }

    public List<Workshop> searchWorkshops(String workshopName) throws SQLException {
        List<Workshop> workshopList = new ArrayList<>();

        // Prepare a statement
        workshopName += "%";
        PreparedStatement myStatement = myConnection.prepareStatement("SELECT WorkshopID, DayID, NumberOfSeats, WorkshopName FROM Workshops WHERE WorkshopName LIKE ?");
        myStatement.setString(1, workshopName);

        // Create a set of results from the executed query
        ResultSet myResult = myStatement.executeQuery();

        // Add each row from the result set to the result list
        while(myResult.next()){
            Workshop workshopToAdd = rowToWorkshop(myResult);
            workshopList.add(workshopToAdd);
        }

        return workshopList;
    }

    public void insertWorkshop(String DayID, String NumberOfSeats, String WorkshopName) throws SQLException{
        // Prepare insertion statement
        PreparedStatement myStatement = myConnection.prepareStatement("INSERT INTO Workshops (DayID, NumberOfSeats, WorkshopName) VALUES (?, ?, ?)");
        myStatement.setString(1, DayID);
        myStatement.setString(2, NumberOfSeats);
        myStatement.setString(3, WorkshopName);

        // Execute the statement
        myStatement.executeUpdate();

    }

    public Workshop rowToWorkshop(ResultSet myResult) throws SQLException {
        return new Workshop(myResult.getString("WorkshopID"), myResult.getString("DayID"), myResult.getString("NumberOfSeats"), myResult.getString("WorkshopName"));
    }

    public static void main(String args[]) throws SQLException {
        //WorkshopDAO myDAO = new WorkshopDAO("jdbc:sqlserver://dbadmin.iisg.agh.edu.pl", "student", "student");
        JFrame frame = new JFrame("AppGUI");
        frame.setContentPane(new AppGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
