package Course;

import Course.Administer.Administer;
import Course.Instructor.Instructor;
import Course.Student.Register;
import Course.Student.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame{
    private JTextField uName;
    private JPasswordField password;
    private JButton loginBTN, regBTN;
    private JComboBox<String> userType;
    private String id, userName, email, userType1, level;
    SQLManager SQL;
    Login notice = this;

    public Login(){
        setTitle("Login");
        setMinimumSize(new Dimension(450,290));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        uName = new JTextField(25);
        password = new JPasswordField(25);
        loginBTN = new JButton("Login");
        regBTN = new JButton("Register");
        userType = new JComboBox<>(new String[]{"Student", "Instructor","Administrator"});
        SQL = new SQLManager();

        add(loginPanel());
        pack();
        setVisible(true);

        loginButton();
        registerButton();
    }

    private JPanel loginPanel(){
        //panel setup
        JPanel loginP = new JPanel();
        loginP.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(9,9,9,9);

        gc.gridx = 0;
        gc.gridy = 1;
        loginP.add(new JLabel("Email:"),gc);

        gc.gridx = 0;
        gc.gridy = 2;
        loginP.add(new JLabel("Password:"),gc);

        gc.gridwidth = 3;
        gc.gridx = 1;
        gc.gridy = 0;
        loginP.add(userType, gc);

        gc.gridwidth = 3;
        gc.gridx = 1;
        gc.gridy = 1;
        loginP.add(uName, gc);

        gc.gridwidth = 3;
        gc.gridx = 1;
        gc.gridy = 2;
        loginP.add(password,gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = 3;
        loginP.add(loginBTN, gc);

        gc.gridwidth = 1;
        gc.gridx = 2;
        gc.gridy = 3;
        loginP.add(regBTN, gc);

        return loginP;
    }

    public JTextField getuName() {
        return uName;
    }

    public void setuName(JTextField uName) {
        this.uName = uName;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public void setPassword(JPasswordField password) {
        this.password = password;
    }

    public JButton getLoginBTN() {
        return loginBTN;
    }

    public void setLoginBTN(JButton loginBTN) {
        this.loginBTN = loginBTN;
    }

    public JButton getRegBTN() {
        return regBTN;
    }

    public void setRegBTN(JButton regBTN) {
        this.regBTN = regBTN;
    }

    public JComboBox<String> getUserType() {
        return userType;
    }

    public void setUserType(JComboBox<String> userType) {
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType1() {
        return userType1;
    }

    public void setUserType1(String userType1) {
        this.userType1 = userType1;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    //Login button
    private void loginButton(){
        JButton login = getLoginBTN();
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = getuName().getText();
                String pwd = String.valueOf(password.getPassword());
                String selectedUserType = getUserType().getSelectedItem().toString();
                if (email.isEmpty()){
                    if (pwd.isEmpty()) {
                        JOptionPane.showMessageDialog(notice,"The email and password field must not be empty!!","Error!",JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(notice,"The email field must not be empty!!","Error!",JOptionPane.ERROR_MESSAGE);
                    }
                } else if (pwd.isEmpty()){
                    JOptionPane.showMessageDialog(notice,"The password field must not be empty!!","Error!",JOptionPane.ERROR_MESSAGE);
                } else {
                    if (selectedUserType == "Student"){
                        try {
                            ResultSet check = SQL.validateStudent(email);
                            if (check.next()){
                                String studID = check.getString("stud_id");
                                String uEmail = check.getString("email");
                                String uPassword = check.getString("password");
                                if (uPassword.equals(pwd)){
                                    FileWriter myWriter = new FileWriter("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\login.txt");
                                    myWriter.write(studID);
                                    myWriter.close();
                                    Student student = new Student();
                                    student.setVisible(true);
                                    dispose();
                                } else{
                                    JOptionPane.showMessageDialog(notice,"Invalid email or password","Error!",JOptionPane.ERROR_MESSAGE);
                                }
                            } else{
                                JOptionPane.showMessageDialog(notice,"Invalid email or password","Error!",JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException | IOException throwable) {
                            throwable.printStackTrace();
                        }

                    } else if (selectedUserType == "Instructor"){
                        try {
                            ResultSet check = SQL.validateInstructor(email);
                            if (check.next()){
                                String insID = check.getString("ins_id");
                                String uEmail = check.getString("email");
                                String uPassword = check.getString("password");
                                if (uPassword.equals(pwd)){
                                    FileWriter myWriter = new FileWriter("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\login.txt");
                                    myWriter.write(insID);
                                    myWriter.close();
                                    Instructor inst = new Instructor();
                                    inst.setVisible(true);
                                    dispose();
                                } else{
                                    JOptionPane.showMessageDialog(notice,"Invalid email or password","Error!",JOptionPane.ERROR_MESSAGE);
                                }
                            } else{
                                JOptionPane.showMessageDialog(notice,"Invalid email or password","Error!",JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException | IOException throwable) {
                            throwable.printStackTrace();
                        }

                    }else {
                        try {
                            ResultSet check = SQL.validateAdminister(email);
                            if (check.next()){
                                String adminID = check.getString("admin_id");
                                String uEmail = check.getString("email");
                                String uPassword = check.getString("password");
                                if (uPassword.equals(pwd)){
                                    FileWriter myWriter = new FileWriter("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\login.txt");
                                    myWriter.write(adminID);
                                    myWriter.close();
                                    Administer admin = new Administer();
                                    admin.setVisible(true);
                                    dispose();
                                } else{
                                    JOptionPane.showMessageDialog(notice,"Invalid email or password","Error!",JOptionPane.ERROR_MESSAGE);
                                }
                            } else{
                                JOptionPane.showMessageDialog(notice,"Invalid email or password","Error!",JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException | IOException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //Register button
    private void registerButton(){
        JButton reg = getRegBTN();
        reg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register reg = new Register();
                reg.setVisible(true);
                dispose();
            }
        });
    }

}
