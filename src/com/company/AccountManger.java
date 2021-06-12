package com.company;

import java.util.ArrayList;

public class AccountManger 
{
    public static User user;
    /**
     * @param user the user to set
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    public boolean login()
    {
        boolean statusLogin = false;
        while (true) 
        {
            String email = IOUtils.getString("Email (exit : -1) : ");
            if(email.equalsIgnoreCase("-1"))
            {
                break;
            }
            String password = IOUtils.getString("Password : ");
            User user = DBconnection.getAccount(email, password);
            if (user == null) 
            {
                System.out.println("Email or password is incorrect.");
            } 
            else 
            {
                setUser(user);
                statusLogin = true;
                break;
            }
        }
        return statusLogin;
    }

    public boolean logout()
    {
        user = null;
        return true;
    }

    public void register(String fname,String lname,String email,String password)
    {
        if(!DBconnection.userExist(email)) 
        {
            User user = new User(fname, lname, email);
            boolean check = DBconnection.addAccount(user, password);
            if (check) 
            {
                System.out.println("register complete.");
            } 
            else 
            {
                System.out.println("register error.");
            }
        }
        else
        {
           System.out.println("This email was used already.");
        }
    }

    public static String searchUser()
    {
        String receiver = null;
        while (true)
        {
            int count = 0;
            ArrayList<User> allEmail = DBconnection.getAllUser();
            ArrayList<User> searchEmail = new ArrayList<User>();
            if(allEmail.size() > 0)
            {
                String keyword = IOUtils.getString("Search receiver (stop > please type exit) : ");
                if (!keyword.equalsIgnoreCase("exit")) 
                {
                    for (User user : allEmail) 
                    {
                        if (user.getEmail().contains(keyword)) 
                        {
                            count++;
                            searchEmail.add(user);
                            System.out.println(count + " " + user.getFname() + " " + user.getEmail());
                        }
                    }
                    if (count != 0)
                    {
                        String indexString = IOUtils.getString("\nSelect receiver index (search again > please type negative integer, stop to cancel create this message > please type 0): ");
                        try 
                        {
                                int index = Integer.parseInt(indexString);
                                if (index == 0) 
                                {
                                    break;
                                }
                                if (index < 0) 
                                {
                                    count = 0;
                                    continue;
                                }
                                if (index - 1 < searchEmail.size()) 
                                {
                                    String receiverString = searchEmail.get(index - 1).getEmail();
                                    searchEmail.clear();
                                    allEmail.clear();
                                    receiver = receiverString;
                                    break;
                                } 
                                else 
                                {
                                    System.out.println("index is incorrect. Please try again.");
                                }
                        } 
                        catch (NumberFormatException e) 
                        {
                            System.out.println(indexString + " is not a valid integer number");
                        }
                    }
                    else
                    {
                        System.out.println("Not have this user");
                    }
                }
                else
                {
                    break;
                }
            }
            else
            {
                System.out.println("Can not search user. Please check your internet.");
                receiver = null;
                break;
            }
        }
        return receiver;
    }

    public static AccountManger getInstance(){
       AccountManger accountManger = new AccountManger();
       return  accountManger;
    }
}
