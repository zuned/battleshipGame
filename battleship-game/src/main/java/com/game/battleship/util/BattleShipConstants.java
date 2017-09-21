package com.game.battleship.util;

public class BattleShipConstants {

	public static final int NUMBER_OF_PLAYERS = 2;
	public static final String REGULAR_EXPR_BATTLE_FEILD_DIMENSION = "^([1-9] [A-Z])$";
	public static final String REGULAR_EXPR_BATTLE_SHIP_DIMENSION = "(^[PQ]{1}) ([1-9] [1-9]){1}(\\s[A-Z][1-9]){2,}$";
	public static final String REGULAR_EXPR_HIT_COORDINATES_DIMENSION = "^[A-Z][1-9](\\s[A-Z][1-9])*$";
}
