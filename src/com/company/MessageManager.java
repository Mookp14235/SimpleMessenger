package com.company;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MessageManager
{

    public static MessageManager getInstance()
    {
        MessageManager messageManager = new MessageManager();
        return messageManager;
    }

    public String getdate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public void displayMessage(Message message)
    {
        System.out.println("\nSender :"+message.getSender());
        System.out.println("Recevier :"+message.getReceiver());
        System.out.println("Subject :"+message.getSubject());
        System.out.println("Body :\n"+message.getBody());
        System.out.println("Date :"+message.getDate());
    }

    public Message createMessage()
    {
        String text = "";
        String sender = AccountManger.user.getEmail();
        String recevier = AccountManger.searchUser();
        if (recevier != null)
        {
            String subject = IOUtils.getString("Subject : ");
            System.out.println("text ( Stop > 'exit' ):");
            while (true)
            {
                String line = IOUtils.getString("> ");
                if (line.equalsIgnoreCase("exit"))
                {
                    break;
                }
                text = text + line + "\n";
            }
            Message newMessage = new Message(sender, recevier, subject, text, getdate());
            return newMessage;
        }
        return null;
    }

    public void sendMessage(Message message) 
    {
        boolean sendStatus = DBconnection.addMessage(message);
        if(!sendStatus)
        {
            System.out.println("Can not send message. Please check your internet.");
        }
    }

    public void readMessage(Message message)
    {
        if(message == null)
        {
            System.out.println("Can not read this message...");
        }
        else
        {
            boolean check = DBconnection.updateMessage(message,1);
            if(check)
            {
                displayMessage(message);
            }
            else 
            {
                System.out.println("Can not open this message. Please check your internet.");
            }
        }
    }

    public void replyMessage(String sender, String receiver, String sub)
    {
        String text = "";
        while (true)
        {
            String line = IOUtils.getString("> ");
            if (line.equalsIgnoreCase("exit"))
            {
                break;
            }
            text = text + line + "\n";
        }
        sendMessage(new Message(sender,receiver,sub,text,getdate()));
    }

    public void deleteMessage(Message message)
    {
        boolean deleteStatus = false;
        if (message == null)
        {
            System.out.println("Can not delete this message.");
        }
        else
        {
            deleteStatus = DBconnection.updateMessage(message,0);
            if(!deleteStatus)
            {
                System.out.println("Can not open this message. Please check your internet.");
            }
        }
    }
}
