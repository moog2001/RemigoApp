/**
 * @author Ganbayar Sumiyakhuu
 * Date:5/18/2022
 * Description of code:
 * This class implements a simple abstract class to use for inheritance later.
 * firstName: First name of this person
 * field: lastName Last name of this person
 * age: Age of this person
 * gender: gender of this person
 */
package com.example.remigoapp;

abstract class Person {
    protected String firstName;
    protected String lastName;
    protected int age;
    protected String gender;

    /**
     * Simple getter and setter methods for all the parameters.
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
