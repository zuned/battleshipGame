package com.game.battleship.model;

public enum ShipType {

	P(1) , Q(2);
	
	private final int totalAttachShipCanBear;

	private ShipType(int totalAttachShipCanBear) {
		this.totalAttachShipCanBear = totalAttachShipCanBear;
	}
	
	public int getThreashHoldValue() {
		return this.totalAttachShipCanBear;
	}
}
