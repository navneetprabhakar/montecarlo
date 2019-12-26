package com.navneet.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {
	private String status;
	private String message;
	private T data;
	
	public ServiceResponse(String status, String message) {
		this.status=status;
		this.message=message;
	}
	public ServiceResponse(String status) {
		this.status=status;
	}
}
