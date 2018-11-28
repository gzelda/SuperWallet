package com.superwallet.pojo;

import java.util.ArrayList;
import java.util.List;

public class UserbasicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserbasicExample() {
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

        public Criteria andNacknameIsNull() {
            addCriterion("nackName is null");
            return (Criteria) this;
        }

        public Criteria andNacknameIsNotNull() {
            addCriterion("nackName is not null");
            return (Criteria) this;
        }

        public Criteria andNacknameEqualTo(String value) {
            addCriterion("nackName =", value, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameNotEqualTo(String value) {
            addCriterion("nackName <>", value, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameGreaterThan(String value) {
            addCriterion("nackName >", value, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameGreaterThanOrEqualTo(String value) {
            addCriterion("nackName >=", value, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameLessThan(String value) {
            addCriterion("nackName <", value, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameLessThanOrEqualTo(String value) {
            addCriterion("nackName <=", value, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameLike(String value) {
            addCriterion("nackName like", value, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameNotLike(String value) {
            addCriterion("nackName not like", value, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameIn(List<String> values) {
            addCriterion("nackName in", values, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameNotIn(List<String> values) {
            addCriterion("nackName not in", values, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameBetween(String value1, String value2) {
            addCriterion("nackName between", value1, value2, "nackname");
            return (Criteria) this;
        }

        public Criteria andNacknameNotBetween(String value1, String value2) {
            addCriterion("nackName not between", value1, value2, "nackname");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Byte value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Byte value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Byte value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Byte value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Byte value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Byte value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Byte> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Byte> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Byte value1, Byte value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Byte value1, Byte value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andIsagencyIsNull() {
            addCriterion("isAgency is null");
            return (Criteria) this;
        }

        public Criteria andIsagencyIsNotNull() {
            addCriterion("isAgency is not null");
            return (Criteria) this;
        }

        public Criteria andIsagencyEqualTo(Byte value) {
            addCriterion("isAgency =", value, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyNotEqualTo(Byte value) {
            addCriterion("isAgency <>", value, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyGreaterThan(Byte value) {
            addCriterion("isAgency >", value, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyGreaterThanOrEqualTo(Byte value) {
            addCriterion("isAgency >=", value, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyLessThan(Byte value) {
            addCriterion("isAgency <", value, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyLessThanOrEqualTo(Byte value) {
            addCriterion("isAgency <=", value, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyIn(List<Byte> values) {
            addCriterion("isAgency in", values, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyNotIn(List<Byte> values) {
            addCriterion("isAgency not in", values, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyBetween(Byte value1, Byte value2) {
            addCriterion("isAgency between", value1, value2, "isagency");
            return (Criteria) this;
        }

        public Criteria andIsagencyNotBetween(Byte value1, Byte value2) {
            addCriterion("isAgency not between", value1, value2, "isagency");
            return (Criteria) this;
        }

        public Criteria andPhonenumberIsNull() {
            addCriterion("phoneNumber is null");
            return (Criteria) this;
        }

        public Criteria andPhonenumberIsNotNull() {
            addCriterion("phoneNumber is not null");
            return (Criteria) this;
        }

        public Criteria andPhonenumberEqualTo(String value) {
            addCriterion("phoneNumber =", value, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberNotEqualTo(String value) {
            addCriterion("phoneNumber <>", value, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberGreaterThan(String value) {
            addCriterion("phoneNumber >", value, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberGreaterThanOrEqualTo(String value) {
            addCriterion("phoneNumber >=", value, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberLessThan(String value) {
            addCriterion("phoneNumber <", value, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberLessThanOrEqualTo(String value) {
            addCriterion("phoneNumber <=", value, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberLike(String value) {
            addCriterion("phoneNumber like", value, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberNotLike(String value) {
            addCriterion("phoneNumber not like", value, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberIn(List<String> values) {
            addCriterion("phoneNumber in", values, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberNotIn(List<String> values) {
            addCriterion("phoneNumber not in", values, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberBetween(String value1, String value2) {
            addCriterion("phoneNumber between", value1, value2, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andPhonenumberNotBetween(String value1, String value2) {
            addCriterion("phoneNumber not between", value1, value2, "phonenumber");
            return (Criteria) this;
        }

        public Criteria andInviterIsNull() {
            addCriterion("inviter is null");
            return (Criteria) this;
        }

        public Criteria andInviterIsNotNull() {
            addCriterion("inviter is not null");
            return (Criteria) this;
        }

        public Criteria andInviterEqualTo(String value) {
            addCriterion("inviter =", value, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterNotEqualTo(String value) {
            addCriterion("inviter <>", value, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterGreaterThan(String value) {
            addCriterion("inviter >", value, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterGreaterThanOrEqualTo(String value) {
            addCriterion("inviter >=", value, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterLessThan(String value) {
            addCriterion("inviter <", value, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterLessThanOrEqualTo(String value) {
            addCriterion("inviter <=", value, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterLike(String value) {
            addCriterion("inviter like", value, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterNotLike(String value) {
            addCriterion("inviter not like", value, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterIn(List<String> values) {
            addCriterion("inviter in", values, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterNotIn(List<String> values) {
            addCriterion("inviter not in", values, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterBetween(String value1, String value2) {
            addCriterion("inviter between", value1, value2, "inviter");
            return (Criteria) this;
        }

        public Criteria andInviterNotBetween(String value1, String value2) {
            addCriterion("inviter not between", value1, value2, "inviter");
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

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleIsNull() {
            addCriterion("invitedPeople is null");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleIsNotNull() {
            addCriterion("invitedPeople is not null");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleEqualTo(Object value) {
            addCriterion("invitedPeople =", value, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleNotEqualTo(Object value) {
            addCriterion("invitedPeople <>", value, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleGreaterThan(Object value) {
            addCriterion("invitedPeople >", value, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleGreaterThanOrEqualTo(Object value) {
            addCriterion("invitedPeople >=", value, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleLessThan(Object value) {
            addCriterion("invitedPeople <", value, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleLessThanOrEqualTo(Object value) {
            addCriterion("invitedPeople <=", value, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleIn(List<Object> values) {
            addCriterion("invitedPeople in", values, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleNotIn(List<Object> values) {
            addCriterion("invitedPeople not in", values, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleBetween(Object value1, Object value2) {
            addCriterion("invitedPeople between", value1, value2, "invitedpeople");
            return (Criteria) this;
        }

        public Criteria andInvitedpeopleNotBetween(Object value1, Object value2) {
            addCriterion("invitedPeople not between", value1, value2, "invitedpeople");
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