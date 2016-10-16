# Color-Engine
This is a java application built for traders. It utilizes Bloomberg API to provide traders with a visualized real-time data monitoring.


## Getting Started

### Prerequisities

Java8 required, R required, Bloomberg API required

In your ~/db, file ```Indices.csv``` which lists all the Macroeconomics Indicators that you want to run on.

### Run the database update

```
source ('~/colors/getVar.r')
```

## Run the Color Engine

``` javac colors/ColorChoice.java
	java colors.ColorChoice
```

## Dependencies

### Source Dependencies
	
	Bloomberg BLPAPI (tested with 3.8.7.2) (MIT-like License)

## Issues

If you find a bug or want a new feature you can contact jingyunLiu@ocram.com