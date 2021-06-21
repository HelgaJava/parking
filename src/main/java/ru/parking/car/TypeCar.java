package ru.parking.car;

//класс с перечисляемым типом для типов авто
public enum TypeCar {
    CAR("Легковой автомобиль"),
    TRUCK("Грузовой автомобиль");

    private String value;

    TypeCar(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
