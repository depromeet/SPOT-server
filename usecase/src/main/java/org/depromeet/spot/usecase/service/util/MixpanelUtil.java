package org.depromeet.spot.usecase.service.util;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MixpanelUtil {

    @Value("${mixpanel.token}")
    private String mixpanelToken;

    // mixpanelEvent는 eventName(이 단위로 이벤트가 묶임)
    // distinctId는 사용자를 구분하는 데 사용됨.
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Getter
    public enum MixpanelEvent {
        REVIEW_REGISTER("review_register"),
        REVIEW_REGISTER_MAX("review_register"),
        REVIEW_OPEN_COUNT("review_open_count"),
        REVIEW_LIKE_COUNT("review_like_count"),
        REVIEW_SCRAP_COUNT("review_scrap_count"),
        ;

        String value;

        MixpanelEvent(String value) {
            this.value = value;
        }
    }
}
