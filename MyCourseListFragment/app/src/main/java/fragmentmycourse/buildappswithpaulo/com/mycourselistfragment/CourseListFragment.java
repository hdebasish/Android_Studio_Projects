package fragmentmycourse.buildappswithpaulo.com.mycourselistfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fragmentmycourse.buildappswithpaulo.com.mycourselistfragment.Util.ScreenUtility;
import fragmentmycourse.buildappswithpaulo.com.mycourselistfragment.data.Course;
import fragmentmycourse.buildappswithpaulo.com.mycourselistfragment.data.CourseArrayAdapter;
import fragmentmycourse.buildappswithpaulo.com.mycourselistfragment.data.CourseData;

/**
 * Created by paulodichone on 10/10/17.
 */

public class CourseListFragment extends ListFragment{
    List<Course> courses = new CourseData().courseList();
    private Callbacks activity;
    private CourseArrayAdapter adapter;

    public CourseListFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtility screenUtility = new ScreenUtility(getActivity());

        Log.d("WIDTH", String.valueOf(screenUtility.getDpWidth()));

         adapter = new CourseArrayAdapter(getActivity(),
                R.layout.course_listitem, courses);

        for (Course course: courses) {
            Log.d("Coursessssss===", course.getCourseName());
        }

        setListAdapter(adapter);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.course_list_fragment, container, false);

        return view;
    }

    public interface Callbacks {
         void onItemSelected(Course course, int position);
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Course course = courses.get(position);
        this.activity.onItemSelected(course, position );





//        Toast.makeText(getActivity(), "Name: " + course.getCourseName(), Toast.LENGTH_LONG)
//                .show();



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Callbacks) context;
    }
}
