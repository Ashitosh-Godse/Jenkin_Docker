package com.epam.proxy;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.dto.request.ReportRequestDTO;
import com.epam.dto.response.ReportResponseDTO;

@FeignClient(name = "ReportApplication")
@LoadBalancerClient(name = "ReportApplication")
public interface ReportProxy {
	@PostMapping("/report")
	public ResponseEntity<Void> addReport(@RequestBody ReportRequestDTO reportRequestDTO);
		
	
	@GetMapping("/report/{userName}")
	public ResponseEntity<ReportResponseDTO> getReport(@PathVariable String userName);
	
}
