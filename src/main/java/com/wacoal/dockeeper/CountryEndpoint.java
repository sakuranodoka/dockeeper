/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wacoal.dockeeper;

import com.pnpsw.webservice.GetCountryRequest;
import com.pnpsw.webservice.GetCountryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 *
 * @author Administrator
 */
@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://pnpsw.com/webservice";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
            GetCountryResponse response = new GetCountryResponse();
            response.setCountry("this is a test");

            return response;
    }
}
