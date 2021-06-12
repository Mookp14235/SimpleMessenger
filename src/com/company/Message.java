package com.company;

import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Message 
{
    private int index = 0;
    private String sender = null;
    private String receiver = null;
    private String subject = null;
    private String body = null;
    private String date = null;
    private boolean statusRead;
    private boolean senderDeleteStatus;
    private boolean receiverDeleteStatus;
    private boolean sDelete;
    private boolean rDelete;
    private ArrayList<Integer> currentFolder; // 1-inbox 2-read 3-send 4-delete

    public Message(String sender, String receiver, String subject, String body , String date )
    {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
        this.date = date;
    }

    public Message(int index, String sender, String receiver, String subject, String body, String date, boolean statusRead, boolean senderDelete, boolean receiverDelete,
                   boolean sDelete, boolean rDelete, ArrayList<Integer> currentFolder)
    {
        this.index = index;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.statusRead = statusRead;
        this.senderDeleteStatus = senderDelete;
        this.receiverDeleteStatus = receiverDelete;
        this.sDelete = sDelete;
        this.rDelete = rDelete;
        this.currentFolder = new ArrayList<Integer>(currentFolder);
    }


    /**
     * @return the index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * @return the subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * @return the sender
     */
    public String getSender()
    {
        return sender;
    }

    /**
     * @return the receiver
     */
    public String getReceiver()
    {
        return receiver;
    }

    /**
     * @return the date
     */
    public String getDate()
    {
        return date;
    }

    /**
     * @return the body
     */
    public String getBody() 
    {
        return body;
    }

    /**
     * @return the read status
     */
    public boolean getStatusRead() 
    {
        return statusRead;
    }

    public void setSubject(String sub)
    {
            this.subject = sub;
    }

    public void setBody() 
    {
        String text = "";
        System.out.println("text ( stop > 'exit' ) :");
        while (true) {
            String line = IOUtils.getString("> ");
            if(line.equalsIgnoreCase("exit"))
            {
                break;
            }
            text = text + line + "\n";
        }
        this.body = text;
    }

    public void setDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.date = dtf.format(now);
    }

    public void setSender(String sender)
    {
            this.sender = sender;
    }

    public void setReceiver(String receiver) 
    {
            this.receiver = receiver;
    }

    public void setStatusRead(boolean statusRead) 
    {
        this.statusRead = statusRead;
    }

    public ArrayList<Integer> getCurrentFolder()
    {
        return currentFolder;
    }

    public void setSenderDeleteStatus(boolean senderDelete) 
    {
        this.senderDeleteStatus = senderDelete;
    }

    public void setReceiverDeleteStatus(boolean receiverDelete) 
    {
        this.receiverDeleteStatus = receiverDelete;
    }

    public boolean getReceiverDeleteStatus() 
    {
        return receiverDeleteStatus;
    }

    public boolean getsenderDeleteStatus() 
    {
        return senderDeleteStatus;
    }

    public boolean getSDelete()
    {
        return  sDelete;
    }

    public boolean getRDelete()
    {
        return rDelete;
    }
}
