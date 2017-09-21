package com.game.battleship.model;

import com.game.battleship.util.BattleGameUtil;

public class BattlePlayer{

	private final String playerName;
	private final BattleField battleField;
	public BattlePlayer(String playerName , BattleField battleField) {
		this.playerName = playerName;
		this.battleField = battleField;
	}
	public String getPlayerName() {
		return playerName;
	}
	public BattleField getBattleField() {
		return battleField;
	}
	public boolean isPlayerDead(String lastHitTargetCordinate) {
		char[] hitCoordinate = lastHitTargetCordinate.toCharArray();
		int yCoordinate = hitCoordinate[0] - 'A';
		int xCoordinate = hitCoordinate[1] - 49;
		int deadCount = 0;
		for(BattleShip battleShip : this.battleField.getBattleShips() ){
			if( battleShip.isActive() ){
				if(battleShip.isAttachOnMe(xCoordinate , yCoordinate)){
					BattleGameUtil.updateForLiveOrDead(this.battleField , battleShip);
					if(!battleShip.isActive()){
						deadCount++;
					}
				}
			} else {
				deadCount++;
			}
		}
		return battleField.getBattleShips().size() == deadCount;
	}
}
