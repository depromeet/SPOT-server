package org.depromeet.spot.common.exception.section;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class SectionException extends BusinessException {

    protected SectionException(SectionErrorCode errorCode) {
        super(errorCode);
    }

    public static class SectionNotFoundException extends SectionException {
        public SectionNotFoundException() {
            super(SectionErrorCode.SECTION_NOT_FOUND);
        }
    }

    public static class SectionNotBelongStadiumException extends SectionException {
        public SectionNotBelongStadiumException() {
            super(SectionErrorCode.SECTION_NOT_BELONG_TO_STADIUM);
        }
    }

    public static class SectionNameDuplicateException extends SectionException {
        public SectionNameDuplicateException() {
            super(SectionErrorCode.DUPLICATE_NAME);
        }
    }

    public static class SectionAliasDuplicateException extends SectionException {
        public SectionAliasDuplicateException() {
            super(SectionErrorCode.DUPLICATE_ALIAS);
        }
    }
}
