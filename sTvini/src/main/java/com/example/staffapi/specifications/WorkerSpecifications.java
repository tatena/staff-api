package com.example.staffapi.specifications;

import com.example.staffapi.enitity.Worker;
import org.springframework.data.jpa.domain.Specification;

public class WorkerSpecifications {

    public static Specification<Worker> firstNameLike(String firstName) {
        return ((root, query, cb) -> cb.like(root.get("firstName"),"%" + firstName + "%"));
    }


    public static Specification<Worker> lastNameLike(String lastName) {
        return ((root, query, cb) -> cb.like(root.get("lastName"),"%" + lastName + "%"));
    }

}
