package com.game.battleship.model;

import java.util.ArrayList;
import java.util.List;

import com.game.battleship.util.BattleGameUtil;

public class BattleField implements Cloneable {

	private int field[][];
	private final int width;
	private final int height;
	private final List<BattleShip> battleShips =new ArrayList<>();
	
	public BattleField(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int[][] getField() {
		return field;
	}

	public List<BattleShip> getBattleShips() {
		return battleShips;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean addAllBattleShip(List<BattleShip> battleShips){
		this.field = new int[height][width];
		for(BattleShip battleShip : battleShips){
			if(BattleGameUtil.isBattleShipForOverlapping(this, battleShip))
				BattleGameUtil.initializeBattleShipHitCount(this , battleShip);
			else
				return false;
		}
		return this.getBattleShips().addAll(battleShips);
	}
	
	@Override
	public BattleField clone()  {
		return new BattleField(this.width ,this.height);
	}
}
