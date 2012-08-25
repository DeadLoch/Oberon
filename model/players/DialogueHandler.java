package oberon.model.players;

import oberon.Server;

public class DialogueHandler {

	private Client c;
	
	public DialogueHandler(Client client) {
		this.c = client;
	}
	
	/**
	 * Handles all talking
	 * @param dialogue The dialogue you want to use
	 * @param npcId The npc id that the chat will focus on during the chat
	 */
	 
	 	public void clearChatTypes() {
		c.normalChat = false;
		c.quest1Chat = false;
	}
	
	  	public void sendQuestDialogue(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		clearChatTypes();
		c.quest1Chat = true;
		switch(dialogue) {
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 1:
			sendNpcChat4("Aww man...", "I'm having another one of those days...", "Sometimes life just shits a brick", 
			"and throws it at your face!", c.talkingNpc, "Hans");
			c.nextChat = 0;
			break;
		}
	}
	
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		clearChatTypes();
		c.normalChat = true;
		switch(dialogue) {
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 1:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			c.dialogueAction = 1;
			c.nextChat = 2;
			break;
		case 2:
			sendOption2("Yea! I'm fearless!",  "No way! That looks scary!");
			c.dialogueAction = 1;
			c.nextChat = 0;
			break;
		case 3:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 4;
		break;
		case 5:
			sendNpcChat4("Hello adventurer...", "My name is Kolodion, the master of this mage bank.", "Would you like to play a minigame in order ", 
						"to earn points towards recieving magic related prizes?", c.talkingNpc, "Kolodion");
			c.nextChat = 6;
		break;
		case 6:
			sendNpcChat4("The way the game works is as follows...", "You will be teleported to the wilderness,", 
			"You must kill mages to recieve points,","redeem points with the chamber guardian.", c.talkingNpc, "Kolodion");
			c.nextChat = 15;
		break;
		case 11:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 12;
		break;
		case 12:
			sendOption2("Yes I would like a slayer task.", "No I would not like a slayer task.");
			c.dialogueAction = 5;
		break;
		case 13:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I see I have already assigned you a task to complete.", 
			"Would you like me to give you an easier task?", c.talkingNpc, "Duradel");
			c.nextChat = 14;
		break;
		case 14:
			sendOption2("Yes I would like an easier task.", "No I would like to keep my task.");
			c.dialogueAction = 6;
		break;
		case 15:
			sendOption2("Yes I would like to play", "No, sounds too dangerous for me.");
			c.dialogueAction = 7;
		break;
		case 16:
			sendOption2("I would like to reset my barrows brothers.", "I would like to fix all my barrows");
			c.dialogueAction = 8;
		break;
		case 17:
			sendOption5("Air", "Mind", "Water", "Earth", "More");
			c.dialogueAction = 10;
			c.dialogueId = 17;
			c.teleAction = -1;
		break;
		case 18:
			sendOption5("Fire", "Body", "Cosmic", "Astral", "More");
			c.dialogueAction = 11;
			c.dialogueId = 18;
			c.teleAction = -1;
		break;
		case 19:
			sendOption5("Nature", "Law", "Death", "Blood", "More");
			c.dialogueAction = 12;
			c.dialogueId = 19;
			c.teleAction = -1;
		break;
		case 20:
			sendNpcChat4("My goodness, these robes are hot - ", "Oh, hello! I didn't see", "you there. Would you like to", "purchase some magical goods?",c.talkingNpc, "Aubury");
			c.nextChat = 21;
		break;
		case 21:
			sendOption2("Yes, I need that extra magic bonus.","No, I'll think I stick with my current equipment.");
			c.dialogueAction = 13;
		break;
		case 22:
			sendNpcChat4("Hello there young adventurer!","I have too much armor to hold.","Would you help a man out","by purchasing some equipment?",c.talkingNpc, "Horvik");
			c.nextChat = 23;
		break;	
		case 23:
			sendOption2("Sure, I'm in need of better armour.","No, your armour is hard, but mine is harder!");
			c.dialogueAction = 14;
		break;	
		case 24:
			sendNpcChat4("Looking to kill an enemy from a distance?","I think I may be able to help you out.","Would you like to browse through my wares,","and possibly purchase an item?",c.talkingNpc, "Archie");
			c.nextChat = 25;
		break;
		case 25:
			sendOption2("Yes, I want to pierce the heart of my enemy from a distance.","No, I prefer my enemies within hands' reach.");
			c.dialogueAction = 15;
		break;
		case 26:
			sendNpcChat2("Okay, but if you are interested in looking at weapons,","you should check with my friend Zeke.",c.talkingNpc, "Horvik");
			c.nextChat = 0;
		break;	
		case 27:
			sendNpcChat4("Hello there my friend!","I see your weapon is getting dull.","Would you consider getting","a new weapon from my shop?",c.talkingNpc, "Zeke");
			c.nextChat = 28;
		break;	
		case 28:
			sendOption2("Sure, let me see what all you have in stock.","No, my weapon is sharp as razor!");
			c.dialogueAction = 16;
		break;	
		case 29:
			sendNpcChat2("Hello there young adventurer!","Would you like to change your appearance?",c.talkingNpc, "Makeover Mage");
			c.nextChat = 30;
		break;
		case 30:
			sendOption2("Yes, I don't like my current appearance.","No, I look fine.");
			c.dialogueAction = 17;
		break;
		case 31:
			sendNpcChat2("Hey man, you are looking a little shifty.","I'll take those 'special' items off of your hands.",c.talkingNpc, "Sigmund");
			c.nextChat = 32;
		break;
		case 32:
			sendOption2("Yeh, these don't belong to me, anyways.","No, I don't want to get any trouble started.");
			c.dialogueAction = 18;
		break;	
		case 33:
			sendOption5("Slayer Tower","Taverly","Brimhaven","Lumbridge Cave","More");
			c.dialogueAction = 19;
			c.dialogueId = 33;
			c.teleAction = -1;
		break;	
		case 34:
			sendOption5("Relekka Cave","Back","","","");
			c.dialogueAction = 20;
			c.dialogueId = 34;
			c.teleAction = -1;
		break;
		case 35:
			sendNpcChat2("You should check out Oberon!","I hear it is going to be @dre@ A W E S O M E!",c.talkingNpc, "Sergeant Damien");
			c.dialogueAction = 21;
		break;	
		case 36:
			sendNpcChat2("Hello there young one, a nice day isn't it?","Would you be interested in buying some general goods?",c.talkingNpc, "Shopkeeper");
			c.nextChat = 37;
		break;	
		case 37:
			sendOption2("Yes, I'm in need of general ware.","No, have a nice day.");
			c.dialogueAction = 22;
		break;	
		case 38:
			sendOption5("Barrows","Pest Control","Fight Caves","Duel Arena","Click here");
			c.dialogueAction = 23;
			c.dialogueId = 38;
			c.teleAction = -1;
		break;
		case 39:
			sendNpcChat4("Hello!", "My name is Mazchna and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Mazchna");
			c.nextChat = 12;
		break;
		case 40:
			sendNpcChat4("Hello!", "My name is Mazchna and I am a master of the slayer skill.", "I see I have already assigned you a task to complete.", 
			"Would you like me to give you an easier task?", c.talkingNpc, "Mazchna");
			c.nextChat = 14;
		break;
		case 129:
			sendOption5("@red@Please choose an experience rate:", "Rookie", "Knight", "Elite", "@red@You will be frozen until you choose a rate.");
			c.dialogueAction = 28;
			c.dialogueId = 39;
		break;
		}
	}
	
	/*
	 * Information Box
	 */
	
	public void sendStartInfo(String text, String text1, String text2, String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}
	
	/*
	 * Options
	 */
	
	private void sendOption(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2470);
	 	c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126("Click here to continue", 2473);
		c.getPA().sendFrame164(13758);
	}	
	
	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}
	
	private void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame126(s2, 2462);
		c.getPA().sendFrame164(2459);
	}
	
	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}
	
	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	/*
	 * Statements
	 */
	
	public void sendStatement(String s) { // 1 line click here to continue chat box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}
	
	/*
	 * Npc Chatting
	 */
	
	private void sendNpcChat1(String s) {
		
	}
	
	public void sendNpcChat1(String s, int ChatNpc, String name) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(name, 4884);
		c.getPA().sendFrame126(s, 4885);
		c.getPA().sendFrame75(ChatNpc, 4883);
		c.getPA().sendFrame164(4882);
	}

	public void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		c.getPA().sendFrame200(4888, 591);
		c.getPA().sendFrame126(name, 4889);
		c.getPA().sendFrame126(s, 4890);
		c.getPA().sendFrame126(s1, 4891);
		c.getPA().sendFrame75(ChatNpc, 4888);
		c.getPA().sendFrame164(4887);
	}

	public void sendNpcChat3(String s, String s1, String s2, int ChatNpc, String name) {
		c.getPA().sendFrame200(4894, 591);
		c.getPA().sendFrame126(name, 4895);
		c.getPA().sendFrame126(s, 4896);
		c.getPA().sendFrame126(s1, 4897);
		c.getPA().sendFrame126(s2, 4898);
		c.getPA().sendFrame75(ChatNpc, 4894);
		c.getPA().sendFrame164(4893);
	}
	
	private void sendNpcChat4(String s, String s1, String s2, String s3, int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}
	
	/*
	 * Player Chating Back
	 */
	
	
	private void sendPlayerChat1(String s) {
		c.getPA().sendFrame200(969, 591);
		c.getPA().sendFrame126(c.playerName, 970);
		c.getPA().sendFrame126(s, 971);
		c.getPA().sendFrame185(969);
		c.getPA().sendFrame164(968);
	}
	
	private void sendPlayerChat2(String s, String s1) {
		c.getPA().sendFrame200(974, 591);
		c.getPA().sendFrame126(c.playerName, 975);
		c.getPA().sendFrame126(s, 976);
		c.getPA().sendFrame126(s1, 977);
		c.getPA().sendFrame185(974);
		c.getPA().sendFrame164(973);
	}
	
	private void sendPlayerChat3(String s, String s1, String s2) {
		c.getPA().sendFrame200(980, 591);
		c.getPA().sendFrame126(c.playerName, 981);
		c.getPA().sendFrame126(s, 982);
		c.getPA().sendFrame126(s1, 983);
		c.getPA().sendFrame126(s2, 984);
		c.getPA().sendFrame185(980);
		c.getPA().sendFrame164(979);
	}
	
	private void sendPlayerChat4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame200(987, 591);
		c.getPA().sendFrame126(c.playerName, 988);
		c.getPA().sendFrame126(s, 989);
		c.getPA().sendFrame126(s1, 990);
		c.getPA().sendFrame126(s2, 991);
		c.getPA().sendFrame126(s3, 992);
		c.getPA().sendFrame185(987);
		c.getPA().sendFrame164(986);
	}
}
