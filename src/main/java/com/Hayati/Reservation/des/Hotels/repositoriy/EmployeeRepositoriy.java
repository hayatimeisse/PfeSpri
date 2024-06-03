package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hayati.Reservation.des.Hotels.entity.Employee;

public interface EmployeeRepositoriy extends JpaRepository<Employee, Long> {
    
}
