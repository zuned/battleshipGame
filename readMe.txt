------------------------------------------------------------------------------------------------------
Software Required: JDK 8 And Maven 3+
------------------------------------------------------------------------------------------------------
Step to build program: mvn clean install
------------------------------------------------------------------------------------------------------
1. Buld program by executing : mvn clean install
2. Please browse to target\ folder where battleship-game-0.0.1-SNAPSHOT.jar is present
3. Place application.properties file over here.
4. change configuration and test data in application.properties [default sample provided with name application.properties /application.properties_1]
5. To run application : java -jar battleship-game-0.0.1-SNAPSHOT.jar


------------------------------------------------------------------------------------------------------
Architecture Breif:
This is battleShip Game designed for user input from command prompt.
The code has 2 provider for user input -> CommandLine And FileBased [default implementation is commandLine but hook is provided for
for file based.]
The architecture is as such that we can increase any number of input provider with affecting other input provider and other areas of code.

Architecture : 
	Code is devided into 3 parts 
		Part 1: User Provide Input Parmeters Based On provide[commandLine or fileBased]
		Part 2: We Build All Entity [BattleField / BattlePlayer / BattleShip ]
		Part 3: Play Game

------------------------------------------------------------------------------------------------------	
	Configuration:
		application.properties file [Sample : property file are attached with name application.properties /application.properties_1]
		Please note based on userinput.numberOfBattleShip , userinput.battleShip.battleShipsInfo_ field will increase. refer attached Sample
------------------------------------------------------------------------------------------------------	
	I have written Test using Mockito By mocking provider service to test the Game.
------------------------------------------------------------------------------------------------------
JUnit Test And Integration Test are provided to test the Game. 	
------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------
OUTPUT OF Integration Junit
------------------------------------------------------------------------------------------------------
Example of Game Input: 
Player-1 fires a missile with target A1 which got miss
Player-2 fires a missile with target A1 which got hit.
Player-2 fires a missile with target B2 which got miss
Player-1 fires a missile with target B2 which got hit.
Player-1 fires a missile with target B2 which got hit.
Player-1 fires a missile with target B3 which got miss
Player-2 fires a missile with target B3 which got miss
Player-1 has no more missiles left to launch
Player-2 fires a missile with target A1 which got hit.
Player-2 fires a missile with target D1 which got miss
Player-1 has no more missiles left to launch
Player-2 fires a missile with target E1 which got miss
Player-1 has no more missiles left to launch
Player-2 fires a missile with target D4 which got hit.
Player-2 fires a missile with target D4 which got miss
Player-1 has no more missiles left to launch
Player-2 fires a missile with target D5 which got hit.
Congrates! Player 2 won the battle.

------------------------------------------------------------------------------------------------------
