package oberon.model.players.packets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException; 

import oberon.Config;
import oberon.Connection;
import oberon.Server;
import oberon.model.items.ItemAssistant;
import oberon.model.players.Client;
import oberon.model.players.PacketType;
import oberon.model.players.Player;
import oberon.model.players.PlayerHandler;
import oberon.util.Misc;
import oberon.world.WorldMap;

/**
 * Commands
 **/
public class Commands implements PacketType 
{

    
    @Override
    public void processPacket(Client c, int packetType, int packetSize) 
    {
    String playerCommand = c.getInStream().readString();
    if (Config.SERVER_DEBUG)
        Misc.println(c.playerName+" playerCommand: "+playerCommand);
    
    if (c.playerRights >= 0)
        playerCommands(c, playerCommand);
    if (c.playerRights >= 1)
        moderatorCommands(c, playerCommand);
    if	(c.playerRights >= 2)
        administratorCommands(c, playerCommand);
    if	(c.playerRights >= 3)
        ownerCommands(c, playerCommand);
    }
        
    public void playerCommands(Client c, String playerCommand)
    {
    	if(Config.SERVER_DEBUG)
    		Misc.println(c.playerName+" playerCommand: "+playerCommand);
    		if (playerCommand.startsWith("/") && playerCommand.length() > 3) {
    			if (c.clanId >= 0) {
    				System.out.println(playerCommand);
    				playerCommand = playerCommand.substring(1);
    				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
    			} else {
    				if (c.clanId != -1)
    					c.clanId = -1;
    				if (c.playerRights < 1)
    					c.sendMessage("You are not in a clan.");
    			}
    			return;
    	}
    	if (playerCommand.startsWith("empty")) {
    		c.getPA().handleEmpty();
    		}
    	if (playerCommand.startsWith("changepass") && playerCommand.length() > 15) {
			c.playerPass = playerCommand.substring(15);
			c.sendMessage("Your password is now: " + c.playerPass);			
		}
    	if (playerCommand.equalsIgnoreCase("players")) {
			c.sendMessage("There are currently "+PlayerHandler.getPlayerCount()+ " players online.");
		}
    	if (playerCommand.startsWith("commands")) { //change name to whatever, info, donate etc.
			c.commands();
		}
    	if (playerCommand.startsWith("shops")) {
    		c.getPA().spellTeleport(2879, 10198, 1);
    	}
    	if (playerCommand.startsWith("forums")) {
    		c.getPA().sendFrame126("www.oberonrsps.com", 12000);
    	}
    }
    
    /*M O D E R A T O R*/
    public void moderatorCommands(Client c, String playerCommand)
    {
    	if (playerCommand.startsWith("xteleto")) {
			String name = playerCommand.substring(8);
			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
				if (Server.playerHandler.players[i] != null) {
					if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
						c.getPA().movePlayer(Server.playerHandler.players[i].getX(), Server.playerHandler.players[i].getY(), Server.playerHandler.players[i].heightLevel);
					}
				}
			}
    	}
    	String[] yellTitle = { "@mag@Player@bla@", "@blu@Moderator@bla@", "@yel@Admin@bla@", "@dre@Developer@bla@" };
    	if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
    		for (int j = 0; j < Server.playerHandler.players.length; j++) {
    		Client all = (Client)Server.playerHandler.players[j];
    		if (Server.playerHandler.players[j] != null) {
    				all.sendMessage("[" + yellTitle[c.playerRights] + "] " + Misc.optimizeText(c.playerName) + ":@blu@ " + playerCommand.substring(1));
    			}
    		}
    	}
    	if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') { // use as ::ban name
			try {	
				String playerToBan = playerCommand.substring(4);
				Connection.addNameToBanList(playerToBan);
				Connection.addNameToFile(playerToBan);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Server.playerHandler.players[i].disconnected = true;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("unban")) {
			try {	
				String playerToBan = playerCommand.substring(6);
				Connection.removeNameFromBanList(playerToBan);
				c.sendMessage(playerToBan + " has been unbanned.");
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("mute")) {
			try {	
				String playerToBan = playerCommand.substring(5);
				Connection.addNameToMuteList(playerToBan);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client)Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName);
							break;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}			
		}
		if (playerCommand.startsWith("unmute")) {
			try {	
				String playerToBan = playerCommand.substring(7);
				Connection.unMuteUser(playerToBan);
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}			
		}
		if (playerCommand.equalsIgnoreCase("mypos")) {
			c.sendMessage("X: "+c.absX);
			c.sendMessage("Y: "+c.absY);
		}
		if (playerCommand.startsWith("tmute") && c.playerRights >= 1 && c.playerRights <= 3) {
			
			try {	
				String[] args = playerCommand.split("-");
                                        if(args.length < 2) {
                                            c.sendMessage("Currect usage: ::tmute-playername-time");
                                            return;
                                        }
                                        String playerToMute = args[1];
                                        int muteTimer = Integer.parseInt(args[2])*1000;

				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMute)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName+" for "+muteTimer/1000+" seconds");
                                                                c2.muteEnd = System.currentTimeMillis()+ muteTimer;
							break;
						} 
					}
				}
                                        
                                                                                     		
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
		}			
	}
}
    
    /*A D M I N I S T R A T O R*/
    public void administratorCommands(Client c, String playerCommand)
    {    
    	if (playerCommand.startsWith("anim")) {
			String[] args = playerCommand.split(" ");
			c.startAnimation(Integer.parseInt(args[1]));
			c.getPA().requestUpdates();
		}
    	if (playerCommand.startsWith("tele")) {
			String[] arg = playerCommand.split(" ");
			if (arg.length > 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
			else if (arg.length == 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
		}
    	if (playerCommand.startsWith("object")) {
			String[] args = playerCommand.split(" ");				
			c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
		}
    	if (playerCommand.startsWith("master")) {
			int i = 0;		
			c.getPA().addSkillXP((15000000), 0);
			c.getPA().addSkillXP((15000000), 1);
			c.getPA().addSkillXP((15000000), 2);
			c.getPA().addSkillXP((15000000), 3);
			c.getPA().addSkillXP((15000000), 4);
			c.getPA().addSkillXP((15000000), 5);
			c.getPA().addSkillXP((15000000), 6);
			c.getPA().addSkillXP((15000000), 7);
			c.getPA().addSkillXP((15000000), 8);
			c.getPA().addSkillXP((15000000), 9);
			c.getPA().addSkillXP((15000000), 10);
			c.getPA().addSkillXP((15000000), 11);
			c.getPA().addSkillXP((15000000), 12);
			c.getPA().addSkillXP((15000000), 13);
			c.getPA().addSkillXP((15000000), 14);
			c.getPA().addSkillXP((15000000), 15);
			c.getPA().addSkillXP((15000000), 16);
			c.getPA().addSkillXP((15000000), 17);
			c.getPA().addSkillXP((15000000), 18);
			c.getPA().addSkillXP((15000000), 19);
			c.getPA().addSkillXP((15000000), 20);
			c.getPA().addSkillXP((15000000), 21);
			c.playerXP[3] = c.getPA().getXPForLevel(99)+5;
			c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
		}
    	if (playerCommand.startsWith("level")) {
            try {
                    String[] args = playerCommand.split(" ");
                    if (args.length == 3) {
                            int skill = Integer.parseInt(args[1]);
                            int level = Integer.parseInt(args[2]);
                            if ((skill <= 99) && (skill >= 0)) {
                            		c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
                            		c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
                                    c.getPA().refreshSkill(skill);      
                            }
                    }
            } catch (Exception e) {   
            }
    }
    	if (playerCommand.startsWith("ipban")) { // use as ::ipban name
			try {
				String playerToBan = playerCommand.substring(6);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
							Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
							c.sendMessage("You have IP banned the user: "+Server.playerHandler.players[i].playerName+" with the host: "+Server.playerHandler.players[i].connectedFrom);
							Server.playerHandler.players[i].disconnected = true;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("ipmute")) {
			try {	
				String playerToBan = playerCommand.substring(7);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
							c.sendMessage("You have IP Muted the user: "+Server.playerHandler.players[i].playerName);
							Client c2 = (Client)Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName);
							break;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}			
		}
		if (playerCommand.startsWith("unipmute")) {
			try {	
				String playerToBan = playerCommand.substring(9);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.unIPMuteUser(Server.playerHandler.players[i].connectedFrom);
							c.sendMessage("You have Un Ip-Muted the user: "+Server.playerHandler.players[i].playerName);
							break;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}			
		}	
		if (playerCommand.startsWith("item")) {
			if (c.inWild())
				return;
			try {
			String[] args = playerCommand.split(" ");
			if (args.length == 3) {
				int newItemID = Integer.parseInt(args[1]);
				int newItemAmount = Integer.parseInt(args[2]);
				if ((newItemID <= 20000) && (newItemID >= 0)) {
					c.getItems().addItem(newItemID, newItemAmount);
					System.out.println("Spawned: " + newItemID + " by: " + c.playerName);
				} else {
					c.sendMessage("No such item.");
				}
			} else {
				c.sendMessage("Use as ::item 995 200");
			}
			} catch (Exception e) {
			
			}
    	}
    }
    
    public void ownerCommands(Client c, String playerCommand)
    {
    	if(playerCommand.equalsIgnoreCase("npcreset") && c.playerRights > 0){
            for (int i = 0; i < Server.npcHandler.maxNPCs; i++) 

            {
            	if (Server.npcHandler.npcs[i] != null) {
		            Server.npcHandler.npcs[i].isDead = true;
		            Server.npcHandler.npcs[i].actionTimer = 0;
				}
			}

		}
    	if(playerCommand.startsWith("npc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(4));
				if(newNPC > 0) {
					Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
					c.sendMessage("You spawn a Npc.");
				} else {
					c.sendMessage("No such NPC.");
				}
			} catch(Exception e) {
				
			}			
		}
    	if (playerCommand.startsWith("reloadshops")) {
			Server.shopHandler = new oberon.world.ShopHandler();
		}
		if (playerCommand.startsWith("fakels")) {
			int item = Integer.parseInt(playerCommand.split(" ")[1]);
			Server.clanChat.handleLootShare(c, item, 1);
		}
		if(playerCommand.startsWith("getid")) {
			String a[] = playerCommand.split(" ");
			String name = "";
			int results = 0;
			for(int i = 1; i < a.length; i++)
				name = name + a[i]+ " ";
			name = name.substring(0, name.length()-1);
			c.sendMessage("Searching: " + name);
			for (int j = 0; j < Server.itemHandler.ItemList.length; j++) {
				if (Server.itemHandler.ItemList[j] != null)
					if (Server.itemHandler.ItemList[j].itemName.replace("_", " ").toLowerCase().contains(name.toLowerCase())) {
						c.sendMessage("@blu@" 
								+ Server.itemHandler.ItemList[j].itemName.replace("_", " ") 
								+ " - " 
								+ Server.itemHandler.ItemList[j].itemId);
						results++;
					}
			}
			c.sendMessage(results + " results found...");
		}
		if (playerCommand.startsWith("interface")) {
			String[] args = playerCommand.split(" ");
			c.getPA().showInterface(Integer.parseInt(args[1]));
		}
		if (playerCommand.startsWith("gfx")) {
			String[] args = playerCommand.split(" ");
			c.gfx0(Integer.parseInt(args[1]));
		}
		if (playerCommand.startsWith("update")) {
			String[] args = playerCommand.split(" ");
			int a = Integer.parseInt(args[1]);
			PlayerHandler.updateSeconds = a;
			PlayerHandler.updateAnnounced = false;
			PlayerHandler.updateRunning = true;
			PlayerHandler.updateStartTime = System.currentTimeMillis();
		}
		if (playerCommand.equals("vote")) {
			for (int j = 0; j < Server.playerHandler.players.length; j++)
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.getPA().sendFrame126("www.oberonrsps.com", 12000);
				}
		}
		if (playerCommand.startsWith("uidban")) {
            try {
                String playerToBan = playerCommand.substring(7);
                for (int i = 0; i < PlayerHandler.players.length; i++) {
                    if (PlayerHandler.players[i] != null) {
                        if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan) && PlayerHandler.players[i].playerRights != 3) {
                            Connection.addUidToBanList(PlayerHandler.players[i].UUID);
                            Connection.addUidToFile(PlayerHandler.players[i].UUID);
                            if (c.playerRights == 3) {
                                c.sendMessage("@red@[" + PlayerHandler.players[i].playerName + "] has been UUID Banned with the UUID: " + PlayerHandler.players[i].UUID);
                            } else {
                                c.sendMessage("@red@[" + PlayerHandler.players[i].playerName + "] has been UUID Banned.");
                            }
                          PlayerHandler.players[i].disconnected = true;
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
		if(playerCommand.startsWith("unuidban")) {
       	 String player = playerCommand.substring(9);
       	 Connection.getUidForUser(c, player);
       }
		if (playerCommand.startsWith("clip")) {
			String filePath = "./src/oberon/world/WalkingCheck/";
		BufferedWriter bw = null;
		try 
			{				
		bw = new BufferedWriter(new FileWriter(filePath, true));
		bw.write("tiles.put("+c.heightLevel+" << 28 | "+c.absX+" << 14 | "+c.absY+", true);");
		bw.newLine();
		bw.flush();
		} 
			catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
		{
			try 
			{
				bw.close();
			} 
			catch (IOException ioe2) 
			{
			}
		}
	}
}
}
}