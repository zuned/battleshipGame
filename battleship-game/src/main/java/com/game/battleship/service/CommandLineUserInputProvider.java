package com.game.battleship.service;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.game.battleship.exception.BattleShipRuntimeException;
import com.game.battleship.util.BattleShipConstants;
import static com.game.battleship.util.UserInputValidationUtil.*;

@Component
public class CommandLineUserInputProvider implements UserInputProvider {

	private final Scanner consoleInput = new Scanner(System.in);

	@Override
	public String getInputFromUserForDimensionOfBattleField() {
		System.out.print(
				"Please Enter Valid Dimention For Battle Field [X Y], where [1 >= X <= 9] , [A >= Y <= Z] Coordinate : ");
		String dimenstion = consoleInput.nextLine();
		validateBattleFieldDimenstion(dimenstion);
		return dimenstion;
	}

	@Override
	public int getInputFromUserForNumberOfBattleShip() {
		System.out.print("Please enter number of battleship : ");
		try {
			return Integer.parseInt(consoleInput.nextLine());
		} catch (NumberFormatException e) {
			throw new BattleShipRuntimeException("error.invalidInput.InvalidBattleShipNumber",
					"Invalid input for number of battleShip");
		}
	}

	@Override
	public String[] getInputFromUserForDimensionOfBattleShip(int numberOfBattleShip) {
		String[] battleShipDimensionInfo = new String[numberOfBattleShip];
		for (int i = 0; i < numberOfBattleShip; i++) {
			System.out.print("Please provide dimensions of  " + (i + 1)
					+ " in format [shipType size[w * h] [yx]] eg: Q 1 1 A1 B2 : ");
			battleShipDimensionInfo[i] = consoleInput.nextLine();
			validateBattleSipDimensions(battleShipDimensionInfo[i]);
		}
		return battleShipDimensionInfo;
	}

	@Override
	public String[] getUserInputForHitTargetCoordinate() {
		String[] attackCoordinatesOfPlayer = new String[BattleShipConstants.NUMBER_OF_PLAYERS];
		for (int i = 0; i < BattleShipConstants.NUMBER_OF_PLAYERS; i++) {
			System.out.print("Please player " + (i + 1) + " missile targets in format (YX) Followed by space : ");
			attackCoordinatesOfPlayer[i] = consoleInput.nextLine();
			validateHitCoordinates(attackCoordinatesOfPlayer[i]);
		}
		return attackCoordinatesOfPlayer;
	}
}
