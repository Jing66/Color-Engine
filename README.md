## COLOR ENGINE USER GUIDE

### Start the program: Go to the task bar and double click the cmd icon.  

### After starting the program, you have the panel to make choices as follows: 

The left list shows all the available choices of indicators. The right list shows the selected indicators, including the ones from previous saving.

#### Functionalities:
•	Add indicators: 
Select the indicator on the left scroll list and click [ADD]. The pop-up window will ask you to choose “If Actual comes higher than Expected, do you want to sell or buy”. The bond/inverse attribute will be saved in the scope of this run once chosen. (Referring to the old color excel, choosing “sell” is equivalence to “bond”)

•	Remove the indicators: 
Select the indicator on the right scroll list and click [REMOVE]

•	Save the choices: 
click [SAVE], then the indicator as well as its bond/inverse attribute are saved. The next time you open the program, previous saved choices will be shown.

•	Run the program: 
After choosing indicators from the left list, make sure the right list is not empty, then click [GO].

•	Add an Indicator: 
If desired indicator is not on the left list, type its Index and its nickname to the input fields at the bottom and click [Add/Update Database]. Index needs to be directly copied from Bloomberg (aka, Bloomberg Index), otherwise the program cannot calculate its VAR. Nickname can be whatever you want it to be called and will show up as it is in the left list in the future. (For Example: [CPI CHNG Index] in first input field, [CPI MoM] in second). After hinted the database have been updated, the new indicator will show up at the bottom line.

•	Update the name of an indicator: 
Same steps as Adding Indicator. Put in the Bloomberg index and the name you want it to show up as, and it will update the new nickname, placed at the original line.
 
•	Update VAR (do it once a year): 
If after a year you want to update the VAR. Empty the two input fields at the bottom and click [Add/Update Database].

### Each indicator will generate bars like the following:

The first line shows the name of the indicator and the choice of “bond” or “Inverse”. 
The second line has editable field of Expectation, Var. After the number comes out, MVar will also show up. 
If “bond”, higher than expectation = sell (red to right); lower than expectation = buy (blue on left). 
If “Inverse”, higher than expectation = buy (blue on left); lower than expectation = sell (red on left).

#### Functionalities:
•	Change Expectation: 
The input in the middle, by default, is the expectation number fetched from Bloomberg. You can change it to whatever number you want, and press [Enter] on keyboard. (The change will have no effect if [Enter] is not pressed).
•	Change VAR: 
By default the VAR is calculated using past 10 years data. Change it the same way as changing Expectations and press [Enter].
•	Adjust the size: 
You can use the arrows at the right side of the bar to adjust the width of the box, according to its importance.

### Some Final Notes:
•	If [GO] is clicked over 20min prior to the data releasing time, the program might close automatically. Recommendation: Save every time when preparing, and open it no longer than 20min before the data releasing time.

•	If adding the index keeps failing, the steps to add indicators manually: 
Go to folder:  `Desktop/JingyLiu/db/. `
1) Open indices.csv, insert a line, fill in cells as the first line indicated (the first cell is Index from Bloomberg and the second cell is its name) 
2) Either run [update database] as indicated above, OR manually do:  
3) Open vars.csv, insert a line, fill in the cells as the first line indicated.

#### Explanation of the file system (where everything is saved if you want to change things manually):
In `Desktop/JingyLiu/db/` you will see the following files:
1.	indices.csv: This is where all the indicators of left list come from. 
2.	vars.csv: This is an excel file run by an R script to generate the VAR for all the indicators shown in Indices.csv. To run the R-script directly, open Windows Command Line and copy this line: 
`"C:\Program Files\R\R-3.3.1\bin\x64\Rscript.exe" "C:/Users/windows7/Desktop/JingyLiu/db/getVar.r"`
Make sure vars.csv is closed when you execute this command.
3.	saved.csv: This is the file where all your choices and bond/inverse attribute are saved. 
4.	getVar.r: This is the R script that generates vars.csv. To run the script in its environment, open R-Studio

The executable program file is `Desktop/JingyLiu/Colors.jar`
### DON’T MOVE THOSE FILES MENTIONED ABOVE, OTHERWISE NOTHING WILL WORK!

### Contact
If there are problems cannot be resolved with the help of this guide, please contact: jingyun.liu@mail.mcgill.ca
