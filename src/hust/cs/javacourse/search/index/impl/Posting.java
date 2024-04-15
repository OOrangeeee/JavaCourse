package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author 晋晨曦
 */
public class Posting extends AbstractPosting {

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Posting posting = (Posting) obj;
        return docId == posting.docId && freq == posting.freq && positions.equals(posting.positions);
    }

    @Override
    public String toString() {
        return "Posting{" +
                "docId=" + docId +
                ", freq=" + freq +
                ", positions=" + positions +
                '}';
    }

    @Override
    public int getDocId() {
        return docId;
    }

    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    @Override
    public int getFreq() {
        return freq;
    }

    @Override
    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public List<Integer> getPositions() {
        return positions;
    }

    @Override
    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    @Override
    public int compareTo(AbstractPosting o) {
        return docId - o.getDocId();
    }

    @Override
    public void sort() {
        Collections.sort(positions);
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(docId);
            out.writeObject(freq);
            out.writeObject(positions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

@Override
public void readObject(ObjectInputStream in) {
    try {
        Object obj = in.readObject();
        if (obj instanceof Integer) {
            docId = (Integer) obj;
        } else {
            throw new IllegalArgumentException("Expected Integer for docId, got " + obj.getClass().getName());
        }

        obj = in.readObject();
        if (obj instanceof Integer) {
            freq = (Integer) obj;
        } else {
            throw new IllegalArgumentException("Expected Integer for freq, got " + obj.getClass().getName());
        }

        obj = in.readObject();
        if (obj instanceof List) {
            positions = (List<Integer>) obj;
        } else {
            throw new IllegalArgumentException("Expected List<Integer> for positions, got " + obj.getClass().getName());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
