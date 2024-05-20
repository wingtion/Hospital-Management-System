import javax.swing.*;
import java.awt.*;

public class PatientRecordAddingWindow {
    private JFrame frame = new JFrame();
    private JButton backButton;
    private HospitalDAO hospitalDAO = new HospitalDAO();
    private JLabel nameLabel; private JTextField nameTextField; private JLabel contactNoLabel; private JTextField contactNoTextField;
    private  JLabel ageLabel; private  JTextField ageTextField; private JLabel genderLabel; private JComboBox genderComboBox;
    private JLabel bloodGroupLabel; private JTextField bloodGroupTextField; private JLabel addressLabel; private JTextField addressTextField;
    private  JLabel majorDiseaseLabel; private JTextField majorDiseaseTextField;

    public PatientRecordAddingWindow(){

        frame.setTitle("Patient Add");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        //CREATING back button
        createBackButton();

        //CREATING Components of the Patient Adding Window
        createPatientWindowComponents();


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
        backButton.setBounds(10,10,95,40);
        backButton.setFocusable(false);

        backButton.addActionListener(e-> handleButtonClick(backButton));

        frame.add(backButton);
    }
    public void createPatientWindowComponents(){
        /*
        //Creating PatientID label AND patientID Text field
        JLabel patientIDLabel = new JLabel("Patient ID:");
        patientIDLabel.setBounds(130,60,150,42);
        patientIDLabel.setFont(new Font("Times New Roman", Font.BOLD ,15));

        JTextField patientIDTextField = new JTextField();
        patientIDTextField.setBounds(220 , 66 , 250 , 30);
        patientIDTextField.setHorizontalAlignment(JTextField.CENTER); //text fielda yazılanlar ortalanarak yazılır
        patientIDTextField.setFont(new Font("Arial", Font.BOLD ,15)); //text field yazı tipi,şekli,sizeı
        //-----------------------------------------------------------------------------------------------------------

         */

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


        //********************SAVE BUTTON and IMAGE for save button************************
        //CREATING IMAGE FOR SAVE BUTTON
        ImageIcon saveImg = new ImageIcon(getClass().getResource("images/4856668.png"));
        Image image = saveImg.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        saveImg = new ImageIcon(newimg);  // transform it back

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(215,440 , 150,50);
        saveButton.setIcon(saveImg);

        //save button on click:
        saveButton.addActionListener(e-> handleButtonClick(saveButton));


        //frame.add(patientIDLabel); frame.add(patientIDTextField);
        frame.add(nameLabel); frame.add(nameTextField);
        frame.add(contactNoLabel); frame.add(contactNoTextField);
        frame.add(ageLabel); frame.add(ageTextField);
        frame.add(genderLabel); frame.add(genderComboBox);
        frame.add(bloodGroupLabel); frame.add(bloodGroupTextField);
        frame.add(addressLabel); frame.add(addressTextField);
        frame.add(majorDiseaseLabel); frame.add(majorDiseaseTextField);
        frame.add(saveButton);

        frame.setVisible(true);
    }
    public void handleButtonClick(JButton button){
        if(button.getText().equals("Back")){
            HospitalMainScreen hospitalMainScreen = new HospitalMainScreen(); //butona tıklayınca açılmasını istedigimiz pencerenin instance'ını oluşturduk.
            button.setEnabled(false);
            frame.dispose();
        }else if(button.getText().equals("Save")){
            String name = nameTextField.getText();
            String gender = genderComboBox.getSelectedItem().toString();
            String blood_group = bloodGroupTextField.getText();
            String any_major_disease_suffered = majorDiseaseTextField.getText();
            String address = addressTextField.getText();

            try{

                if(nameTextField.getText().trim().isEmpty() || genderComboBox.getSelectedItem().toString().trim().isEmpty() ||
                        bloodGroupTextField.getText().trim().isEmpty() || majorDiseaseTextField.getText().trim().isEmpty() ||
                        addressTextField.getText().trim().isEmpty() || contactNoTextField.getText().trim().isEmpty() ||
                        ageTextField.getText().trim().isEmpty()){

                    throw new IllegalArgumentException("There are empty fields. Please fill them before clicking the save button.");

                }

                int contactNo = Integer.parseInt(contactNoTextField.getText());
                int age = Integer.parseInt(ageTextField.getText());

                hospitalDAO.addPatient(name, contactNo , age , gender, blood_group, address , any_major_disease_suffered);
                JOptionPane.showMessageDialog(frame,"Patient saved successfully");
                nameTextField.setText(""); contactNoTextField.setText("");
                ageTextField.setText(""); bloodGroupTextField.setText("");
                addressTextField.setText(""); majorDiseaseTextField.setText("");
            }catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "The Contact NO or AGE must contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }


        }
    }


}
