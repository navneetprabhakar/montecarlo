package com.navneet.service.impl;

import com.navneet.models.CasinoRequest;
import com.navneet.models.CasinoResponse;
import com.navneet.models.ServiceResponse;
import com.navneet.service.SimulationService;
import com.navneet.service.helper.SimulationServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author navneet
 *
 */
@Service
public class SimulationServiceImpl implements SimulationService {

	@Autowired private SimulationServiceHelper simulationServiceHelper;
	
	@Override
	public ServiceResponse<CasinoResponse> simulateCasino(CasinoRequest request) {
		ServiceResponse<CasinoResponse> response=simulationServiceHelper.validateCasinoRequest(request);
		if(response.getStatus().equals("success")) {
			CasinoResponse casinoResponse=new CasinoResponse();
			Double amount=request.getAmount();
			for(int i=0;i<request.getGameSize();i++) {
				amount=simulationServiceHelper.runGame(i,amount, request.getBetAmount(), request.getWinRatio());
				if(null==casinoResponse.getBankruptRound() && amount<=0) {
					casinoResponse.setBankruptRound(i);
					break;
				}
			}
			casinoResponse.setEndAmount(amount);
			response=new ServiceResponse<CasinoResponse>("success", "Simulation Complete", casinoResponse);
		}
		return response;
	}

}
