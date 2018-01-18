package com.belac.ines.foodie.classes;

/**
 * Created by Ines on 17.1.2018..
 */

public class Menu {
    private String firstMeal;
    private String secondMeal;
    private String thirdMeal;

    public Menu(String first, String second, String third) {
        this.firstMeal = first;
        this.secondMeal = second;
        this.thirdMeal = third;
    }

    public String getFirstMeal() { return firstMeal; }

    public void setFirstMeal(String firstMeal) { this.firstMeal = firstMeal; }

    public String getSecondMeal() { return secondMeal; }

    public void setSecondMeal(String secondMeal) { this.secondMeal = secondMeal; }

    public String getThirdMeal() { return thirdMeal; }

    public void setThirdMeal(String thirdMeal) { this.thirdMeal = thirdMeal; }
}
