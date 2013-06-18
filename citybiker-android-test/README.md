RoboElectric requires a separated Java Project to be created in the same Eclipse workspace as main project. The Java project for tests here is named citybiker-android-test and it's stored in the root directory of citybiker-android. 

What HAS TO BE DONE (and it's not done yet) is to put eclipse-specified files (.classpath? .project?) into .gitignore list. But let others' download the current repo first.

To run tests:
1)  citybiker-android should be cloned to some directory. 
2)  then it can be imported to Eclipse. 
3) additionaly, Java (not Android!) project citybiker-android-test should be imported to Eclipse workspace as a separated project.
4) Steps from http://pivotal.github.io/robolectric/eclipse-quick-start.html should be performed to make it run :
	a) "Create a Java project for your tests" - additional source "citybiker-android/test/ should be added
	b) "Create a Java project for your tests" - dependency on citybiker-android should be set in citybiker-android-test
	c) "Configure build path" - JUnit library (ver4) should be added to citybiker-android-test from developers Eclipse (java?) SDK
	d) "Configure build path" - Android jars (android.jar and maps.jar) should be added from developers SDK repository
	e) "Create a test Run Configuration" - run configuration should be added.


citybiker-android-test 
