package Course.Instructor;

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
import java.util.ArrayList;
import java.util.Scanner;

public class Instructor extends JFrame {
    private JTextField moduleMarksTF;
    private DefaultTableModel aDTM, mDTM;
    private JTable instructorModuleJT, instructorMarksJT;
    private JButton addMarksBTN, saveMarksBTN, instructorLogoutBTN ,Details;
    private JMenuItem exit, about, help;
    private JScrollPane instructorModuleJSP, instructorMarksJSP;
    private String uId, uName, uEmail, uType, loginID;
    private JComboBox<String> modulesJCB;
    private ArrayList<Object> modulesList;
    private String[] modules;
    private JLabel studId, studName, moduleID, moduleName;
    SQLManager SQL;
    Instructor notice = this;

    public Instructor(){
        setTitle("Course Management System");
        setMinimumSize(new Dimension(1250,700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);


        modulesList = new ArrayList();
        studId = new JLabel();
        studName = new JLabel();
        moduleID = new JLabel();
        moduleName = new JLabel();

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
        JPanel instructorMainPanel = new JPanel();
        add(instructorMainPanel);
        instructorMainPanel.setLayout(new GridLayout(2,2));

        SQL = new SQLManager();


        LoadData();


        instructorMainPanel.add(assignModule());
        instructorMainPanel.add(instructorPersonalDetails());
        instructorMainPanel.add(marksTablePanel());
        refreshAssignModuleTable();
        instructorMainPanel.add(marksOperationPanel());
        saveBTN();
        refreshMarksTable();

        Logout();
        getMarks();
        setVisible(true);
    }

    private JPanel instructorPersonalDetails(){
        JPanel instructorPersonalDetail = new JPanel();
        instructorPersonalDetail.setBorder(BorderFactory.createTitledBorder("Personal Details"));

        instructorLogoutBTN = new JButton("Logout");

        instructorPersonalDetail.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8,8,8,8);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        instructorPersonalDetail.add(new JLabel("ID: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        instructorPersonalDetail.add(new JLabel("Name: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        instructorPersonalDetail.add(new JLabel("Email: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        instructorPersonalDetail.add(new JLabel("User Type: "),gbc);

        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        instructorPersonalDetail.add(new JLabel(getuId()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        instructorPersonalDetail.add(new JLabel(getuName()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        instructorPersonalDetail.add(new JLabel(getuEmail()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        instructorPersonalDetail.add(new JLabel(getuType()),gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 4;
        gbc.gridy = 6;
        instructorPersonalDetail.add(instructorLogoutBTN,gbc);


        return instructorPersonalDetail;
    }

    private JPanel assignModule(){
        JPanel assigned = new JPanel();
        assigned.setBorder(BorderFactory.createTitledBorder("Assigned Module"));
        assigned.setLayout(new GridLayout(1,1));
        aDTM = new DefaultTableModel();
        Object[] colName = {"Module ID", "Module Name","Course Name", "Instructor Name","Module Type", "Level", "Semester"};
        aDTM.setColumnIdentifiers(colName);
        instructorModuleJT = new JTable(aDTM);
        instructorModuleJSP = new JScrollPane(instructorModuleJT);
        assigned.add(instructorModuleJSP);

        return assigned;
    }

    private JPanel marksOperationPanel(){
        JPanel marks = new JPanel();
        marks.setBorder(BorderFactory.createTitledBorder("Marks Operation"));

        saveMarksBTN = new JButton("Save");
        moduleMarksTF = new JTextField(5);
        modulesJCB = new JComboBox(getModules());
        studId = new JLabel();

        marks.setLayout(new GridBagLayout());
        Details = new JButton("Get Details");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8,8,8,8);


        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        marks.add(modulesJCB,gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 0;
        marks.add(Details,gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        marks.add(new JLabel("Student ID: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        marks.add(new JLabel("Student Name: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        marks.add(new JLabel("Module ID: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        marks.add(new JLabel("Module Name: "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        marks.add(new JLabel("Marks: "),gbc);

        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        marks.add(studId,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        marks.add(studName,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        marks.add(moduleID,gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        marks.add(moduleName,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 5;
        marks.add(moduleMarksTF,gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        marks.add(new JLabel(""),gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        marks.add(saveMarksBTN,gbc);

        return marks;
    }

    private JPanel marksTablePanel(){
        JPanel marksTable = new JPanel();
        marksTable.setBorder(BorderFactory.createTitledBorder("Marks Table"));
        marksTable.setLayout(new GridLayout(1,1));
        mDTM = new DefaultTableModel();
        Object[] colName = {"Marks ID", "Student ID","Student Name","Course Name","Module ID", "Module Name","Marks", "Remarks"};
        mDTM.setColumnIdentifiers(colName);
        instructorMarksJT = new JTable(mDTM);
        instructorMarksJSP = new JScrollPane(instructorMarksJT);
        marksTable.add(instructorMarksJSP);

        return marksTable;
    }

    public JTextField getModuleMarksTF() {
        return moduleMarksTF;
    }

    public void setModuleMarksTF(JTextField moduleMarksTF) {
        this.moduleMarksTF = moduleMarksTF;
    }

    public DefaultTableModel getaDTM() {
        return aDTM;
    }

    public void setaDTM(DefaultTableModel aDTM) {
        this.aDTM = aDTM;
    }

    public DefaultTableModel getmDTM() {
        return mDTM;
    }

    public void setmDTM(DefaultTableModel mDTM) {
        this.mDTM = mDTM;
    }

    public JTable getInstructorModuleJT() {
        return instructorModuleJT;
    }

    public void setInstructorModuleJT(JTable instructorModuleJT) {
        this.instructorModuleJT = instructorModuleJT;
    }

    public JTable getInstructorMarksJT() {
        return instructorMarksJT;
    }

    public void setInstructorMarksJT(JTable instructorMarksJT) {
        this.instructorMarksJT = instructorMarksJT;
    }

    public JButton getAddMarksBTN() {
        return addMarksBTN;
    }

    public void setAddMarksBTN(JButton addMarksBTN) {
        this.addMarksBTN = addMarksBTN;
    }

    public JButton getInstructorLogoutBTN() {
        return instructorLogoutBTN;
    }

    public void setInstructorLogoutBTN(JButton instructorLogoutBTN) {
        this.instructorLogoutBTN = instructorLogoutBTN;
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

    public JScrollPane getInstructorModuleJSP() {
        return instructorModuleJSP;
    }

    public void setInstructorModuleJSP(JScrollPane instructorModuleJSP) {
        this.instructorModuleJSP = instructorModuleJSP;
    }

    public JScrollPane getInstructorMarksJSP() {
        return instructorMarksJSP;
    }

    public void setInstructorMarksJSP(JScrollPane instructorMarksJSP) {
        this.instructorMarksJSP = instructorMarksJSP;
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

    public JComboBox<String> getModulesJCB() {
        return modulesJCB;
    }

    public void setModulesJCB(JComboBox<String> modulesJCB) {
        this.modulesJCB = modulesJCB;
    }

    public ArrayList getModulesList() {
        return modulesList;
    }


    public String[] getModules() {
        return modules;
    }

    public void setModules(String[] modules) {
        this.modules = modules;
    }

    public void setModulesList(ArrayList<Object> modulesList) {
        this.modulesList = modulesList;
    }

    public JButton getDetails() {
        return Details;
    }

    public void setDetails(JButton details) {
        Details = details;
    }

    public JLabel getStudId() {
        return studId;
    }

    public void setStudId(JLabel studId) {
        this.studId = studId;
    }

    public JLabel getStudName() {
        return studName;
    }

    public void setStudName(JLabel studName) {
        this.studName = studName;
    }

    public JLabel getModuleID() {
        return moduleID;
    }

    public void setModuleID(JLabel moduleID) {
        this.moduleID = moduleID;
    }

    public JLabel getModuleName() {
        return moduleName;
    }

    public void setModuleName(JLabel moduleName) {
        this.moduleName = moduleName;
    }

    public JButton getSaveMarksBTN() {
        return saveMarksBTN;
    }

    public void setSaveMarksBTN(JButton saveMarksBTN) {
        this.saveMarksBTN = saveMarksBTN;
    }

    private void Logout(){
        JButton logout = getInstructorLogoutBTN();
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
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
        ResultSet check = SQL.validateInstructor1(loginID);
        try {
            if (check.next()) {
                String insID = check.getString("ins_id");
                String email = check.getString("email");
                String name = check.getString("name");
                String type = "Instructor";
                setuId(insID);
                setuEmail(email);
                setuName(name);
                setuType(type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void refreshAssignModuleTable(){
        getaDTM().setRowCount(0);
        try {
            ResultSet moduleResult = SQL.getModuleTable();
            while (moduleResult.next()) {
                if(getuName().equals(moduleResult.getString("instructor"))){
                    getaDTM().addRow(new Object[]{
                            moduleResult.getString("module_id"),
                            moduleResult.getString("module_name"),
                            moduleResult.getString("course_name"),
                            moduleResult.getString("instructor"),
                            moduleResult.getString("module_type"),
                            moduleResult.getString("level"),
                            moduleResult.getString("semester"),
                    });
                    String m = moduleResult.getString("module_name");
                    modulesList.add(m);
                }
                setModules(modulesList.toArray(new String[modulesList.size()]));
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void refreshMarksTable(){
        getmDTM().setRowCount(0);
        try {
            ResultSet moduleResult = SQL.getMarks();
            String selectedModule = getModulesJCB().getSelectedItem().toString();
            while (moduleResult.next()) {
                if(selectedModule.equals(moduleResult.getString("module_name")) ){
                    getmDTM().addRow(new Object[]{
                            moduleResult.getString("marks_id"),
                            moduleResult.getString("stud_id"),
                            moduleResult.getString("student_name"),
                            moduleResult.getString("course_name"),
                            moduleResult.getString("module_id"),
                            moduleResult.getString("module_name"),
                            moduleResult.getString("marks"),
                            moduleResult.getString("remarks"),
                    });
                }
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void getMarks(){
        JButton getMarks = getDetails();
        getMarks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshMarksTable();
            }
        });
    }

    private void saveBTN(){
        JTable data = getInstructorMarksJT();
        DefaultTableModel model = getmDTM();
        data.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selRow = data.getSelectedRow();
                studId.setText(model.getValueAt(selRow, 1).toString());
                studName.setText(model.getValueAt(selRow, 2).toString());
                moduleName.setText(model.getValueAt(selRow, 3).toString());
                moduleID.setText(model.getValueAt(selRow, 4).toString());
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
        JButton updateMarksbtn = getSaveMarksBTN();
        updateMarksbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleRow = instructorMarksJT.getSelectedRow();
                if (seleRow == -1){
                    JOptionPane.showMessageDialog(notice,"Please select the row you want to update!!", "Error!",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    String marks = getModuleMarksTF().getText();
                    int marksInt = Integer.parseInt(marks);
                    String result = "";
                    if (marks.isEmpty()){
                        JOptionPane.showMessageDialog(notice,"The marks field most not be Empty!!", "Error!",JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (marksInt >= 40){
                            result = "Pass";
                        }else {
                            result = "Fail";
                        }
                        String rowValue = model.getValueAt(seleRow,0).toString();
                        SQL.updateMarks(rowValue,marksInt,result);
                        JOptionPane.showMessageDialog(notice,"Success!!", "Error!",JOptionPane.INFORMATION_MESSAGE);
                        refreshMarksTable();
                        getModuleMarksTF().setText("");
                    }
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(notice,"Something Went Wrong!!", "Error!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
