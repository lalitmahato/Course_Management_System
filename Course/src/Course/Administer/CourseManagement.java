package Course.Administer;

import Course.SQLManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseManagement extends JFrame {
    private JTextField courseNameTF, courseIDTF, moduleIDTF, moduleNameTF;
    private JButton courseAddBTN, courseDeleteBTN, courseUpdateBTN, moduleAddBTN, moduleDeleteBTN, moduleUpdateBTN,backToMainPanelBTN;
    private JComboBox<String> moduleType, level, semester, instructor, moduleCourseName;
    private DefaultTableModel moduleDTModel,courseDTModel;
    private JTable courseTableJT, moduleTableJT;
    private JMenuItem exit, about, help;
    private JScrollPane courseJSP, moduleJSP;
    private ArrayList<Object> coursesList, instructorsList;
    String[] Courses, Instructors;
    CourseManagement notice = this;
    SQLManager SQL;

    public CourseManagement(){
        setTitle("Course Management System");
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

        SQL = new SQLManager();
        instructorsList = new ArrayList<>();
        coursesList = new ArrayList<>();
        listOfCourse();
        listOfInstructors();

        //Panel setup
        JPanel courseManagement = new JPanel();
        add(courseManagement);
        courseManagement.setLayout(new GridLayout(2,2));

        courseManagement.add(courseControlPanel());
        courseManagement.add(CourseTable());
        courseManagement.add(moduleControlPanel());
        courseManagement.add(ModuleTable());

        deleteCourseBTN();
        deleteModule();
        updateModule();
        refreshModuleTable();
        refreshCourseTable();
        updateCourseBTN();
        backBTN();
        addCourse();
        addModule();
    }

    private JPanel CourseTable(){
        JPanel courseTables = new JPanel();
        courseTables.setBorder(BorderFactory.createTitledBorder("Course Lists"));
        courseTables.setLayout(new GridLayout(1,1));
        courseDTModel = new DefaultTableModel();
        Object[] names = {"Course ID","Course Name"};
        courseDTModel .setColumnIdentifiers(names);
        courseTableJT = new JTable(courseDTModel );
        courseJSP = new JScrollPane(courseTableJT);
        courseTables.add(courseJSP);

        return courseTables;
    }

    private JPanel courseControlPanel(){
        JPanel coursePanel = new JPanel();
        coursePanel.setBorder(BorderFactory.createTitledBorder("Course Management Panel"));

        courseNameTF = new JTextField(30);
        courseAddBTN = new JButton("Add");
        courseUpdateBTN = new JButton("Update");
        courseDeleteBTN = new JButton("Delete");

        coursePanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(8,8,8,8);

        gc.gridx = 0;
        gc.gridy = 0;
        coursePanel.add(new JLabel("Course Name: "),gc);

        gc.gridwidth = 3;
        gc.gridx = 1;
        gc.gridy = 0;
        coursePanel.add(courseNameTF,gc);

        gc.gridx = 0;
        gc.gridy = 1;
        coursePanel.add(new JLabel(" "),gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = 2;
        coursePanel.add(courseAddBTN,gc);

        gc.gridx = 2;
        gc.gridy = 2;
        coursePanel.add(courseUpdateBTN,gc);

        gc.gridx = 3;
        gc.gridy = 2;
        coursePanel.add(courseDeleteBTN,gc);

        return coursePanel;
    }

    private JPanel ModuleTable(){
        JPanel modulePanel = new JPanel();
        modulePanel.setBorder(BorderFactory.createTitledBorder("Module Lists"));
        modulePanel.setLayout(new GridLayout(1,1));
        moduleDTModel = new DefaultTableModel();
        Object[] names = {"Module ID","Module Name", "Course Name","Instructor", "Module Type", "Level", "Semester"};
        moduleDTModel.setColumnIdentifiers(names);
        moduleTableJT = new JTable(moduleDTModel);
        moduleJSP = new JScrollPane(moduleTableJT);
        modulePanel.add(moduleJSP);

        return modulePanel;
    }

    private JPanel moduleControlPanel(){
        JPanel modulePanel = new JPanel();
        modulePanel.setBorder(BorderFactory.createTitledBorder("Module Management Panel"));

        moduleAddBTN = new JButton("Add");
        moduleUpdateBTN = new JButton( "Update");
        moduleDeleteBTN = new JButton("Delete");
        backToMainPanelBTN = new JButton("Back");
        moduleIDTF = new JTextField(30);
        moduleNameTF = new JTextField(30);
        moduleCourseName = new JComboBox(getCourses());
        moduleType = new JComboBox<>(new String[]{"Compulsory","Optional"});
        level = new JComboBox<>(new String[]{"4","5","6"});
        semester = new JComboBox<>(new String[]{"Summer", "Autumn"});
        instructor = new JComboBox<>(getInstructors());

        modulePanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(8,8,8,8);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        modulePanel.add(new JLabel("Module Name: "), gc);

        gc.gridx = 0;
        gc.gridy = 1;
        modulePanel.add(new JLabel("Course Name: "), gc);

        gc.gridx = 0;
        gc.gridy = 2;
        modulePanel.add(new JLabel("Module Type: "), gc);

        gc.gridx = 0;
        gc.gridy = 3;
        modulePanel.add(new JLabel("Level: "), gc);

        gc.gridx = 3;
        gc.gridy = 3;
        modulePanel.add(new JLabel("Semester: "), gc);

        gc.gridx = 0;
        gc.gridy = 4;
        modulePanel.add(new JLabel("Instructor: "), gc);

        gc.gridwidth = 4;
        gc.gridx = 1;
        gc.gridy = 0;
        modulePanel.add(moduleNameTF, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        modulePanel.add(moduleCourseName, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        modulePanel.add(moduleType, gc);

        gc.gridwidth = 2;
        gc.gridx = 1;
        gc.gridy = 3;
        modulePanel.add(level, gc);

        gc.gridx = 4;
        gc.gridy = 3;
        modulePanel.add(semester, gc);

        gc.gridwidth = 5;
        gc.gridx = 1;
        gc.gridy = 4;
        modulePanel.add(instructor, gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = 5;
        modulePanel.add(moduleAddBTN, gc);

        gc.gridx = 2;
        gc.gridy = 5;
        modulePanel.add(moduleUpdateBTN, gc);

        gc.gridx = 3;
        gc.gridy = 5;
        modulePanel.add(moduleDeleteBTN, gc);

        gc.gridx = 4;
        gc.gridy = 5;
        modulePanel.add(backToMainPanelBTN ,gc);
        return modulePanel;
    }

    public JTextField getCourseNameTF() {
        return courseNameTF;
    }

    public void setCourseNameTF(JTextField courseNameTF) {
        this.courseNameTF = courseNameTF;
    }

    public JTextField getCourseIDTF() {
        return courseIDTF;
    }

    public void setCourseIDTF(JTextField courseIDTF) {
        this.courseIDTF = courseIDTF;
    }

    public JTextField getModuleIDTF() {
        return moduleIDTF;
    }

    public void setModuleIDTF(JTextField moduleIDTF) {
        this.moduleIDTF = moduleIDTF;
    }

    public JTextField getModuleNameTF() {
        return moduleNameTF;
    }

    public void setModuleNameTF(JTextField moduleNameTF) {
        this.moduleNameTF = moduleNameTF;
    }

    public JButton getCourseAddBTN() {
        return courseAddBTN;
    }

    public void setCourseAddBTN(JButton courseAddBTN) {
        this.courseAddBTN = courseAddBTN;
    }

    public JButton getCourseDeleteBTN() {
        return courseDeleteBTN;
    }

    public void setCourseDeleteBTN(JButton courseDeleteBTN) {
        this.courseDeleteBTN = courseDeleteBTN;
    }

    public JButton getCourseUpdateBTN() {
        return courseUpdateBTN;
    }

    public void setCourseUpdateBTN(JButton courseUpdateBTN) {
        this.courseUpdateBTN = courseUpdateBTN;
    }

    public JButton getModuleAddBTN() {
        return moduleAddBTN;
    }

    public void setModuleAddBTN(JButton moduleAddBTN) {
        this.moduleAddBTN = moduleAddBTN;
    }

    public JButton getModuleDeleteBTN() {
        return moduleDeleteBTN;
    }

    public void setModuleDeleteBTN(JButton moduleDeleteBTN) {
        this.moduleDeleteBTN = moduleDeleteBTN;
    }

    public JButton getModuleUpdateBTN() {
        return moduleUpdateBTN;
    }

    public void setModuleUpdateBTN(JButton moduleUpdateBTN) {
        this.moduleUpdateBTN = moduleUpdateBTN;
    }

    public JButton getBackToMainPanelBTN() {
        return backToMainPanelBTN;
    }

    public void setBackToMainPanelBTN(JButton backToMainPanelBTN) {
        this.backToMainPanelBTN = backToMainPanelBTN;
    }

    public JComboBox<String> getModuleType() {
        return moduleType;
    }

    public void setModuleType(JComboBox<String> moduleType) {
        this.moduleType = moduleType;
    }

    public JComboBox<String> getLevel() {
        return level;
    }

    public void setLevel(JComboBox<String> level) {
        this.level = level;
    }

    public JComboBox<String> getSemester() {
        return semester;
    }

    public void setSemester(JComboBox<String> semester) {
        this.semester = semester;
    }

    public JComboBox<String> getInstructor() {
        return instructor;
    }

    public void setInstructor(JComboBox<String> instructor) {
        this.instructor = instructor;
    }

    public JComboBox<String> getModuleCourseName() {
        return moduleCourseName;
    }

    public void setModuleCourseName(JComboBox<String> moduleCourseName) {
        this.moduleCourseName = moduleCourseName;
    }

    public DefaultTableModel getModuleDTModel() {
        return moduleDTModel;
    }

    public void setModuleDTModel(DefaultTableModel moduleDTModel) {
        this.moduleDTModel = moduleDTModel;
    }

    public DefaultTableModel getCourseDTModel() {
        return courseDTModel;
    }

    public void setCourseDTModel(DefaultTableModel courseDTModel) {
        this.courseDTModel = courseDTModel;
    }

    public JTable getCourseTableJT() {
        return courseTableJT;
    }

    public void setCourseTableJT(JTable courseTableJT) {
        this.courseTableJT = courseTableJT;
    }

    public JTable getModuleTableJT() {
        return moduleTableJT;
    }

    public void setModuleTableJT(JTable moduleTableJT) {
        this.moduleTableJT = moduleTableJT;
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

    public JScrollPane getCourseJSP() {
        return courseJSP;
    }

    public void setCourseJSP(JScrollPane courseJSP) {
        this.courseJSP = courseJSP;
    }

    public JScrollPane getModuleJSP() {
        return moduleJSP;
    }

    public void setModuleJSP(JScrollPane moduleJSP) {
        this.moduleJSP = moduleJSP;
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

    public ArrayList<Object> getInstructorsList() {
        return instructorsList;
    }

    public void setInstructorsList(ArrayList<Object> instructorsList) {
        this.instructorsList = instructorsList;
    }

    public String[] getInstructors() {
        return Instructors;
    }

    public void setInstructors(String[] instructors) {
        Instructors = instructors;
    }

    private void backBTN(){
        JButton back = getBackToMainPanelBTN();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administer admin = new Administer();
                admin.setVisible(true);
                dispose();
            }
        });
    }

    //course management
    private void addCourse(){
        JButton addC = getCourseAddBTN();
        addC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cName = getCourseNameTF().getText();
                if (cName.isEmpty()){
                    JOptionPane.showMessageDialog(notice,"The field must not be empty!!","Error!",JOptionPane.ERROR_MESSAGE);
                }else {
                    SQL.insert(cName);
                    refreshCourseTable();
                    JOptionPane.showMessageDialog(notice, "Successfully Added!...", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    getCourseNameTF().setText("");
                }
            }
        });
    }

    //Course Table
    private void refreshCourseTable(){
        getCourseDTModel().setRowCount(0);
        try {
            ResultSet courseResult = SQL.getCourseTable();
            while (courseResult.next()){
                getCourseDTModel().addRow(new Object[]{
                        courseResult.getString("course_id"),
                        courseResult.getString("course_name"),
                });
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    //update course
    private void updateCourseBTN(){
        JTable data = getCourseTableJT();
        DefaultTableModel model = getCourseDTModel();
        data.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selRow = data.getSelectedRow();
                getCourseNameTF().setText(model.getValueAt(selRow,1).toString());
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

        JButton updateC = getCourseUpdateBTN();
        updateC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleRow = courseTableJT.getSelectedRow();
                if (seleRow == -1){
                    JOptionPane.showMessageDialog(notice,"Please select the row you want to update!!", "Error!",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    String cName = getCourseNameTF().getText();
                    if (cName.isEmpty()){
                        JOptionPane.showMessageDialog(notice,"The field must not be empty!!");
                    }else {
                        String rowValue = model.getValueAt(seleRow,0).toString();
                        SQL.updateCourse(rowValue,cName);
                        JOptionPane.showMessageDialog(notice, "Successfully Updated!...", "Success!", JOptionPane.INFORMATION_MESSAGE);
                        refreshCourseTable();
                        getCourseNameTF().setText("");

                    }
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(notice,"Something Went Wrong!!", "Error!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void deleteCourseBTN(){
        JButton del = getCourseDeleteBTN();
        DefaultTableModel model = getCourseDTModel();
        JTable data = getCourseTableJT();
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (data.getSelectedRowCount() == 1){
                    int seleRow = courseTableJT.getSelectedRow();
                    int rowVal = Integer.parseInt(model.getValueAt(seleRow,0).toString());
                    SQL.deleteCourse(rowVal);
                    JOptionPane.showMessageDialog(notice,"Successfully Deleted!","Error!",JOptionPane.ERROR_MESSAGE);
                    refreshCourseTable();
                    getCourseNameTF().setText("");
                } else{
                    JOptionPane.showMessageDialog(notice,"Please select the row you want to Delete!!", "Error!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //Refresh course Table
    private void refreshModuleTable(){
        getModuleDTModel().setRowCount(0);
        try {
            ResultSet mr = SQL.getModuleTable();
            while (mr.next()) {
                getModuleDTModel().addRow(new Object[]{
                        mr.getString("module_id"),
                        mr.getString("module_name"),
                        mr.getString("course_name"),
                        mr.getString("instructor"),
                        mr.getString("module_type"),
                        mr.getString("level"),
                        mr.getString("semester"),
                });
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    //Module Control
    private void addModule(){
        JButton addM = getModuleAddBTN();
        addM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mName = getModuleNameTF().getText();
                String moduleType = getModuleType().getSelectedItem().toString();
                String gender = getLevel().getSelectedItem().toString();
                String seme = getSemester().getSelectedItem().toString();
                String instructorM = getInstructor().getSelectedItem().toString();
                String courseName = getModuleCourseName().getSelectedItem().toString();
                int lev = Integer.parseInt(getLevel().getSelectedItem().toString());
                if (mName.isEmpty()){
                    JOptionPane.showMessageDialog(notice,"The field must not be empty!!","Error!",JOptionPane.ERROR_MESSAGE);
                }else {
                    SQL.addModule(mName,courseName,instructorM,moduleType,lev,seme);
                    refreshModuleTable();
                    getModuleNameTF().setText("");
                }
            }
        });
    }

    //update module
    private void updateModule(){
        JTable data = getModuleTableJT();
        DefaultTableModel model = getModuleDTModel();
        data.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selRow = data.getSelectedRow();
                getModuleNameTF().setText(model.getValueAt(selRow,1).toString());

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
        JButton updateM = getModuleUpdateBTN();
        updateM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleRow = moduleTableJT.getSelectedRow();
                if (seleRow == -1){
                    JOptionPane.showMessageDialog(notice,"Please select the row you want to update!!", "Error!",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    String mName = getModuleNameTF().getText();
                    String moduleType = getModuleType().getSelectedItem().toString();
                    String gender = getLevel().getSelectedItem().toString();
                    String seme = getSemester().getSelectedItem().toString();
                    String instructorM = getInstructor().getSelectedItem().toString();
                    String courseName = getModuleCourseName().getSelectedItem().toString();
                    int lev = Integer.parseInt(getLevel().getSelectedItem().toString());
                    if (mName.isEmpty()){
                        JOptionPane.showMessageDialog(notice,"The Field most not be Empty!!", "Error!",JOptionPane.ERROR_MESSAGE);
                    } else {
                        String rowValue = model.getValueAt(seleRow,0).toString();
                        SQL.updateModule(rowValue, mName, courseName, instructorM, moduleType, lev, seme);
                        refreshModuleTable();
                        getModuleNameTF().setText("");
                    }
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(notice,"Something Went Wrong!!", "Error!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    //delete module
    private void deleteModule(){
        JButton del = getModuleDeleteBTN();
        DefaultTableModel model = getModuleDTModel();
        JTable data = getModuleTableJT();
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if (data.getSelectedRowCount() == 1){
                        int seleRow = moduleTableJT.getSelectedRow();
                        int rowVal = Integer.parseInt(model.getValueAt(seleRow,0).toString());
                        SQL.deleteModule(rowVal);
                        JOptionPane.showMessageDialog(notice,"Successfully Deleted!","Error!",JOptionPane.ERROR_MESSAGE);
                        refreshModuleTable();
                        getCourseNameTF().setText("");
                    } else {
                        JOptionPane.showMessageDialog(notice,"Please select the row you want to Delete!!", "Error!",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception a){
                    JOptionPane.showMessageDialog(notice,"The data is linked with other data so can not be Delete!!", "Error!",JOptionPane.ERROR_MESSAGE);
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

    public void listOfInstructors(){
        try {
            ResultSet result = SQL.instructors();
            instructorsList.clear();
            while (result.next()){
                String c = result.getString("name");
                instructorsList.add(c);
            }
            setInstructors(instructorsList.toArray(new String[instructorsList.size()]));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
