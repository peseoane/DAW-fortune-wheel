JAVAC := javac
JAR := jar cfm

srcdir := src
sourcepath := $(srcdir)/main/java
main := $(sourcepath)/wof/gui/WheelOfFortuneFrame.java
manifest := MANIFEST.MF
bindir := bin
wof := wof.jar

.PHONY: all cleanbin clean

all:
	mkdir -p $(bindir)
	$(JAVAC) -d $(bindir) -sourcepath $(sourcepath) $(main)
	$(JAR) $(wof) $(manifest) -C $(bindir) . -C $(srcdir)/main resources

cleanbin:
	rm -rf $(bindir)

clean:
	rm -rf $(bindir) $(wof)
