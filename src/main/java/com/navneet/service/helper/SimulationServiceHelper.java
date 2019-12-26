package com.navneet.service.helper;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.navneet.models.CasinoRequest;
import com.navneet.models.CasinoResponse;
import com.navneet.models.ServiceResponse;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Component
public class SimulationServiceHelper {
	
	public ServiceResponse<CasinoResponse> validateCasinoRequest(CasinoRequest request){
		if(null==request) {
			log.info("Casino request cannot be empty.");
			return new ServiceResponse<>("failure","Casino request cannot be empty.");
		}
		if(null==request.getAmount() || request.getAmount()<=0.0) {
			log.info("Amount must be greater than 0.");
			return new ServiceResponse<>("failure","Amount must be greater than 0.");
		}
		if(null==request.getBetAmount() || request.getBetAmount()<=0.0) {
			log.info("Bet Amount must be greater than 0.");
			return new ServiceResponse<>("failure","Bet Amount must be greater than 0.");
		}
		if(request.getAmount()<request.getBetAmount()) {
			log.info("Amount must be greater than Bet Amount.");
			return new ServiceResponse<>("failure","Amount must be greater than Bet Amount.");
		}
		if(null==request.getWinRatio() || request.getWinRatio()>1.0 || request.getWinRatio()<0.0) {
			log.info("Win ratio must be between 0 and 1.");
			return new ServiceResponse<>("failure","Win ratio must be between 0 and 1.");
		}
		if(null==request.getGameSize() || request.getGameSize()<0) {
			log.info("Number of games cannot be less than 0.");
			return new ServiceResponse<>("failure","Number of games cannot be less than 0.");
		}
		log.info("Request validated.");
		return new ServiceResponse<>("success");
	}
	
	public Double runGame(Integer round,Double amount, Double betAmount, Double winRatio) {
		Random random=new Random();
		if(random.nextDouble()>winRatio) {
			if(betAmount<amount) {
				amount-=betAmount;
			}else {
				amount=0.0;
			}
			log.info("Game no. "+(round+1)+" result: LOSS, amount:"+amount);
		}else {
			log.info("Game no. "+(round+1)+" result: WIN, amount:"+amount);
			amount+=betAmount;
		}
		return amount;
	}
	
}
