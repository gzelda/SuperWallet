package com.superwallet.pojo;

import java.util.ArrayList;
import java.util.List;

public class EthtokenExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EthtokenExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andEthaddressIsNull() {
            addCriterion("ETHAddress is null");
            return (Criteria) this;
        }

        public Criteria andEthaddressIsNotNull() {
            addCriterion("ETHAddress is not null");
            return (Criteria) this;
        }

        public Criteria andEthaddressEqualTo(String value) {
            addCriterion("ETHAddress =", value, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressNotEqualTo(String value) {
            addCriterion("ETHAddress <>", value, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressGreaterThan(String value) {
            addCriterion("ETHAddress >", value, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressGreaterThanOrEqualTo(String value) {
            addCriterion("ETHAddress >=", value, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressLessThan(String value) {
            addCriterion("ETHAddress <", value, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressLessThanOrEqualTo(String value) {
            addCriterion("ETHAddress <=", value, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressLike(String value) {
            addCriterion("ETHAddress like", value, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressNotLike(String value) {
            addCriterion("ETHAddress not like", value, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressIn(List<String> values) {
            addCriterion("ETHAddress in", values, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressNotIn(List<String> values) {
            addCriterion("ETHAddress not in", values, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressBetween(String value1, String value2) {
            addCriterion("ETHAddress between", value1, value2, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andEthaddressNotBetween(String value1, String value2) {
            addCriterion("ETHAddress not between", value1, value2, "ethaddress");
            return (Criteria) this;
        }

        public Criteria andLockedamountIsNull() {
            addCriterion("lockedAmount is null");
            return (Criteria) this;
        }

        public Criteria andLockedamountIsNotNull() {
            addCriterion("lockedAmount is not null");
            return (Criteria) this;
        }

        public Criteria andLockedamountEqualTo(Double value) {
            addCriterion("lockedAmount =", value, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountNotEqualTo(Double value) {
            addCriterion("lockedAmount <>", value, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountGreaterThan(Double value) {
            addCriterion("lockedAmount >", value, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountGreaterThanOrEqualTo(Double value) {
            addCriterion("lockedAmount >=", value, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountLessThan(Double value) {
            addCriterion("lockedAmount <", value, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountLessThanOrEqualTo(Double value) {
            addCriterion("lockedAmount <=", value, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountIn(List<Double> values) {
            addCriterion("lockedAmount in", values, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountNotIn(List<Double> values) {
            addCriterion("lockedAmount not in", values, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountBetween(Double value1, Double value2) {
            addCriterion("lockedAmount between", value1, value2, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andLockedamountNotBetween(Double value1, Double value2) {
            addCriterion("lockedAmount not between", value1, value2, "lockedamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountIsNull() {
            addCriterion("availableAmount is null");
            return (Criteria) this;
        }

        public Criteria andAvailableamountIsNotNull() {
            addCriterion("availableAmount is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableamountEqualTo(Double value) {
            addCriterion("availableAmount =", value, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountNotEqualTo(Double value) {
            addCriterion("availableAmount <>", value, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountGreaterThan(Double value) {
            addCriterion("availableAmount >", value, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountGreaterThanOrEqualTo(Double value) {
            addCriterion("availableAmount >=", value, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountLessThan(Double value) {
            addCriterion("availableAmount <", value, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountLessThanOrEqualTo(Double value) {
            addCriterion("availableAmount <=", value, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountIn(List<Double> values) {
            addCriterion("availableAmount in", values, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountNotIn(List<Double> values) {
            addCriterion("availableAmount not in", values, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountBetween(Double value1, Double value2) {
            addCriterion("availableAmount between", value1, value2, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAvailableamountNotBetween(Double value1, Double value2) {
            addCriterion("availableAmount not between", value1, value2, "availableamount");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Double value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Double value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Double value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Double value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Double value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Double> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Double> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Double value1, Double value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Double value1, Double value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andCanlockIsNull() {
            addCriterion("canLock is null");
            return (Criteria) this;
        }

        public Criteria andCanlockIsNotNull() {
            addCriterion("canLock is not null");
            return (Criteria) this;
        }

        public Criteria andCanlockEqualTo(Byte value) {
            addCriterion("canLock =", value, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockNotEqualTo(Byte value) {
            addCriterion("canLock <>", value, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockGreaterThan(Byte value) {
            addCriterion("canLock >", value, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockGreaterThanOrEqualTo(Byte value) {
            addCriterion("canLock >=", value, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockLessThan(Byte value) {
            addCriterion("canLock <", value, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockLessThanOrEqualTo(Byte value) {
            addCriterion("canLock <=", value, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockIn(List<Byte> values) {
            addCriterion("canLock in", values, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockNotIn(List<Byte> values) {
            addCriterion("canLock not in", values, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockBetween(Byte value1, Byte value2) {
            addCriterion("canLock between", value1, value2, "canlock");
            return (Criteria) this;
        }

        public Criteria andCanlockNotBetween(Byte value1, Byte value2) {
            addCriterion("canLock not between", value1, value2, "canlock");
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