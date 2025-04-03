package ru.thevalidator.timeattackracing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "classification_category")
public class ClassificationCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "max_power", nullable = false)
    private Integer maxPower;

    @Size(max = 255)
    @NotNull
    @Column(name = "wheel_drive_type", nullable = false)
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

    public Integer getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Integer maxPower) {
        this.maxPower = maxPower;
    }

    public String getWheelDriveType() {
        return wheelDriveType;
    }

    public void setWheelDriveType(String wheelDriveType) {
        this.wheelDriveType = wheelDriveType;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ClassificationCategoryEntity category)) return false;

        return name.equals(category.name)
                && maxPower.equals(category.maxPower)
                && wheelDriveType.equals(category.wheelDriveType);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + maxPower.hashCode();
        result = 31 * result + wheelDriveType.hashCode();
        return result;
    }

}