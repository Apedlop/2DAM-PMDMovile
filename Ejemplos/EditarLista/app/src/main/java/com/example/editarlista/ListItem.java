package com.example.editarlista;

public class ListItem {

    private String text;
    private int selecedOption;

    public ListItem(String text, int selecedOption) {
        this.text = text;
        this.selecedOption = selecedOption;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSelecedOption() {
        return selecedOption;
    }

    public void setSelecedOption(int selecedOption) {
        this.selecedOption = selecedOption;
    }
}
