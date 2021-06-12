package com.company;

import java.util.ArrayList;

public class Folder
{
    public static ArrayList<Message> allMessage;
    protected  ArrayList<Message> messagesFolder = new ArrayList<Message>();

    public Folder()
    {
        updateAllMessage();
    }

    protected void display()
    {
        int i = 1;
        updateArrayMessage();
        System.out.println("\nList message :");
        if(messagesFolder.size() > 0)
        {
            for (Message message : messagesFolder) 
            {
                System.out.println(i + " " + message.getSubject() + " " + message.getDate()+" "+message.getSender()+" "+message.getCurrentFolder());
                i++;
            }
        }
        else
        {
            System.out.println("      No have message.");
        }
    }

    public static void updateAllMessage()
    {
        allMessage = DBconnection.getMessage();
    }

    protected void updateArrayMessage() 
    {
        messagesFolder = DBconnection.getMessage();
    }

    protected Message getMessage(int index)
    {
        Message message = null;
        if (messagesFolder.size() > index && index  >= 0)
        {
            message = messagesFolder.get(index);
        }
        else
        {
            System.out.println("Error to get this message.");
        }
        return message;
    }

    protected static void clearFolder(Folder folder)
    {
        folder.messagesFolder.clear();
    }
}
