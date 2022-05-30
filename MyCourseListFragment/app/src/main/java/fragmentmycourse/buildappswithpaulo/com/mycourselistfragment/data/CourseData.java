package fragmentmycourse.buildappswithpaulo.com.mycourselistfragment.data;

import java.util.ArrayList;

/**
 * Created by paulodichone on 10/10/17.
 */

public class CourseData {

    private String[] courseNames = {"First Course", "Second Course ", "Third Course", "Fourth Course",
            "Fifth Course", "Sixth Course", "Seventh Course"};

    public Arra yList<Course> courseList() {
        ArrayList<Course> list = new ArrayList<>();

        for (int i = 0; i < courseNames.length; i++) {

            Course course = new Course(courseNames[i], courseNames[i].replace(" ", "").toLowerCase());

            list.add(course);

        }

        return list;
    }

}
