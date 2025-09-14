package com.example.shoppingapp.classes;

import java.time.LocalDateTime;

public class Review {
    private int Mark;
    private String Message;
    private LocalDateTime Date;

    public Review(int mark, String message) {
        this.Mark = mark;
        this.Message = message;
        this.Date = LocalDateTime.now();
    }

    public int getMark() {
        return this.Mark;
    }
    public String getMessage() {
        return this.Message;
    }
    public LocalDateTime getDate() {
        return this.Date;
    }
    public void setMark(int mark) {
        this.Mark = mark;
    }
    public void setMessage(String message) {
        this.Message = message;
    }
    public void setDate(LocalDateTime date) {
        this.Date = date;
    }

    @Override
    public String toString() {
        return ("Mark: " + (this.Mark) + ", Message " + (this.Message) +
                ", Date: " + (this.Date));
    }

    /**
     * Rounds the average review score to the nearest 0.0 or 0.5
     **/
    public static Float roundReviewAverage(Float mean) {
        float decimalPart = 0f;
        int wholePart = 0;

        if (mean > 1) {
            while (mean > 1) {
                mean -= 1;
                wholePart += 1;
            }
            decimalPart = mean;
        }
        if (decimalPart <= 0.25) {
            decimalPart = 0f;
        } else if (0.25 < decimalPart && decimalPart < 0.75) {
            decimalPart = 0.5f;
        } else {
            decimalPart = 0f;
            wholePart += 1;
        }
        return wholePart + decimalPart;
    }
}
