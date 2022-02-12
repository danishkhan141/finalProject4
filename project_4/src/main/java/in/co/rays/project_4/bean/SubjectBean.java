package in.co.rays.project_4.bean;

/**
 * Subject JavaBean encapsulates Subject attributes
 * 
 * @author Danish
 *
 */
public class SubjectBean extends BaseBean {

	private String subjectName;

	private String description;

	private long courseId;

	private String courseName;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return subjectName;
	}

}
