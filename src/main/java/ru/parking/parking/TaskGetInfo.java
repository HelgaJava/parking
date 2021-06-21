package ru.parking.parking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.parking.car.ModelCar;
import ru.parking.car.TypeCar;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskGetInfo extends TimerTask {
    private int amountPlaces;

    @Override
    public void run() {
        int amountQueue = TaskQueueIn.getInputQueue().size(); //кол-во авто в очереди
        List<ModelCar> modelCars = new ArrayList<>(); //инициализируем массив для того, чтобы избежать исключения ConcurrentModificationException
        modelCars = TaskParkingIn.getPlacesParking(); //список припаркованных авто
        //далее вычисляем нужные значения и выводим информацию
        int freePlaces = getAmountPlaces() - modelCars.size();
        int notFreePlaces = modelCars.size();
        int amountCar = 0;
        int amountTruck = 0;
        Iterator<ModelCar> iterator = modelCars.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getTypeCar() == TypeCar.CAR) {
                amountCar++;
            } else amountTruck++;
        }
        amountTruck = amountTruck/2;
        System.out.println("Свободных мест: " + freePlaces);
        System.out.println("Занято мест: " + notFreePlaces + " из них легковых " + amountCar + ", грузовых " + amountTruck);
        System.out.println("Автомобилей, ожидающих в очереди: " + amountQueue);

    }


}
