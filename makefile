all: Principal.class Usuario.class App.class Rider.class

Principal.class: Principal.java Usuario.class App.class Rider.class
	javac Principal.java

Usuario.class: Usuario.java
	javac Usuario.java

App.class: App.java
	javac App.java

Rider.class: Rider.java
	javac Rider.java

clean:
	rm -f *.class