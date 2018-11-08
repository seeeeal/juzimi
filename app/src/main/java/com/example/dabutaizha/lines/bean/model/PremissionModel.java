package com.example.dabutaizha.lines.bean.model;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/5/23 下午10:09.
 */
@Entity
public class PremissionModel implements Serializable {

    @Id(assignable = true)
    private long premissionModelId;

    @NameInDb("PREMISSION_INPUT_SENTENCE")
    private boolean inputSentencePremission = true;

    public PremissionModel() {
    }

    public PremissionModel(long premissionModelId, boolean inputSentencePremission) {
        this.premissionModelId = premissionModelId;
        this.inputSentencePremission = inputSentencePremission;
    }

    public long getPremissionModelId() {
        return premissionModelId;
    }

    public void setPremissionModelId(long premissionModelId) {
        this.premissionModelId = premissionModelId;
    }

    public boolean isInputSentencePremission() {
        return inputSentencePremission;
    }

    public void setInputSentencePremission(boolean inputSentencePremission) {
        this.inputSentencePremission = inputSentencePremission;
    }

    @Override
    public String toString() {
        return "PremissionModel{" +
                "premissionModelId=" + premissionModelId +
                ", inputSentencePremission=" + inputSentencePremission +
                '}';
    }
}
