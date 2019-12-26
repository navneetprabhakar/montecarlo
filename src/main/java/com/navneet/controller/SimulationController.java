package com.navneet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.navneet.models.CasinoRequest;
import com.navneet.models.CasinoResponse;
import com.navneet.models.ServiceResponse;
import com.navneet.service.SimulationService;

@RestController
public class SimulationController {

	@Autowired private SimulationService simulationService;
	
	@PostMapping("casinoGame")
	public ServiceResponse<CasinoResponse> simulateCasino(@RequestBody CasinoRequest request){
		return simulationService.simulateCasino(request);
	}
}
