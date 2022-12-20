package org.example;

import lombok.Getter;
import lombok.Setter;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.*;
import java.util.stream.Collectors;

public class MyList<T extends Data> implements List<T> {
    private final List<String> encryptList;
    private final T converter;

    @Getter
    @Setter
    private KeyPair keyPair;

    public MyList(T converter, KeyPair keyPair) {
        this.converter = converter;
        this.keyPair = keyPair;
        this.encryptList = new ArrayList<>();
    }

    @Override
    public int size() {
        return encryptList.size();
    }

    @Override
    public boolean isEmpty() {
        return encryptList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        try {
            String enc = DecEnCode.encryptRSAToBase64((Data)o, keyPair.getPublic());
            return encryptList.contains(enc);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new SecureIterator<>(encryptList.listIterator());
    }

    @Override
    public Object[] toArray() {
        return encryptList.stream().map((d) -> {
            try {
                return DecEnCode.<T>decryptRSA(d, keyPair.getPrivate(), converter);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return encryptList.stream().map((d) -> {
            try {
                return DecEnCode.<T>decryptRSA(d, keyPair.getPrivate(), converter);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).toList().toArray(a);
    }

    @Override
    public boolean add(T t) {
        try {
            String data = DecEnCode.encryptRSAToBase64(t, keyPair.getPublic());
            return encryptList.add(data);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean remove(Object o) {
        try {
            String data = DecEnCode.encryptRSAToBase64((Data) o, keyPair.getPublic());
            return encryptList.add(data);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Collection<String> encCollection = c.stream().map((a) -> {
            try {
                return DecEnCode.encryptRSAToBase64((Data) a, keyPair.getPublic());
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(ArrayList::new));

        return encryptList.containsAll(encCollection);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Collection<String> enc = c.stream().map((d) -> {
            try {
                return DecEnCode.encryptRSAToBase64(d, keyPair.getPublic());
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(ArrayList::new));

        return encryptList.addAll(enc);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Collection<String> enc = c.stream().map((d) -> {
            try {
                return DecEnCode.encryptRSAToBase64(d, keyPair.getPublic());
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(ArrayList::new));

        return encryptList.addAll(index, enc);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Collection<String> enc = c.stream().map((d) -> {
            try {
                return DecEnCode.encryptRSAToBase64((Data) d, keyPair.getPublic());
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(ArrayList::new));

        return encryptList.removeAll(enc);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Collection<String> enc = c.stream().map((d) -> {
            try {
                return DecEnCode.encryptRSAToBase64((Data) d, keyPair.getPublic());
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(ArrayList::new));

        return encryptList.retainAll(enc);
    }

    @Override
    public void clear() {
        encryptList.clear();
    }

    @Override
    public T get(int index) {
        try {
            return DecEnCode.decryptRSA(encryptList.get(index), keyPair.getPrivate(), converter);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T set(int index, T element) {
        try {
            return DecEnCode.decryptRSA(encryptList.set(index, DecEnCode.encryptRSAToBase64(element, keyPair.getPublic())), keyPair.getPrivate(), converter);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(int index, T element) {
        try {
            encryptList.add(index, DecEnCode.encryptRSAToBase64(element, keyPair.getPublic()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T remove(int index) {
        try {
            return DecEnCode.decryptRSA(encryptList.remove(index), keyPair.getPrivate(), converter);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int indexOf(Object o) {
        try {
            return encryptList.indexOf(DecEnCode.encryptRSAToBase64((Data) o, keyPair.getPublic()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        try {
            return encryptList.lastIndexOf(DecEnCode.encryptRSAToBase64((Data) o, keyPair.getPublic()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        return new SecureIterator<>(encryptList.listIterator());
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new SecureIterator<>(encryptList.listIterator(index));
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return encryptList.subList(fromIndex, toIndex).stream().map(s -> {
            try {
                return DecEnCode.<T>decryptRSA(s, keyPair.getPrivate(), converter);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private class SecureIterator<E extends Data> implements ListIterator<E> {

        private final ListIterator<String> iterator;

        public SecureIterator(ListIterator<String> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            try {
                return (E) DecEnCode.decryptRSA(iterator.next(), keyPair.getPrivate(), converter);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean hasPrevious() {
            return iterator.hasPrevious();
        }

        @Override
        public E previous() {
            try {
                return (E) DecEnCode.decryptRSA(iterator.previous(), keyPair.getPrivate(), converter);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int nextIndex() {
            return iterator.nextIndex();
        }

        @Override
        public int previousIndex() {
            return iterator.previousIndex();
        }

        @Override
        public void remove() {
            iterator.remove();
        }

        @Override
        public void set(E e) {
            try {
                iterator.set(DecEnCode.encryptRSAToBase64(e, keyPair.getPublic()));
            } catch (GeneralSecurityException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public void add(E e) {
            try {
                iterator.add(DecEnCode.encryptRSAToBase64(e, keyPair.getPublic()));
            } catch (GeneralSecurityException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}