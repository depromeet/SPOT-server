package org.depromeet.spot.usecase.port.out.mixpanel;

import org.depromeet.spot.domain.mixpanel.MixpanelEvent;

public interface MixpanelRepository {
    void eventTrack(MixpanelEvent mixpanelEvent, String distinctId);
}
