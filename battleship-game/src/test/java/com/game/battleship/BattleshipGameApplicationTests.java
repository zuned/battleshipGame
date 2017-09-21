package com.game.battleship;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import com.game.battleship.model.BattleField;
import com.game.battleship.model.BattlePlayer;
import com.game.battleship.model.BattleShip;
import com.game.battleship.model.ShipType;
import com.game.battleship.service.UserInputProvider;
import com.game.battleship.util.BattleGameUtil;
import com.game.battleship.util.BattleShipConstants;

public class BattleshipGameApplicationTests {
	
	@Mock
	private BattleshipGameApplication battleshipGameApplication;
	private UserInputProvider userInputProvider;
	
	//////////////////Test Data////////////////////////////
	private final int userInputNumberOfBattleShips = 2;
	private String userInputbattleFieldDimension ;	
	private int userInputNumberOfBattleShip;
	private String[] userInputbattleShipInfos;
////////////////Test Data////////////////////////////
	
	@SuppressWarnings("unchecked")
	private LinkedList<String> queue[] = new LinkedList[BattleShipConstants.NUMBER_OF_PLAYERS];
	
	@Before
    public void setupMock() {
		MockitoAnnotations.initMocks(this);
		this.battleshipGameApplication = new BattleshipGameApplication();
		this.userInputProvider = mock(UserInputProvider.class);
		this.battleshipGameApplication.setUserInputProvider(userInputProvider); 
		when(userInputProvider.getInputFromUserForDimensionOfBattleField()).thenReturn("5 E");
		when(userInputProvider.getInputFromUserForNumberOfBattleShip()).thenReturn(userInputNumberOfBattleShips);
		when(userInputProvider.getInputFromUserForDimensionOfBattleShip(userInputNumberOfBattleShips)).thenReturn("Q 1 1 A1 B2,P 2 1 D4 C3".split(","));
		when(userInputProvider.getUserInputForHitTargetCoordinate()).thenReturn("A1 B2 B2 B3,A1 B2 B3 A1 D1 E1 D4 D4 D5 D5".split(","));
		this.userInputbattleFieldDimension = userInputProvider.getInputFromUserForDimensionOfBattleField(); 
		this.userInputNumberOfBattleShip = userInputProvider.getInputFromUserForNumberOfBattleShip();
		this.userInputbattleShipInfos =userInputProvider.getInputFromUserForDimensionOfBattleShip(userInputNumberOfBattleShips);
	}

	@Test
	public void testBattleshipGame() {
		BattlePlayer[] battleShipForPlayers = battleshipGameApplication.intializeBattleshipGame(userInputbattleFieldDimension,userInputNumberOfBattleShip,userInputbattleShipInfos );
		
		Assert.assertEquals("2 players should be created", battleShipForPlayers.length, BattleShipConstants.NUMBER_OF_PLAYERS);
		Assert.assertEquals("players battleShip should be equal", battleShipForPlayers[0].getBattleField().getBattleShips().size(), battleShipForPlayers[1].getBattleField().getBattleShips().size());
		Assert.assertEquals("Total battleShip count should be "+ userInputNumberOfBattleShips, battleShipForPlayers[0].getBattleField().getBattleShips().size(),userInputNumberOfBattleShips);
		
		queue[0] = new LinkedList<>();
		queue[0].addAll(Arrays.asList("A1 B2 B2 B3".split(" ")));
		queue[1] = new LinkedList<>();
		queue[1].addAll(Arrays.asList("A1 B2 B3 A1 D1 E1 D4 D4 D5 D5".split(" ")));
		when(userInputProvider.getHitTargetCoordinate(0)).thenAnswer((InvocationOnMock invocation)->queue[0].size()> 0 ? queue[0].removeFirst(): null); //(queue[0].removeLast());
		when(userInputProvider.getHitTargetCoordinate(1)).thenAnswer((InvocationOnMock invocation)->queue[1].size()>0 ?queue[1].removeFirst(): null); 
		battleshipGameApplication.startGame(battleShipForPlayers);
	}
	
	
	@Test
	public void testAttackOn() {
		BattlePlayer[] battleShipForPlayers = battleshipGameApplication.intializeBattleshipGame(userInputbattleFieldDimension,userInputNumberOfBattleShip,userInputbattleShipInfos );
		int playerIndex = 0;
		//When on target with value > 0
		 boolean success = BattleGameUtil.attackOn(battleShipForPlayers[playerIndex], "D4");
		 assertTrue("Attack On correct co-ordinate with value > 0 ", success==true);
		//When on target with value == 0
		 success = BattleGameUtil.attackOn(battleShipForPlayers[playerIndex], "D1");
		 assertTrue("Attack On correct co-ordinate with value > 0 ", success == false);
		//When outside target but in battleFeild
		 success = BattleGameUtil.attackOn(battleShipForPlayers[playerIndex],"D9");
		 assertTrue("Attack On correct co-ordinate not lies on battleField", success == false);
	}
	
	@Test
	public void testBattleShipType() {
		BattlePlayer[] battleShipForPlayers = battleshipGameApplication.intializeBattleshipGame(userInputbattleFieldDimension,userInputNumberOfBattleShip,userInputbattleShipInfos );
		int playerIndex = 0;
		//P - Type Should Take only one hit to destroy the block
		 boolean success = BattleGameUtil.attackOn(battleShipForPlayers[playerIndex], "D4");
		 assertTrue("P - Type Should Take only one hit to destroy the block", success==true);
		//P - Type Second Hit should always return false on same block
		 success = BattleGameUtil.attackOn(battleShipForPlayers[playerIndex], "D4");
		 assertTrue("P - Type Second Hit should always return false on same block ", success == false);
		//Q - Type Should Take two hit to destroy the block
		 success = BattleGameUtil.attackOn(battleShipForPlayers[playerIndex], "A1");
		 assertTrue("Q - Type Should Take two hit to destroy the block", success==true);
		//Q - Type Should Take two hit to destroy the block
		 success = BattleGameUtil.attackOn(battleShipForPlayers[playerIndex], "A1");
		 assertTrue("Q - Type Should Take two hit to destroy the block", success==true);
		//Q - Type Third Hit should always return false
		 success = BattleGameUtil.attackOn(battleShipForPlayers[playerIndex], "A1");
		 assertTrue("Q - Type Should Take two hit to destroy the block", success==false);
	}
	
	@Test
	public void testIsBattleShipForOverlapping() {
		BattleField battleField = BattleGameUtil.prepareBattleFiled(this.userInputbattleFieldDimension);
		List<BattleShip> battleShips = new ArrayList<>();
		battleShips.add(new BattleShip(ShipType.P, 0, 0, 2, 2));
		battleField.addAllBattleShip(battleShips);
		boolean successFulAdd = BattleGameUtil.isBattleShipForOverlapping(battleField, battleShips.get(0));
		Assert.assertTrue("Since same ship is already addred to battleField, overlapping not allowed", successFulAdd == false);
	}
	
	@Test
	public void testIsAttackOnMe(){
		BattleShip battleShip = new BattleShip(ShipType.P, 0, 0, 2, 2);
		boolean success = battleShip.isAttachOnMe(0, 1);
		Assert.assertTrue("BattleShip co-ordinates are with in reach of attach co-ordinates", success == true);
		success = battleShip.isAttachOnMe(0, 2);
		Assert.assertTrue("BattleShip co-ordinates are outside the reach of attack co-ordinates.", success == false);
	}
	
	@Test
	public void testIsPlayerDead() {
		BattlePlayer[] battleShipForPlayers = battleshipGameApplication.intializeBattleshipGame(userInputbattleFieldDimension,userInputNumberOfBattleShip,userInputbattleShipInfos );
		int playerIndex = 0;
		BattleGameUtil.attackOn(battleShipForPlayers[playerIndex],"D4");
		boolean result = battleShipForPlayers[playerIndex].isPlayerDead("D4");
		Assert.assertTrue("Player is not dead.", result == false);
		BattleGameUtil.attackOn(battleShipForPlayers[playerIndex],"D5");
		result = battleShipForPlayers[playerIndex].isPlayerDead("D5");
		Assert.assertTrue("Player is not dead.", result == false);
		BattleGameUtil.attackOn(battleShipForPlayers[playerIndex],"A1");
		result = battleShipForPlayers[playerIndex].isPlayerDead("A1");
		Assert.assertTrue("Player is not dead.", result == false);
		BattleGameUtil.attackOn(battleShipForPlayers[playerIndex],"A1");
		result = battleShipForPlayers[playerIndex].isPlayerDead("A1");
		Assert.assertTrue("Player is dead.", result == true);
		
	}
	
}
