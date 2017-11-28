/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wacoal.dockeeper;

import com.wacoal.dockeeper.wsdl.GetEmpByFilter;
import com.wacoal.dockeeper.wsdl.GetEmpByFilterResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 *
 * @author Administrator
 */
public class EmpClient extends WebServiceGatewaySupport {
    //GetEmpByFilter req = new GetEmpByFilter();
    
    public GetEmpByFilterResponse getEmpByFilter() {
        GetEmpByFilter req = new GetEmpByFilter();
        req.setSex("M");
        req.setEmpId("92642");
        req.setEmpStatus("S");

        GetEmpByFilterResponse resp = (GetEmpByFilterResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://webapp.wacoalsampan.com/empData/empService1.asmx",
                        req,
                        new SoapActionCallback("http://tempuri.org/GetEmpByFilter"));
        return resp;
    }
}
