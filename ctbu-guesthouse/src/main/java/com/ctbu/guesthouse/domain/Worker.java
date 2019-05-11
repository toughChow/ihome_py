package com.ctbu.guesthouse.domain;

import javax.persistence.*;

@Entity
@Table(name = "gh_worker")
public class Worker {

    @Id
    @Column(name = "worker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String workerName;

    @Column(length = 32)
    private String workerAddress;

    @Column(length = 32)
    private String workerPosition;

    @Column(length = 32)
    private String workerSex;

    @Column
    private Float workerWages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerAddress() {
        return workerAddress;
    }

    public void setWorkerAddress(String workerAddress) {
        this.workerAddress = workerAddress;
    }

    public String getWorkerPosition() {
        return workerPosition;
    }

    public void setWorkerPosition(String workerPosition) {
        this.workerPosition = workerPosition;
    }

    public String getWorkerSex() {
        return workerSex;
    }

    public void setWorkerSex(String workerSex) {
        this.workerSex = workerSex;
    }

    public Float getWorkerWages() {
        return workerWages;
    }

    public void setWorkerWages(Float workerWages) {
        this.workerWages = workerWages;
    }
}
