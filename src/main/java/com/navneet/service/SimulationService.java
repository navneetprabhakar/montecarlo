package com.navneet.service;

import com.navneet.models.CasinoRequest;
import com.navneet.models.CasinoResponse;
import com.navneet.models.ServiceResponse;

public interface SimulationService {
	public ServiceResponse<CasinoResponse> simulateCasino(CasinoRequest request);
}
