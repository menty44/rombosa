//package com.example.easynotes.model;
//
//
///**
// * Created by admin on 9/7/18.
// */
//
//import org.springframework.cglib.core.Predicate;
//import org.springframework.data.jpa.domain.Specification;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//
//import static com.example.easynotes.controller.SearchOperation.*;
//
///**
// * Fredrick Oluoch
// * http://www.blaqueyard.com
// * 0720106420 | 0722508906
// * email: menty44@gmail.com
// */
//
//public class UserSpecification implements Specification<User> {
//
//    private SearchCriteria criteria;
//
//    @Override
//    public Predicate toPredicate(
//            Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//
//        switch (criteria.getOperation()) {
//            case EQUALITY:
//                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
//            case NEGATION:
//                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
//            case GREATER_THAN:
//                return builder.greaterThan(root.<String> get(
//                        criteria.getKey()), criteria.getValue().toString());
//            case LESS_THAN:
//                return builder.lessThan(root.<String> get(
//                        criteria.getKey()), criteria.getValue().toString());
//            case LIKE:
//                return builder.like(root.<String> get(
//                        criteria.getKey()), criteria.getValue().toString());
//            case STARTS_WITH:
//                return builder.like(root.<String> get(criteria.getKey()), criteria.getValue() + "%");
//            case ENDS_WITH:
//                return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue());
//            case CONTAINS:
//                return builder.like(root.<String> get(
//                        criteria.getKey()), "%" + criteria.getValue() + "%");
//            default:
//                return null;
//        }
//    }
//}
