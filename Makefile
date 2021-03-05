all: compile

compile:
	javac *.java

execute:
	java Main

clean:
	rm -f *.class *~
