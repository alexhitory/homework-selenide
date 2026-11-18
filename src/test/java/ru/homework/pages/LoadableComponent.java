package ru.homework.pages;

/** A page or component must wait for its readiness before it is used. */
public interface LoadableComponent<T> {
    T shouldBeOpen();
}
