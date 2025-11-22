package task_4;

/**
* Class representing a person
* @author lkoeble 21487
*/
public class Person {
    private String name;
    private String lastName;
    private String alias;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return name + " " + lastName + " - " + alias;
    }

    /**
     * Check if the person object has all necessary attributes
     * @return true or false
     */
    public boolean isComplete()
    {
        return (name != null && lastName != null && alias != null);
    }
}
