package producer.x1.controllers;

import commons.msgentities.request.RequestAlphaAsync;
import commons.msgentities.request.RequestAlphaSync;
import commons.msgentities.request.RequestBetaAsync;
import commons.msgentities.request.RequestBetaAsyncMiss;
import commons.msgentities.response.ResponseX2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import producer.x1.producerclient.inf.MyX1Producer;



@RestController
public class X1Controller {

    private final static String producerId = X1Controller.class.getName();



    @Autowired
    private MyX1Producer myX1Producer;

    @PostMapping(value = "/test/async", produces= MediaType.APPLICATION_JSON_VALUE)
    public void sendDataAsync() {

        RequestAlphaAsync requestAlphaAsync = new RequestAlphaAsync();
        requestAlphaAsync.setProducer(requestAlphaAsync.getClass().getName() + "_" + producerId);
        myX1Producer.sentMyRequestAlphaASync(requestAlphaAsync);

        RequestBetaAsyncMiss requestBetaAsyncMiss = new RequestBetaAsyncMiss();
        requestBetaAsyncMiss.setProducer(requestBetaAsyncMiss.getClass().getName() + "_" + producerId);
        myX1Producer.sentMyRequestBetaAsyncMiss(requestBetaAsyncMiss);

        RequestBetaAsync requestBetaAsync = new RequestBetaAsync();
        requestBetaAsync.setProducer(requestBetaAsync.getClass().getName() + "_" + producerId);
        myX1Producer.sentMyRequestBetaAsync(requestBetaAsync);
    }

    @PostMapping(value = "/test/sync", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseX2 sendDataSync() {

        RequestAlphaSync requestAlphaSync = new RequestAlphaSync();
        requestAlphaSync.setProducer(requestAlphaSync.getClass().getName()+"_"+producerId);

        ResponseX2 responseX2 = myX1Producer.sendMyRequestAlphaSync(requestAlphaSync);
        return responseX2;

    }
}
