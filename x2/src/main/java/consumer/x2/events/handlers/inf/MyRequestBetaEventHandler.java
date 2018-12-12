package consumer.x2.events.handlers.inf;

import commons.msgentities.request.RequestBetaAsync;

public interface MyRequestBetaEventHandler {
    void processReq(RequestBetaAsync requestBetaAsync);
}
