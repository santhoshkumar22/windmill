package com.windmillfarm.management.exception;

public class WindMillFarmException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public WindMillFarmException(String errorMessage) {
		super(errorMessage);
	}
}