package home.common;

import java.util.List;

/** Interface: any service implementing it promises to support text search. */
public interface Searchable<T> {
    List<T> search(String query);
}
