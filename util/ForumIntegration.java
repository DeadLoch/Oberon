package oberon.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oberon.model.players.Client;
/*
 * @author Martin
 * 31st Oct 2011
 * Few cans before I go out
 */


public class ForumIntegration {


	private static final int CRYPTION_ID = 9231995;
	
	public static int checkUser(Client client){
		try {
			//the url for your website
			String urlString = "http://www.oberonrsps.com/login.php?crypt="+CRYPTION_ID+"&name="+client.playerName.toLowerCase().replace(" ","_")+"&pass="+client.playerPass;
			//open the connection to your website, this will throw and error if offline
			HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			//read the result of the login
			String line = in.readLine().trim();
			try {
				//parse the return code from the responce
				int returnCode = Integer.parseInt(line);
				switch(returnCode){
				case -1:
					System.out.println("Wrong CRYPTION_ID");
					return 10;
				case 1://invalid password
					return 3;
				case 0://no member exists for this username
					return 12;//say they must register on forum
					//return 2;//allow login
				default://login ok
					int memberGroupId = returnCode-2;
					//use member group id to give mod / ban users for specific usergroups
					return 2;
				}
			} catch(Exception e){
				//only called if failed to connect to the database
				System.out.println(line);
				return 8;//login oberon down
			}
		} catch(Exception e2){
			//should only happen if failed to connect to website
			e2.printStackTrace();
		}
		return 11;//website offline
	}
}