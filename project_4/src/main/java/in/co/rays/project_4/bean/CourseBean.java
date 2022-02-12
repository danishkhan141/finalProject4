package in.co.rays.project_4.bean;

/**
 Course JavaBean encapsulates Course attributes
 @author Danish Khan
*/
public class CourseBean extends BaseBean {
	private String name;

	/** Description of Course. */
	private String description;

	/** Duration of Course. */

	private String duration;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.bean.DropdownListBean#getKey()
	 */
	public String getKey() {
		return id + "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.bean.DropdownListBean#getValue()
	 */
	public String getValue() {
		return name;
	}

}
