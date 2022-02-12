package in.co.rays.project_4.bean;

import java.util.Date;

/**
 * Student JavaBean encapsulates Student attributes
 *
 * @author Danish
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

public class StudentBean extends BaseBean {

    /**
     * First Name of Student
     */
    private String firstName;
    /**
     * Last Name of Student
     */
    private String lastName;
    /**
     * Date of Birth of Student
     */
    private Date dob;
    /**
     * Mobileno of Student
     */
    private String mobileNo;
    /**
     * Email of Student
     */
    private String email;
    /**
     * CollegeId of Student
     */
    private long collegeId;
    /**
     * College name of Student
     */
    private String collegeName;

    /**
     * accessor
     */

   
    public String getKey() {
        return id + "";
    }

    /**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the collegeId
	 */
	public long getCollegeId() {
		return collegeId;
	}

	/**
	 * @param collegeId the collegeId to set
	 */
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	/**
	 * @return the collegeName
	 */
	public String getCollegeName() {
		return collegeName;
	}

	/**
	 * @param collegeName the collegeName to set
	 */
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

    public String getValue() {
        return firstName + " " + lastName;
    }

}

