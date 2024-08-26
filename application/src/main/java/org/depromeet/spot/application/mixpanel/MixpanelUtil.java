package org.depromeet.spot.application.mixpanel;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MixpanelUtil {

    @Value("${mixpanel.token}")
    private String mixpanelToken;

    public void track(MixpanelEvent mixpanelEvent, String distinctId) {
        try {

            // 믹스패널 이벤트 메시지 생성
            MessageBuilder messageBuilder = new MessageBuilder(mixpanelToken);
            // 이벤트 생성
            JSONObject sentEvent = messageBuilder.event(distinctId, mixpanelEvent.getValue(), null);

            // 만든 여러 이벤트를 delivery
            ClientDelivery delivery = new ClientDelivery();
            delivery.addMessage(sentEvent);

            // Mixpanel로 데이터 전송
            MixpanelAPI mixpanel = new MixpanelAPI();
            mixpanel.deliver(delivery);
            log.info("{} 정상 동작 완료", mixpanelEvent.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
