public class Student implements Comparable<Student> {
	String firstName, middleName, lastName;
	Long id;

	public Student(String f, String m, String l, long i) {
		this.firstName = f;
		this.middleName = m;
		this.lastName = l;
		this.id = i;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String toString() {
		return lastName + "," + " " + firstName + " " + middleName + " " + id;
	}

	public int compareTo(Student s) {
		if (lastName.compareTo(s.getLastName()) < 0)
			return -1;
		else if (lastName.compareTo(s.getLastName()) > 0)
			return 1;
		else
			return CompareMiddle(s);
	}

	public int CompareMiddle(Student s) {
		if (middleName.compareTo(s.getMiddleName()) < 0)
			return -1;
		else if (middleName.compareTo(s.getMiddleName()) > 0)
			return 1;
		else
			return CompareFirst(s);
	}

	public int CompareFirst(Student s) {
		if (firstName.compareTo(s.getFirstName()) < 0)
			return -1;
		else if (firstName.compareTo(s.getFirstName()) > 0)
			return 1;
		else
			return 0;
	}
}
