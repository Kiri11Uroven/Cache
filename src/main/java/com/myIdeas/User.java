package com.myIdeas;

import java.util.Objects;

class User implements UserBuilder {

    private long account;
    private String name;
    private double value;

    public User(long account, String name, double value) {
        this.account = account;
        this.name = name;
        this.value = value;
    }

    public User() {

    }

    public long getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return account == user.account && Double.compare(user.value, value) == 0 && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, name, value);
    }

    @Override
    public String toString() {
        return "Record{" +
                "account=" + account +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public UserBuilder setAccount(long account) {
        this.account = account;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setValue(double value) {
        this.value = value;
        return this;
    }

    @Override
    public User build() {
        return new User(account, name, value);
    }

}
