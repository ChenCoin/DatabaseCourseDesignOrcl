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
public class StudentItem {
    private final SimpleStringProperty studentID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty age;
    private final SimpleStringProperty sex;
    private final SimpleStringProperty class2;

    public StudentItem(String studentID, String name, String age, String sex, String class2) {
	this.studentID = new SimpleStringProperty(studentID);
	this.name = new SimpleStringProperty(name);
	this.age = new SimpleStringProperty(age);
	this.sex = new SimpleStringProperty(sex);
	this.class2 = new SimpleStringProperty(class2);
    }

    /**
     * @return the studentID
     */
    public String getStudentID() {
	return studentID.get();
    }

    /**
     * @param studentID the studentID to set
     */
    public void setStudentID(String studentID) {
	this.studentID.set(studentID);
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

    /**
     * @return the class2
     */
    public String getClass2() {
	return class2.get();
    }

    /**
     * @param class2 the class2 to set
     */
    public void setClass2(String class2) {
	this.class2.set(class2);
    }

}
