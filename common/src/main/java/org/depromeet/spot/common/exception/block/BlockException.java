package org.depromeet.spot.common.exception.block;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class BlockException extends BusinessException {

    protected BlockException(BlockErrorCode errorCode) {
        super(errorCode);
    }

    public static class BlockNotFoundException extends BlockException {
        public BlockNotFoundException() {
            super(BlockErrorCode.BLOCK_NOT_FOUND);
        }

        public BlockNotFoundException(Object obj) {
            super(BlockErrorCode.BLOCK_NOT_FOUND.appended(obj));
        }
    }
}
