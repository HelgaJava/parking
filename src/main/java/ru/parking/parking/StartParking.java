package ru.parking.parking;

import java.util.Scanner;
import java.util.Timer;

public class StartParking {
    private static Scanner scanner = new Scanner(System.in);
    private static final int periodInfo = 5;

    public static void getStartValue(){
        //запрос на ввод начальных значений
        System.out.println("Введите количество парковочных мест: ");
        int amountPlaces = getValue();

        System.out.println("Введите максимальную длину очереди автомобилей: ");
        int lengthQueue = getValue();

        System.out.println("Введите интервал для входа авто: ");
        int timeInput = getValue();

        System.out.println("Введите интервал для выхода авто: ");
        int timeOutput = getValue();

        //создаем таймеры для каждого вида задач. Поскольку таймер работает в своем потоке отдельно Thread не генерируем.
        //поскольку изначально парковка пуста, то тоймеры стартуют с задержкой в 1 мс
        Timer timerIn = new Timer();
        timerIn.purge();
        timerIn.schedule(new TaskQueueIn(timerIn, lengthQueue, timeInput), 0, timeInput * 1000l);

        Timer timerOut = new Timer();
        timerOut.purge();
        timerOut.schedule(new TaskParkingIn(timerOut, amountPlaces, timeOutput), 1, timeOutput * 1000l);

        Timer timerInfo = new Timer();
        timerInfo.purge();
        timerInfo.schedule(new TaskGetInfo(amountPlaces), 2, periodInfo*1000l);

    }

    private static int getValue() {
        int number = 0;
        if (scanner.hasNextInt()){
            number = scanner.nextInt();
        }
        return number;
    }
}
