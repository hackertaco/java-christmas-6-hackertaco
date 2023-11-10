package christmas;

import static christmas.config.RuleConfig.DATE_DISCOUNT_PRICE;
import static christmas.config.RuleConfig.GIVING_EVENT_MIN_BOUND;
import static christmas.config.RuleConfig.GIVING_EVENT_PRODUCT;
import static christmas.config.RuleConfig.MAX_DAY;
import static christmas.config.RuleConfig.MAX_ORDER_COUNT;
import static christmas.config.RuleConfig.MENU_DIVIDER;
import static christmas.config.RuleConfig.MENU_SPLITTER;
import static christmas.config.RuleConfig.MIN_DAY;
import static christmas.config.RuleConfig.SPECIAL_DISCOUNT_DATE;
import static christmas.config.RuleConfig.TARGET_MONTH;
import static christmas.config.RuleConfig.TARGET_YEAR;
import static christmas.config.RuleConfig.WEEKDAY_DISCOUNT_MENU_GROUP;
import static christmas.config.RuleConfig.WEEKEND_DISCOUNT_MENU_GROUP;

import camp.nextstep.edu.missionutils.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Application {
    static int eventDay;

    public static void main(String[] args) {
        // 기능 1. 날짜 입력 받기
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n"
                + "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");


        while(true){
            try{
                String date = Console.readLine();
                validateEventDate(Integer.parseInt(date));
                eventDay = Integer.parseInt(date);
                break;
            }catch (NumberFormatException e ){
                System.out.println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
            }catch (IllegalArgumentException e){
                System.out.println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
            }
        }

        // 기능 2.
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        List<String> menus = new ArrayList<>();
        while(true){
            try{
                String menuBeforeValidated = Console.readLine();
                menus = menuOneByOne(menuBeforeValidated);
                validateMenu(menus);
                validateMenuCount(menus);
                break;
            }catch (NumberFormatException e ){
                System.out.println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
            }catch (IllegalArgumentException e){
                System.out.println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }
        }

        // 기능 3.
        countMenu(menus);
        countPrice();

        showResult();
    }

    public static void showResult(){
        // 주문 메뉴를 확인해준다.
        showMenu();
        // 할인 전 총 금액을 보여준다.
        showTotal();
        // 증정 메뉴를 보여준다.
        showGift();
        // 혜택 내역을 보여준다.
        showTotalEventList();
        // 총혜택 금액을 보여준다.
        showTotalEventPrice();
        // 할인 후 예상 결제 금액을 보여준다.
        showPriceAfterDiscount();
        // 12월 이벤트 배지를 보여준다.
        showBadge();
    }

    public static EnumMap<Menu, Integer> menuCount = new EnumMap<>(Menu.class);
    public static int totalPrice = 0;
    public static int totalEventPrice = 0;
    public static Map<String, Integer> event = new HashMap<>();
    public static void countMenu(List<String> menus){
        for(String menu: menus){
            String korMenu = menu.split(MENU_DIVIDER)[0];

            Menu targetMenu = Arrays.stream(Menu.values()).filter(m->m.getName().equals(korMenu)).findAny().get();
            menuCount.put(targetMenu, menuCount.getOrDefault(menuCount.get(targetMenu), 1));
        }
    }
    public static void showMenu(){
        System.out.println("<주문 메뉴>");
        for(Entry<Menu, Integer> m: menuCount.entrySet()){
            System.out.println(m.getKey().getName() + " " + m.getValue().toString()+"개");
        }

    }
    public static void showTotal(){
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(totalPrice);
    }
    public static void showGift(){
        System.out.println("<증정 메뉴>");
        if(event.containsKey("증정 이벤트")){
            System.out.println(Menu.valueOf(GIVING_EVENT_PRODUCT) + " " + "1개");
        }else {
            System.out.println("없음");
        }
    }
    public static void showTotalEventList(){
        System.out.println("<혜택 내역>");
        if(event.isEmpty()){
            System.out.println("없음");
            return;
        }
        for(Entry<String, Integer> m: event.entrySet()){
            System.out.println(m.getKey() + ": -" +m.getValue());
        }
    }
    public static void showTotalEventPrice(){
        System.out.println("<총혜택 금액>");
        for(Integer m: event.values()){
            totalEventPrice+=m;
        }
        System.out.printf("%d원%n", totalEventPrice);
    }
    public static void showPriceAfterDiscount(){
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.printf("%d원%n", totalPrice - totalEventPrice);
    }
    public static void showBadge(){
        System.out.printf("<%d월 이벤트 배지>%n", TARGET_MONTH);
        String badge;
        if(totalEventPrice >= 20000){
            badge = "산타";
        } else if (totalEventPrice >= 10000) {
            badge = "트리";
        } else if (totalEventPrice >= 5000) {
            badge = "별";
        }else{
            badge = "없다";
        }
        System.out.println(badge);
    }
    public static void countPrice(){
        // 총액 계산
        countTotal();
        // 일자 할인 계산
        countDiscountByDate();
        // 증정 이벤트 계산
        countGivingEvent();
    }
    public static void countTotal(){
        for(Menu menu: menuCount.keySet()){
           int curr = menu.getPrice() * menuCount.get(menu);
           totalPrice+= curr;
        }
    }
    public static void countDiscountByDate(){
        List<Integer> specialDay = SPECIAL_DISCOUNT_DATE;
        // 평일 이벤트
        LocalDate date = LocalDate.of(TARGET_YEAR, TARGET_MONTH, eventDay);
        String day = date.getDayOfWeek().toString();
        if(isWeekend(day)){
            int count = 0;
            for(Menu m : menuCount.keySet()){
                List<Menu> menu = MenuGroup.valueOf(WEEKEND_DISCOUNT_MENU_GROUP).getMenuGroup();
                if(menu.contains(m)){
                    count++;
                }
            }
            if(count > 0){
                event.put("주말 할인", count * DATE_DISCOUNT_PRICE);
            }
        }
        if(!isWeekend(day)){
            int count = 0;
            for(Menu m : menuCount.keySet()){
                List<Menu> menu = MenuGroup.valueOf(WEEKDAY_DISCOUNT_MENU_GROUP).getMenuGroup();
                if(menu.contains(m)){
                    count++;
                }
            }
            if(count > 0){
                event.put("평일 할인", count * DATE_DISCOUNT_PRICE);
            }
        }
        if(eventDay <= 25){
            event.put("크리스마스 디데이 할인", (eventDay - 1) * 100 + 1000);
        }
        if(specialDay.contains(eventDay)){
            event.put("특별 할인", 1000);
        }

    }

    public static boolean isWeekend(String day){
        return day == "SATURDAY" || day == "SUNDAY";
    }
    public static void countGivingEvent(){
        if(totalPrice >= GIVING_EVENT_MIN_BOUND){
            event.put("증정 이벤트", 25000);
        }

    }
    public static void validateEventDate(int day){
        if(day < MIN_DAY || day > MAX_DAY){
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }
    public static List<String> menuOneByOne(String menuBeforeValidated){
        return List.of(menuBeforeValidated.split(MENU_SPLITTER));
    }

    public static void validateMenu(List<String> menus){
        checkDivider(menus);
        List<String> m = menus.stream().map(menu-> menu.split(MENU_DIVIDER)[0]).collect(Collectors.toList());
        if(!checkMenuExists(m)){
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
        if(isMenuDuplicate(m)){
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
        if(checkOnlyBeverageExist(m)){
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }
    public static void checkDivider(List<String> menus){
        for(String menu: menus){
            if(!menu.contains(MENU_DIVIDER)){
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }
        }

    }
    public static boolean checkMenuExists(List<String> menus){

        for(String m: menus){
            if(!Arrays.stream(Menu.values()).anyMatch(s -> s.getName().equals(m))){
                return false;
            };
        }
        return true;
    }
    public static boolean isMenuDuplicate(List<String> menus){
        return new HashSet<>(menus).size() != menus.size();
    }

    public static boolean checkOnlyBeverageExist(List<String> menus){
        for(String menu: menus){
            boolean haveName = MenuGroup.BEVERAGE.getMenuGroup().stream().anyMatch(b -> b.getName().equals(menu));
            if(haveName == false){
                return false;
            }
        }
        return true;

    }
    public static void validateMenuCount(List<String> menus){
        List<String> m = menus.stream().map(menu-> menu.split(MENU_DIVIDER)[1]).collect(Collectors.toList());
        // 개수 20개 초과인지 확인
        // 숫자로 파싱 가능한지 확인

        for(String s: m){
            try {
                checkOrderCount(Integer.parseInt(s));
                checkZeroCount(Integer.parseInt(s));
            }catch (NumberFormatException e){
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }
    static int orderCount = 0;
    public static void checkOrderCount(int count){

        orderCount+= count;
        if(orderCount > MAX_ORDER_COUNT){
            orderCount = 0;
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");

        }
    }
    public static void checkZeroCount(int count){
        if(count < 1){
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }
}
