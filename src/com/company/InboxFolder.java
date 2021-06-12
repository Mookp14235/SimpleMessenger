package com.company;

import java.util.ArrayList;

public class InboxFolder extends Folder
{
    public InboxFolder()
    {
        updateArrayMessage();
    }

    @Override
    public void updateArrayMessage()
    {
        messagesFolder.clear();
        for (Message message : Folder.allMessage)
        {
            ArrayList<Integer> check = new ArrayList<Integer>(message.getCurrentFolder());
            if(check.contains(1))
            {
                messagesFolder.add(message);
            }
        }
    }



}
