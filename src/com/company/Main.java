package com.company;
public class Main
{
    /*  */
    public static void main(String[] args)
    {
        MessageManager messageManager = MessageManager.getInstance();
        AccountManger accountManager = AccountManger.getInstance();
        while (true) 
        {
            System.out.println("\n1 login");
            System.out.println("2 register");
            System.out.println("3 Close program");
            int key = IOUtils.getInteger("Select number for working >> ");
            switch (key)
            {
                case 1:
                    boolean check = accountManager.login();
                    if (check)
                    {
                        Folder.updateAllMessage();
                        InboxFolder inbox = new InboxFolder();
                        ReadFolder readFolder = new ReadFolder();
                        SendFolder sendFolder = new SendFolder();
                        BinFolder binFolder = new BinFolder();
                        while (true)
                        {
                            System.out.println("\n1 Create Message");
                            System.out.println("2 read Message");
                            System.out.println("3 logout");
                            key = IOUtils.getInteger("Select number for working >> ");
                            if (key == 1)
                            {
                                Message message = messageManager.createMessage();
                                if (message != null)
                                {
                                    String sendString = IOUtils.getString("send mail ? (y/n)");
                                    if (sendString.equalsIgnoreCase("y"))
                                    {
                                        messageManager.sendMessage(message);
                                    }
                                }
                            }
                            else if (key == 3)
                            {
                                boolean checkLogout = accountManager.logout();
                                if (checkLogout)
                                {
                                    Folder.clearFolder(inbox);
                                    Folder.clearFolder(readFolder);
                                    Folder.clearFolder(sendFolder);
                                    Folder.clearFolder(binFolder);
                                    break;
                                }
                            }
                            else if (key == 2)
                            {
                                while (true)
                                {
                                    Folder.updateAllMessage();
                                    System.out.println("1 Inbox Folder");
                                    System.out.println("2 Read Folder");
                                    System.out.println("3 Send Folder");
                                    System.out.println("4 Bin Folder");
                                    System.out.println("5 back");
                                    int select = IOUtils.getInteger("select (1-5) >>");
                                    if(select == 5)
                                    {
                                        break;
                                    }
                                    switch (select)
                                    {
                                        case 1:
                                            mailManager(inbox, messageManager);
                                            break;
                                        case 2:
                                            mailManager(readFolder, messageManager);
                                            break;
                                        case 3:
                                            mailManager(sendFolder, messageManager);
                                            break;
                                        case 4:
                                            mailManager(binFolder, messageManager);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    String fname = IOUtils.getString("First name : ");
                    String lname = IOUtils.getString("Last name  : ");
                    String email = IOUtils.getString("Email      : ");
                    String password = IOUtils.getString("Password   :");
                    if (fname.isEmpty())
                    {
                        System.out.println("Please input first name");
                        break;
                    }
                    if (lname.isEmpty())
                    {
                        System.out.println("Please input last name");
                        break;
                    }
                    if (email.isEmpty())
                    {
                        System.out.println("Please input email");
                        break;
                    }
                    if (password.isEmpty())
                    {
                        System.out.println("Please input password ");
                        break;
                    }
                    accountManager.register(fname,lname,email,password);
                    break;
                case 3:
                    System.out.println("Good bye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("input is incorrect.");
                    break;
            }
        }
    }


    public static void mailManager(Folder folder, MessageManager messageManager)
    {
        while (true) 
        {
            int index;
            Folder.updateAllMessage();
            folder.display();
            if(folder.messagesFolder.size() <= 0)
            {
                System.out.println("");
                break;
            }

            int select = IOUtils.getInteger("1 - read message\n2 - delete message\n3 - close folder\nselect funtion >>");
            if (select == 3) 
            {
                break;
            }
            else if (select > 3 || select < 0)
            {
                System.out.println("please select 1, 2, or 3.");
                continue;
            }

            String indexString = IOUtils.getString("select index >>");

            try 
            {
                index = Integer.parseInt(indexString);
            } 
            catch (NumberFormatException e) 
            {
                System.out.println(indexString + " is not a valid integer number");
                continue;
            }

            Message message = folder.getMessage(index-1);

            if (message != null) 
            {
                if (select == 1)
                {
                    messageManager.readMessage(message);
                    if(folder.getClass() != BinFolder.class)
                    {
                        String reply = IOUtils.getString("Do you want to reply message? <y/n> : ");
                        if (reply.equalsIgnoreCase("y"))
                        {
                            messageManager.replyMessage(message.getSender(), message.getReceiver(), message.getSubject());
                        }
                    }
                }
                else if (select == 2)
                {
                    if(!message.getCurrentFolder().contains(4))
                        messageManager.deleteMessage(message);
                    else
                        ((BinFolder)folder).deleteMessage(message);
                }
                else 
                {
                    System.out.println("selecting function is incorrect.");
                }
            }
        }
    }
}
