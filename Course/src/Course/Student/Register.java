package Course.Student;

import Course.Login;
import Course.SQLManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Register extends JFrame {
    private JTextField emailTF, nameTF, dobTF, addressTF, phoneTF, fatherNameTF, motherNameTF, parentPhoneNoTF;
    private JButton registerBTN, loginBTN;
    private JComboBox<String> course, gender;
    private JPasswordField passwordField, rePasswordField;
    private String generateIdNo;
    private ArrayList<Object> coursesList;
    String[] Courses;
    Register notice = this;
    SQLManager SQL;

    public Register(){
        setTitle("Register");
        setMinimumSize(new Dimension(550,610));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        SQL = new SQLManager();
        coursesList = new ArrayList<>();
        listOfCourse();

        nameTF = new JTextField(30);
        emailTF = new JTextField(30);
        dobTF = new JTextField(10);
        gender = new JComboBox<>(new String[]{"Male","Female","Other"});
        addressTF = new JTextField(30);
        phoneTF = new JTextField(10);
        fatherNameTF = new JTextField(30);
        motherNameTF = new JTextField(30);
        parentPhoneNoTF = new JTextField(20);
        course = new JComboBox(getCourses());
        passwordField = new JPasswordField(30);
        rePasswordField = new JPasswordField(30);
        registerBTN = new JButton("Register");
        loginBTN = new JButton("Login");


        add(Register());
        pack();
        setVisible(true);
        LoginBtn();
        registerBtn();
    }

    private JPanel Register(){
        JPanel reg = new JPanel();
        reg.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(8,8,8,8);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        reg.add(new JLabel("Name: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 0;
        reg.add(nameTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        reg.add(new JLabel("Email: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 1;
        reg.add(emailTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        reg.add(new JLabel("Date of Birth: "),gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = 2;
        reg.add(dobTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 2;
        gc.gridy = 2;
        reg.add(new JLabel("Gender: "),gc);

        gc.gridwidth = 1;
        gc.gridx = 4;
        gc.gridy = 2;
        reg.add(gender,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 3;
        reg.add(new JLabel("Address: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 3;
        reg.add(addressTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 4;
        reg.add(new JLabel("Phone No.: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 4;
        reg.add(phoneTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 5;
        reg.add(new JLabel("Father Name: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 5;
        reg.add(fatherNameTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 6;
        reg.add(new JLabel("Mother Name: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 6;
        reg.add(motherNameTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 7;
        reg.add(new JLabel("Parent Phone No.: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 7;
        reg.add(parentPhoneNoTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 8;
        reg.add(new JLabel("Course: "),gc);

        gc.gridwidth = 3;
        gc.gridx = 1;
        gc.gridy = 8;
        reg.add(course,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 9;
        reg.add(new JLabel("Password: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 9;
        reg.add(passwordField,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 10;
        reg.add(new JLabel("Confirm Password: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 10;
        reg.add(rePasswordField,gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = 12;
        reg.add(registerBTN, gc);

        gc.gridwidth = 2;
        gc.gridx = 2;
        gc.gridy = 12;
        reg.add(loginBTN, gc);

        return reg;
    }

    public JTextField getEmailTF() {
        return emailTF;
    }

    public void setEmailTF(JTextField emailTF) {
        this.emailTF = emailTF;
    }

    public JTextField getNameTF() {
        return nameTF;
    }

    public void setNameTF(JTextField nameTF) {
        this.nameTF = nameTF;
    }

    public JTextField getDobTF() {
        return dobTF;
    }

    public void setDobTF(JTextField dobTF) {
        this.dobTF = dobTF;
    }

    public JTextField getAddressTF() {
        return addressTF;
    }

    public void setAddressTF(JTextField addressTF) {
        this.addressTF = addressTF;
    }

    public JTextField getPhoneTF() {
        return phoneTF;
    }

    public void setPhoneTF(JTextField phoneTF) {
        this.phoneTF = phoneTF;
    }

    public JTextField getFatherNameTF() {
        return fatherNameTF;
    }

    public void setFatherNameTF(JTextField fatherNameTF) {
        this.fatherNameTF = fatherNameTF;
    }

    public JTextField getMotherNameTF() {
        return motherNameTF;
    }

    public void setMotherNameTF(JTextField motherNameTF) {
        this.motherNameTF = motherNameTF;
    }

    public JTextField getParentPhoneNoTF() {
        return parentPhoneNoTF;
    }

    public void setParentPhoneNoTF(JTextField parentPhoneNoTF) {
        this.parentPhoneNoTF = parentPhoneNoTF;
    }

    public JButton getRegisterBTN() {
        return registerBTN;
    }

    public void setRegisterBTN(JButton registerBTN) {
        this.registerBTN = registerBTN;
    }

    public JButton getLoginBTN() {
        return loginBTN;
    }

    public void setLoginBTN(JButton loginBTN) {
        this.loginBTN = loginBTN;
    }

    public JComboBox<String> getCourse() {
        return course;
    }

    public void setCourse(JComboBox<String> course) {
        this.course = course;
    }

    public JComboBox<String> getGender() {
        return gender;
    }

    public void setGender(JComboBox<String> gender) {
        this.gender = gender;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JPasswordField getRePasswordField() {
        return rePasswordField;
    }

    public void setRePasswordField(JPasswordField rePasswordField) {
        this.rePasswordField = rePasswordField;
    }

    public String getGenerateIdNo() {
        return generateIdNo;
    }

    public void setGenerateIdNo(String generateIdNo) {
        this.generateIdNo = generateIdNo;
    }

    public ArrayList<Object> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(ArrayList<Object> coursesList) {
        this.coursesList = coursesList;
    }

    public String[] getCourses() {
        return Courses;
    }

    public void setCourses(String[] courses) {
        Courses = courses;
    }

    private void LoginBtn(){
        JButton logIN = getLoginBTN();
        logIN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login log = new Login();
                log.setVisible(true);
                dispose();
            }
        });
    }

    private void registerBtn(){
        JButton regis= getRegisterBTN();
        regis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = getEmailTF().getText();
                    String name = getNameTF().getText();
                    String dob = getDobTF().getText();
                    String address = getAddressTF().getText();
                    String phone = getPhoneTF().getText();
                    String father = getFatherNameTF().getText();
                    String mother = getMotherNameTF().getText();
                    String parentPhone = getParentPhoneNoTF().getText();
                    String pwd = String.valueOf(passwordField.getPassword());
                    String repwd = String.valueOf(rePasswordField.getPassword());
                    String course = getCourse().getSelectedItem().toString();
                    String gender = getGender().getSelectedItem().toString();
                    int level = 4;

                    if (email.isEmpty() || name.isEmpty() || dob.isEmpty() || address.isEmpty() || phone.isEmpty() || father.isEmpty() || mother.isEmpty() || parentPhone.isEmpty()){
                        JOptionPane.showMessageDialog(notice,"The field must not be empty!!","Error!",JOptionPane.ERROR_MESSAGE);
                    }else {
                        if (pwd.equals(repwd)){
                            File studIDFile = new File("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\studID.txt");
                            Scanner myReader = new Scanner(studIDFile);
                            while (myReader.hasNextLine()) {
                                String data = myReader.nextLine();
                                generateIdNo = Integer.toString(Integer.parseInt(data) + 1);
                            }
                            FileWriter myWriter = new FileWriter("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\studID.txt");
                            myWriter.write(generateIdNo);
                            myWriter.close();

                            SQL.insert(generateIdNo,name,email,dob,gender,address,phone,father,mother,parentPhone,course,pwd,level);
                            JOptionPane.showMessageDialog(notice, "Successfully Registered!...", "Success!", JOptionPane.INFORMATION_MESSAGE);
                            Login log = new Login();
                            log.setVisible(true);
                            dispose();
                        }else{
                            JOptionPane.showMessageDialog(notice,"Password does not match!!","Error!",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(notice,"Something went wrong!!","Error!",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public void listOfCourse(){
        try {
            ResultSet result = SQL.courses();
            coursesList.clear();
            while (result.next()){
                String c = result.getString("course_name");
                coursesList.add(c);
            }
            setCourses(coursesList.toArray(new String[coursesList.size()]));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
