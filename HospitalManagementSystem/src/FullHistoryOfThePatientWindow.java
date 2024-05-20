import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FullHistoryOfThePatientWindow {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private Statement statement;
    private Connection connection;
    private JButton backButton;
    public FullHistoryOfThePatientWindow(){
        connection = DatabaseConnection.getConnection();

        frame = new JFrame("Full History of The Patient");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit from application (DO_NOTHING_ON_CLOSE çarpıya bassak da uygulama kapanmaz)
        frame.setResizable(false); //prevent frame from being resized
        frame.setSize(1000,450); //sets the x-dimension, and y-dimension of frame
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);  //sets the window at center

        //*********************************CREATING TABLE FOR FULL HISTORY OF THE PATIENTS*********************************
        // Create columns for the table
        String[] columns = {"ID", "Name", "Contact No", "Age", "Gender", "Blood Group", "Address", "Any Major Disease Suffered Earlier" ,
                "Symptom" , "Diagnosis" , "Medicines" , "wardReq" , "typeWard"};

        // Create a default table model with the specified columns
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(900, 800)); // Set preferred size of the table
        table.setBorder(null);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Set layout of content pane to null
        frame.getContentPane().setLayout(null);

        // Set bounds of scroll pane
        scrollPane.setBounds(40, 70, 900, 300); //tablonun lokasyonu ve size'ını ayarladık
        frame.getContentPane().add(scrollPane);
        //****************************************************************************************************************

        //creating back button:
        createBackButton();

        // Fetch data from the database and add to the table
        fetchFullHistoryOfThePatients();



        frame.setVisible(true);
    }

    public void createBackButton(){
        //CREATING IMAGE FOR back button
        ImageIcon img = new ImageIcon(getClass().getResource("images/2099190.png"));
        Image image = img.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(newimg);  // transform it back

        //Back button
        backButton = new JButton("Back");
        backButton.setIcon(img); //setting the adding image for our button
        backButton.setLayout(null);
        backButton.setBounds(37,14,95,39);
        backButton.setFocusable(false);

        backButton.addActionListener(e-> {
            try {
                handleButtonClick(backButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        frame.add(backButton);
    }
    public void handleButtonClick(JButton button) throws IOException {
        if(button.getText().equals("Back")){
            HospitalMainScreen hospitalMainScreen = new HospitalMainScreen();
            button.setEnabled(false);
            frame.dispose();
        }
    }
    public void fetchFullHistoryOfThePatients(){

        try {
            String sql = "SELECT id , name ,contact_no ,age  , gender, blood_group , address, any_major_disease_suffered_earlier ," +
                    "     symptoms, diagnosis, medicines, ward_requirement, type_of_ward " +
                    "FROM patients JOIN diagnosis_info " +
                    "ON id = diagnosis_info.patient_id;";

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate through the result set and add rows to the table model
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact_no"),
                        resultSet.getInt("age"),
                        resultSet.getString("gender"),
                        resultSet.getString("blood_group"),
                        resultSet.getString("address"),
                        resultSet.getString("any_major_disease_suffered_earlier"),
                        resultSet.getString("symptoms"),
                        resultSet.getString("diagnosis"),
                        resultSet.getString("medicines"),
                        resultSet.getString("ward_requirement"),
                        resultSet.getString("type_of_ward"),
                };
                model.addRow(row);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        try {
            // Use either light or dark theme
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        FullHistoryOfThePatientWindow fullHistoryOfThePatientWindow = new FullHistoryOfThePatientWindow();
    }
}
