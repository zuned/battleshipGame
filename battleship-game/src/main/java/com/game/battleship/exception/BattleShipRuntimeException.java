package com.game.battleship.exception;

public class BattleShipRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String errorCode;

	public BattleShipRuntimeException(final String errorCode ,final String message){
		super(message);
		this.errorCode = errorCode;
	}
	
	public BattleShipRuntimeException(final String errorCode ,final String message , Throwable exception){
		super(message, exception);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
