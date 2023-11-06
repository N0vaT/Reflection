package edu.school21.OrmManager.ormManager;

import edu.school21.OrmManager.annotations.OrmColumn;
import edu.school21.OrmManager.annotations.OrmColumnId;
import edu.school21.OrmManager.annotations.OrmEntity;
import edu.school21.OrmManager.exceptions.AnnotationOrmEntityException;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.*;

public class OrmManagerImpl implements OrmManager{

    @Override
    public void save(Object entity) {
        String tableName;
        String columnIdName;
        String valueId;
        // K - columnName, V - columnParam (type, value, size)
        Map<String, List<String>> columnMap = new LinkedHashMap<>();
        List<String> listColumnName = new ArrayList<>();
        Class entityClass = entity.getClass();
        OrmEntity annotationTable = (OrmEntity) entityClass.getAnnotation(OrmEntity.class);
        if(annotationTable == null){
            throw new AnnotationOrmEntityException("Entity + " + entity + "entity is not annotated OrmEntity");
        }
        tableName = annotationTable.table();
        if(tableName.equals("")) tableName = entityClass.getSimpleName();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            try {
            field.setAccessible(true);
            if(field.isAnnotationPresent(OrmColumnId.class)){
                columnIdName = field.getName();
                valueId = field.get(entity).toString();
            }
            if(field.isAnnotationPresent(OrmColumn.class)){
                String columnName = "";
                String columnSize = "";
                OrmColumn annotationColumn = field.getAnnotation(OrmColumn.class);
                columnName = annotationColumn.name();
                columnSize = Integer.toString(annotationColumn.length());
                List<String> listValue = new ArrayList<>();
                if(columnName.equals("")){
                    columnName = field.getName();
                }
                listValue.add(field.getType().getSimpleName());
                listValue.add(field.get(entity).toString());
                listValue.add(columnSize);
                listColumnName.add(columnName);
                columnMap.put(columnName, listValue);
            }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        StringBuilder allColumns = new StringBuilder();
        StringBuilder allValues = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, List<String>> element: columnMap.entrySet()) {
            allColumns.append(element.getKey());
            if("String".equals(element.getValue().get(0))){
                allValues.append("'");
            }
            allValues.append(element.getValue().get(1));
            if("String".equals(element.getValue().get(0))){
                allValues.append("'");
            }
            if(i != columnMap.size()){
                allColumns.append(", ");
                allValues.append(", ");
            }
            i++;
        }
        System.out.println("INSERT INTO " + tableName + "(" + allColumns + ")");
        System.out.println("VALUES (" + allValues + ");");
        System.out.println("");

    }

    @Override
    public void update(Object entity) {
        String tableName = "";
        String columnIdName = "";
        String valueId = "";
        // K - columnName, V - columnParam (type, value, size)
        Map<String, List<String>> columnMap = new LinkedHashMap<>();
        List<String> listColumnName = new ArrayList<>();
        Class entityClass = entity.getClass();
        OrmEntity annotationTable = (OrmEntity) entityClass.getAnnotation(OrmEntity.class);
        if(annotationTable == null){
            throw new AnnotationOrmEntityException("Entity + " + entity + "entity is not annotated OrmEntity");
        }
        tableName = annotationTable.table();
        if(tableName.equals("")) tableName = entityClass.getSimpleName();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if(field.isAnnotationPresent(OrmColumnId.class)){
                    columnIdName = field.getName();
                    valueId = field.get(entity).toString();
                }
                if(field.isAnnotationPresent(OrmColumn.class)){
                    String columnName;
                    String columnSize;
                    OrmColumn annotationColumn = field.getAnnotation(OrmColumn.class);
                    columnName = annotationColumn.name();
                    columnSize = Integer.toString(annotationColumn.length());
                    List<String> listValue = new ArrayList<>();
                    if(columnName.equals("")){
                        columnName = field.getName();
                    }
                    listValue.add(field.getType().getSimpleName());
                    listValue.add(field.get(entity).toString());
                    listValue.add(columnSize);
                    listColumnName.add(columnName);
                    columnMap.put(columnName, listValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        StringBuilder allColumnsSet = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, List<String>> element: columnMap.entrySet()) {
            allColumnsSet.append(element.getKey()).append(" = ");
            if("String".equals(element.getValue().get(0))){
                allColumnsSet.append("'");
            }
            allColumnsSet.append(element.getValue().get(1));
            if("String".equals(element.getValue().get(0))){
                allColumnsSet.append("'");
            }
            if(i != columnMap.size()){
                allColumnsSet.append(" , ");
            }
            i++;
        }
        System.out.println("UPDATE " + tableName);
        System.out.println("SET " + allColumnsSet);
        System.out.println("WHERE " + columnIdName + " = " + valueId + ";");
        System.out.println("");
    }

    @Override
    public <T> T findById(Long id, Class<T> aClass) {
        Object entity;
        try {
            entity = aClass.newInstance();
            String tableName = "";
            String columnIdName = "";
            String valueId = "";
            // K - columnName, V - columnParam (type, value, size)
            Map<String, List<String>> columnMap = new LinkedHashMap<>();
            List<String> listColumnName = new ArrayList<>();
            Class entityClass = entity.getClass();
            OrmEntity annotationTable = (OrmEntity) entityClass.getAnnotation(OrmEntity.class);
            if(annotationTable == null){
                throw new AnnotationOrmEntityException("Entity + " + entity + "entity is not annotated OrmEntity");
            }
            tableName = annotationTable.table();
            if(tableName.equals("")) tableName = entityClass.getSimpleName();
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if(field.isAnnotationPresent(OrmColumnId.class)){
                        columnIdName = field.getName();
                        valueId = field.get(entity).toString();
                    }
                    if(field.isAnnotationPresent(OrmColumn.class)){
                        String columnName;
                        String columnSize;
                        OrmColumn annotationColumn = field.getAnnotation(OrmColumn.class);
                        columnName = annotationColumn.name();
                        columnSize = Integer.toString(annotationColumn.length());
                        List<String> listValue = new ArrayList<>();
                        if(columnName.equals("")){
                            columnName = field.getName();
                        }
                        listValue.add(field.getType().getSimpleName());
                        listValue.add(field.get(entity).toString());
                        listValue.add(columnSize);
                        listColumnName.add(columnName);
                        columnMap.put(columnName, listValue);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            StringBuilder allColumns = new StringBuilder();
            int i = 1;
            for (Map.Entry<String, List<String>> element: columnMap.entrySet()) {
                allColumns.append(element.getKey());
                if(i != columnMap.size()){
                    allColumns.append(", ");
                }
                i++;
            }
            System.out.println("SELECT (" + columnIdName + ", " + allColumns + ")");
            System.out.println("FROM " + tableName);
            System.out.println("WHERE " + columnIdName + " = " + id + ";");
            System.out.println("");
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return (T) entity;
    }
}
