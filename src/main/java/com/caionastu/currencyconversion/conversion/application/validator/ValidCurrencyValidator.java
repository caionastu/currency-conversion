package com.caionastu.currencyconversion.conversion.application.validator;

import com.caionastu.currencyconversion.conversion.domain.Currency;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ValidCurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Class<Currency> targetClass = Currency.class;
        String field = "initials";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Currency> query = criteriaBuilder.createQuery(targetClass);
        Root<?> root = query.from(targetClass);
        query.select(root.get(field));
        query.where(criteriaBuilder.equal(root.get(field), value.toUpperCase()));

        return !entityManager.createQuery(query)
                .getResultList()
                .isEmpty();
    }
}
