package ru.parking.parking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.parking.car.ModelCar;
import ru.parking.car.TypeCar;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskQueueIn extends TimerTask {
    private Timer timer;
    private int maxLengthQueue;
    private int timeQueue;
    private static int i = 0;
    private static ArrayDeque<ModelCar> modelCarsQueue = new ArrayDeque<>();

    @Override
    public void run() {
        i++; //получаем id нового авто
        getInputAuto(i); //ставим авто с новым id  в очередь
        if (modelCarsQueue.size() == maxLengthQueue) { //проверка на наличие свободных мест в очереди. Если мест нет - завершаем работу парковки
            System.out.println("Места в очереди для парковки закончились!");
            timer.cancel();
            System.exit(-1);

        }

    }

    private void getInputAuto(int id) {
        TypeCar typeCar;
        Random random = new Random();
        int i = random.nextInt(10); //генерируем случайное число для определние вероятности выбора типа авто
        if (i <= 2) { //грузовки генерируются с вероятность 20%
            typeCar = TypeCar.TRUCK;
        } else typeCar = TypeCar.CAR;

        //получаем авто и ставим в очередь
        ModelCar modelCar = new ModelCar(id, typeCar);
        modelCarsQueue.addLast(modelCar);
        System.out.println(modelCar.getTypeCar() + " с id = " + id + " встал в очередь на въезд.");
    }

    public static ArrayDeque<ModelCar> getInputQueue() {
        return modelCarsQueue;
    }


}
