package com.myIdeas;

import java.util.*;

public class Cache {
    static final List<User> users = new ArrayList<>();
    static final Map<String, List<Integer>> nameIndices = new HashMap<>();
    static final Map<Long, Integer> accountIndices = new HashMap<>();
    static final Map<Double, List<Integer>> valueIndices = new HashMap<>();

    private Cache() {
    }

    public static void addUser(User user) {
        addedUserValidator(user);
        users.add(user);
        List<Integer> addedNameIndices = new ArrayList<>();
        List<Integer> addedValueIndices = new ArrayList<>();
        int index = users.indexOf(user);
        addedNameIndices.add(index);
        addedValueIndices.add(index);
        accountIndices.put(user.getAccount(), index);
        addNameIndex(user, addedNameIndices);
        addValueIndex(user, addedValueIndices);
    }

    public static List<User> findUsersByName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        List<User> usersByName = new ArrayList<>();
        for (Integer index : nameIndices.get(name)) {
            usersByName.add(users.get(index));
        }
        return usersByName;
    }

    public static User findUserByAccount(Long account) {
        if (account == null || account < 0) {
            throw new IllegalArgumentException("Номер аккаунта не может быть меньше нуля");
        }
        User tempUser = users.get(accountIndices.get(account));
        return new User(tempUser.getAccount(), tempUser.getName(), tempUser.getValue());
    }

    public static List<User> findUsersByValue(Double value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Значение баланса не может быть меньше нуля");
        }
        List<User> usersByValue = new ArrayList<>();
        for (Integer index : valueIndices.get(value)) {
            usersByValue.add(users.get(index));
        }
        return usersByValue;
    }

    public static void removeUser(long account) {
        User user = findUserByAccount(account);
        removeUserValidator(user);
        int index = users.indexOf(user);
        removeNameIndexes(user.getName(), index);
        removeValueIndexes(user.getValue(), index);
        users.remove(user);
        accountIndices.remove(user.getAccount());
        updateAccountIndices(index);
    }

    public static void updateUserName(long account, String newName) {
        User oldUser = findUserByAccount(account);
        User newUser = new User();
        newUser.setName(newName).setAccount(oldUser.getAccount()).setValue(oldUser.getValue()).build();
        removeUser(account);
        addUser(newUser);
    }

    public static void updateUserValue(long account, double newValue) {
        User oldUser = findUserByAccount(account);
        User newUser = new User();
        newUser.setName(oldUser.getName()).setAccount(oldUser.getAccount()).setValue(newValue).build();
        removeUser(account);
        addUser(newUser);
    }

    private static void updateAccountIndices(int index) {
        for (User user : users) {
            if (accountIndices.get(user.getAccount()) > index) {
                accountIndices.put(user.getAccount(), accountIndices.get(user.getAccount()) - 1);
            }
        }
    }

    private static void removeNameIndexes(String name, int y) {
        Iterator<Map.Entry<String, List<Integer>>> iterator = nameIndices.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<Integer>> entry = iterator.next();
            String CheckingName = entry.getKey();
            List<Integer> indexes = entry.getValue();
            if (name.equals(CheckingName) && indexes.remove((Integer) y) && indexes.isEmpty()) {
                iterator.remove();
            }
            indexes.replaceAll(currentIndex -> currentIndex > y ? currentIndex - 1 : currentIndex);
        }
    }

    private static void removeValueIndexes(double value, int y) {
        Iterator<Map.Entry<Double, List<Integer>>> iterator = valueIndices.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Double, List<Integer>> entry = iterator.next();
            Double checkingValue = entry.getKey();
            List<Integer> indexes = entry.getValue();
            if (value == checkingValue && indexes.remove((Integer) y) && indexes.isEmpty()) {
                iterator.remove();
            }
            indexes.replaceAll(currentIndex -> currentIndex > y ? currentIndex - 1 : currentIndex);
        }
    }

    private static void addedUserValidator(User user) {
        if (accountIndices.containsKey(user.getAccount())) {
            throw new IllegalArgumentException("Пользователя с таким номером аккаунта уже существует");
        }
        if (user.getName() == null || user.getName().equals("")) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (user.getAccount() == 0 || user.getAccount() < 0) {
            throw new IllegalArgumentException("Аккаунт не может быть меньше нуля");
        }
        if (user.getValue() < 0) {
            throw new IllegalArgumentException("Значение баланса не может быть меньше нуля");
        }
    }

    private static void removeUserValidator(User user) {
        if (!accountIndices.containsKey(user.getAccount())) {
            throw new IllegalArgumentException("Пользователя с таким номером аккаунта не существует");
        }
        if (user.getName() == null || user.getName().equals("")) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (user.getAccount() == 0 || user.getAccount() < 0) {
            throw new IllegalArgumentException("Значение баланса не может быть меньше нуля");
        }
        if (user.getValue() < 0) {
            throw new IllegalArgumentException("Значение баланса не может быть меньше нуля");
        }
    }

    private static void addNameIndex(User user, List<Integer> newUserIndices) {
        if (nameIndices.containsKey(user.getName())) {
            nameIndices.get(user.getName()).add(users.indexOf(user));
        } else {
            nameIndices.put(user.getName(), newUserIndices);
        }
    }

    private static void addValueIndex(User user, List<Integer> newUserIndices) {
        if (valueIndices.containsKey(user.getValue())) {
            valueIndices.get(user.getValue()).add(users.indexOf(user));
        } else {
            valueIndices.put(user.getValue(), newUserIndices);
        }
    }
}






