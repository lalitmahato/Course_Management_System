package Course.Student;

import Course.Login;
import Course.SQLManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student extends JFrame {
    private DefaultTableModel DTModel1, DTModel2;
    private JButton enrolBTN, logoutBTN, generateMarkSheetBTN;
    private JTable EnrolModelJT, ChooseModelJT;
    private String id, personName, email, userType, level, loginID, eCourse;
    private JLabel mID, mName, cName, mInstructor, module_type, sLevel, cSemester;
    private JScrollPane jsp1, jsp2;
    private JMenuItem exit, about, help;
    private int countEnroll = 0;
    Student notice = this;
    SQLManager SQL;


    public Student() {
        setTitle("Course Management System");
        setMinimumSize(new Dimension(1250, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        mID = new JLabel();
        mName = new JLabel();
        cName= new JLabel();
        mInstructor = new JLabel();
        module_type = new JLabel();
        sLevel = new JLabel();
        cSemester = new JLabel();

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
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));

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

        SQL = new SQLManager();
        LoadData();

        //Panel setup
        JPanel studentMainPanel = new JPanel();
        add(studentMainPanel);
        studentMainPanel.setLayout(new GridLayout(2, 2));

        studentMainPanel.add(EnrolledModule());
        studentMainPanel.add(personalDetail());
        studentMainPanel.add(AvailableModuleForEnroll());
        studentMainPanel.add(enrollModule());



        logoutBTN();
        refreshModuleTable();
        refreshEnrollModuleTable();
        enrollNewModule();
        System.out.println();
        setVisible(true);
    }

    //Personal detail panel
    private JPanel personalDetail() {
        JPanel personalDetailPanel = new JPanel();
        personalDetailPanel.setBorder(BorderFactory.createTitledBorder("Personal Details"));

        logoutBTN = new JButton("Logout");

        personalDetailPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        personalDetailPanel.add(new JLabel("ID: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        personalDetailPanel.add(new JLabel("Name: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        personalDetailPanel.add(new JLabel("Email: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        personalDetailPanel.add(new JLabel("User Type: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        personalDetailPanel.add(new JLabel("Level: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        personalDetailPanel.add(new JLabel("Course: "), gbc);

        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        personalDetailPanel.add(new JLabel(getId()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        personalDetailPanel.add(new JLabel(getPersonName()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        personalDetailPanel.add(new JLabel(getEmail()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        personalDetailPanel.add(new JLabel(getUserType()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        personalDetailPanel.add(new JLabel(getLevel()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        personalDetailPanel.add(new JLabel(geteCourse()),gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 4;
        gbc.gridy = 7;
        personalDetailPanel.add(logoutBTN, gbc);

        return personalDetailPanel;
    }

    //Enrolled course panel
    private JPanel EnrolledModule() {
        JPanel enrolledModel = new JPanel();
        enrolledModel.setBorder(BorderFactory.createTitledBorder("Enrolled Modules"));
        enrolledModel.setLayout(new GridLayout(1, 1));
        DTModel1 = new DefaultTableModel();
        Object[] colName = {"Enroll ID", "Module Name", "Course Name", "Module Type", "Level", "Semester","Instructor","Instructor ID","Student ID"};
        DTModel1.setColumnIdentifiers(colName);
        EnrolModelJT = new JTable(DTModel1);
        jsp1 = new JScrollPane(EnrolModelJT);
        enrolledModel.add(jsp1);

        return enrolledModel;
    }

    //course Enroll Panel
    private JPanel enrollModule() {
        JPanel enrolPanel = new JPanel();
        enrolPanel.setBorder(BorderFactory.createTitledBorder("Enroll Module Detail"));
        enrolPanel.setLayout(new GridBagLayout());

        enrolBTN = new JButton("Enrol");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        enrolPanel.add(new JLabel("Module ID: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        enrolPanel.add(new JLabel("Module Name: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        enrolPanel.add(new JLabel("Course Name: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        enrolPanel.add(new JLabel("Instructor: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        enrolPanel.add(new JLabel("Module Type: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        enrolPanel.add(new JLabel("Level: "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        enrolPanel.add(new JLabel("Semester: "), gbc);

        gbc.gridwidth = 5;
        gbc.gridx = 1;
        gbc.gridy = 1;
        enrolPanel.add(mID, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        enrolPanel.add(mName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        enrolPanel.add(cName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        enrolPanel.add(mInstructor, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        enrolPanel.add(module_type, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        enrolPanel.add(sLevel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        enrolPanel.add(cSemester, gbc);

        gbc.gridx = 4;
        gbc.gridy = 8;
        enrolPanel.add(enrolBTN, gbc);

        return enrolPanel;
    }

    //Optional course enroll panel
    private JPanel AvailableModuleForEnroll() {
        JPanel optCourseList = new JPanel();
        optCourseList.setBorder(BorderFactory.createTitledBorder("Available Modules For Enroll"));
        optCourseList.setLayout(new GridLayout(1, 1));
        DTModel2 = new DefaultTableModel();
        Object[] colName = {"Module ID", "Module Name","Course Name", "Instructor Name", "Module Type", "Level", "Semester"};
        DTModel2.setColumnIdentifiers(colName);
        ChooseModelJT = new JTable(DTModel2);
        jsp2 = new JScrollPane(ChooseModelJT);
        optCourseList.add(jsp2);

        return optCourseList;
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

    public JButton getEnrolBTN() {
        return enrolBTN;
    }

    public void setEnrolBTN(JButton enrolBTN) {
        this.enrolBTN = enrolBTN;
    }

    public JButton getLogoutBTN() {
        return logoutBTN;
    }

    public void setLogoutBTN(JButton logoutBTN) {
        this.logoutBTN = logoutBTN;
    }

    public JButton getGenerateMarkSheetBTN() {
        return generateMarkSheetBTN;
    }

    public void setGenerateMarkSheetBTN(JButton generateMarkSheetBTN) {
        this.generateMarkSheetBTN = generateMarkSheetBTN;
    }

    public JTable getEnrolModelJT() {
        return EnrolModelJT;
    }

    public void setEnrolModelJT(JTable enrolModelJT) {
        EnrolModelJT = enrolModelJT;
    }

    public JTable getChooseModelJT() {
        return ChooseModelJT;
    }

    public void setChooseModelJT(JTable chooseModelJT) {
        ChooseModelJT = chooseModelJT;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String geteCourse() {
        return eCourse;
    }

    public void seteCourse(String eCourse) {
        this.eCourse = eCourse;
    }

    public JLabel getmID() {
        return mID;
    }

    public void setmID(JLabel mID) {
        this.mID = mID;
    }

    public JLabel getmName() {
        return mName;
    }

    public void setmName(JLabel mName) {
        this.mName = mName;
    }

    public JLabel getcName() {
        return cName;
    }

    public void setcName(JLabel cName) {
        this.cName = cName;
    }

    public JLabel getmInstructor() {
        return mInstructor;
    }

    public void setmInstructor(JLabel mInstructor) {
        this.mInstructor = mInstructor;
    }

    public JLabel getModule_type() {
        return module_type;
    }

    public void setModule_type(JLabel module_type) {
        this.module_type = module_type;
    }

    public JLabel getsLevel() {
        return sLevel;
    }

    public void setsLevel(JLabel sLevel) {
        this.sLevel = sLevel;
    }

    public JLabel getcSemester() {
        return cSemester;
    }

    public void setcSemester(JLabel cSemester) {
        this.cSemester = cSemester;
    }

    //Logout button
    private void logoutBTN() {
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
    private void refreshModuleTable(){
        getDTModel2().setRowCount(0);
        try {
            ResultSet moduleResult = SQL.getModuleTable();
            while (moduleResult.next()) {
                if(getLevel().equals(moduleResult.getString("level")) && geteCourse().equals(moduleResult.getString("course_name"))){
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

            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void enrollNewModule(){
        JTable data = getChooseModelJT();
        DefaultTableModel model = getDTModel2();
        data.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selRow = data.getSelectedRow();
                mID.setText(model.getValueAt(selRow, 0).toString());
                mName.setText(model.getValueAt(selRow,1).toString());
                cName.setText(model.getValueAt(selRow, 2).toString());
                mInstructor.setText(model.getValueAt(selRow,3).toString());
                module_type.setText(model.getValueAt(selRow,4).toString());
                sLevel.setText(model.getValueAt(selRow,5).toString());
                cSemester.setText(model.getValueAt(selRow,6).toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        JButton enroll = getEnrolBTN();
        enroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countEnroll == 4){
                    JOptionPane.showMessageDialog(notice,"You can only enroll four module", "Error!",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int seleRow = ChooseModelJT.getSelectedRow();
                if (seleRow == -1){
                    JOptionPane.showMessageDialog(notice,"Please select the row you want to update!!", "Error!",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    String moduleId = model.getValueAt(seleRow, 0).toString();
                    String moduleName = model.getValueAt(seleRow,1).toString();
                    String CourseName = model.getValueAt(seleRow, 2).toString();
                    String mInstructor = model.getValueAt(seleRow,3).toString();
                    String module_type = model.getValueAt(seleRow,4).toString();
                    String cLevel = model.getValueAt(seleRow,5).toString();
                    String cSemester = model.getValueAt(seleRow,6).toString();
                    int lev = Integer.parseInt(cLevel);

                    SQL.enrollModule(moduleName, CourseName,module_type,lev, cSemester, mInstructor,getId(), getPersonName(), moduleId);
                    refreshModuleTable();
                    refreshEnrollModuleTable();
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(notice,"Something Went Wrong!!", "Error!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    private void refreshEnrollModuleTable(){
        getDTModel1().setRowCount(0);
        countEnroll = 0;
        try {
            ResultSet moduleResult = SQL.getEnrollTable(getId());
            while (moduleResult.next()) {
                if(getId().equals(moduleResult.getString("stud_id"))){
                    getDTModel1().addRow(new Object[]{
                            moduleResult.getString("enroll_id"),
                            moduleResult.getString("module_name"),
                            moduleResult.getString("course_name"),
                            moduleResult.getString("module_type"),
                            moduleResult.getString("level"),
                            moduleResult.getString("semester"),
                            moduleResult.getString("instructor"),
                            moduleResult.getString("ins_id"),
                            moduleResult.getString("stud_id"),
                    });
                    countEnroll += 1;
                }
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
        ResultSet check = SQL.validateStudent1(loginID);
        try {
            if (check.next()) {
                String studID = check.getString("stud_id");
                String uEmail = check.getString("email");
                String studName = check.getString("name");
                String course = check.getString("course");
                String uType = "Student";
                String studLevel = String.valueOf(check.getInt("level"));
                setId(studID);
                setPersonName(studName);
                setEmail(uEmail);
                setUserType(uType);
                setLevel(studLevel);
                seteCourse(course);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}


