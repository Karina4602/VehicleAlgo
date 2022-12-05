import java.util.Iterator;

public class PQ_LL {
    edge head;

    public PQ_LL() {
        head = null;
    }


    void sortedAdd(edge _new) {
        edge walker;
        if (head == null || head.getWeight() >= _new.getWeight()) {
            _new.next = head;
            head = _new;
        } else {
            walker = head;
            while (walker.next != null && walker.next.getWeight() < _new.getWeight()) {
                walker = walker.next;
            }
            _new.next = walker.next;
            walker.next = _new;
        }
    }

    void add(edge _new) {
        if (head == null)
            head = _new;
        else {
            edge walker = head;
            while (walker.next != null) {
                walker = walker.next;
            }
            walker.next = _new;
        }
    }

    int size() {
        edge walker;
        int i = 0;
        for (walker = head; walker != null; walker = walker.next) {
            i++;
        }
        return i;

    }

    public edge getedge(int index) {
        edge walker = head;
        for (int i = 0; i < index; i++) {
            walker = walker.next;
        }
        return walker;
    }


    void clear() {
        head = null;
    }


    void remove(int i) {
        if (i == 0) {
            head = head.next;
        } else {
            edge walker = getedge(i - 1);
            edge toRMv = walker.next;
            walker.next = toRMv.next;

        }
    }


}




