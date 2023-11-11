package christmas.model;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", 6000),
    TAPAS("타파스", 5500),
    CEASER_SALAD("시저샐러드", 8000),
    TBONE_STEAK("티본스테이크", 55000),
    BARBEQUE_RIB("바비큐립", 54000),
    MARINA_PASTA("해산물파스타", 35000),
    CHRISTMAS_PASTA("크리스마스파스타", 25000),
    CHOCOLATE_CAKE("초코케이크", 15000),
    ICECREAM("아이스크림", 5000),
    ZERO_COKE("제로콜라", 3000),
    RED_WINE("레드와인", 60000),
    CHAMPAGNE("샴페인", 25000);

    private String name;
    private int price;

    Menu(String name, int price){
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

}
