/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Table;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author coin
 */
public class TeacherItem {
    private final SimpleStringProperty teacherID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty age;
    private final SimpleStringProperty sex;

    public TeacherItem(String a, String b, String c, String d) {
	this.teacherID = new SimpleStringProperty(a);
	this.name = new SimpleStringProperty(b);
	this.age = new SimpleStringProperty(c);
	this.sex = new SimpleStringProperty(d);
    }

    /**
     * @return the studentID
     */
    public String getTeacherID() {
	return teacherID.get();
    }

    /**
     * @param studentID the studentID to set
     */
    public void setTeacherID(String studentID) {
	this.teacherID.set(studentID);
    }

    /**
     * @return the studentName
     */
    public String getName() {
	return name.get();
    }

    /**
     * @param studentName the studentName to set
     */
    public void setName(String studentName) {
	this.name.set(studentName);
    }

    /**
     * @return the age
     */
    public String getAge() {
	return age.get();
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
	this.age.set(age);
    }

    /**
     * @return the sex
     */
    public String getSex() {
	return sex.get();
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
	this.sex.set(sex);
    }
    
}
