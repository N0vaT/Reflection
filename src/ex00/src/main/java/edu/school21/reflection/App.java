package edu.school21.reflection;


import edu.school21.reflection.exceptions.InputClassNameException;

import java.lang.reflect.*;
import java.util.Scanner;

public class App {
    private final static String DELIMITER = "---------------";
    private static Scanner scanner = new Scanner(System.in);
    private static final String USER_CLASS_NAME = "User";
    private static final String CAR_CLASS_NAME = "Car";
    public static void main(String[] args) {
            try{
                String className = startMenu();
                printFieldsAndMethods(className);
                Object object = createObject(className);
                changedObjectField(object);
                methodCall(object);
            }catch(RuntimeException e){
                System.out.println(e.getMessage() + ". Try again");
                System.out.println("??????????????");
            }
    }

    private static String startMenu(){
        System.out.println("Classes:\n\t- " + USER_CLASS_NAME +"\n\t- " + CAR_CLASS_NAME);
        System.out.println(DELIMITER);
        System.out.println("Enter class name:");
        String className = scanner.nextLine();
        if(!className.equals(USER_CLASS_NAME) && !className.equals(CAR_CLASS_NAME)){
            throw new InputClassNameException("Class named - " + className + " does not exist");
        }
        System.out.println(DELIMITER);
        return className;
    }
    private static void printFieldsAndMethods(String className){
        System.out.println("fields:");
        try {
            Class clazz = Class.forName("edu.school21.reflection.models." + className);
            for (Field field : clazz.getFields()) {
                System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
            }
            for (Field field : clazz.getDeclaredFields()){
                System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
            }
            System.out.println("methods:");
            for(Method method : clazz.getDeclaredMethods()){
              if(!"toString".equals(method.getName())){
                  System.out.print("\t" + method.getGenericReturnType().getClass().getSimpleName() + " " + method.getName());
                  Parameter[] params = method.getParameters();
                  System.out.print("(");
                  for(int i = 0; i < params.length; i++){
                      System.out.print(params[i].getType().getSimpleName());
                      if(i != params.length - 1){
                          System.out.print(", ");
                      }
                  }
                  System.out.println(")");
                }
            }
            System.out.println(DELIMITER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object createObject(String className){
        System.out.println("Let’s create an object.");
        Object object;
        try {
            Class clazz = Class.forName("edu.school21.reflection.models." + className);
            object = clazz.newInstance();
            for(Field field : object.getClass().getDeclaredFields()){
                System.out.println(field.getName() + ":");
                String value = scanner.nextLine();
                setFieldValue(object, field, value);
            }
            System.out.println(object);
            System.out.println(DELIMITER);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
    private static void changedObjectField(Object object){
        System.out.println("Enter name of the field for changing:");
        String fieldName = scanner.nextLine();
        System.out.println("Enter String value:");
        String value = scanner.nextLine();
        Class clazz = object.getClass();
        for (Field field : clazz.getFields()) {
            if(fieldName.equals(field.getName())){
                setFieldValue(object, field, value);
            }
        }
        for (Field field : clazz.getDeclaredFields()){
            if(fieldName.equals(field.getName())){
                setFieldValue(object, field, value);
            }
        }
        System.out.println("Object updated: " + object);
        System.out.println(DELIMITER);
    }

    private static void setFieldValue(Object object, Field field, String value){
        try{
            field.setAccessible(true);
            if("String".equals(field.getType().getSimpleName())){
                field.set(object, (String) value);
            }else if("long".equals(field.getType().getSimpleName())){
                field.set(object, (long) Long.parseLong(value));
            }else if("int".equals(field.getType().getSimpleName())){
                field.set(object, (int) Integer.parseInt(value));
            }else if("double".equals(field.getType().getSimpleName())){
                field.set(object, (double) Double.parseDouble(value));
            }else if("boolean".equals(field.getType().getSimpleName())){
                field.set(object, (boolean) Boolean.parseBoolean(value));
            }else{
                throw new RuntimeException("Invalid field type");
            }
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    private static void methodCall(Object object){
        Class clazz = object.getClass();
        System.out.println("Enter name of the method for call:");
        String methodName = scanner.nextLine();
        try {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if(methodName.equals(method.getName())){
                    Class<?>[] params = method.getParameterTypes();
                    Object[] args = new Object[params.length];
                    if(params.length != 0){
                        for (int i = 0; i < params.length; i++) {
                            String typeName = params[i].getSimpleName();
                            System.out.println("Enter " + typeName +  " value:");
                            String valueI = scanner.nextLine();
                            args[i] = parseValue(valueI, typeName);
                            method.setAccessible(true);
                            System.out.println("Method returned:\n" + method.invoke(object, args));
                            return;
                        }
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseValue(String value, String typeName){
        try{
            if("String".equals(typeName)){
                return value;
            }else if("long".equals(typeName)){
                return Long.parseLong(value);
            }else if("int".equals(typeName)){
                return Integer.parseInt(value);
            }else if("double".equals(typeName)){
                return Double.parseDouble(value);
            }else if("boolean".equals(typeName)){
                return Boolean.parseBoolean(value);
            }else{
                throw new RuntimeException("Invalid method type");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
