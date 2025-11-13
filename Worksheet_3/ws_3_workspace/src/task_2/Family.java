package task_2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class representing a family
 * @author lkoebel 21487
 */
public class Family implements Iterable<String>{

    private ArrayList<String> names = new ArrayList<>();

    /**
     * Constructor for a new family with father and mother
     * @param father Father name
     * @param mother Mother Name
     */
    public Family(String father, String mother)
    {
        this.names.add(father);
        this.names.add(mother);
    }

    public Family(String _memberName, FamilyMember _member)
    {
        switch (_member){
            case FamilyMember.FATHER -> {
                this.names.add(_memberName);
                this.names.add("none");
            }
            case FamilyMember.MOTHER -> {
                this.names.add("none");
                this.names.add(_memberName);
            }
            case FamilyMember.CHILD -> {
                throw new IllegalArgumentException("Family without parents is not allowed");
            }default -> {
                throw new IllegalArgumentException("Family member " + _member + " is not supported for constructor");
            }
        }
    }

    public void addChild(String _kind)
    {
        if (names.size() < 2){
            throw new IllegalArgumentException("Family does not support children with or no parents");
        }

        names.add(_kind);
    }

    /**
     * Get a specific family member
     * @param _member The type of member to get
     * @return The member
     */
    public String getMember(FamilyMember _member){
        switch (_member){
            case FamilyMember.FATHER -> {
                if(names.size() > 0 && names.get(0) != null){
                    return names.get(0);
                }else return "Family has no father";
            }
            case FamilyMember.MOTHER -> {
                if(names.size() > 1 && names.get(1) != null){
                    return names.get(1);
                }else return "Family has no mother";
            }
            case FamilyMember.CHILD -> {
                if(names.size() > 2){
                    StringBuilder sb = new StringBuilder();
                    for(int i = 2; i < names.size(); i++)
                    {
                        sb.append(names.get(i)).append(", ");
                    }
                    sb.delete(sb.length() - 2, sb.length());
                    return sb.toString();
                }else return "";
            }default -> {
                return "Unsupported family member type";
            }

        }
    }

    @Override
    public Iterator<String> iterator() {
        return new FamilyIterator();
    }

    private class FamilyIterator implements Iterator<String>{
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < names.size();
        }

        @Override
        public String next() {
            return names.get(index) + " (Class internal hash code: " + System.identityHashCode(names.get(index++)) + ")";
        }
    }
}