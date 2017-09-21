package com.game.battleship;

import java.util.List;

import com.game.battleship.exception.BattleShipRuntimeException;
import com.game.battleship.model.BattleField;
import com.game.battleship.model.BattlePlayer;
import com.game.battleship.model.BattleShip;
import com.game.battleship.service.UserInputProvider;
import com.game.battleship.util.ApplicationContextProvider;
import com.game.battleship.util.BattleGameUtil;
import com.game.battleship.util.BattleShipConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BattleshipGameApplication {

	private UserInputProvider userInputProvider;

	@Value("${provider}")
	private String provider;
	
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(BattleshipGameApplication.class);
		app.run(args);
		BattleshipGameApplication battleShipGame = ApplicationContextProvider.getBean("battleshipGameApplication",BattleshipGameApplication.class);
		battleShipGame.battleshipGame();
	}

	public void battleshipGame() {
		System.out.println("provider : "+provider);
		this.userInputProvider =  ApplicationContextProvider.getBean(provider+"UserInputProvider" , UserInputProvider.class);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String battleFieldDimenstions = this.userInputProvider.getInputFromUserForDimensionOfBattleField();
		int  numberOfBattleShip = this.userInputProvider.getInputFromUserForNumberOfBattleShip();
		String battleShipInfos[] = this.userInputProvider.getInputFromUserForDimensionOfBattleShip(numberOfBattleShip);
		String [] attackCoordinates = this.userInputProvider.getUserInputForHitTargetCoordinate();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		BattlePlayer[] numberOfBattleShipForPlayer = intializeBattleshipGame(battleFieldDimenstions , numberOfBattleShip , battleShipInfos  );
		this.userInputProvider.intializeAttachQueue(attackCoordinates);
		System.out.println("Lets Start the Game ...");
		startGame(numberOfBattleShipForPlayer);
	}

	public BattlePlayer[] intializeBattleshipGame(String battleFieldDimension, int numberOfBattleShip,
			String[] battleShipInfos) {
		BattleField battleField = BattleGameUtil.prepareBattleFiled(battleFieldDimension);
		BattlePlayer battlePlayers[] = new BattlePlayer[BattleShipConstants.NUMBER_OF_PLAYERS];
		List<BattleShip>[] playerWiseBattleShips = BattleGameUtil.buildBattleShip(battleShipInfos ,numberOfBattleShip);
		for(int i = 0 ; i < BattleShipConstants.NUMBER_OF_PLAYERS ; i++){
			if(playerWiseBattleShips[i].size() == numberOfBattleShip){
				BattlePlayer battlePlayer = new BattlePlayer("player_"+(i+1), battleField.clone());
				if( battlePlayer.getBattleField().addAllBattleShip(playerWiseBattleShips[i])){
					battlePlayers[i] = battlePlayer;
				}else{
					String errorMessage = "Unable add battleship to battleField for Player-"+(i+1)+" as co-ordinates are overlapping";
					System.err.println(errorMessage);
					throw new BattleShipRuntimeException("error.invalidData.overlapping", errorMessage);
				}
			}else{
				String errorMessage = "Total number of battleship for Player-"+(i+1)+" is not equal to "+numberOfBattleShip;
				System.err.println(errorMessage);
				throw new BattleShipRuntimeException("error.invalidData.battleShipCount", errorMessage);
			}
		}
		return battlePlayers;
	}

	public void startGame(BattlePlayer numberOfBattleShipForPlayer[]) {
		int attackerIndex = 0;
		int attackToIndex = 1;
		boolean isPlayerDead = false;
		boolean isGameDraw = false;
		do {
			String hitTargetCordinate = this.userInputProvider.getHitTargetCoordinate(attackerIndex);
			boolean isSuccess = false;
			if (hitTargetCordinate == null) {
				if (this.userInputProvider.isGameDraw()) {
					isGameDraw = true;
					break;
				}
				System.out.println("Player-" + (attackerIndex + 1) + " has no more missiles left to launch");
			} else {
				isSuccess = BattleGameUtil.attackOn(numberOfBattleShipForPlayer[attackToIndex], hitTargetCordinate);
				if (isSuccess) {
					System.out.println(
							"Player-" + (attackerIndex + 1) + " fires a missile with target "+hitTargetCordinate+" which got hit.");
				} else {
					System.out.println(
							"Player-" + (attackerIndex + 1) + " fires a missile with target "+hitTargetCordinate+" which got miss");
				}
			}

			isPlayerDead = isSuccess && numberOfBattleShipForPlayer[attackToIndex].isPlayerDead(hitTargetCordinate);
			if (!isSuccess) {
				int temp = attackerIndex;
				attackerIndex = attackToIndex;
				attackToIndex = temp;
			}
		} while (!isPlayerDead);

		if (isGameDraw) {
			System.out.println("Game is Draw! Nobody Wins.");
		} else {
			System.out.println("Congrates! Player " + (attackerIndex + 1) + " won the battle.");
		}
	}

	public void setUserInputProvider(UserInputProvider userInputProvider) {
		this.userInputProvider = userInputProvider;
	}
}
