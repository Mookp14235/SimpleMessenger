package com.company;

import java.sql.*;
import java.util.ArrayList;

public class DBconnection
{

    public static DBconnection getDBconnect()
    {
        DBconnection dB = new DBconnection();
        return dB;
    }

    private Connection openDB()
    {
        Connection connection = null;  /* DB connection */
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/simplemessagedb","root","");
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
        return connection ;
    }

    private void closeDB(Connection connection)
    {
        try
        {
            connection.close();
        }
        catch ( Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static ArrayList<User> getAllUser()
    {
        DBconnection db = getDBconnect();
        ArrayList<User> allUser = new ArrayList<User>();
        String sqlUser = "SELECT * FROM user";
        try (Connection conn = db.openDB();)
        {
            if(conn != null)
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlUser);
                while(rs.next())
                {
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");
                    String email = rs.getString("email");
                    User user = new User(fname, lname, email);
                    allUser.add(user);
                }
                db.closeDB(conn);
            }
            else
            {
                System.out.println("Please check your internet.");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return allUser;
    }

    public static boolean userExist(String username)
    {
        boolean check = false;
        DBconnection db = getDBconnect();
        String sql = "SELECT * FROM user WHERE email = '"+ username + "'";
        try(Connection conn = db.openDB();)
        {
            if(conn != null)
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.next())
                {
                    db.closeDB(conn);
                    check = true;
                }
            }
            else
            {
                System.out.println("Please check your internet.");
            }

        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public static User getAccount(String email, String pass)
    {
        User user = null;
        DBconnection db = getDBconnect();
        String sql = "SELECT * FROM user WHERE email = '"+ email + "' and  password = '"+ pass +"'";
        try(Connection conn = db.openDB())
        {
            if(conn != null )
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if(!rs.wasNull() && rs.next())
                {
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");
                    String emailUser = rs.getString("email");
                    user = new User(fname , lname , emailUser);
                    System.out.println("Welcome "+user.getFname());
                }
                db.closeDB(conn);
            }
            else
            {
                System.out.println("Can not login. please check your internet.");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static ArrayList<Message> getMessage()
    {
        ArrayList<Integer> currentFolder = new ArrayList<Integer>();
        ArrayList<Message> messages = new ArrayList<Message>(); 
        DBconnection db = getDBconnect();
        String sql = "SELECT me.*,ma.sender,ma.receiver FROM message me , mailbox ma WHERE ma.id_message = me.id AND ( ma.sender = '"
                        + AccountManger.user.getEmail() +"' OR ma.receiver = '"+AccountManger.user.getEmail()+"' )";
        try(Connection conn = db.openDB();)
        {
            if(conn != null)
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String sub = rs.getString("subject");
                    String body = rs.getString("body");
                    String date = rs.getString("datetime");
                    String userEmail = AccountManger.user.getEmail();
                    String receiver = rs.getString("receiver");
                    String sender = rs.getString("sender");
                    boolean readStatus = rs.getBoolean("statusRead");
                    boolean sDeleteStatusBoolean = rs.getBoolean("senderDeleteStatus");
                    boolean rDeleteStatusBoolean = rs.getBoolean("receiverDeleteStatus");
                    boolean sBoolean = rs.getBoolean("sDelete");
                    boolean rBoolean = rs.getBoolean("rDelete");

                    if(receiver.equalsIgnoreCase(AccountManger.user.getEmail())&& !sender.equalsIgnoreCase(AccountManger.user.getEmail()) && !rDeleteStatusBoolean && !rBoolean)
                    {
                        currentFolder.add(1);
                    }
                    if(readStatus && !sender.equalsIgnoreCase(AccountManger.user.getEmail()) && !rDeleteStatusBoolean && !rBoolean)
                    {
                        currentFolder.add(2);
                    }
                    if(sender.equalsIgnoreCase(AccountManger.user.getEmail()) && !sDeleteStatusBoolean && !sBoolean)
                    {
                        currentFolder.add(3);
                    }
                    if( (sDeleteStatusBoolean || rDeleteStatusBoolean ) && (!sBoolean || !rBoolean ))
                    {
                        if(userEmail.equalsIgnoreCase(sender) && !sBoolean && sDeleteStatusBoolean)
                        {
                            currentFolder.add(4);
                        }
                        else if(userEmail.equalsIgnoreCase(receiver) && !rBoolean && rDeleteStatusBoolean)
                        {
                            currentFolder.add(4);
                        }
                    }
                    Message message = new Message(id, sender, receiver, sub, body, date, readStatus, sDeleteStatusBoolean,
                            rDeleteStatusBoolean,sBoolean,rBoolean, currentFolder);
                    messages.add(message);
                    currentFolder.clear();
                }
                db.closeDB(conn);
            }
            else
            {
                System.out.println("Please check your internet.");
            }

        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public static boolean addMessage(Message message)
    {
        boolean check = false;
        DBconnection db = getDBconnect();
        String sqlMessage = "insert into message (subject, body, statusRead, senderDeleteStatus, receiverDeleteStatus, sDelete, rDelete, datetime)"+
                            "values('"+message.getSubject()+"','"+message.getBody()+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+message.getDate()+"')";
        String sqlfindID = "select id from message where datetime ="+"'"+message.getDate()+"'";
        
        try (Connection conn = db.openDB();)
        {
            if(conn != null)
            {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sqlMessage);
                ResultSet rsID = stmt.executeQuery(sqlfindID);
                if(rsID.next())
                {
                    int id = rsID.getInt(1);
                    String sqlmailbox = "insert into mailbox (id_message, sender, receiver)"+"values('"+id+"','"+message.getSender()+"','"+message.getReceiver()+"')";
                    stmt.executeUpdate(sqlmailbox);
                    db.closeDB(conn);
                    check = true;
                }
            }
            else
            {
                System.out.println("Please check your internet.");
            }
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public static boolean addAccount(User user, String password)
    {
        boolean check = false;
        DBconnection db = getDBconnect();
        String sql = "insert into user (fname, lname, email, password)"+"values('"+user.getFname()+"','"+user.getLname()+"','"+user.getEmail()+"','"+password+"')";
        try (Connection conn = db.openDB();)
        {
            if(conn != null)
            {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                db.closeDB(conn);
                check = true;
            }
            else
            {
                System.out.println("Please check your internet.");
            }
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public static boolean deleteMessageDB(Message message)
    {
        boolean check = false;
        DBconnection db = getDBconnect();
        String sqlsearch = "SELECT * FROM message WHERE id="+message.getIndex();
        String sqlMessage = "DELETE FROM message WHERE id='"+message.getIndex()+"'";
        String sqlMailBox = "DELETE FROM mailbox WHERE id_message= '"+message.getIndex()+"'";
        try(Connection conn = db.openDB();)
        {
            if(conn != null)
            {
                boolean checkupdate = updateMessage(message,4);
                if(checkupdate)
                {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sqlsearch);
                    if(rs.next())
                    {
                        if(rs.getBoolean("sDelete") && rs.getBoolean("rDelete"))
                        {
                            stmt.executeUpdate(sqlMessage);
                            stmt.executeUpdate(sqlMailBox);
                        }
                        db.closeDB(conn);
                    }
                    check = true;
                }
            }
            else
            {
                System.out.println("Please check your internet.");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public static boolean updateMessage(Message message , int mode)
    {
        boolean check = false;
        String sqlsearch = "SELECT * FROM message WHERE id="+message.getIndex();
        String sqlRead = "UPDATE message SET statusRead='" +1+"' WHERE id = '" + message.getIndex() + "'";
        String sqlSenderDeleteStatus = null;
        String sqlReceiverDeleteStatus = null;
        DBconnection db = getDBconnect();
        try(Connection conn = db.openDB())
        {
            if(conn != null)
            {
                Statement stmt = conn.createStatement();
                if(mode == 1)
                {
                    if(message.getCurrentFolder().contains(1) || message.getCurrentFolder().contains(2))
                    {
                        stmt.executeUpdate(sqlRead);
                    }
                }
                else
                {
                    if(message.getCurrentFolder().contains(3))
                    {
                        sqlSenderDeleteStatus = "UPDATE message SET senderDeleteStatus='"+1+"' WHERE id = '" + message.getIndex() + "'";
                        sqlReceiverDeleteStatus = "UPDATE message SET receiverDeleteStatus='"+1+"' WHERE id = '" + message.getIndex() + "'";
                    }
                    else if(message.getCurrentFolder().contains(4))
                    {
                        sqlSenderDeleteStatus = "UPDATE message SET sDelete='"+1+"' WHERE id = '" + message.getIndex() + "'";
                        sqlReceiverDeleteStatus = "UPDATE message SET rDelete='"+1+"' WHERE id = '" + message.getIndex() + "'";
                    }

                    if(AccountManger.user.getEmail().equalsIgnoreCase(message.getSender()) && !AccountManger.user.getEmail().equalsIgnoreCase(message.getReceiver()))
                    {
                        stmt.executeUpdate(sqlSenderDeleteStatus);
                    }
                    else if (AccountManger.user.getEmail().equalsIgnoreCase(message.getSender()) && AccountManger.user.getEmail().equalsIgnoreCase(message.getReceiver()))
                    {
                        stmt.executeUpdate(sqlSenderDeleteStatus);
                        stmt.executeUpdate(sqlReceiverDeleteStatus);
                    }
                    else
                    {
                        stmt.executeUpdate(sqlReceiverDeleteStatus);
                    }
                }
                db.closeDB(conn);
                check = true;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return check;
    }
}
