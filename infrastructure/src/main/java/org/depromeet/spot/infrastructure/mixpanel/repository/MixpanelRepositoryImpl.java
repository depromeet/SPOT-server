package org.depromeet.spot.infrastructure.mixpanel.repository;

import java.io.IOException;

import org.depromeet.spot.infrastructure.mixpanel.property.MixpanelProperties;
import org.depromeet.spot.usecase.port.out.mixpanel.MixpanelRepository;
import org.depromeet.spot.usecase.service.event.MixpanelEvent;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MixpanelRepositoryImpl implements MixpanelRepository {

    private final MixpanelProperties mixpanelProperties;

    // TODO : 믹스 패널 코드를 비즈니스 로직과 분리하기
    //    1. 동시성 제어는 할 필요가 없는가?
    //        -> 어차피 믹스 패널에서 알아서 동시성에 대한 처리를 해 놨을 것이기 때문에 필요 없지 않은가?
    //    2. 굳이 실시간 처리가 필요한가?
    //        -> 메시지큐를 사용하거나, Redis 등에 저장해서 한 번에 처리하는 방법도 괜찮지 않을까?
    //    3. 이벤트 요청에 실패했을 경우에는 어떻게 처리할 것인가?

    // mixpanelEvent는 eventName(이 단위로 이벤트가 묶임)
    // distinctId는 사용자를 구분하는 데 사용됨.
    @Override
    public void eventTrack(MixpanelEvent mixpanelEvent) {
        try {

            // 믹스패널 이벤트 메시지 생성
            MessageBuilder messageBuilder = new MessageBuilder(mixpanelProperties.token());

            // 이벤트 생성
            JSONObject sentEvent =
                    messageBuilder.event(
                            mixpanelEvent.getDistinctId(),
                            mixpanelEvent.getMixpanelEventName().getValue(),
                            null);

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
}
