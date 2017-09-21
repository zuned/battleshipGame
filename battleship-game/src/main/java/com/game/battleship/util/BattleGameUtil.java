package com.game.battleship.util;

import java.util.ArrayList;
import java.util.List;

import com.game.battleship.exception.BattleShipRuntimeException;
import com.game.battleship.model.BattleField;
import com.game.battleship.model.BattlePlayer;
import com.game.battleship.model.BattleShip;
import com.game.battleship.model.ShipType;

public class BattleGameUtil {

	public static boolean isBattleShipForOverlapping(BattleField battleField, BattleShip battleShip) {
		int xMax = battleShip.getxLocationCordinate() + battleShip.getWidth();
		int yMax = battleShip.getyLocationCordinate() + battleShip.getHeight();

		for (int i = battleShip.getyLocationCordinate(); i < yMax; i++) {
			for (int j = battleShip.getxLocationCordinate(); j < xMax; j++) {
				try {
					if (battleField.getField()[i][j] != 0) {
						System.err.println("Overlapping happened please try again with valid input");
						return false;
					}
				} catch (IndexOutOfBoundsException eob) {
					char xLastChar = (char) ('A' + battleField.getWidth() - 1);
					int yLastIndex = battleField.getHeight();
					System.err.println("BattleShip coordinate should be : X between [A-" + xLastChar
							+ "], Y between [1-" + yLastIndex + "]");
					return false;
				}
			}
		}

		return true;
	}

	public static void initializeBattleShipHitCount(BattleField battleField, BattleShip battleShip) {
		int xMax = battleShip.getxLocationCordinate() + battleShip.getWidth();
		int yMax = battleShip.getyLocationCordinate() + battleShip.getHeight();
		for (int i = battleShip.getyLocationCordinate(); i < yMax; i++) {
			for (int j = battleShip.getxLocationCordinate(); j < xMax; j++) {
				battleField.getField()[i][j] = battleShip.getShipType().getThreashHoldValue();
			}
		}

	}

	public static List<BattleShip>[] buildBattleShip(String battleShipInfos[], int numberOfBattleShip) {
		if (battleShipInfos.length != numberOfBattleShip) {
			return null;
		}
		List<BattleShip>[] baltteShipPlayerWise = intializeBattleShip();
		for (String battleShipInfo : battleShipInfos) {
			// [shipType size[w * h] [yx]] eg: Q 1 1 A1 B2
			String battleShipParameters[] = battleShipInfo.split(" ");
			if (battleShipParameters.length == (3 + BattleShipConstants.NUMBER_OF_PLAYERS)) {
				try {
					ShipType shipType = ShipType.valueOf(battleShipParameters[0]);
					int width = Integer.parseInt(battleShipParameters[1]);
					int height = Integer.parseInt(battleShipParameters[2]);
					for (int i = 0; i < BattleShipConstants.NUMBER_OF_PLAYERS; i++) {
						char coordinatPlayer[] = battleShipParameters[3 + i].toCharArray();// [0]
																							// -
																							// 'A';
						int yLoc = coordinatPlayer[0] - 'A';
						int xLoc = coordinatPlayer[1] - 49;
						baltteShipPlayerWise[i].add(new BattleShip(shipType, xLoc, yLoc, width, height));
					}
				} catch (Exception e) {
					String errorMessage = "Please enter valid ship parameters ShipType[P/Q] xSize[0-9] ySize[0-9] yLocationCordinate[A-Z] xLocationCordinate[0-9]  ";
					System.err.println(errorMessage);
					throw new BattleShipRuntimeException("error.invalidArgument.battleShipParam", errorMessage);
				}
			} else {
				String errorMessage = "Invalid Number of parameter please enter in below format [ shipType  xSize ySize followed by every plaryer ship cordinates]";
				System.err.println(errorMessage);
				throw new BattleShipRuntimeException("error.invalidCount.battleShipParam", errorMessage);
			}
		}
		return baltteShipPlayerWise;
	}

	private static List<BattleShip>[] intializeBattleShip() {
		@SuppressWarnings("unchecked")
		List<BattleShip>[] baltteShipPlayerWise = new ArrayList[BattleShipConstants.NUMBER_OF_PLAYERS];
		for (int i = 0; i < BattleShipConstants.NUMBER_OF_PLAYERS; i++) {
			baltteShipPlayerWise[i] = new ArrayList<>();
		}
		return baltteShipPlayerWise;
	}

	/**
	 * @param battlePlayer
	 * @param hitTargetCordinate
	 * @return true if attack on player successful else false
	 */
	public static boolean attackOn(BattlePlayer battlePlayer, String hitTargetCordinate) {
		char[] hitCoordinate = hitTargetCordinate.toCharArray();
		if (hitCoordinate.length == 2) {
			int yCoordinate = hitCoordinate[0] - 'A';
			int xCoordinate = (hitCoordinate[1]) - 49;

			boolean isTargetUnderReachable = (battlePlayer.getBattleField().getWidth() > xCoordinate)
					&& (battlePlayer.getBattleField().getHeight() > yCoordinate);

			if (isTargetUnderReachable) {
				if (battlePlayer.getBattleField().getField()[yCoordinate][xCoordinate] > 0) {
					battlePlayer.getBattleField().getField()[yCoordinate][xCoordinate] -= 1;
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * @param battleFieldDimension
	 * @return
	 */
	public static BattleField prepareBattleFiled(String battleFieldDimension) {
		String battleFieldDimensionArr[] = battleFieldDimension.split(" ");
		final char battleFieldXDimensionChar = battleFieldDimensionArr[1].toCharArray()[0];
		final int width = Integer.parseInt(battleFieldDimensionArr[0]);
		final int height = battleFieldXDimensionChar - 'A' + 1;
		return new BattleField(width, height);
	}

	public static void updateForLiveOrDead(BattleField battleField, BattleShip battleShip) {

		int xMax = battleShip.getxLocationCordinate() + battleShip.getWidth();
		int yMax = battleShip.getyLocationCordinate() + battleShip.getHeight();
		for (int i = battleShip.getyLocationCordinate(); i < yMax; i++) {
			for (int j = battleShip.getxLocationCordinate(); j < xMax; j++) {
				if (battleField.getField()[i][j] > 0) {
					return;
				}
			}
		}

		battleShip.setActive(false);
	}

}
