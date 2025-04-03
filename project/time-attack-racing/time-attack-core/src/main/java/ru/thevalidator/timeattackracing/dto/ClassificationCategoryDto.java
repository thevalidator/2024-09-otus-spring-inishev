package ru.thevalidator.timeattackracing.dto;

public class ClassificationCategoryDto {

    private Integer id;

    private String name;

    private Integer maxPowerLimit;

    private String wheelDriveType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxPowerLimit() {
        return maxPowerLimit;
    }

    public void setMaxPowerLimit(Integer maxPowerLimit) {
        this.maxPowerLimit = maxPowerLimit;
    }

    public String getWheelDriveType() {
        return wheelDriveType;
    }

    public void setWheelDriveType(String wheelDriveType) {
        this.wheelDriveType = wheelDriveType;
    }

}
