import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.*;
import java.awt.*;

public class HospitalMainScreen extends JFrame {
    public HospitalMainScreen(){
        //setting up our frame
        this.setSize(488,260);
        this.setTitle("Hospital Operations"); //sets title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit from application (DO_NOTHING_ON_CLOSE çarpıya bassak da uygulama kapanmaz)
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false); //prevent frame from being resized


        //Creating Add New Patient Record Button:
        createAddNewPatientRecordButton();

        //Creating Add Diagnosis Information Button:
        createAddDiagnosisInformationButton();

        //Creating Full History of The Patient button:
        createFullHistoryOfThePatientButton();

        //Creating Update Patient Record button:
        createUpdatePatientRecordButton();

        //Creating LOGOUT button:
        createLogoutButton();

        this.setVisible(true);
    }

    public void createLogoutButton(){

        //creating img for Full History of patient button
        ImageIcon img = new ImageIcon(getClass().getResource("images/7090891.png"));
        Image image = img.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(newimg);  // transform it back

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFocusable(false);
        //logoutButton.setBackground(new Color(121, 16, 16));
        logoutButton.setLayout(null);
        logoutButton.setBounds(175,150,120,50);
        logoutButton.setIcon(img);

        logoutButton.addActionListener(e -> handleButtonClick(logoutButton));

        this.add(logoutButton);
    }
    public void createUpdatePatientRecordButton(){
        //creating img for Full History of patient button
        ImageIcon img = new ImageIcon(getClass().getResource("images/patient-medical-record-flat-icon-for-apps-or-websites-free-vector.jpg"));
        Image image = img.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(newimg);  // transform it back

        JButton updatePatientRecordButton = new JButton("Update Patient Record");
        updatePatientRecordButton.setFocusable(false);
        updatePatientRecordButton.setLayout(null);
        updatePatientRecordButton.setBounds(240,80,220,50);
        updatePatientRecordButton.setIcon(img);

        updatePatientRecordButton.addActionListener(e -> handleButtonClick(updatePatientRecordButton));

        this.add(updatePatientRecordButton);
    }
    public void createFullHistoryOfThePatientButton(){
        //creating img for Full History of patient button
        ImageIcon img = new ImageIcon(getClass().getResource("images/health-check.png"));
        Image image = img.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(newimg);  // transform it back

        JButton fullHistoryOfThePatientButton = new JButton("Full History of The Patient");
        fullHistoryOfThePatientButton.setFocusable(false);
        fullHistoryOfThePatientButton.setLayout(null);
        fullHistoryOfThePatientButton.setBounds(10,80,220,50);
        fullHistoryOfThePatientButton.setIcon(img);

        fullHistoryOfThePatientButton.addActionListener(e -> handleButtonClick(fullHistoryOfThePatientButton));

        this.add(fullHistoryOfThePatientButton);
    }
    public void createAddDiagnosisInformationButton(){
        JButton addDiagnosisInformationButton = new JButton("Add Diagnosis Information");
        //CREATING IMAGE FOR - Add Diagnosis Information Button
        ImageIcon img = new ImageIcon(getClass().getResource("images/8638122.png"));
        Image image = img.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(newimg);  // transform it back

        addDiagnosisInformationButton.setIcon(img);
        addDiagnosisInformationButton.setLayout(null);
        addDiagnosisInformationButton.setBounds(240,10,220,50);
        addDiagnosisInformationButton.setFocusable(false);

        addDiagnosisInformationButton.addActionListener(e-> handleButtonClick(addDiagnosisInformationButton));

        this.add(addDiagnosisInformationButton);
    }
    public void createAddNewPatientRecordButton(){
        //CREATING IMAGE FOR add new patient record button
        ImageIcon img = new ImageIcon(getClass().getResource("images/2355692.png"));
        Image image = img.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(newimg);  // transform it back

        //Patient Adding button
        JButton patientRecordAddingButton = new JButton("Add New Patient Record");
        patientRecordAddingButton.setIcon(img); //setting the adding image for our button
        patientRecordAddingButton.setLayout(null);
        patientRecordAddingButton.setBounds(10,10,220,50);
        patientRecordAddingButton.setFocusable(false);

        patientRecordAddingButton.addActionListener(e-> handleButtonClick(patientRecordAddingButton));

        this.add(patientRecordAddingButton);
    }
    public void handleButtonClick(JButton button){
        if(button.getText().equals("Add New Patient Record")){
            PatientRecordAddingWindow myWindow = new PatientRecordAddingWindow(); //butona tıklayınca açılmasını istedigimiz pencerenin instance'ını oluşturduk.
            button.setEnabled(false);
            this.dispose();
        }else if(button.getText().equals("Add Diagnosis Information")){
            DiagnosisInfoAddingScreen diagnosisInfoAddingScreen = new DiagnosisInfoAddingScreen();
            button.setEnabled(false);
            this.dispose();
        }else if(button.getText().equals("Full History of The Patient")){
            FullHistoryOfThePatientWindow fullHistoryOfThePatientWindow = new FullHistoryOfThePatientWindow();
            button.setEnabled(false);
            this.dispose();
        }else if(button.getText().equals("Update Patient Record")){
            UpdatePatientRecordWindow updatePatientRecordWindow = new UpdatePatientRecordWindow();
            button.setEnabled(false);
            this.dispose();
        }else if(button.getText().equals("Logout")){
            int choice = JOptionPane.showConfirmDialog(this, "Do you really want to Logout?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(choice==0){
                LoginScreen loginScreen = new LoginScreen();
                button.setEnabled(false);
                this.dispose();
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
        HospitalMainScreen hospitalMainScreen = new HospitalMainScreen();
    }
}
