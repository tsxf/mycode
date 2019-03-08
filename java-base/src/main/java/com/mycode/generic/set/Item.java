package com.mycode.generic.set;

import java.util.Objects;

/**
 * 蛮小江
 * 2018/8/12 15:44
 */
public class Item implements  Comparable<Item> {

    private String description;
    private int partNumber;

    public Item(String description, int partNumber) {
        this.description = description;
        this.partNumber = partNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object otherObject) {
        if(this ==  otherObject){
             return  true;
        }
        if(otherObject == null){
            return  false;
        }
        if(getClass() != otherObject.getClass()){
              return  false;
        }
       Item other = (Item) otherObject;
        return Objects.equals(description, other.description) && partNumber == other.partNumber;
    }

    @Override
    public String toString() {
        return "[description="+description+",partNumber="+partNumber+"]";
    }

    @Override
    public int compareTo(Item other) {
        //先比较partNumber
        int diff = Integer.compare(partNumber, other.partNumber);
        //如果相等再比较description
        return diff !=0 ? diff : description.compareTo(other.description) ;
    }
}
