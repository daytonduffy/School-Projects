/** Class to represent a camper as an object. Implements the comparable interface
 * to allow us to compare them to each other.
 * @author Michelle Jensen 
 */

public class Camper implements Comparable<Camper> {
	
	private String firstName;
	private String lastName;
	private int age;
	private String cabin;
	
	/** Constructor that sets the firstName, lastName, and age of an instance of the camper class.
	 * @param firstName, the first name of the camper
	 * @param lastName, the last name of the camper
	 * @param age, the age of the camper
	 * @throws IllegalArgumentException, if the age is outside of the range [8,14] (inclusive).
	 */
	public Camper (String firstName, String lastName, int age)
	{		
		if(age > 14 || age < 8)
			throw new IllegalArgumentException("This person is either too old or too young to be in Camp Badger.");
		
		this.firstName= firstName;
		this.lastName = lastName;
		this.age = age;
		this.cabin = "";
	}
	
	/**Accessor that returns both the first and last name of a Camper in the correct format. 
	 * @return the string formated as "lastName, firstName". Ex. "Badger, Bucky"
	 */
	public String getName()
	{return lastName + ", " + firstName;}
	
	/** Getter for age field.
	 *  @return The age of this Camper.
	 */
	public int getAge()
	{return age;};
	
	/** Getter for firstName field.
	 * @ return The firstName of this Camper.  
	 */
	public String getFirstName()
	{return firstName;}
	
	/** Getter for lastName field.
	 * @return The lastName of this Camper. 
	 */
	public String getLastName()
	{return lastName;}
	
	/** Getter for cabin field.
	 * @return The cabin of this Camper.
	 */
	public String getCabin()
	{return cabin;}
	
	/** Mutator for cabin field.
	 *	@param The name of the cabin to be assigned to this Camper. 
	 */
	public void assignCabin(String cabin)
	{this.cabin=cabin;}
	
	/** Returns a string representation of this Camper.
	 * @return This instance of camper formatted as
	 * "<lastName>, <firstName> Age: <age> Cabin: <cabin>". Ex. "Bucky, Badger Age: 10 Cabin: Badger Bunkhouse"
	 */
	public String toString()
	{return lastName + ", "+ firstName + " Age: " + age + " Cabin: "+cabin;}

	/** Method required to use the Comparable interface. Compares based on "lastName, firstName" string lexigraphically.
	 * @return 0 if they are the same, a negative int (<0) if this Camper is less than the argument, a positive int (>0) if this Camper
	 * is greater than the argument.
	 * @author <Dayton Duffy, 9079428331>
	 */
	@Override
	public int compareTo(Camper camper) {
	    String thisName = getName();
	    String camperName = camper.getName();
	    int answer = thisName.compareTo(camperName);

	    return answer;
	}
}
