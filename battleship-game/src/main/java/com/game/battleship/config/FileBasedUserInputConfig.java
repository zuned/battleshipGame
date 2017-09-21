package com.game.battleship.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@ConfigurationProperties
@EnableConfigurationProperties(FileBasedUserInputConfig.class)
@Component
public class FileBasedUserInputConfig {
	
	@Autowired
	private Environment env;
	
	@Value("${userinput.battleFieldDimension}")
	String battleFieldDimension;
	@Value("${userinput.numberOfBattleShip}")
	int numberOfBattleShip;
//	@Value("${userinput.battleShip1.BattleShipsInfo}")
//	String 	battleShip1Info;
//	@Value("${userinput.battleShip2.BattleShipsInfo}")
//	String 	battleShip2Info;
//	@Value("${userinput.player1.HitTargetCoordinate}")
//	String 	player1HitTargetCoordinate;
//	@Value("${userinput.player2.HitTargetCoordinate}")
//	String 	player2HitTargetCoordinate;
	public String getBattleFieldDimension() {
		return battleFieldDimension;
	}
	public int getNumberOfBattleShip() {
		return numberOfBattleShip;
	}
	public void setNumberOfBattleShip(int numberOfBattleShip) {
		this.numberOfBattleShip = numberOfBattleShip;
	}

	public void setBattleFieldDimension(String battleFieldDimension) {
		this.battleFieldDimension = battleFieldDimension;
	}
	public String getPlayerBattleShipsInfo(int battleShipNumber) {
		return env.getProperty("userinput.battleShip.battleShipsInfo_"+battleShipNumber);
	}
	
	public String getPlayerHitTargetCoordinate(int playerNumber) {
		return env.getProperty("userinput.player.HitTargetCoordinate_"+playerNumber);
	}
}
