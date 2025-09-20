package app.domain;

public enum BikeType {
    MOUNTAIN("горный"),
    GRAVEL("гравийный"),
    ROAD("шоссейный"),
    CITY("городской"),
    ELECTRIC("электрический");

    private final String russianType;

    BikeType(String russianType) {
        this.russianType = russianType;
    }

    public final String getRussianName() {
        return russianType;
    }

    @Override
    public String toString() {
        return russianType;
    }
}
