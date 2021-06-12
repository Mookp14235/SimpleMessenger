package com.company;

import java.util.ArrayList;

public class ReadFolder extends Folder
{
    public ReadFolder()
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
            if(check.contains(2))
            {
                messagesFolder.add(message);
            }
        }
    }
}
