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
        if (isValidToAdd(user)) {
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
    }

    public static List<Integer> findIndicesByName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        return new ArrayList<>(nameIndices.get(name));
    }

    public static User findByAccount(Long account) {
        if (account == null || account < 0) {
            throw new IllegalArgumentException("Номер аккаунта не может быть меньше нуля");
        }
        return users.get(accountIndices.get(account));
    }

    public static List<Integer> findIndicesByValue(Double value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Значение баланса не может быть меньше нуля");
        }
        return new ArrayList<>(valueIndices.get(value));
    }

    public static void removeUser(User user) {
        if (isValidToRemove(user)) {
            int i = users.indexOf(user);
            removeNameIndexes(user.getName(), i);
            removeValueIndexes(user.getValue(), i);
            users.remove(user);
            accountIndices.remove(user.getAccount());
            updateAccountIndices(i);
        }
    }

    public static void updateUser(User oldUser, String newName) {
        User newUser = new User(oldUser.getAccount(), newName, oldUser.getValue());
        removeUser(oldUser);
        addUser(newUser);
    }

    public void updateUser(User oldUser, double newValue) {
        User newUser = new User(oldUser.getAccount(), oldUser.getName(), newValue);
        removeUser(oldUser);
        addUser(newUser);
    }

    private static void updateAccountIndices(int index) {
        for (User user : users) {
            if (accountIndices.get(user.getAccount()) > index) {
                accountIndices.put(user.getAccount(), accountIndices.get(user.getAccount()) - 1);
            }
        }
    }


    private static void removeNameIndexes(String user, int y) {
        Iterator<Map.Entry<String, List<Integer>>> iterator = nameIndices.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<Integer>> entry = iterator.next();
            String name = entry.getKey();
            List<Integer> indexes = entry.getValue();
            if (user.equals(name) && indexes.remove((Integer) y) && indexes.isEmpty()) {
                iterator.remove();
            }
            indexes.replaceAll(currentIndex -> currentIndex > y ? currentIndex - 1 : currentIndex);
        }
    }


    private static void removeValueIndexes(double value, int y) {
        Iterator<Map.Entry<Double, List<Integer>>> iterator = valueIndices.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Double, List<Integer>> entry = iterator.next();
            Double val = entry.getKey();
            List<Integer> indexes = entry.getValue();
            if (value == val && indexes.remove((Integer) y) && indexes.isEmpty()) {
                iterator.remove();
            }
            indexes.replaceAll(currentIndex -> currentIndex > y ? currentIndex - 1 : currentIndex);
        }
    }

    private static boolean isValidToAdd(User user) {
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
        return true;
    }

    private static boolean isValidToRemove(User user) {
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
        return true;
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






