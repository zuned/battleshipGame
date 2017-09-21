package com.game.battleship.model;

public class BattleShip {

	private final ShipType shipType;
	private final int xLocationCordinate;
	private final int yLocationCordinate;
	private final int width;
	private final int height;

	private boolean isActive;

	public BattleShip(ShipType shipType, int xLocationCordinate , int yLocationCordinate, 
			int width, int height ) {
		this.shipType = shipType;
		this.xLocationCordinate = xLocationCordinate;
		this.yLocationCordinate = yLocationCordinate;
		this.width = width;
		this.height = height;
		this.isActive = true;
	}

	public ShipType getShipType() {
		return shipType;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getxLocationCordinate() {
		return xLocationCordinate;
	}

	public int getyLocationCordinate() {
		return yLocationCordinate;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isAttachOnMe(int xCoordinate, int yCoordinate) {
		int xMax = this.xLocationCordinate +this.width - 1;
		int yMax = this.yLocationCordinate + this.height - 1;
		boolean isCoordinateLiesInBetween = (this.xLocationCordinate <= xCoordinate &&  xCoordinate <= xMax)&&
		(this.yLocationCordinate <= yCoordinate && yCoordinate <= yMax);
		return isCoordinateLiesInBetween;
	}

}
