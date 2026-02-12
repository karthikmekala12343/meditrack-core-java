package com.airtribe.meditrack.entity;

import java.io.Serializable;

/**
 * Abstract base class for all persons (Doctor, Patient, etc.)
 * Demonstrates encapsulation, access modifiers, and abstraction.
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private String gender;
    private String address;
    
    /**
     * Instantiates a new person.
     */
    // Constructor demonstrating constructor chaining
    public Person() {
        this("", "", "", "", 0, "", "");
    }
    
    /**
     * Instantiates a new person.
     *
     * @param id the id
     * @param name the name
     * @param email the email
     * @param phone the phone
     * @param age the age
     * @param gender the gender
     * @param address the address
     */
    public Person(String id, String name, String email, String phone, int age, String gender, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }
    
    // Getters and Setters - Encapsulation
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    // Abstract method demonstrating abstraction
    public abstract String getRole();
    
    // Equals and HashCode - Important for collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Person person = (Person) o;
        return id != null && id.equals(person.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
