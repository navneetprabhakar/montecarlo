package com.navneet.models;

import lombok.Data;

@Data
public class ServiceRequest<T> {
	private T data;
}
