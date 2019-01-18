package com.superwallet.pojo;

import java.util.ArrayList;
import java.util.List;

public class EthvalidationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EthvalidationExample() {
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

        public Criteria andTransferidIsNull() {
            addCriterion("transferId is null");
            return (Criteria) this;
        }

        public Criteria andTransferidIsNotNull() {
            addCriterion("transferId is not null");
            return (Criteria) this;
        }

        public Criteria andTransferidEqualTo(Long value) {
            addCriterion("transferId =", value, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidNotEqualTo(Long value) {
            addCriterion("transferId <>", value, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidGreaterThan(Long value) {
            addCriterion("transferId >", value, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidGreaterThanOrEqualTo(Long value) {
            addCriterion("transferId >=", value, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidLessThan(Long value) {
            addCriterion("transferId <", value, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidLessThanOrEqualTo(Long value) {
            addCriterion("transferId <=", value, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidIn(List<Long> values) {
            addCriterion("transferId in", values, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidNotIn(List<Long> values) {
            addCriterion("transferId not in", values, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidBetween(Long value1, Long value2) {
            addCriterion("transferId between", value1, value2, "transferid");
            return (Criteria) this;
        }

        public Criteria andTransferidNotBetween(Long value1, Long value2) {
            addCriterion("transferId not between", value1, value2, "transferid");
            return (Criteria) this;
        }

        public Criteria andHashvalueIsNull() {
            addCriterion("hashValue is null");
            return (Criteria) this;
        }

        public Criteria andHashvalueIsNotNull() {
            addCriterion("hashValue is not null");
            return (Criteria) this;
        }

        public Criteria andHashvalueEqualTo(String value) {
            addCriterion("hashValue =", value, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueNotEqualTo(String value) {
            addCriterion("hashValue <>", value, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueGreaterThan(String value) {
            addCriterion("hashValue >", value, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueGreaterThanOrEqualTo(String value) {
            addCriterion("hashValue >=", value, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueLessThan(String value) {
            addCriterion("hashValue <", value, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueLessThanOrEqualTo(String value) {
            addCriterion("hashValue <=", value, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueLike(String value) {
            addCriterion("hashValue like", value, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueNotLike(String value) {
            addCriterion("hashValue not like", value, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueIn(List<String> values) {
            addCriterion("hashValue in", values, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueNotIn(List<String> values) {
            addCriterion("hashValue not in", values, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueBetween(String value1, String value2) {
            addCriterion("hashValue between", value1, value2, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andHashvalueNotBetween(String value1, String value2) {
            addCriterion("hashValue not between", value1, value2, "hashvalue");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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