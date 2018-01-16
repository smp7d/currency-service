package com.smp7d.currency.web;

import io.swagger.annotations.ApiOperation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smp7d.currency.domain.DateFormattedExchangeRates;
import com.smp7d.currency.service.RatesService;

@RestController
public class RatesController {
	@Autowired
	private RatesService service;

	// TODO add API documentation for this API endpoint
	@ApiOperation(value = "Load rates for a particular currency spcified by 'base'. Can specify a day and a target currency to filter on.")
	@RequestMapping(value = "/rates", method = RequestMethod.GET, produces = "application/json")
	public DateFormattedExchangeRates load(@RequestParam("base") String base,
			@RequestParam(value = "timestamp", required = false) String date,
			@RequestParam(value = "target", required = false) String target) {
		Optional<String> targetOptional = Optional.ofNullable(target);

		if (date != null) {
			return service.retrieveRates(base, date, targetOptional);
		}
		return service.retrieveLatestRates(base, targetOptional);
	}

	public void setService(RatesService service) {
		this.service = service;
	}

}
