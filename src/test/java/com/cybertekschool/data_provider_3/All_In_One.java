package com.cybertekschool.data_provider_3;

import org.junit.Test;

import java.io.IOException;

public class All_In_One {

	@Test
	public void allInOne() throws IOException, InterruptedException {

		//first, check the quiz/assignments
		Quizz_Assignment_Check_EU6_and_EU7_Together_3 quizz_assignment_check_eu6_and_eu7_together_3 = new Quizz_Assignment_Check_EU6_and_EU7_Together_3();
		quizz_assignment_check_eu6_and_eu7_together_3.quizAssignmentCheck();

		//second, check the recordings
		Recordings_EU6_and_EU7_Together_3 recordings_eu6_and_eu7_together_3 = new Recordings_EU6_and_EU7_Together_3();
		recordings_eu6_and_eu7_together_3.checkRecordings();

		//third, check the zoom attendances
		Zoom_Att_EU6_and_EU7_Together_3 zoom_att_eu6_and_eu7_together_3 = new Zoom_Att_EU6_and_EU7_Together_3();
		zoom_att_eu6_and_eu7_together_3.checkZoomAttendance();

	}


}
