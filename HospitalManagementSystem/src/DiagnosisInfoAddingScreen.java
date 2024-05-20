import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DiagnosisInfoAddingScreen {

    private HospitalDAO hospitalDAO;
    private JTable table;
    private DefaultTableModel model;
    private JFrame frame;
    private JTextField idSearchField;
    private JButton backButton;
    private JLabel symptomsLabel; private JTextField symptomsTextField;
    private JLabel diagnosisLabel; private JTextField diagnosisTextField;
    private JLabel medicinesLabel; private JTextField medicinesTextField;
    private JLabel wardRequiredLabel; private JCheckBox wardRequiredCheckBox;
    private JLabel typeOfWardLabel; private JComboBox typeOfWardComboBox;
    private JButton saveButton;

    public DiagnosisInfoAddingScreen() {
        hospitalDAO = new HospitalDAO();

        frame = new JFrame();
        frame.setTitle("Add&Check Diagnosis Information");
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);  //sets the window at center

        // Create search label and text field
        JLabel searchLabel = new JLabel("Search Patient ID:");
        searchLabel.setBounds(300, 20, 120, 30);
        frame.add(searchLabel);

        idSearchField = new JTextField();
        idSearchField.setBounds(410, 20, 120, 30);
        frame.add(idSearchField);


        // Create columns for the table
        String[] columns = {"ID", "Name", "Contact No", "Age", "Gender", "Blood Group", "Address", "Any Major Disease Suffered Earlier"};

        // Create a default table model with the specified columns
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(900, 300)); // Set preferred size of the table
        table.setBorder(null);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Set layout of content pane to null
        frame.getContentPane().setLayout(null);

        // Set bounds of scroll pane
        scrollPane.setBounds(40, 70, 900, 150); //tablonun lokasyonu ve size'ını ayarladık
        frame.getContentPane().add(scrollPane);

        // Create search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(540, 20, 90, 30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatient();
            }
        });
        frame.add(searchButton);

        // Add action listener to search field(enter'a basınca aratma)
        idSearchField.addActionListener(e -> searchPatient());
        // * searchField.addActionListener(...) adds an ActionListener to the searchField component.
        // An ActionListener is notified whenever the user presses "Enter" while the searchField has focus.
        //
        // * (e -> searchPatient()) is a lambda expression that defines what action should be taken when the ActionListener is triggered.
        // In this case, it calls the searchPatient() method.
        // So, whenever the user presses "Enter" in the searchField, the searchPatient() method will be executed.

        //creating Diagnosis Info Adding Screen Components:
        createDiagnosisInfoAddingScreenComponents();

        //creating action listener for save button
        saveButton.addActionListener(e -> handleButtonClick(saveButton));

        // Fetch data from the database and add to the table
        fetchPatientData();

        //creating Back button:
        createBackButton();


        frame.setVisible(true);
    }

    private void createDiagnosisInfoAddingScreenComponents(){
        //Creating -symptoms- label AND symptoms Text field
        symptomsLabel = new JLabel("Symptoms:");
        symptomsLabel.setBounds(50,250,150,42);
        symptomsLabel.setLayout(null);
        symptomsLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        symptomsTextField = new JTextField();
        symptomsTextField.setBounds(145 , 258 , 200 , 30);
        symptomsTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı

        frame.add(symptomsLabel);
        frame.add(symptomsTextField);

        //Creating -DIAGNOSIS- label AND Diagnosis Text field
        diagnosisLabel = new JLabel("Diagnosis:");
        diagnosisLabel.setBounds(50,300,150,42);
        diagnosisLabel.setLayout(null);
        diagnosisLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        diagnosisTextField = new JTextField();
        diagnosisTextField.setBounds(145 , 308 , 200 , 30);
        diagnosisTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı

        frame.add(diagnosisLabel);
        frame.add(diagnosisTextField);

        //Creating -MEDICINES- label AND Diagnosis Text field
        medicinesLabel = new JLabel("Medicines:");
        medicinesLabel.setBounds(50,350,150,42);
        medicinesLabel.setLayout(null);
        medicinesLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        medicinesTextField = new JTextField();
        medicinesTextField.setBounds(145 , 358 , 200 , 30);
        medicinesTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı

        frame.add(medicinesLabel);
        frame.add(medicinesTextField);

        //Creating -TypeOfWard- label AND TypeOfWard JComboBox
        typeOfWardLabel = new JLabel("Type of Ward?");
        typeOfWardLabel.setBounds(420,300,150,42);
        typeOfWardLabel.setLayout(null);
        typeOfWardLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        typeOfWardComboBox = new JComboBox();
        typeOfWardComboBox.addItem("General");
        typeOfWardComboBox.addItem("Single ");
        typeOfWardComboBox.addItem("Duo");
        typeOfWardComboBox.setBounds(545 , 306, 200 , 30);
        typeOfWardComboBox.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        typeOfWardComboBox.setFocusable(false);

        typeOfWardLabel.setVisible(false);
        typeOfWardComboBox.setVisible(false);

        frame.add(typeOfWardLabel);
        frame.add(typeOfWardComboBox);

        //Creating -WardRequired- label AND Ward Required CheckBox
        wardRequiredLabel = new JLabel("Ward Required?");
        wardRequiredLabel.setBounds(420,250,150,42);
        wardRequiredLabel.setLayout(null);
        wardRequiredLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        wardRequiredCheckBox = new JCheckBox("YES");
        wardRequiredCheckBox.setBounds(545 , 256, 200 , 30);
        wardRequiredCheckBox.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        wardRequiredCheckBox.setFocusable(false);

        //checkbox seçili ise ekstra gözükecek componentleri ayarladık
        wardRequiredCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wardRequiredCheckBox.isSelected()){
                    typeOfWardLabel.setVisible(true);
                    typeOfWardComboBox.setVisible(true);
                    frame.revalidate();
                    frame.repaint();
                }else{
                    typeOfWardLabel.setVisible(false);
                    typeOfWardComboBox.setVisible(false);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        frame.add(wardRequiredLabel);
        frame.add(wardRequiredCheckBox);

        //********************SAVE BUTTON and IMAGE for save button************************
        //CREATING IMAGE FOR SAVE BUTTON
        ImageIcon saveImg = new ImageIcon(getClass().getResource("images/4856668.png"));
        Image image = saveImg.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        saveImg = new ImageIcon(newimg);  // transform it back

        saveButton = new JButton("SAVE");
        saveButton.setBounds(788, 12 , 150,38);
        saveButton.setFocusable(false);
        saveButton.setBackground(new Color(0xFF12AF42, true));
        saveButton.setIcon(saveImg);

        frame.add(saveButton);

    }
    private void createBackButton(){
        //CREATING IMAGE FOR back button
        ImageIcon img = new ImageIcon(getClass().getResource("images/2099190.png"));
        Image image = img.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(newimg);  // transform it back

        //Back button
        backButton = new JButton("Back");
        backButton.setIcon(img); //setting the adding image for our button
        backButton.setLayout(null);
        backButton.setBounds(39,14,95,39);
        backButton.setFocusable(false);

        backButton.addActionListener(e-> handleButtonClick(backButton));

        frame.add(backButton);
    }
    private void fetchPatientData() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Execute SQL query to retrieve patient information
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name, contact_no, age, gender, blood_group, " +
                    "address, any_major_disease_suffered_earlier FROM patients");

            // Iterate through the result set and add rows to the table model
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contact_no"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("blood_group"),
                        rs.getString("address"),
                        rs.getString("any_major_disease_suffered_earlier")
                };
                model.addRow(row);
            }

            // Close connections
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void searchPatient() {
        String searchId = idSearchField.getText().trim();

        if (!searchId.isEmpty()) {
            // Clear existing table data
            model.setRowCount(0);

            try {
                Connection connection = DatabaseConnection.getConnection();

                // Execute SQL query to retrieve patient information based on ID
                PreparedStatement pstmt = connection.prepareStatement("SELECT id, name, contact_no, age, gender, blood_group, " +
                        "address, any_major_disease_suffered_earlier FROM patients WHERE id = ?");
                pstmt.setInt(1, Integer.parseInt(searchId));
                ResultSet rs = pstmt.executeQuery();

                // Add row to the table model if a patient with the specified ID is found
                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("contact_no"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("blood_group"),
                            rs.getString("address"),
                            rs.getString("any_major_disease_suffered_earlier")
                    };
                    model.addRow(row);
                }

                // Close connections
                rs.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(frame, "Wrong ID!", "Error", JOptionPane.ERROR_MESSAGE);
                model.setRowCount(0);
                fetchPatientData();
            }
        } else {
            // If search field is empty, fetch all patient data
            JOptionPane.showMessageDialog(frame,"Please enter an ID to search!");
            model.setRowCount(0); //without this method , whenever the searchbar is empty if we click the search button the table will duplicate itself
            //When you call setRowCount(0), you are essentially setting the number of rows in the table model to zero.
            //This means that all existing rows in the table model will be removed, leaving an empty table with no rows.
            fetchPatientData();

        }
    }
    private void handleButtonClick(JButton button){
        if(button.getText().equals("Back")){
            HospitalMainScreen hospitalMainScreen = new HospitalMainScreen(); //butona tıklayınca açılmasını istedigimiz pencerenin instance'ını oluşturduk.
            button.setEnabled(false);
            frame.dispose();
        }else if(button.getText().equals("SAVE")){
            try{
                int patientID = Integer.parseInt(idSearchField.getText());
                if(wardRequiredCheckBox.isSelected()){
                    wardRequiredCheckBox.setText("YES");
                }else{
                    wardRequiredCheckBox.setText("NO");
                }

                hospitalDAO.addDiagnosisInfo(symptomsTextField.getText() , diagnosisTextField.getText() , medicinesTextField.getText() ,
                        wardRequiredCheckBox.getText() , typeOfWardComboBox.getSelectedItem().toString() , patientID);

                JOptionPane.showMessageDialog(frame, "Saved successfully.", "Notification", JOptionPane.INFORMATION_MESSAGE);
                model.setRowCount(0);
                symptomsTextField.setText(""); diagnosisTextField.setText(""); medicinesTextField.setText("");
                typeOfWardComboBox.setSelectedIndex(0); wardRequiredCheckBox.setSelected(false);
                idSearchField.setText("");
                fetchPatientData();


            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(frame, "The ID that you entered is not usable. ", "Error", JOptionPane.ERROR_MESSAGE);
                model.setRowCount(0);
                fetchPatientData();
            }



        }
    }

    public static void main(String[] args) {

        try {
            // Use either light or dark theme
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Create and display the table
        SwingUtilities.invokeLater(() -> new DiagnosisInfoAddingScreen());
    }
}


