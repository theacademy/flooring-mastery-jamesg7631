package com.mthree.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO {
    private Scanner scanner;
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public double readDouble(String prompt) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;

        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input Error. Please try again.");
            }
        }
        return num;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result;
        do {
            result = readInt(prompt);
        } while (result < min || result > max);
        return result;
    }

    @Override
    public long readLong(String prompt) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String readString(String prompt) {
        this.print(prompt);
        String readLine = scanner.nextLine();
        return readLine;
    }
}
