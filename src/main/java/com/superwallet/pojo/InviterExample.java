package com.superwallet.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InviterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InviterExample() {
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

        public Criteria andInviteridIsNull() {
            addCriterion("inviterID is null");
            return (Criteria) this;
        }

        public Criteria andInviteridIsNotNull() {
            addCriterion("inviterID is not null");
            return (Criteria) this;
        }

        public Criteria andInviteridEqualTo(String value) {
            addCriterion("inviterID =", value, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridNotEqualTo(String value) {
            addCriterion("inviterID <>", value, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridGreaterThan(String value) {
            addCriterion("inviterID >", value, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridGreaterThanOrEqualTo(String value) {
            addCriterion("inviterID >=", value, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridLessThan(String value) {
            addCriterion("inviterID <", value, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridLessThanOrEqualTo(String value) {
            addCriterion("inviterID <=", value, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridLike(String value) {
            addCriterion("inviterID like", value, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridNotLike(String value) {
            addCriterion("inviterID not like", value, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridIn(List<String> values) {
            addCriterion("inviterID in", values, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridNotIn(List<String> values) {
            addCriterion("inviterID not in", values, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridBetween(String value1, String value2) {
            addCriterion("inviterID between", value1, value2, "inviterid");
            return (Criteria) this;
        }

        public Criteria andInviteridNotBetween(String value1, String value2) {
            addCriterion("inviterID not between", value1, value2, "inviterid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidIsNull() {
            addCriterion("beinvitedID is null");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidIsNotNull() {
            addCriterion("beinvitedID is not null");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidEqualTo(String value) {
            addCriterion("beinvitedID =", value, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidNotEqualTo(String value) {
            addCriterion("beinvitedID <>", value, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidGreaterThan(String value) {
            addCriterion("beinvitedID >", value, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidGreaterThanOrEqualTo(String value) {
            addCriterion("beinvitedID >=", value, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidLessThan(String value) {
            addCriterion("beinvitedID <", value, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidLessThanOrEqualTo(String value) {
            addCriterion("beinvitedID <=", value, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidLike(String value) {
            addCriterion("beinvitedID like", value, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidNotLike(String value) {
            addCriterion("beinvitedID not like", value, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidIn(List<String> values) {
            addCriterion("beinvitedID in", values, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidNotIn(List<String> values) {
            addCriterion("beinvitedID not in", values, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidBetween(String value1, String value2) {
            addCriterion("beinvitedID between", value1, value2, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andBeinvitedidNotBetween(String value1, String value2) {
            addCriterion("beinvitedID not between", value1, value2, "beinvitedid");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeIsNull() {
            addCriterion("invitingTime is null");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeIsNotNull() {
            addCriterion("invitingTime is not null");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeEqualTo(Date value) {
            addCriterion("invitingTime =", value, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeNotEqualTo(Date value) {
            addCriterion("invitingTime <>", value, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeGreaterThan(Date value) {
            addCriterion("invitingTime >", value, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("invitingTime >=", value, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeLessThan(Date value) {
            addCriterion("invitingTime <", value, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeLessThanOrEqualTo(Date value) {
            addCriterion("invitingTime <=", value, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeIn(List<Date> values) {
            addCriterion("invitingTime in", values, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeNotIn(List<Date> values) {
            addCriterion("invitingTime not in", values, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeBetween(Date value1, Date value2) {
            addCriterion("invitingTime between", value1, value2, "invitingtime");
            return (Criteria) this;
        }

        public Criteria andInvitingtimeNotBetween(Date value1, Date value2) {
            addCriterion("invitingTime not between", value1, value2, "invitingtime");
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