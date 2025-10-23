package task_2;

import java.util.ArrayList;

public class Program{
    public static void main(String[] args) {
        Family family1 = new Family("Max", "Moritz");
        Family family2 = new Family("Hans", "Eva");
        Family family3 = new Family("Hans", FamilyMember.FATHER);
        Family family4 = new Family("Hans", FamilyMember.MOTHER);

        family3.addChild("Max");
        family4.addChild("Max");

        family1.addChild("Hand Gustav");
        family1.addChild("Sina Maria Brigitte");

        ArrayList<Family> families = new ArrayList<>();

        families.add(family1);
        families.add(family2);
        families.add(family3);
        families.add(family4);

        for (Family f : families) {
            for (FamilyMember familyMember : FamilyMember.values()) {
                System.out.println(f.toString() + " - MEMBER: " + familyMember + " - VALUE: " + f.getMember(familyMember));
            }
        }

        Family familyIT = new Family("Gustav", "Agarte");
        familyIT.addChild("child");

        for (String name : familyIT){
            System.out.println(name + " | " + System.identityHashCode(name));
            name = "sldknysdnv.snv";
        }

        for (String name : familyIT){
            System.out.println(name);
        }
    }
}
