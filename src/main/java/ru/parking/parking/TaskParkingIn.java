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
public class TaskParkingIn extends TimerTask {
    private Timer timer;
    private int amountPlaces;
    private int timeParking;
    private static List<ModelCar> modelCarsParking = new ArrayList<>();

    @Override
    public void run() {
        //получаем очередь с авто, т.к. оттуда будем брать авто для парковки
        ArrayDeque<ModelCar> modelCarsQueue = TaskQueueIn.getInputQueue();

        //проверяем наличие свободных мест на парковке. Если все ОК, вызываем методы на выход/вход
        if (modelCarsParking.size() < amountPlaces) {
            leaveParking();
            toPark(modelCarsQueue);
        }

    }

    private void leaveParking() {
        if (modelCarsParking.size() > 0) {
            //генерируем слуяайное число для получения случайного индекса для выезда с парковки
            Random random = new Random();
            int i = random.nextInt(modelCarsParking.size()); //индекс должен быть в пределах занятых местж
            for (int j = 0; j < modelCarsParking.size(); j++) {
                //проверяем случайный индекс на налдичие такого же в списке припаркованных авто. Если он есть - удлаяем авто с парковки
                if (i == j) {
                    ModelCar modelFirstCarParking = modelCarsParking.get(i); // получаем удаляемое авто по индексу
                    modelCarsParking.remove(i);
                    //проверяем является ли авто грузовкиом, если да - находим объект с таким же id в списке (по условию грузовик занимает 2 места) и удалем его
                    if (modelFirstCarParking.getTypeCar() == TypeCar.TRUCK) {
                        int j2 = modelCarsParking.indexOf(modelFirstCarParking);
                        modelCarsParking.remove(j2);
                    }
                    System.out.println(modelFirstCarParking.getTypeCar() + " с id = " + modelFirstCarParking.getId() + " покинул парковку.");
                }

            }
        }

    }

    private void toPark(ArrayDeque<ModelCar> modelCarsQueue) {
        //получаем 1-й авто в очереди на парковку
        ModelCar modelFirstQueue = modelCarsQueue.peekFirst();
        if (modelFirstQueue != null) {
            if (modelFirstQueue.getTypeCar() == TypeCar.CAR) { //если это легковой атомобиль - добавляем его в списко и удалем из очереди на паркову
                modelCarsParking.add(modelFirstQueue);
                modelFirstQueue = modelCarsQueue.pollFirst();
            } else { //если это грузовки - прверяем наличие свободных мест в количестве 2 и более и если все ОК - занимае 2 места на парковке и удаляем авто из очереди
                int checkPlaces = getAmountPlaces() - modelCarsParking.size();
                if (checkPlaces >= 2) {
                    modelCarsParking.add(modelFirstQueue);
                    modelCarsParking.add(modelFirstQueue);
                    modelFirstQueue = modelCarsQueue.pollFirst();
                }

            }
            System.out.println(modelFirstQueue.getTypeCar() + " с id = " + modelFirstQueue.getId() + " припарковался.");
        }
        for (ModelCar modelCar : modelCarsParking) {
            System.out.println("На парковке сейчас: " + modelCar);
        }
    }


    public static List<ModelCar> getPlacesParking() {
        return modelCarsParking;
    }



}
