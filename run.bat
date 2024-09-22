set profile=layer%1
java -Dspring.profiles.active=%profile% -jar target\tracing.jar
