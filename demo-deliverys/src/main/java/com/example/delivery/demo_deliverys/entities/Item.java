    package com.example.delivery.demo_deliverys.entities;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;


    @NoArgsConstructor
    @AllArgsConstructor

    @Entity
    public class Item {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private long id;
        private String name;
        private float weight;
        private String code;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vehicle_id")
        @JsonBackReference
        @JsonIgnore
        private Vehicle vehicle;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        public Vehicle getVehicle() { return vehicle; }
        public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }


    }
