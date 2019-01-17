package com.superwallet.pojo;

import java.util.ArrayList;
import java.util.List;

public class OptconfExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OptconfExample() {
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

        public Criteria andConfnameIsNull() {
            addCriterion("confName is null");
            return (Criteria) this;
        }

        public Criteria andConfnameIsNotNull() {
            addCriterion("confName is not null");
            return (Criteria) this;
        }

        public Criteria andConfnameEqualTo(String value) {
            addCriterion("confName =", value, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameNotEqualTo(String value) {
            addCriterion("confName <>", value, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameGreaterThan(String value) {
            addCriterion("confName >", value, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameGreaterThanOrEqualTo(String value) {
            addCriterion("confName >=", value, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameLessThan(String value) {
            addCriterion("confName <", value, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameLessThanOrEqualTo(String value) {
            addCriterion("confName <=", value, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameLike(String value) {
            addCriterion("confName like", value, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameNotLike(String value) {
            addCriterion("confName not like", value, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameIn(List<String> values) {
            addCriterion("confName in", values, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameNotIn(List<String> values) {
            addCriterion("confName not in", values, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameBetween(String value1, String value2) {
            addCriterion("confName between", value1, value2, "confname");
            return (Criteria) this;
        }

        public Criteria andConfnameNotBetween(String value1, String value2) {
            addCriterion("confName not between", value1, value2, "confname");
            return (Criteria) this;
        }

        public Criteria andConfvalueIsNull() {
            addCriterion("confValue is null");
            return (Criteria) this;
        }

        public Criteria andConfvalueIsNotNull() {
            addCriterion("confValue is not null");
            return (Criteria) this;
        }

        public Criteria andConfvalueEqualTo(String value) {
            addCriterion("confValue =", value, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueNotEqualTo(String value) {
            addCriterion("confValue <>", value, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueGreaterThan(String value) {
            addCriterion("confValue >", value, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueGreaterThanOrEqualTo(String value) {
            addCriterion("confValue >=", value, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueLessThan(String value) {
            addCriterion("confValue <", value, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueLessThanOrEqualTo(String value) {
            addCriterion("confValue <=", value, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueLike(String value) {
            addCriterion("confValue like", value, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueNotLike(String value) {
            addCriterion("confValue not like", value, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueIn(List<String> values) {
            addCriterion("confValue in", values, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueNotIn(List<String> values) {
            addCriterion("confValue not in", values, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueBetween(String value1, String value2) {
            addCriterion("confValue between", value1, value2, "confvalue");
            return (Criteria) this;
        }

        public Criteria andConfvalueNotBetween(String value1, String value2) {
            addCriterion("confValue not between", value1, value2, "confvalue");
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