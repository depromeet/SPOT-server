package org.depromeet.spot.usecase.port.out.mixpanel;

import org.depromeet.spot.usecase.service.event.MixpanelEvent;

public interface MixpanelRepository {
    void eventTrack(MixpanelEvent mixpanelEvent);
}
