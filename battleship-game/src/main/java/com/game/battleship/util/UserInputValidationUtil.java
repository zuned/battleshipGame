package com.game.battleship.util;

import com.game.battleship.exception.BattleShipRuntimeException;

public final class UserInputValidationUtil {
	
	public static  boolean validateBattleFieldDimenstion(String dimension) {
		if (! dimension.matches(BattleShipConstants.REGULAR_EXPR_BATTLE_FEILD_DIMENSION) ){
			throw new BattleShipRuntimeException("error.invalidInput.BattleFiledDimension", "BattleFeild Dimenstions should be in format [width height].");
		}
		return true;
	}
	
	public static   boolean validateBattleSipDimensions(String dimension) {
		if (! dimension.matches(BattleShipConstants.REGULAR_EXPR_BATTLE_SHIP_DIMENSION) ){
			throw new BattleShipRuntimeException("error.invalidInput.BattleShipDimension", "BattleFeild Dimenstions should be in format [ShipType width height ship location for each player].");
		}
		return true;
	}
	
	public static   boolean validateHitCoordinates(String dimension) {
		if (! dimension.matches(BattleShipConstants.REGULAR_EXPR_HIT_COORDINATES_DIMENSION) ){
			throw new BattleShipRuntimeException("error.invalidInput.hitCoordinates", "Invalid Hit Coordinates, Valid Formate is height[A-Z]width[1-9] followed by space");
		}
		return true;
	}

}
