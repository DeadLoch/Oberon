package oberon.model.players.skills;

import java.util.Calendar;

import oberon.Config;
import oberon.event.Event;
import oberon.event.EventContainer;
import oberon.event.EventManager;
import oberon.model.items.Item;
import oberon.model.players.Client;


public class Farming {

	private Client c;
	private int growthTimer = 300000;
	private int produce, produceAmt, herbXP, stage, patchId;
	private Calendar plantHerbTime, updateHerbTime;
	private long plantHerbMillis, updateHerbMillis;
	private Calendar pickHerbTime = Calendar.getInstance();
	
	public int getPatchId() {
		return patchId;
	}
	
	private enum FarmData {
		
		GUAM(5291, 199, 11, 13, 1),
		MARRENTIL(5292, 201, 14, 15, 14),
		TARROMIN(5293, 203, 16, 18, 19),
		HARRALANDER(5294, 205, 22, 24, 26),
		RANARR(5295, 207, 27, 31, 32),
		TOADFLAX(5296, 3049, 34, 39, 38),
		IRIT(5297, 209, 43, 49, 44),
		AVANTOE(5298, 211, 55, 62, 50),
		KWUARM(5299, 213, 69, 78, 56),
		SNAPDRAGON(5300, 3051, 88, 99, 62),
		CADANTINE(5301, 215, 107, 120, 67),
		LANTADYME(5302, 2485, 135, 152, 73),
		DWARF_WEED(5303, 217, 171, 192, 79),
		TORSTOL(5304, 219, 200, 225, 85);
		
		private int seedId, produce, plantXP, herbXP, req;
		
		private FarmData(int seedId, int produce, int plantXP, int herbXP, int req) {
			this.seedId = seedId;
			this.produce = produce;
			this.plantXP = plantXP;
			this.herbXP = herbXP;
			this.req = req;
		}
		
		public int getSeed() {
			return seedId;
		}
		
		public int getReq() {
			return req;
		}
		
		public int getProduce() {
			return produce;
		}
		
		public int getPlantXP() {
			return plantXP;
		}
		
		public int getHerbXP() {
			return herbXP;
		}
	}
	
	public Farming(Client c) {
		this.c = c;
	}
	
	public FarmData checkSeed(int seed) {
		for (FarmData f : FarmData.values()) {
			if ((c.playerEquipment[c.playerWeapon] == (f.getSeed()) || c.getItems().playerHasItem(seed))) {
				System.out.println("LOL");
				//c.currentAxe = axe.getId(); //<------ you had axe, so it wasn't getting the actual id
            	return f;
			} else {
				c.sendMessage("You need an axe to cut this tree!");
            	return null;
    		}
			/*if (f.getSeed() == seed) {
				return f;
			}*/
		}
		return null;
	}
	
	public void checkSeedOnPatch(int objectId, int itemId) {
		patchId = objectId;
		FarmData f = checkSeed(itemId);
		if (f != null) {
			newHerbPatch(f);
		}
		return;
	}
	
	public Patch choosePatch() {
		for(Patch p : Patch.values()) {
			if(p.getPatchId() == patchId) {
				return p;
			}
		}
		return null;
	}

	public void newHerbPatch(FarmData f){
		pickHerbTime.add(Calendar.SECOND, growthTimer);
		if (c.playerLevel[c.playerFarming] >= f.getReq()) {
			if (c.getItems().playerHasItem(f.getSeed(), 1)) {
				c.getItems().deleteItem(f.getSeed(), c.getItems().getItemSlot(f.getSeed()), 1);
				c.getPA().addSkillXP(f.getPlantXP(), c.playerFarming);
				c.getPA().refreshSkill(c.playerFarming);
				produce = f.getProduce();
				produceAmt = (int) (Math.random()*15+4);
				herbXP = f.getHerbXP();
				updatePatch(choosePatch());
				plantHerbTime = Calendar.getInstance();
				plantHerbMillis = plantHerbTime.getTimeInMillis();
				stage = 0;
				c.sendMessage("You plant the seed.");
			}
		} else {
			c.sendMessage("You need a farming level of "+f.getReq()+" to plant this seed.");
		}
	}
	
	public void updatePatch(final Patch p){
		EventManager.getSingleton().addEvent(new Event() {
			@Override
	        public void execute(EventContainer container) {
				updateHerbTime = Calendar.getInstance();
				updateHerbMillis = updateHerbTime.getTimeInMillis();
				if (produceAmt > 0) {
					if(stage == 0 && updateHerbMillis >= plantHerbMillis){
						c.getPA().checkObjectSpawn(8139, p.getPatchX(), p.getPatchY(), 1, 10);
						plantHerbMillis += growthTimer*1/4;
						stage = 1;
					} else if(stage == 1 && updateHerbMillis >= plantHerbMillis){
						c.getPA().checkObjectSpawn(8140, p.getPatchX(), p.getPatchY(), 1, 10);
						plantHerbMillis += growthTimer*1/4;
						stage = 2;
					} else if(stage == 2 && updateHerbMillis >= plantHerbMillis){
						c.getPA().checkObjectSpawn(8141, p.getPatchX(), p.getPatchY(), 1, 10);
						plantHerbMillis += growthTimer*1/4;
						stage = 3;
					} else if(stage == 3 && updateHerbMillis >= plantHerbMillis){
						c.getPA().checkObjectSpawn(8142, p.getPatchX(), p.getPatchY(), 1, 10);
						plantHerbMillis += growthTimer*1/4;
						stage = 4;
					} else if(stage == 4 && updateHerbMillis >= plantHerbMillis){
						c.getPA().checkObjectSpawn(8143, p.getPatchX(), p.getPatchY(), 1, 10);
						container.stop();
					}
				}
	        }
		}, 1000);
	}
	
	public void finishedHerbPatch(Patch p){
		if (produceAmt > 0) {
			if (c.getItems().freeSlots() > 0) {
				c.getItems().addItem(produce, 1);
				c.getPA().addSkillXP(herbXP, c.playerFarming);
				produceAmt--;
				c.sendMessage("You pick a herb.");
			} else {
				c.sendMessage("You do not have enough inventory space to pick this herb.");
			}
		} else {
			c.getPA().checkObjectSpawn(p.getPatchId(), p.getPatchX(), p.getPatchY(), 1, 10);
		}
	}
}
