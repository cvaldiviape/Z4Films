package com.shared.utils.filter;

import com.shared.utils.ValidateUtil;
import java.util.List;
import java.util.Optional;

public class FilterUtil {

    public static <T extends Searchable<U>, U> T find(List<T> list, U valueToSearch, String nameValueNotFound) {
        return list.stream()
                .filter(item -> filterGeneric(item, valueToSearch))
                .findFirst()
                .orElseThrow(() ->  ValidateUtil.throwNotFoundException(nameValueNotFound));
    }

    public static <T extends Searchable<U>, U> Optional<T> find(List<T> list, U valueToSearch) {
        return list.stream()
                .filter(item -> filterGeneric(item, valueToSearch))
                .findFirst();
    }

    private static <T extends Searchable<U>, U> boolean filterGeneric(T  item, U valueToSearch) {
        try {
            return item.getSearcheableField().equals(valueToSearch);
        } catch (Exception e) {
            return false;
        }
    }

}