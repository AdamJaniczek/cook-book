package pl.com.itsystems.cookbook.category;

public enum CategoryIcon {
    UNKNOWN("unknown.png"),
    BREAKFAST_1("breakfast_1.png"),
    BREAKFAST_2("breakfast_2.png"),
    BREAKFAST_3("breakfast_3.png"),
    LUNCH_1("lunch_1.png"),
    LUNCH_2("lunch_2.png"),
    LUNCH_3("lunch_3.png"),
    DINNER_1("dinner_1.png"),
    DINNER_2("dinner_2.png"),
    DINNER_3("dinner_3.png");

    private String fileName;

    CategoryIcon(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
