package org.depromeet.spot.usecase.service.event;

import org.depromeet.spot.usecase.port.out.mixpanel.MixpanelRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MixpanelEventListener {

    private final MixpanelRepository mixpanelRepository;

    @Async
    @EventListener
    public void eventTrack(MixpanelEvent mixpanelEvent) {
        mixpanelRepository.eventTrack(mixpanelEvent);
    }
}
