package com.company;

public class User 
{
    private int id = 0;
    private String fname = null;
    private String lname = null;
    private String email = null;

    public User(int id ,String fname , String lname , String email)
    {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public User( String fname, String lname, String email)
    {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public String getFname()
    {
        return fname;
    }

    public String getLname()
    {
        return lname;
    }

    public String getEmail()
    {
        return email;
    }
}
