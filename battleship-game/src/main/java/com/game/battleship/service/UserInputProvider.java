package com.game.battleship.service;

import java.util.Arrays;
import java.util.LinkedList;

import com.game.battleship.util.BattleShipConstants;

public interface UserInputProvider {

	/**
	 * The output is in format - [width height]
	 * @return
	 */
	String getInputFromUserForDimensionOfBattleField();

	/**
	 * int value for number of battleShip For Each Player
	 * @return
	 */
	int getInputFromUserForNumberOfBattleShip();

	/**
	 * The output should be in format [shipType size[w * h] [yx]] eg: Q 1 1 A1 B2 
	 * @return each battleShip Dimension for Each player
	 */
	String[] getInputFromUserForDimensionOfBattleShip(int numberOfBattleShip);

	/**
	 *  The output should be in format - A1 B2 B2 B3 [Target Locations separated by space =[yx] of player 1 ]
	 * @return
	 */
	String[] getUserInputForHitTargetCoordinate();
	
	
	
	@SuppressWarnings("unchecked") LinkedList<String> attackQueue[] = new LinkedList[BattleShipConstants.NUMBER_OF_PLAYERS];
	
	default  void intializeAttachQueue(String[] attackCoordinates) {
		for(int i =0 ; i < BattleShipConstants.NUMBER_OF_PLAYERS ; i++){
			attackQueue[i] = new LinkedList<>();
			attackQueue[i].addAll(Arrays.asList(attackCoordinates[i].split(" ")));
		}
	}

	default String getHitTargetCoordinate(int attackerIndex) {
		return attackQueue[attackerIndex].size()>0 ? attackQueue[attackerIndex].removeFirst() : null;
	}

	default boolean isGameDraw() {
		for (int i = 0; i < BattleShipConstants.NUMBER_OF_PLAYERS; i++) {
			if (attackQueue[i].size() > 0) {
				return false;
			}
		}
		return true;
	}

}
