package com.superwallet.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransferExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TransferExample() {
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

        public Criteria andSourceaddressIsNull() {
            addCriterion("sourceAddress is null");
            return (Criteria) this;
        }

        public Criteria andSourceaddressIsNotNull() {
            addCriterion("sourceAddress is not null");
            return (Criteria) this;
        }

        public Criteria andSourceaddressEqualTo(String value) {
            addCriterion("sourceAddress =", value, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressNotEqualTo(String value) {
            addCriterion("sourceAddress <>", value, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressGreaterThan(String value) {
            addCriterion("sourceAddress >", value, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressGreaterThanOrEqualTo(String value) {
            addCriterion("sourceAddress >=", value, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressLessThan(String value) {
            addCriterion("sourceAddress <", value, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressLessThanOrEqualTo(String value) {
            addCriterion("sourceAddress <=", value, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressLike(String value) {
            addCriterion("sourceAddress like", value, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressNotLike(String value) {
            addCriterion("sourceAddress not like", value, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressIn(List<String> values) {
            addCriterion("sourceAddress in", values, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressNotIn(List<String> values) {
            addCriterion("sourceAddress not in", values, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressBetween(String value1, String value2) {
            addCriterion("sourceAddress between", value1, value2, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andSourceaddressNotBetween(String value1, String value2) {
            addCriterion("sourceAddress not between", value1, value2, "sourceaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressIsNull() {
            addCriterion("destAddress is null");
            return (Criteria) this;
        }

        public Criteria andDestaddressIsNotNull() {
            addCriterion("destAddress is not null");
            return (Criteria) this;
        }

        public Criteria andDestaddressEqualTo(String value) {
            addCriterion("destAddress =", value, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressNotEqualTo(String value) {
            addCriterion("destAddress <>", value, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressGreaterThan(String value) {
            addCriterion("destAddress >", value, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressGreaterThanOrEqualTo(String value) {
            addCriterion("destAddress >=", value, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressLessThan(String value) {
            addCriterion("destAddress <", value, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressLessThanOrEqualTo(String value) {
            addCriterion("destAddress <=", value, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressLike(String value) {
            addCriterion("destAddress like", value, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressNotLike(String value) {
            addCriterion("destAddress not like", value, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressIn(List<String> values) {
            addCriterion("destAddress in", values, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressNotIn(List<String> values) {
            addCriterion("destAddress not in", values, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressBetween(String value1, String value2) {
            addCriterion("destAddress between", value1, value2, "destaddress");
            return (Criteria) this;
        }

        public Criteria andDestaddressNotBetween(String value1, String value2) {
            addCriterion("destAddress not between", value1, value2, "destaddress");
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

        public Criteria andAmountEqualTo(Integer value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Integer value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Integer value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Integer value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Integer value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Integer> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Integer> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Integer value1, Integer value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andTransfertypeIsNull() {
            addCriterion("transferType is null");
            return (Criteria) this;
        }

        public Criteria andTransfertypeIsNotNull() {
            addCriterion("transferType is not null");
            return (Criteria) this;
        }

        public Criteria andTransfertypeEqualTo(Integer value) {
            addCriterion("transferType =", value, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeNotEqualTo(Integer value) {
            addCriterion("transferType <>", value, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeGreaterThan(Integer value) {
            addCriterion("transferType >", value, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("transferType >=", value, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeLessThan(Integer value) {
            addCriterion("transferType <", value, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeLessThanOrEqualTo(Integer value) {
            addCriterion("transferType <=", value, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeIn(List<Integer> values) {
            addCriterion("transferType in", values, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeNotIn(List<Integer> values) {
            addCriterion("transferType not in", values, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeBetween(Integer value1, Integer value2) {
            addCriterion("transferType between", value1, value2, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTransfertypeNotBetween(Integer value1, Integer value2) {
            addCriterion("transferType not between", value1, value2, "transfertype");
            return (Criteria) this;
        }

        public Criteria andTokentypeIsNull() {
            addCriterion("tokenType is null");
            return (Criteria) this;
        }

        public Criteria andTokentypeIsNotNull() {
            addCriterion("tokenType is not null");
            return (Criteria) this;
        }

        public Criteria andTokentypeEqualTo(Byte value) {
            addCriterion("tokenType =", value, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeNotEqualTo(Byte value) {
            addCriterion("tokenType <>", value, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeGreaterThan(Byte value) {
            addCriterion("tokenType >", value, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("tokenType >=", value, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeLessThan(Byte value) {
            addCriterion("tokenType <", value, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeLessThanOrEqualTo(Byte value) {
            addCriterion("tokenType <=", value, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeIn(List<Byte> values) {
            addCriterion("tokenType in", values, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeNotIn(List<Byte> values) {
            addCriterion("tokenType not in", values, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeBetween(Byte value1, Byte value2) {
            addCriterion("tokenType between", value1, value2, "tokentype");
            return (Criteria) this;
        }

        public Criteria andTokentypeNotBetween(Byte value1, Byte value2) {
            addCriterion("tokenType not between", value1, value2, "tokentype");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNull() {
            addCriterion("createdTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNotNull() {
            addCriterion("createdTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeEqualTo(Date value) {
            addCriterion("createdTime =", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotEqualTo(Date value) {
            addCriterion("createdTime <>", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThan(Date value) {
            addCriterion("createdTime >", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createdTime >=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThan(Date value) {
            addCriterion("createdTime <", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThanOrEqualTo(Date value) {
            addCriterion("createdTime <=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIn(List<Date> values) {
            addCriterion("createdTime in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotIn(List<Date> values) {
            addCriterion("createdTime not in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeBetween(Date value1, Date value2) {
            addCriterion("createdTime between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotBetween(Date value1, Date value2) {
            addCriterion("createdTime not between", value1, value2, "createdtime");
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