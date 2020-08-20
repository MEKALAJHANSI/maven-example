package com.dltk;

public enum Dataset {

    TEST_DATA("Test_Data"),TRAIN_DATA("Train_Data");

    String value = null;

    Dataset(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
