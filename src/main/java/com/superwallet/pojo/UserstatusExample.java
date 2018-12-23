package com.superwallet.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserstatusExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserstatusExample() {
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

        public Criteria andLastoptimeIsNull() {
            addCriterion("lastOpTime is null");
            return (Criteria) this;
        }

        public Criteria andLastoptimeIsNotNull() {
            addCriterion("lastOpTime is not null");
            return (Criteria) this;
        }

        public Criteria andLastoptimeEqualTo(Date value) {
            addCriterion("lastOpTime =", value, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeNotEqualTo(Date value) {
            addCriterion("lastOpTime <>", value, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeGreaterThan(Date value) {
            addCriterion("lastOpTime >", value, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lastOpTime >=", value, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeLessThan(Date value) {
            addCriterion("lastOpTime <", value, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeLessThanOrEqualTo(Date value) {
            addCriterion("lastOpTime <=", value, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeIn(List<Date> values) {
            addCriterion("lastOpTime in", values, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeNotIn(List<Date> values) {
            addCriterion("lastOpTime not in", values, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeBetween(Date value1, Date value2) {
            addCriterion("lastOpTime between", value1, value2, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastoptimeNotBetween(Date value1, Date value2) {
            addCriterion("lastOpTime not between", value1, value2, "lastoptime");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceIsNull() {
            addCriterion("lastOpDevice is null");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceIsNotNull() {
            addCriterion("lastOpDevice is not null");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceEqualTo(String value) {
            addCriterion("lastOpDevice =", value, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceNotEqualTo(String value) {
            addCriterion("lastOpDevice <>", value, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceGreaterThan(String value) {
            addCriterion("lastOpDevice >", value, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceGreaterThanOrEqualTo(String value) {
            addCriterion("lastOpDevice >=", value, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceLessThan(String value) {
            addCriterion("lastOpDevice <", value, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceLessThanOrEqualTo(String value) {
            addCriterion("lastOpDevice <=", value, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceLike(String value) {
            addCriterion("lastOpDevice like", value, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceNotLike(String value) {
            addCriterion("lastOpDevice not like", value, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceIn(List<String> values) {
            addCriterion("lastOpDevice in", values, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceNotIn(List<String> values) {
            addCriterion("lastOpDevice not in", values, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceBetween(String value1, String value2) {
            addCriterion("lastOpDevice between", value1, value2, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andLastopdeviceNotBetween(String value1, String value2) {
            addCriterion("lastOpDevice not between", value1, value2, "lastopdevice");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeIsNull() {
            addCriterion("invitedTime is null");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeIsNotNull() {
            addCriterion("invitedTime is not null");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeEqualTo(Date value) {
            addCriterion("invitedTime =", value, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeNotEqualTo(Date value) {
            addCriterion("invitedTime <>", value, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeGreaterThan(Date value) {
            addCriterion("invitedTime >", value, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("invitedTime >=", value, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeLessThan(Date value) {
            addCriterion("invitedTime <", value, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeLessThanOrEqualTo(Date value) {
            addCriterion("invitedTime <=", value, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeIn(List<Date> values) {
            addCriterion("invitedTime in", values, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeNotIn(List<Date> values) {
            addCriterion("invitedTime not in", values, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeBetween(Date value1, Date value2) {
            addCriterion("invitedTime between", value1, value2, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andInvitedtimeNotBetween(Date value1, Date value2) {
            addCriterion("invitedTime not between", value1, value2, "invitedtime");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Byte value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Byte value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Byte value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Byte value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Byte value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Byte> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Byte> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Byte value1, Byte value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Byte value1, Byte value2) {
            addCriterion("state not between", value1, value2, "state");
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