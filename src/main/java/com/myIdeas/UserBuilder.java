package com.myIdeas;

public interface UserBuilder {

    UserBuilder setName(String name);
    UserBuilder setAccount(long account);
    UserBuilder setValue(double value);
    User build();
}
