package com.rareart.lesson19_2.servlets.gson_adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.rareart.lesson19_2.model.Department;

public class DepartmentAdapterFactory implements TypeAdapterFactory {

    private final Class<? extends Department> implementationClass;

    public DepartmentAdapterFactory(Class<? extends Department> implementationClass) {
        this.implementationClass = implementationClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        if (!Department.class.equals(typeToken.getRawType())){
            return null;
        }
        return (TypeAdapter<T>) gson.getAdapter(implementationClass);
    }
}
