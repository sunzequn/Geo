package com.sunzequn.geo.data.baike.clean;

/**
 * Created by sunzequn on 2016/4/13.
 */
public class RemoveRule {
    private String rule;
    private String type;
    private int priority;
    private String comment;

    public RemoveRule() {
    }

    public RemoveRule(String rule, String type, int priority, String comment) {
        this.rule = rule;
        this.type = type;
        this.priority = priority;
        this.comment = comment;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "RemoveRule{" +
                "rule='" + rule + '\'' +
                ", type='" + type + '\'' +
                ", priority=" + priority +
                ", comment='" + comment + '\'' +
                '}';
    }
}
