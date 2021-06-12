package com.company;

import java.util.ArrayList;

public class BinFolder extends Folder
{
    public BinFolder()
    {
        updateArrayMessage();
    }

    @Override
    public void updateArrayMessage()
    {
        messagesFolder.clear();
        for (Message message : Folder.allMessage) 
        {
            ArrayList<Integer> check = new ArrayList<>(message.getCurrentFolder());
            if(check.contains(4))
            {
                messagesFolder.add(message);
            }
        }
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
            deleteStatus = DBconnection.deleteMessageDB(message);
            if(!deleteStatus)
            {
                System.out.println("Can not delete this message.");
            }
        }
    }
}
