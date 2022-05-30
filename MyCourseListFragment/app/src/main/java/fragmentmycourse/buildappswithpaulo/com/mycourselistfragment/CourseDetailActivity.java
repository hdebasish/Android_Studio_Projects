package fragmentmycourse.buildappswithpaulo.com.mycourselistfragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import fragmentmycourse.buildappswithpaulo.com.mycourselistfragment.data.Course;
import fragmentmycourse.buildappswithpaulo.com.mycourselistfragment.data.CourseData;

public class CourseDetailActivity extends AppCompatActivity {

    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);


        if ( savedInstanceState == null) {


            Bundle extra = getIntent().getExtras();
            int position = extra.getInt("course_id");

            course = new CourseData().courseList().get(position);

            CourseDetailFragment fragment = new CourseDetailFragment();
            fragment.setArguments(extra);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.detailContainer, fragment)
                    .commit();

        }
    }
}
