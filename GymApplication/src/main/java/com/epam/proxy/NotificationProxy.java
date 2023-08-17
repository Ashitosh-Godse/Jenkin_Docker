package com.epam.proxy;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.dto.response.CredentialsDTO;

@FeignClient(name = "NotificationService")
@LoadBalancerClient(name = "NotificationService")
public interface NotificationProxy {
	@PostMapping("/notification")
	public void sendEmailAfterRegistration(@RequestBody CredentialsDTO credentialsDTO);
	
	@PostMapping("/notification/update-profile")
	public void sendEmailAfterUpdation(@RequestBody CredentialsDTO credentialsDTO);
	

}
