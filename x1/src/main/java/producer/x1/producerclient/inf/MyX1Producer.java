package producer.x1.producerclient.inf;

import commons.msgentities.request.RequestAlphaAsync;
import commons.msgentities.request.RequestAlphaSync;
import commons.msgentities.request.RequestBetaAsync;
import commons.msgentities.request.RequestBetaAsyncMiss;
import commons.msgentities.response.ResponseX2;
import commons.msginfra.entities.ServiceMessage;
import commons.msginfra.producers.inf.ServiceMessageProducer;

public interface MyX1Producer extends ServiceMessageProducer<ServiceMessage, ServiceMessage> {


    void sentMyRequestAlphaASync(RequestAlphaAsync requestAlphaAsync);

    void sentMyRequestBetaAsyncMiss(RequestBetaAsyncMiss requestBetaAsyncMiss);

    void sentMyRequestBetaAsync(RequestBetaAsync requestBetaAsync);

    ResponseX2 sendMyRequestAlphaSync(RequestAlphaSync requestAlphaSync);


}