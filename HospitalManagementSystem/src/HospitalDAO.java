import java.sql.*;

public class HospitalDAO {

    PreparedStatement preparedStatement;
    Statement statement;
    Connection connection;

    public HospitalDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public boolean login(String enteredUsername, String enteredPassword) {

        String sql = "Select username , password FROM users WHERE username = ? AND password = ? ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, enteredUsername);
            preparedStatement.setString(2, enteredPassword);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void signIn(String enteredUsername, String enteredPassword) {


        String sql = "INSERT INTO users(username , password) VALUES(? , ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, enteredUsername);
            preparedStatement.setString(2, enteredPassword);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addPatient(String name , int contactNo, int age, String gender, String blood_group ,
                           String address , String any_major_disease_suffered_earlier){

        String sql = "INSERT INTO patients(name, contact_no, age , gender, blood_group, address, any_major_disease_suffered_earlier) VALUES(?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2, contactNo);
            preparedStatement.setInt(3,age);
            preparedStatement.setString(4,gender);
            preparedStatement.setString(5,blood_group);
            preparedStatement.setString(6,address);
            preparedStatement.setString(7,any_major_disease_suffered_earlier);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addDiagnosisInfo(String symptoms , String diagnosis , String medicines ,
                                 String ward_requirement , String type_of_ward , int patient_id){

        String sql = "INSERT INTO diagnosis_info(symptoms, diagnosis, medicines , ward_requirement, type_of_ward, patient_id ) VALUES(?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,symptoms);
            preparedStatement.setString(2, diagnosis);
            preparedStatement.setString(3,medicines);
            preparedStatement.setString(4,ward_requirement);
            preparedStatement.setString(5,type_of_ward);
            preparedStatement.setInt(6,patient_id);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
