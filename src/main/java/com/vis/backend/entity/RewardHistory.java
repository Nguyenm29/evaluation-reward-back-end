package com.vis.backend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reward_history")
public class RewardHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "modified_at")
    private java.sql.Timestamp modifiedAt;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "plus_point")
    private Float plusPoint;

    @Column(name = "point_use")
    private Float pointUse;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "date")
    private java.sql.Timestamp date;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public java.sql.Timestamp getModifiedAt() {
        return this.modifiedAt;
    }

    public void setModifiedAt(java.sql.Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public java.sql.Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Float getPlusPoint() {
        return this.plusPoint;
    }

    public void setPlusPoint(Float plusPoint) {
        this.plusPoint = plusPoint;
    }

    public Float getPointUse() {
        return this.pointUse;
    }

    public void setPointUse(Float pointUse) {
        this.pointUse = pointUse;
    }

    public Long getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public java.sql.Timestamp getDate() {
        return this.date;
    }

    public void setDate(java.sql.Timestamp date) {
        this.date = date;
    }
}
