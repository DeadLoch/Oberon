package oberon.model.players.skills;

public enum Patch {
	
	FALADOR(8150, 3058, 3311),
	CATHERBY(8151, 2813, 3463),
	ARDOUGNE(8152, 2670, 3374),	
	CANAFIS(8153, 3605, 3529);
	
	private int patchId, patchX, patchY;
	
	private Patch(int patchId, int patchX, int patchY) {
		this.patchId = patchId;
		this.patchX = patchX;
		this.patchY = patchY;
	}
	
	public int getPatchId() {
		return patchId;
	}
	
	public int getPatchX() {
		return patchX;
	}
	
	public int getPatchY() {
		return patchY;
	}
}
