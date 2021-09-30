package com.cybertekschool.data_provider;

import org.junit.Test;

import java.io.IOException;

public class All_In_One {

	@Test
	public void allInOne() throws IOException, InterruptedException {

		//first, check the quiz/assignments
		Quizz_Assignment_Check quizz_assignment_check_eu6_and_eu7_together_3 = new Quizz_Assignment_Check();
		quizz_assignment_check_eu6_and_eu7_together_3.quizAssignmentCheck();

		//second, check the recordings
		Recordings_Check recordings_eu6_and_eu7_together_3 = new Recordings_Check();
		recordings_eu6_and_eu7_together_3.checkRecordings();

		//third, check the zoom attendances
		Zoom_Attendances_Check zoom_att_eu6_and_eu7_together_3 = new Zoom_Attendances_Check();
		zoom_att_eu6_and_eu7_together_3.checkZoomAttendance();

	}


}
