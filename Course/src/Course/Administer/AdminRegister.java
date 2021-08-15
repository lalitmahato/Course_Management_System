package Course.Administer;

import Course.SQLManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AdminRegister extends JFrame {
    private JTextField emailTF, nameTF, dobTF, addressTF, phoneNoTF;
    private JButton registerBTN, backBTN;
    private JComboBox<String> userType, gender;
    private JPasswordField passwordPF, rePasswordPF;
    private String instructorID, administratorID;
    AdminRegister notice = this;
    SQLManager SQL;

    public AdminRegister(){
        setTitle("Register");
        setMinimumSize(new Dimension(550,550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        nameTF = new JTextField(30);
        emailTF = new JTextField(30);
        dobTF = new JTextField(10);
        gender = new JComboBox<>(new String[]{"Male","Female","Other"});
        addressTF = new JTextField(30);
        phoneNoTF = new JTextField(10);
        userType = new JComboBox<>(new String[]{"Instructor", "Administrator"});
        passwordPF = new JPasswordField(30);
        rePasswordPF = new JPasswordField(30);
        registerBTN = new JButton("Register");
        backBTN = new JButton("Back");
        SQL = new SQLManager();

        add(registerUser());
        pack();
        setVisible(true);
        backBtn();
        registerBTN();
    }

    private JPanel registerUser(){
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
        reg.add(phoneNoTF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 5;
        reg.add(new JLabel("User Type: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 5;
        reg.add(userType,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 6;
        reg.add(new JLabel("Password: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 6;
        reg.add(passwordPF,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 7;
        reg.add(new JLabel("Confirm Password: "),gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 7;
        reg.add(rePasswordPF,gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = 8;
        reg.add(registerBTN, gc);

        gc.gridwidth = 2;
        gc.gridx = 2;
        gc.gridy = 8;
        reg.add(backBTN, gc);

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

    public JTextField getPhoneNoTF() {
        return phoneNoTF;
    }

    public void setPhoneNoTF(JTextField phoneNoTF) {
        this.phoneNoTF = phoneNoTF;
    }

    public JButton getRegisterBTN() {
        return registerBTN;
    }

    public void setRegisterBTN(JButton registerBTN) {
        this.registerBTN = registerBTN;
    }

    public JButton getBackBTN() {
        return backBTN;
    }

    public void setBackBTN(JButton backBTN) {
        this.backBTN = backBTN;
    }

    public JComboBox<String> getUserType() {
        return userType;
    }

    public void setUserType(JComboBox<String> userType) {
        this.userType = userType;
    }

    public JComboBox<String> getGender() {
        return gender;
    }

    public void setGender(JComboBox<String> gender) {
        this.gender = gender;
    }

    public JPasswordField getPasswordPF() {
        return passwordPF;
    }

    public void setPasswordPF(JPasswordField passwordPF) {
        this.passwordPF = passwordPF;
    }

    public JPasswordField getRePasswordPF() {
        return rePasswordPF;
    }

    public void setRePasswordPF(JPasswordField rePasswordPF) {
        this.rePasswordPF = rePasswordPF;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public String getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(String administratorID) {
        this.administratorID = administratorID;
    }

    private void registerBTN(){
        JButton register = getRegisterBTN();
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String user = getUserType().getSelectedItem().toString();
                String gender = getGender().getSelectedItem().toString();
                String email = getEmailTF().getText();
                String name = getNameTF().getText();
                String dob = getDobTF().getText();
                String address = getAddressTF().getText();
                String phone = getPhoneNoTF().getText();
                String passwd = String.valueOf(passwordPF.getPassword());
                String repasswd = String.valueOf(rePasswordPF.getPassword());
                try {
                    if (email.isEmpty() || name.isEmpty() || dob.isEmpty() || address.isEmpty() || phone.isEmpty() || passwd.isEmpty() || repasswd.isEmpty()){
                        JOptionPane.showMessageDialog(notice,"The field must not be empty!!","Error!",JOptionPane.ERROR_MESSAGE);
                    }else {
                        if (passwd.equals(repasswd)){
                            if (user == "Instructor"){

                                File studIDFile = new File("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\instructorID.txt");
                                Scanner myReader = new Scanner(studIDFile);
                                while (myReader.hasNextLine()) {
                                    String data = myReader.nextLine();
                                    instructorID = Integer.toString(Integer.parseInt(data) + 1);
                                }
                                FileWriter myWriter = new FileWriter("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\instructorID.txt");
                                myWriter.write(instructorID);
                                myWriter.close();

                                SQL.insert(instructorID,name,email,dob,gender,address,phone,passwd);
                                JOptionPane.showMessageDialog(notice,"Successfully Registered!...","Success!",JOptionPane.INFORMATION_MESSAGE);
                                getEmailTF().setText("");
                                getNameTF().setText("");
                                getDobTF().setText("");
                                getAddressTF().setText("");
                                getPhoneNoTF().setText("");
                                getPasswordPF().setText("");
                                getRePasswordPF().setText("");
                            }else{
                                File studIDFile = new File("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\administratorID.txt");
                                Scanner myReader = new Scanner(studIDFile);
                                while (myReader.hasNextLine()) {
                                    String data = myReader.nextLine();
                                    administratorID = Integer.toString(Integer.parseInt(data) + 1);
                                }
                                FileWriter myWriter = new FileWriter("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\administratorID.txt");
                                myWriter.write(administratorID);
                                myWriter.close();

                                SQL.insertAdministrator(administratorID,name,email,dob,gender,address,phone,passwd);
                                JOptionPane.showMessageDialog(notice,"Successfully Registered!...","Success!",JOptionPane.INFORMATION_MESSAGE);
                                getEmailTF().setText("");
                                getNameTF().setText("");
                                getDobTF().setText("");
                                getAddressTF().setText("");
                                getPhoneNoTF().setText("");
                                getPasswordPF().setText("");
                                getRePasswordPF().setText("");
                            }
                        }else {
                            JOptionPane.showMessageDialog(notice,"Password Does Not Match!!","Error!",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(notice,"Something went wrong!!","Error!",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

    private void backBtn(){
        JButton back = getBackBTN();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administer admin = new Administer();
                admin.setVisible(true);
                dispose();
            }
        });
    }
}
