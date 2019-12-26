package com.navneet.models;

import lombok.Data;

@Data
public class CasinoRequest {
	private Double amount;
	private Double betAmount;
	private Double winRatio;
	private Integer gameSize;
}
