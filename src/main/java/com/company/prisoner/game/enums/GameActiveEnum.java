package com.company.prisoner.game.enums;

/**
 * @author user
 */

public enum GameActiveEnum {

    INITIAL(0),

    START(1),

    STOP(2),

    INVALID(3)
    ;




    int status;

    GameActiveEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
