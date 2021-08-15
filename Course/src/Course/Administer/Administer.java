package Course.Administer;

import Course.Login;
import Course.SQLManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administer extends JFrame {
    private JMenuItem exit, about, help;
    private JButton logoutBTN, courseManagementBTN, addUserBTN, resultManagementBTN;
    private DefaultTableModel DTModel1, DTModel2;
    private JScrollPane jsp1, jsp2;
    private JTable courseJT, modulesJT;
    private int admin_id;
//    private String adminName, adminEmail, adminGender, adminDob, adminPhone;
    private String uId, uName, uEmail, uType, loginID;
    SQLManager SQL;

    public Administer(){
        setResizable(false);setTitle("Course Management System");
        setMinimumSize(new Dimension(1250,750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        //menuBar or Menu Items
        JMenuBar jmb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu view = new JMenu("View");

        //setting shortcut key for file
        file.setMnemonic(KeyEvent.VK_F);
        view.setMnemonic(KeyEvent.VK_V);

        jmb.add(file);
        jmb.add(view);

        exit = new JMenuItem("Exit");
        help = new JMenuItem("Help!");
        about = new JMenuItem("About");

        //Setting Shortcut key for menu items
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,KeyEvent.CTRL_DOWN_MASK));

        file.add(exit);
        file.add(help);
        view.add(about);

        setJMenuBar(jmb);

        //Menu ActionListener
        //Exit
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Panel setup
        JPanel administratorMainPanel = new JPanel();
        add(administratorMainPanel);
        administratorMainPanel.setLayout(new GridLayout(2,2));

        SQL = new SQLManager();
        LoadData();

        administratorMainPanel.add(courses());
        administratorMainPanel.add(personalDetail());
        administratorMainPanel.add(modules());
        administratorMainPanel.add(administratorControl());

        logoutBTN();
        administratorCourseManagement();
        addNewUser();
        refreshCourseTable();
        refreshModuleTable();
        UpgradeLevel();

    }

    //Personal detail panel
    private JPanel personalDetail(){
        JPanel personalDetailPanel = new JPanel();
        personalDetailPanel.setBorder(BorderFactory.createTitledBorder("Personal Details"));

        logoutBTN = new JButton("Logout");
        personalDetailPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8,8,8,8);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        personalDetailPanel.add(new JLabel("ID: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        personalDetailPanel.add(new JLabel("Name: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        personalDetailPanel.add(new JLabel("Email: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        personalDetailPanel.add(new JLabel("User Type: "),gbc);

        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        personalDetailPanel.add(new JLabel(getuId()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        personalDetailPanel.add(new JLabel(getuName()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        personalDetailPanel.add(new JLabel(getuEmail()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        personalDetailPanel.add(new JLabel(getuType()),gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 4;
        gbc.gridy = 6;
        personalDetailPanel.add(logoutBTN,gbc);

        return personalDetailPanel;
    }

    //Course List Panel
    private JPanel courses(){
        JPanel courseList = new JPanel();
        courseList.setBorder(BorderFactory.createTitledBorder("Course Lists"));
        courseList.setLayout(new GridLayout(1,1));
        DTModel1 = new DefaultTableModel();
        Object[] colName = {"Course ID", "Course Name"};
        DTModel1.setColumnIdentifiers(colName);
        courseJT = new JTable(DTModel1);
        jsp1 = new JScrollPane(courseJT);
        courseList.add(jsp1);

        return courseList;
    }

    //Administrator control panel
    private JPanel administratorControl(){
        JPanel administrator = new JPanel();
        administrator.setBorder(BorderFactory.createTitledBorder("Administrator Control Panel"));

        courseManagementBTN = new JButton("Course Management");
        addUserBTN = new JButton("Add User");
        resultManagementBTN = new JButton("Manage Result");

        administrator.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8,8,8,8);

        gbc.gridx = 0;
        gbc.gridy = 0;
        administrator.add(courseManagementBTN,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        administrator.add(addUserBTN,gbc);

//        gbc.gridx = 3;
//        gbc.gridy = 0;
//        administrator.add(resultManagementBTN ,gbc);

        return administrator;
    }

    //Module Panel
    private JPanel modules() {
        JPanel moduleList = new JPanel();
        moduleList.setBorder(BorderFactory.createTitledBorder("Module Lists"));
        moduleList.setLayout(new GridLayout(1,1));
        DTModel2 = new DefaultTableModel();
        Object[] colName = {"Module ID", "Module Name","Course", "Instructor Name", "Module Type", "Level", "Semester"};
        DTModel2.setColumnIdentifiers(colName);
        modulesJT = new JTable(DTModel2);
        jsp2 = new JScrollPane(modulesJT);
        moduleList.add(jsp2);

        return moduleList;
    }

    public JMenuItem getExit() {
        return exit;
    }

    public void setExit(JMenuItem exit) {
        this.exit = exit;
    }

    public JMenuItem getAbout() {
        return about;
    }

    public void setAbout(JMenuItem about) {
        this.about = about;
    }

    public JMenuItem getHelp() {
        return help;
    }

    public void setHelp(JMenuItem help) {
        this.help = help;
    }

    public JButton getLogoutBTN() {
        return logoutBTN;
    }

    public void setLogoutBTN(JButton logoutBTN) {
        this.logoutBTN = logoutBTN;
    }

    public JButton getCourseManagementBTN() {
        return courseManagementBTN;
    }

    public void setCourseManagementBTN(JButton courseManagementBTN) {
        this.courseManagementBTN = courseManagementBTN;
    }

    public JButton getAddUserBTN() {
        return addUserBTN;
    }

    public void setAddUserBTN(JButton addUserBTN) {
        this.addUserBTN = addUserBTN;
    }

    public JButton getResultManagementBTN() {
        return resultManagementBTN;
    }

    public void setResultManagementBTN(JButton resultManagementBTN) {
        this.resultManagementBTN = resultManagementBTN;
    }

    public DefaultTableModel getDTModel1() {
        return DTModel1;
    }

    public void setDTModel1(DefaultTableModel DTModel1) {
        this.DTModel1 = DTModel1;
    }

    public DefaultTableModel getDTModel2() {
        return DTModel2;
    }

    public void setDTModel2(DefaultTableModel DTModel2) {
        this.DTModel2 = DTModel2;
    }

    public JScrollPane getJsp1() {
        return jsp1;
    }

    public void setJsp1(JScrollPane jsp1) {
        this.jsp1 = jsp1;
    }

    public JScrollPane getJsp2() {
        return jsp2;
    }

    public void setJsp2(JScrollPane jsp2) {
        this.jsp2 = jsp2;
    }

    public JTable getCourseJT() {
        return courseJT;
    }

    public void setCourseJT(JTable courseJT) {
        this.courseJT = courseJT;
    }

    public JTable getModulesJT() {
        return modulesJT;
    }

    public void setModulesJT(JTable modulesJT) {
        this.modulesJT = modulesJT;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    //Login button
    private void logoutBTN(){
        JButton logout = getLogoutBTN();
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
    }

    private void administratorCourseManagement() {
        JButton administratorCourseManagement = getCourseManagementBTN();
        administratorCourseManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseManagement manage = new CourseManagement();
                manage.setVisible(true);
                dispose();
            }
        });
    }

    private void addNewUser() {
        JButton addUser = getAddUserBTN();
        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminRegister admin = new AdminRegister();
                admin.setVisible(true);
                dispose();
            }
        });
    }

    private void UpgradeLevel() {
        JButton upgrade = getResultManagementBTN();
        upgrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    private void refreshCourseTable(){
        getDTModel1().setRowCount(0);
        try {
            ResultSet courseResult = SQL.getCourseTable();
            while (courseResult.next()){
                getDTModel1().addRow(new Object[]{
                        courseResult.getString("course_id"),
                        courseResult.getString("course_name"),
                });
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void refreshModuleTable(){
        getDTModel2().setRowCount(0);
        try {
            ResultSet moduleResult = SQL.getModuleTable();
            while (moduleResult.next()) {
                getDTModel2().addRow(new Object[]{
                        moduleResult.getString("module_id"),
                        moduleResult.getString("module_name"),
                        moduleResult.getString("course_name"),
                        moduleResult.getString("instructor"),
                        moduleResult.getString("module_type"),
                        moduleResult.getString("level"),
                        moduleResult.getString("semester"),
                });
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void LoadData() {
        try {
            File studIDFile = new File("D:\\New folder (2)\\Umesh\\New folder\\Course\\src\\login.txt");
            Scanner myReader = null;
            myReader = new Scanner(studIDFile);
            while (myReader.hasNextLine()) {
                loginID = myReader.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ResultSet check = SQL.validateAdminister1(loginID);
        try {
            if (check.next()) {
                String insID = check.getString("admin_id");
                String email = check.getString("email");
                String name = check.getString("name");
                String type = "Administer";
                setuId(insID);
                setuEmail(email);
                setuName(name);
                setuType(type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
