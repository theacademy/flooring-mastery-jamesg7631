package com.mthree.view;

public interface UserIO {
    // Seems a bit ridged and useless. Hard to plan this interface in advance in terms of requirements
    void print(String msg);
    double readDouble(String prompt);
    double readDouble(String prompt, double min, double max); // Don't think I need a maximum
    int readInt(String prompt);
    int readInt(String prompt, int min, int max);
    long readLong(String prompt);
    long readLong(String prompt, long min, long max);
    String readString(String prompt);
}
