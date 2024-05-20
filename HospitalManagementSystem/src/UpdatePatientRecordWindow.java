import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;


public class UpdatePatientRecordWindow {

    private JFrame frame;
    private JButton backButton;
    private HospitalDAO hospitalDAO = new HospitalDAO(); private JTextField patientIDTextField;
    private JLabel nameLabel; private JTextField nameTextField; private JLabel contactNoLabel; private JTextField contactNoTextField;
    private  JLabel ageLabel; private  JTextField ageTextField; private JLabel genderLabel; private JComboBox genderComboBox;
    private JLabel bloodGroupLabel; private JTextField bloodGroupTextField; private JLabel addressLabel; private JTextField addressTextField;
    private  JLabel majorDiseaseLabel; private JTextField majorDiseaseTextField; private JLabel patientIDLabel;

    public UpdatePatientRecordWindow(){
        frame = new JFrame("Update Patient Record");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit from application (DO_NOTHING_ON_CLOSE çarpıya bassak da uygulama kapanmaz)
        frame.setResizable(false); //prevent frame from being resized
        frame.setSize(600,520); //sets the x-dimension, and y-dimension of frame
        frame.setLocationRelativeTo(null);

        //back button
        createBackButton();

        //components of the update window
        createComponentsForUpdatePatientRecordWindow();

        //creating search button:
        JButton searchButton = new JButton("Search");
        searchButton.setFocusable(false);
        searchButton.setBounds(240, 20, 90, 30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchPatientDataAndSetToTextFields();
            }
        });
        frame.add(searchButton);

        //creating update button
        JButton updateButton = new JButton("Update");
        updateButton.setFocusable(false);
        updateButton.setBounds(340, 20, 90, 30);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePatientInfo();
            }
        });
        frame.add(updateButton);


        frame.setVisible(true);
    }
    private void clearTextFields(){
        patientIDTextField.setText("");
        nameTextField.setText("");
        contactNoTextField.setText("");
        ageTextField.setText("");
        genderLabel.setText("");
        genderComboBox.setSelectedIndex(0);
        bloodGroupTextField.setText("");
        addressTextField.setText("");
        majorDiseaseTextField.setText("");
    }
    private void updatePatientInfo(){
        String searchId = patientIDTextField.getText().trim();
        if (!searchId.isEmpty()){

            try {
                if(nameTextField.getText().trim().isEmpty() || genderComboBox.getSelectedItem().toString().trim().isEmpty() ||
                        bloodGroupTextField.getText().trim().isEmpty() || majorDiseaseTextField.getText().trim().isEmpty() ||
                        addressTextField.getText().trim().isEmpty() || contactNoTextField.getText().trim().isEmpty() ||
                        ageTextField.getText().trim().isEmpty()){

                    throw new IllegalArgumentException("There are empty fields. Please fill them before clicking the Update button.");

                }

                Connection connection = DatabaseConnection.getConnection();
                String sql = "UPDATE patients SET name=?, contact_no=?, age=?, gender=?, blood_group=?, address=?, any_major_disease_suffered_earlier=?" +
                        "WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1,nameTextField.getText());

                int contactNo = Integer.parseInt(contactNoTextField.getText());
                preparedStatement.setInt(2,contactNo);

                int age = Integer.parseInt(ageTextField.getText());
                preparedStatement.setInt(3,age);

                preparedStatement.setString(4,genderComboBox.getSelectedItem().toString());
                preparedStatement.setString(5,bloodGroupTextField.getText());
                preparedStatement.setString(6,addressTextField.getText());
                preparedStatement.setString(7,majorDiseaseTextField.getText());

                int id = Integer.parseInt(patientIDTextField.getText());
                preparedStatement.setInt(8, id);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Patient Updated successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
                clearTextFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(frame, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }catch (IllegalArgumentException e){
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            // If search field is empty
            JOptionPane.showMessageDialog(frame,"Please enter an ID to Update!");
            clearTextFields();

        }




    }
    private void fetchPatientDataAndSetToTextFields() {
        String searchId = patientIDTextField.getText().trim();
        if (!searchId.isEmpty()){
            try {
                Connection connection = DatabaseConnection.getConnection();
                String sql = "SELECT id, name, contact_no, age, gender, blood_group, address, any_major_disease_suffered_earlier FROM patients WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                int id = Integer.parseInt(patientIDTextField.getText());
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    nameTextField.setText(resultSet.getString("name"));
                    contactNoTextField.setText(resultSet.getString("contact_no"));
                    ageTextField.setText(resultSet.getString("age"));
                    genderComboBox.setSelectedItem(resultSet.getString("gender"));
                    bloodGroupTextField.setText(resultSet.getString("blood_group"));
                    addressTextField.setText(resultSet.getString("address"));
                    majorDiseaseTextField.setText(resultSet.getString("any_major_disease_suffered_earlier"));
                } else {
                    JOptionPane.showMessageDialog(frame, "No patient found with ID: " + id);
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            // If search field is empty, fetch all patient data
            JOptionPane.showMessageDialog(frame,"Please enter an ID to search!");
            clearTextFields(); //without this method , whenever the searchbar is empty if we click the search button the table will duplicate itself
            //When you call setRowCount(0), you are essentially setting the number of rows in the table model to zero.
            //This means that all existing rows in the table model will be removed, leaving an empty table with no rows.
        }


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
        backButton.setBounds(10,10,95,40);
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
    private void createComponentsForUpdatePatientRecordWindow(){

        //Creating PatientID label AND patientID Text field
        patientIDLabel = new JLabel("Patient ID:");
        patientIDLabel.setBounds(130,60,150,42);
        patientIDLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        patientIDTextField = new JTextField();
        patientIDTextField.setBounds(220 , 66 , 250 , 30);
        patientIDTextField.setHorizontalAlignment(JTextField.CENTER); //text fielda yazılanlar ortalanarak yazılır
        patientIDTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        //-----------------------------------------------------------------------------------------------------------


        //Creating Name label AND Name Text field
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(130,102,150,42);
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        nameTextField = new JTextField();
        nameTextField.setBounds(220 , 108 , 250 , 30);
        nameTextField.setHorizontalAlignment(JTextField.CENTER); //text fielda yazılanlar ortalanarak yazılır
        nameTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        //----------------------------------------------------------------------------------------------------------

        //Creating Contact no label AND Contact no Text field
        contactNoLabel = new JLabel("Contact No:");
        contactNoLabel.setBounds(130,144,150,42);
        contactNoLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        contactNoTextField = new JTextField();
        contactNoTextField.setBounds(220 , 150 , 250 , 30);
        contactNoTextField.setHorizontalAlignment(JTextField.CENTER); //text fielda yazılanlar ortalanarak yazılır
        contactNoTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        //----------------------------------------------------------------------------------------------------------

        //Creating AGE label AND AGE Text field
        ageLabel = new JLabel("Age:");
        ageLabel.setBounds(130,186,150,42);
        ageLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        ageTextField = new JTextField();
        ageTextField.setBounds(220 , 192 , 250 , 30);
        ageTextField.setHorizontalAlignment(JTextField.CENTER); //text fielda yazılanlar ortalanarak yazılır
        ageTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        //----------------------------------------------------------------------------------------------------------

        //Creating Gender label and Gender combobox
        genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(130,228,150,42);
        genderLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        genderComboBox = new JComboBox(); //compenent to choose between Male or Female
        genderComboBox.setBounds(220,234,150,32);
        genderComboBox.addItem("Male"); genderComboBox.addItem("Female");
        //----------------------------------------------------------------------------------------------------------

        //Creating Blood Group label and BLOOD group textfield
        bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setBounds(130,270,150,42);
        bloodGroupLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        bloodGroupTextField = new JTextField();
        bloodGroupTextField.setBounds(220 , 276 , 250 , 30);
        bloodGroupTextField.setHorizontalAlignment(JTextField.CENTER); //text fielda yazılanlar ortalanarak yazılır
        bloodGroupTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        //----------------------------------------------------------------------------------------------------------

        //Creating Address label and Address textfield
        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(130,311,150,42);
        addressLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        addressTextField = new JTextField();
        addressTextField.setBounds(220 , 317 , 250 , 30);
        addressTextField.setHorizontalAlignment(JTextField.CENTER); //text fielda yazılanlar ortalanarak yazılır
        addressTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        //----------------------------------------------------------------------------------------------------------

        //Creating ANY MAJOR DISEASE SUFFERED EARLIER
        majorDiseaseLabel = new JLabel("Any Major Disease Suffered Earlier:");
        majorDiseaseLabel.setBounds(130,353,300,42);
        majorDiseaseLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        majorDiseaseTextField = new JTextField();
        majorDiseaseTextField.setBounds(128 , 395 , 344, 30);
        majorDiseaseTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı


        frame.add(patientIDLabel); frame.add(patientIDTextField);
        frame.add(nameLabel); frame.add(nameTextField);
        frame.add(contactNoLabel); frame.add(contactNoTextField);
        frame.add(ageLabel); frame.add(ageTextField);
        frame.add(genderLabel); frame.add(genderComboBox);
        frame.add(bloodGroupLabel); frame.add(bloodGroupTextField);
        frame.add(addressLabel); frame.add(addressTextField);
        frame.add(majorDiseaseLabel); frame.add(majorDiseaseTextField);

    }
    public void handleButtonClick(JButton button) throws IOException {
        if(button.getText().equals("Back")){
            HospitalMainScreen hospitalMainScreen = new HospitalMainScreen(); //butona tıklayınca açılmasını istedigimiz pencerenin instance'ını oluşturduk.
            button.setEnabled(false);
            frame.dispose();
        }

    }

    public static void main(String[] args) {
        try {
            // Use either light or dark theme
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        UpdatePatientRecordWindow updatePatientRecordWindow = new UpdatePatientRecordWindow();
    }
}
