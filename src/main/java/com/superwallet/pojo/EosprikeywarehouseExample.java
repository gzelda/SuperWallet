package com.superwallet.pojo;

import java.util.ArrayList;
import java.util.List;

public class EosprikeywarehouseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EosprikeywarehouseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andUidIsNull() {
            addCriterion("UID is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("UID is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(String value) {
            addCriterion("UID =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(String value) {
            addCriterion("UID <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(String value) {
            addCriterion("UID >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(String value) {
            addCriterion("UID >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(String value) {
            addCriterion("UID <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(String value) {
            addCriterion("UID <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLike(String value) {
            addCriterion("UID like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotLike(String value) {
            addCriterion("UID not like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<String> values) {
            addCriterion("UID in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<String> values) {
            addCriterion("UID not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(String value1, String value2) {
            addCriterion("UID between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(String value1, String value2) {
            addCriterion("UID not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyIsNull() {
            addCriterion("ownerPriKey is null");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyIsNotNull() {
            addCriterion("ownerPriKey is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyEqualTo(String value) {
            addCriterion("ownerPriKey =", value, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyNotEqualTo(String value) {
            addCriterion("ownerPriKey <>", value, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyGreaterThan(String value) {
            addCriterion("ownerPriKey >", value, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyGreaterThanOrEqualTo(String value) {
            addCriterion("ownerPriKey >=", value, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyLessThan(String value) {
            addCriterion("ownerPriKey <", value, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyLessThanOrEqualTo(String value) {
            addCriterion("ownerPriKey <=", value, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyLike(String value) {
            addCriterion("ownerPriKey like", value, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyNotLike(String value) {
            addCriterion("ownerPriKey not like", value, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyIn(List<String> values) {
            addCriterion("ownerPriKey in", values, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyNotIn(List<String> values) {
            addCriterion("ownerPriKey not in", values, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyBetween(String value1, String value2) {
            addCriterion("ownerPriKey between", value1, value2, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andOwnerprikeyNotBetween(String value1, String value2) {
            addCriterion("ownerPriKey not between", value1, value2, "ownerprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyIsNull() {
            addCriterion("activePriKey is null");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyIsNotNull() {
            addCriterion("activePriKey is not null");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyEqualTo(String value) {
            addCriterion("activePriKey =", value, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyNotEqualTo(String value) {
            addCriterion("activePriKey <>", value, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyGreaterThan(String value) {
            addCriterion("activePriKey >", value, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyGreaterThanOrEqualTo(String value) {
            addCriterion("activePriKey >=", value, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyLessThan(String value) {
            addCriterion("activePriKey <", value, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyLessThanOrEqualTo(String value) {
            addCriterion("activePriKey <=", value, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyLike(String value) {
            addCriterion("activePriKey like", value, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyNotLike(String value) {
            addCriterion("activePriKey not like", value, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyIn(List<String> values) {
            addCriterion("activePriKey in", values, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyNotIn(List<String> values) {
            addCriterion("activePriKey not in", values, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyBetween(String value1, String value2) {
            addCriterion("activePriKey between", value1, value2, "activeprikey");
            return (Criteria) this;
        }

        public Criteria andActiveprikeyNotBetween(String value1, String value2) {
            addCriterion("activePriKey not between", value1, value2, "activeprikey");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}