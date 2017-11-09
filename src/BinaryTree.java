import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {

        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison;
        if (closest == null) comparison = -1;
        else comparison = t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }

        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private Node<T> findParent(T value) {
        if (root == null) return null;
        return findParent(root, value);
    }

    private Node<T> findParent(Node<T> start, T value) {
        if ((start.right != null && start.right.value == value) || (start.left != null && start.left.value == value))
            return start;
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return findParent(start.left, value);
        } else {
            if (start.right == null) return start;
            return findParent(start.right, value);
        }
    }

    private void delete(Node<T> node) {
        create(node, null);
    }

    private void create(Node<T> node, Node<T> newNode) {
        Node<T> parent = findParent(node.value);

        if (parent == null) {
            root.value = newNode.value;
            root.right = newNode.right;
            root.left = newNode.left;
            return;
        }
        if (parent.left == node) parent.left = newNode;
        if (parent.right == node) parent.right = newNode;
    }

    private T findMinRoot(Node<T> root) {
        T minRoot = root.value;
        while (root.left != null) {
            minRoot = root.left.value;
            root = root.left;
        }
        return minRoot;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> node = find((T) o);

        if (node == null) return false;

        if (node.left == null && node.right == null) {
            delete(node);
        } else if (node.left == null && node.right != null) {
            create(node, node.right);
        } else if (node.right == null && node.left != null) {
            create(node, node.left);
        } else {
            T t = findMinRoot(node.right);
            T value = t;
            remove(t);
            node.value = value;
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
        }

        private Node<T> findNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}




