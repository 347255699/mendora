package org.mendora.util.constant;

/**
 * status code
 */
public enum RetCode {
    SUCCESS(0), FAILURE(-1), HALF_SUCCESS(1);

    private int val;

    RetCode(int val) {
        this.val = val;
    }

    public int val() {
        return this.val;
    }
}
