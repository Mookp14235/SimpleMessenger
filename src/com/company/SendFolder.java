package com.company;

import java.util.ArrayList;

public class SendFolder extends Folder
{
    public SendFolder()
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
            if(check.contains(3))
            {
                messagesFolder.add(message);
            }
        }
    }
}
