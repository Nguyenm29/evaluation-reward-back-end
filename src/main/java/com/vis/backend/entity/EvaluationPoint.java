package com.vis.backend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "evaluation_point")
public class EvaluationPoint {
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

    @Column(name = "day_off")
    private Float dayOff;

    @Column(name = "day_work")
    private Float dayWork;

    @Column(name = "late_time")
    private Integer lateTime;

    @Column(name = "over_time")
    private Float overTime;

    @Column(name = "point")
    private Float point;

    @Column(name = "date")
    private java.sql.Timestamp date;
}
