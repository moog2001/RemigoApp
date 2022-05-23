package com.example.remigoapp;

import com.example.remigoapp.Memo.Memo;

import java.util.List;

public class Section {
    int type = Constants.NULL_INT;
    List<Memo> list;

    public Section(List<Memo> listInput, int typeInput) {
        this.list = listInput;
        this.type = typeInput;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Memo> getList() {
        return list;
    }

    public void setList(List<Memo> list) {
        this.list = list;
    }

}
