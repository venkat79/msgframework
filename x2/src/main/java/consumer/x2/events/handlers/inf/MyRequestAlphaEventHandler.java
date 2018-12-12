package consumer.x2.events.handlers.inf;

import commons.msgentities.request.RequestAlphaAsync;

public interface MyRequestAlphaEventHandler  {
   void processReq(RequestAlphaAsync myRequestAlpha);
}
