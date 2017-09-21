package com.game.battleship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.game.battleship.config.FileBasedUserInputConfig;
import com.game.battleship.util.BattleShipConstants;
import static com.game.battleship.util.UserInputValidationUtil.*;

@Component
public class FileBasedUserInputProvider implements UserInputProvider {

	@Autowired
	private FileBasedUserInputConfig fileBasedUserInputConfig;


	@Override
	public String getInputFromUserForDimensionOfBattleField() {
		validateBattleFieldDimenstion(fileBasedUserInputConfig.getBattleFieldDimension());
		return fileBasedUserInputConfig.getBattleFieldDimension();
	}

	@Override
	public int getInputFromUserForNumberOfBattleShip() {
		return fileBasedUserInputConfig.getNumberOfBattleShip();
	}

	@Override
	public String[] getInputFromUserForDimensionOfBattleShip(int numberOfBattleShip) {
		String[] battleShipDimensionInfo = new String[numberOfBattleShip];
		for (int i = 0; i < numberOfBattleShip; i++) {
			battleShipDimensionInfo[i] = fileBasedUserInputConfig.getPlayerBattleShipsInfo(i + 1);
			validateBattleSipDimensions(battleShipDimensionInfo[i]);
		}
		return battleShipDimensionInfo;
	}

	@Override
	public String[] getUserInputForHitTargetCoordinate() {
		String[] attackCoordinatesOfPlayer = new String[BattleShipConstants.NUMBER_OF_PLAYERS];
		for(int i = 0 ; i < BattleShipConstants.NUMBER_OF_PLAYERS ; i++ ){
			attackCoordinatesOfPlayer[i] = fileBasedUserInputConfig.getPlayerHitTargetCoordinate(i+1);
			validateHitCoordinates(attackCoordinatesOfPlayer[i]);
		}
		return attackCoordinatesOfPlayer;
	}

}
