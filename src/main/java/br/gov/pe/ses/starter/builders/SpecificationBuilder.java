package br.gov.pe.ses.starter.builders;

import jakarta.persistence.criteria.*;

import java.util.*;
import java.util.function.Function;

public class SpecificationBuilder<T> {
    private final Root<T> root;
    private final CriteriaBuilder cb;
    private final List<Predicate> predicates = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private Join<?, ?> currentJoin;

    private SpecificationBuilder(Root<T> root, CriteriaBuilder cb) {
        this.root = Objects.requireNonNull(root);
        this.cb = Objects.requireNonNull(cb);
    }

    public static <T> SpecificationBuilder<T> create(Root<T> root, CriteriaBuilder cb) {
        return new SpecificationBuilder<>(root, cb);
    }

    // ========== OPERADORES BÁSICOS ========== //
    public SpecificationBuilder<T> whereEqual(String field, Object value) {
        if (value != null) {
            predicates.add(cb.equal(resolvePath(field), value));
        }
        return this;
    }

    public SpecificationBuilder<T> whereNotEqual(String field, Object value) {
        if (value != null) {
            predicates.add(cb.notEqual(resolvePath(field), value));
        }
        return this;
    }

    public SpecificationBuilder<T> whereLike(String field, String value) {
        if (value != null && !value.isEmpty()) {
            MatchMode anywhere = MatchMode.ANYWHERE;
            predicates.add(cb.like(resolvePath(field), anywhere.toPattern(value)));
        }
        return this;
    }

    public SpecificationBuilder<T> whereLike(String field, String value, MatchMode mode) {
        if (value != null && !value.isEmpty()) {
            predicates.add(cb.like(resolvePath(field), mode.toPattern(value)));
        }
        return this;
    }

    public SpecificationBuilder<T> whereIlike(String field, String value) {
        if (value != null && !value.isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(resolvePath(field)),
                    "%" + value.toLowerCase() + "%"
            ));
        }
        return this;
    }

    public SpecificationBuilder<T> whereIlike(String field, String value, MatchMode mode) {
        if (value != null && !value.isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(resolvePath(field)),
                    mode.toPattern(value.toLowerCase())
            ));
        }
        return this;
    }

    public SpecificationBuilder<T> whereIn(String field, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            predicates.add(resolvePath(field).in(values));
        }
        return this;
    }

    public SpecificationBuilder<T> whereNotIn(String field, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            predicates.add(cb.not(resolvePath(field).in(values)));
        }
        return this;
    }

    // ========== OPERADORES DE COMPARAÇÃO ========== //
    public <Y extends Comparable<? super Y>> SpecificationBuilder<T> whereBetween(
            String field, Y start, Y end) {
        Path<Y> path = resolvePath(field);
        if (start != null) predicates.add(cb.greaterThanOrEqualTo(path, start));
        if (end != null) predicates.add(cb.lessThanOrEqualTo(path, end));
        return this;
    }

    public <Y extends Comparable<? super Y>> SpecificationBuilder<T> whereGreaterThan(
            String field, Y value) {
        if (value != null) {
            predicates.add(cb.greaterThan(resolvePath(field), value));
        }
        return this;
    }

    // ========== OPERADORES LÓGICOS ========== //
    public SpecificationBuilder<T> whereTrue(Expression<Boolean> expression) {
        if (expression != null) {
            predicates.add(cb.isTrue(expression));
        }
        return this;
    }

    public SpecificationBuilder<T> whereFalse(Expression<Boolean> expression) {
        if (expression != null) {
            predicates.add(cb.isFalse(expression));
        }
        return this;
    }

    public SpecificationBuilder<T> whereNull(String field) {
        predicates.add(cb.isNull(resolvePath(field)));
        return this;
    }

    public SpecificationBuilder<T> whereNotNull(String field) {
        predicates.add(cb.isNotNull(resolvePath(field)));
        return this;
    }

    // ========== JOINS E ASSOCIAÇÕES ========== //
    public <Y> SpecificationBuilder<T> join(String associationPath,
                                            Function<Join<T, Y>, SpecificationBuilder<T>> joinFunction) {
        Join<T, Y> join = root.join(associationPath);
        return joinFunction.apply(join);
    }

    public <Y> SpecificationBuilder<T> leftJoin(String associationPath,
                                                Function<Join<T, Y>, SpecificationBuilder<T>> joinFunction) {
        Join<T, Y> join = root.join(associationPath, JoinType.LEFT);
        return joinFunction.apply(join);
    }

    // ========== ORDENAÇÃO ========== //
    public SpecificationBuilder<T> orderBy(String field, boolean ascending) {
        Path<?> path = resolvePath(field);
        orders.add(ascending ? cb.asc(path) : cb.desc(path));
        return this;
    }

    // ========== FUNÇÕES ESPECIAIS ========== //
    public SpecificationBuilder<T> whereDateEqual(String field, Date date) {
        if (date != null) {
            Expression<Date> dateExpr = cb.function("TRUNC", Date.class, resolvePath(field));
            predicates.add(cb.equal(dateExpr, date));
        }
        return this;
    }

    // ========== BUILDERS FINAIS ========== //
    public Predicate build() {
        return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
    }

    public Predicate buildOr() {
        return predicates.isEmpty() ? cb.disjunction() : cb.or(predicates.toArray(new Predicate[0]));
    }

    public void applyToQuery(CriteriaQuery<?> query) {
        if (!orders.isEmpty()) {
            query.orderBy(orders);
        }
        query.distinct(true);
    }

    // ========== UTILITÁRIOS ========== //
    @SuppressWarnings("unchecked")
    private <Y> Path<Y> resolvePath(String fieldPath) {
        String[] parts = fieldPath.split("\\.");
        Path<?> path = currentJoin != null ? currentJoin : root;

        for (String part : parts) {
            path = path.get(part);
        }
        return (Path<Y>) path;
    }

    public enum MatchMode {
        START, END, ANYWHERE, EXACT;

        public String toPattern(String value) {
            return switch (this) {
                case START -> value + "%";
                case END -> "%" + value;
                case ANYWHERE -> "%" + value + "%";
                case EXACT -> value;
            };
        }
    }
}