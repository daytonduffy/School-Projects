javac -cp .:./junit-platform-console-standalone-1.5.2.jar *.java
java -XX:+FlightRecorder -XX:StartFlightRecording=settings=profile,filename=sampleprofile_data.jfr MyProfiler 10000000
