package com.navneet.service;

import com.navneet.models.CasinoRequest;
import com.navneet.models.CasinoResponse;
import com.navneet.models.ServiceResponse;

/**
 * @author navneet
 *
 */
public interface SimulationService {
	
	/**This method runs simulation for Casino game 
	 * @param request 
	 * amount		:: Starting amount of player
	 * betAmount	:: Betting amount of game
	 * winRatio		:: Win possibility ratio range 0-1 
	 * (e.g. Lets say for each roll of dice player wins if he gets 1 & 6 as outcome then winRatio is 2/6 i.e. 0.33)
	 * gameSize		:: Number of iterations of the game.
	 * 
	 * Game rule::
	 * if the result is greater than the winRatio casino wins i.e. player loses the betAmount, otherwise he receives the betAmount
	 * @return
	 * endAmount	:: Final amount at the end of game
	 * bankruptRound:: The game number when his amount reaches 0.
	 * 
	 * Note:: The game assumes that player would continue the game even if his amount reaches 0. 
	 * In that case its assumed that casino is lending him money and the negative endAmount is the amount payable to the casino
	 */
	public ServiceResponse<CasinoResponse> simulateCasino(CasinoRequest request);
}
