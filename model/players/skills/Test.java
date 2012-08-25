package oberon.model.players.skills;

import java.util.HashMap;
import java.util.Map;

import oberon.model.players.Client;


public class Test {
	/**
     * Represents types of axes.
     * 
     * @author Graham Edgecombe
     * 
     */
    private enum Axe {

            /**
             * Rune axe.
             */
            RUNE(1359, 41, 867),

            /**
             * Adamant axe.
             */
            ADAMANT(1357, 31, 869),

            /**
             * Mithril axe.
             */
            MITHRIL(1355, 21, 871),

            /**
             * Black axe.
             */
            BLACK(1361, 6, 873),

            /**
             * Steel axe.
             */
            STEEL(1353, 6, 875),

            /**
             * Iron axe.
             */
            IRON(1349, 1, 877),

            /**
             * Bronze axe.
             */
            BRONZE(1351, 1, 879);

            /**
             * The id.
             */
            private int id;

            /**
             * The level.
             */
            private int level;

            /**
             * The animation.
             */
            private int animation;

            /**
             * Creates the axe.
             * 
             * @param id
             *            The id.
             * @param level
             *            The required level.
             * @param animation
             *            The animation id.
             */
            private Axe(int id, int level, int animation) {
                    this.id = id;
                    this.level = level;
                    this.animation = animation;
            }

            /**
             * Gets the id.
             * 
             * @return The id.
             */
            public int getId() {
                    return id;
            }

            /**
             * Gets the required level.
             * 
             * @return The required level.
             */
            public int getRequiredLevel() {
                    return level;
            }

            /**
             * Gets the animation id.
             * 
             * @return The animation id.
             */
            public int getAnimation() {
                    return animation;
            }
    }
    
    public Axe whichAxe(int axeId){
    	for(Axe a : Axe.values()){
    		if(a.getId() == axeId){
    			return a;
    		}
    	}
    	return null;
    }
    
    public void woodCut(final Client c, Axe a){
    	if (c.playerEquipment[c.playerWeapon] == (a.getId()) || c.getItems().playerHasItem(a.getId())) {
    		//Woodcut
    	} else {
    		c.sendMessage("You need an axe to cut this tree!");
    	}
    }
}
