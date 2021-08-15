package Course;
import SQLConnector.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManager {
    private Connection connection;
    public SQLManager(){
        try{
            connection = DatabaseConnector.getDatabaseConnection();
        } catch (SQLException throwable){
            throwable.printStackTrace();
        }
    }
    // user validation
    public ResultSet validateStudent(String email){
        try {
            String courseTable = "select * from student where email=?";
            PreparedStatement stat = connection.prepareStatement(courseTable);
            stat.setString(1,email);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
    public ResultSet validateStudent1(String id){
        try {
            String courseTable = "select * from student where stud_id=?";
            PreparedStatement stat = connection.prepareStatement(courseTable);
            stat.setString(1,id);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public ResultSet validateInstructor(String email){
        try {
            String courseTable = "select * from instructor where email=?";
            PreparedStatement stat = connection.prepareStatement(courseTable);
            stat.setString(1,email);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public ResultSet validateInstructor1(String id){
        try {
            String courseTable = "select * from instructor where ins_id =?";
            PreparedStatement stat = connection.prepareStatement(courseTable);
            stat.setString(1,id);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public ResultSet validateAdminister(String email){
        try {
            String courseTable = "select * from adminstrator where email=?";
            PreparedStatement stat = connection.prepareStatement(courseTable);
            stat.setString(1,email);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public ResultSet validateAdminister1(String id){
        try {
            String courseTable = "select * from adminstrator where admin_id=?";
            PreparedStatement stat = connection.prepareStatement(courseTable);
            stat.setString(1, id);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    //insert into student table ************
    public void insert(String id, String name, String email, String dob, String gender, String address, String phone_no, String father_name, String mother_name, String parent_phone_no, String course, String password, int level){
        try {
            String insert = "INSERT INTO student(stud_id,name,email,dob,gender,address,phone_no,father_name,mother_name,parent_phone,course,password,level)" + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stat = connection.prepareStatement(insert);
            stat.setString(1,id);
            stat.setString(2,name);
            stat.setString(3,email);
            stat.setString(4,dob);
            stat.setString(5,gender);
            stat.setString(6,address);
            stat.setString(7,phone_no);
            stat.setString(8,father_name);
            stat.setString(9,mother_name);
            stat.setString(10,parent_phone_no);
            stat.setString(11,course);
            stat.setString(12,password);
            stat.setInt(13,level);

            stat.executeUpdate();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Insert into instructor table *************
    public void insert(String id, String name, String email, String dob, String gender, String address, String phone_no, String password){
        try {
            String insertInstructor = "insert into instructor(ins_id, name, email, dob, gender, address, phone_no, password)"+"values(?,?,?,?,?,?,?,?)";
            PreparedStatement stat = connection.prepareStatement(insertInstructor);
            stat.setString(1,id);
            stat.setString(2,name);
            stat.setString(3,email);
            stat.setString(4,dob);
            stat.setString(5,gender);
            stat.setString(6,address);
            stat.setString(7,phone_no);
            stat.setString(8,password);

            stat.executeUpdate();
            stat.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //Insert course *********
    public void insert(String course_name){
        try {
            String insertInstructor = "insert into course(course_name)"+"values(?)";
            PreparedStatement stat = connection.prepareStatement(insertInstructor);
            stat.setString(1,course_name);

            stat.executeUpdate();
            stat.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    //Insert into administrator table **************
    public void insertAdministrator(String id, String name, String email, String dob, String gender, String address, String phone_no, String password){
        try {
            String insertInstructor = "insert into adminstrator(admin_id, name, email, dob, gender, address, phone_no, password)"+"values(?,?,?,?,?,?,?,?)";
            PreparedStatement stat = connection.prepareStatement(insertInstructor);
            stat.setString(1,id);
            stat.setString(2,name);
            stat.setString(3,email);
            stat.setString(4,dob);
            stat.setString(5,gender);
            stat.setString(6,address);
            stat.setString(7,phone_no);
            stat.setString(8,password);

            stat.executeUpdate();
            stat.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //Course Table *********
    public ResultSet getCourseTable(){
        try {
            String courseTable = "select * from course";
            PreparedStatement stat = connection.prepareStatement(courseTable);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    //Module Table(); *******
    public ResultSet getModuleTable(){
        try {
            String moduleTable = "select * from module";
            PreparedStatement stat = connection.prepareStatement(moduleTable);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    //Module Table(); *******
    public ResultSet getEnrollTable(String id){
        try {
            String moduleTable = "select * from enroll where stud_id = ?";
            PreparedStatement stat = connection.prepareStatement(moduleTable);
            stat.setString(1,id);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    //Module Add *****************
    public void addModule( String module_name, String course_name, String instructor_name,String module_type,  int level, String semester ){
        try {
            String insertM = "insert into module(module_name,course_name,instructor, module_type, level, semester)"+"values(?,?,?,?,?,?)";
            PreparedStatement stat = connection.prepareStatement(insertM);
            stat.setString(1,module_name);
            stat.setString(2,course_name);
            stat.setString(3,instructor_name);
            stat.setString(4,module_type);
            stat.setInt(5,level);
            stat.setString(6,semester);

            stat.executeUpdate();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateModule(String rowValue, String mName,String courseName,String instructorM, String moduleType, int lev, String seme){
        try {
            String updateM ="update module set module_name = ?, course_name = ?, instructor = ?, module_type =?, level= ?, semester = ? where module_id = ?";
            PreparedStatement stat = connection.prepareStatement(updateM);
            stat.setString(1,mName);
            stat.setString(2, courseName);
            stat.setString(3, instructorM);
            stat.setString(4, moduleType);
            stat.setInt(5,lev);
            stat.setString(6, seme);
            stat.setString(7, rowValue);

            stat.executeUpdate();
            stat.close();
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //course update *****************
    public void updateMarks(String marks_id, int marks, String result){
        try {
            String updateColumn = "update marks set marks =?, remarks = ? where marks_id =?";
            PreparedStatement stat = connection.prepareStatement(updateColumn);
            stat.setInt(1, marks);
            stat.setString(2,result);
            stat.setString(3, marks_id);

            stat.executeUpdate();
            stat.close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //course update *****************
    public void updateCourse(String id, String course_name){
        try {
            String updateColumn = "update course set course_name =? where course_id =?";
            PreparedStatement stat = connection.prepareStatement(updateColumn);
            stat.setString(1,course_name);
            stat.setString(2,id);

            stat.executeUpdate();
            stat.close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //delete course
    public void deleteCourse(int id){
        try {
            String del = "delete from course where course_id=?";
            PreparedStatement stat = connection.prepareStatement(del);
            stat.setInt(1,id);
            stat.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //delete course
    public void deleteModule(int id){
        try {
            String del = "delete from module where module_id=?";
            PreparedStatement stat = connection.prepareStatement(del);
            stat.setInt(1,id);
            stat.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    // get all the course name
    public ResultSet courses(){
        try {
            String courtliest = "select course_name from course";
            PreparedStatement stat = connection.prepareStatement(courtliest);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public ResultSet modules(){
        try {
            String moduleList = "select module_name from module";
            PreparedStatement stat = connection.prepareStatement(moduleList);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public ResultSet instructors(){
        try {
            String insList = "select name from instructor";
            PreparedStatement stat = connection.prepareStatement(insList);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
    //Enroll Module *****************
    public void enrollModule(String module_name, String course_name,String module_type, int level, String semester, String instructor_name, String stud_id, String stud_name, String module_id ){
        try {
            String insertM = "insert into enroll(module_name,course_name,module_type, level,semester, instructor, stud_id )"+"values(?,?,?,?,?,?,?)";
            String marks = "insert into marks(stud_id, student_name, course_name, module_id, module_name)" + "values(?,?,?,?,?)";
            PreparedStatement stat = connection.prepareStatement(insertM);
            PreparedStatement statM = connection.prepareStatement(marks);
            stat.setString(1,module_name);
            stat.setString(2,course_name);
            stat.setString(3,module_type);
            stat.setInt(4,level);
            stat.setString(5,semester);
            stat.setString(6,instructor_name);
            stat.setString(7,stud_id);

            statM.setString(1,stud_id);
            statM.setString(2, stud_name);
            statM.setString(3, course_name);
            statM.setString(4, module_id);
            statM.setString(5,module_name);

            stat.executeUpdate();
            statM.executeUpdate();
            stat.close();
            statM.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getMarks(){
        try {
            String insList = "select * from marks";
            PreparedStatement stat = connection.prepareStatement(insList);
            return stat.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
